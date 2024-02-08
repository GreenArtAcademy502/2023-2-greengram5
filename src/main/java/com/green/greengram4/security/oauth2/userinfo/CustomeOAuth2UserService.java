package com.green.greengram4.security.oauth2.userinfo;

import com.green.greengram4.security.oauth2.SocialProviderType;
import com.green.greengram4.user.UserMapper;
import com.green.greengram4.user.model.UserEntity;
import com.green.greengram4.user.model.UserSelDto;
import com.green.greengram4.user.model.UserSignupProcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomeOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory factory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialProviderType socialProviderType
                    = SocialProviderType.valueOf(userRequest.getClientRegistration()
                                                            .getRegistrationId()
                                                            .toUpperCase()
                    );
        user.getAttributes();

        OAuth2UserInfo oAuth2UserInfo = factory.getOAuth2UserInfo(socialProviderType, user.getAttributes());
        UserSelDto dto = UserSelDto.builder()
                .providerType(socialProviderType.name())
                .uid(oAuth2UserInfo.getId())
                .build();
        UserEntity savedUser = mapper.selUser(dto);
        if(savedUser == null) { //회원가입 처리
            savedUser = signupUser(oAuth2UserInfo, socialProviderType);
        }

        return null;
    }

    private UserEntity signupUser(OAuth2UserInfo oAuth2UserInfo, SocialProviderType socialProviderType) {
        UserSignupProcDto dto = new UserSignupProcDto();
        dto.setProviderType(socialProviderType.name());
        dto.setUid(oAuth2UserInfo.getId());
        dto.setUpw("social");
        dto.setNm(oAuth2UserInfo.getName());
        dto.setPic(oAuth2UserInfo.getImageUrl());
        dto.setRole("USER");
        int result = mapper.insUser(dto);

        UserEntity entity = new UserEntity();
        entity.setIuser(dto.getIuser());
        entity.setRole(dto.getRole());
        return entity;
    }
}

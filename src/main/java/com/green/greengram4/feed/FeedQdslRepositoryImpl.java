package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.feed.model.FeedSelVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import com.green.greengram4.feed.model.FeedSelDto;
import java.util.List;
import java.util.stream.Collectors;

import static com.green.greengram4.entity.QFeedEntity.feedEntity;
import static com.green.greengram4.entity.QFeedFavEntity.feedFavEntity;
import static com.green.greengram4.entity.QUserEntity.userEntity;

@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FeedEntity> selFeedAll(FeedSelDto dto, Pageable pageable) {

        JPAQuery<FeedEntity> jpaQuery = jpaQueryFactory.select(feedEntity)
                                                        .from(feedEntity)
                                                        .join(feedEntity.userEntity).fetchJoin()
                                                        .orderBy(feedEntity.ifeed.desc())
                                                        .offset(pageable.getOffset())
                                                        .limit(pageable.getPageSize());

        if(dto.getIsFavList() == 1) {
            jpaQuery.join(feedFavEntity)
                    .on(feedEntity.ifeed.eq(feedFavEntity.feedEntity.ifeed)
                        , feedFavEntity.userEntity.iuser.eq(dto.getLoginedIuser()));
        } else {
            jpaQuery.where(whereTargetUser(dto.getTargetIuser()));
        }

        return jpaQuery.fetch();

//        return feedList.stream().map(item ->
//                        FeedSelVo.builder()
//                                .ifeed(item.getIfeed().intValue())
//                                .location(item.getLocation())
//                                .contents(item.getContents())
//                                .createdAt(item.getCreatedAt().toString())
//                                .writerIuser(item.getUserEntity().getIuser().intValue())
//                                .writerNm(item.getUserEntity().getNm())
//                                .writerPic(item.getUserEntity().getPic())
//                                .pics(item.getFeedPicsEntityList().stream().map(pic ->
//                                        pic.getPic()).collect(Collectors.toList()))
//                                .isFav(item.getFeedFavList().stream().anyMatch(fav ->
//                                        fav.getUserEntity().getIuser() == dto.getLoginedIuser()
//                                ) ? 1 : 0)
//                                .build())
//                .collect(Collectors.toList());



    }

    private BooleanExpression whereTargetUser(long targetIuser) {
        return targetIuser == 0 ? null : feedEntity.userEntity.iuser.eq(targetIuser);
    }
}

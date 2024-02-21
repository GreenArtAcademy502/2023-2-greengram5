package com.green.greengram4.feed;

import com.green.greengram4.entity.FeedEntity;
import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.feed.model.FeedSelVo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.print.Pageable;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class FeedQdslRepositoryImpl implements FeedQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FeedSelVo> selFeedAll(Long loginIuser, UserEntity userEntity, Pageable pageable) {
        List<FeedEntity> list = jpaQueryFactory.select(feedEntity);
        return null;
    }
}

package com.green.greengram4.feed;

import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.feed.model.FeedSelVo;

import java.awt.print.Pageable;
import java.util.List;

public interface FeedQdslRepository {
    List<FeedSelVo> selFeedAll(Long loginIuser, UserEntity userEntity, Pageable pageable);
}

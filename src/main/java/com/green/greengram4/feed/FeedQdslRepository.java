package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedSelDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FeedQdslRepository {
    List<FeedSelVo> selFeedAll(FeedSelDto dto, Pageable pageable);
}

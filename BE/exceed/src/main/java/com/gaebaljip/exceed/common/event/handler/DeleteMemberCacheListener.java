package com.gaebaljip.exceed.common.event.handler;

import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.gaebaljip.exceed.adapter.out.redis.RedisUtils;
import com.gaebaljip.exceed.common.RedisScanPattern;
import com.gaebaljip.exceed.common.event.DeleteMemberEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteMemberCacheListener {

    private final RedisUtils redisUtils;

    @TransactionalEventListener(classes = DeleteMemberEvent.class)
    public void handle(DeleteMemberEvent event) {
        String pattern =
                RedisScanPattern.getPrefixAnalysisPattern(
                        String.valueOf(event.getMemberEntity().getId()));
        Set<String> keys = redisUtils.scanKeys(pattern, 10);
        redisUtils.deleteDateWithPipeline(keys);
    }
}

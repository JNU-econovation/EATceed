package com.gaebaljip.exceed.config;

import static com.gaebaljip.exceed.common.RedisKeys.ANALYSIS_CACHE_NAME;
import static com.gaebaljip.exceed.common.RedisKeys.ANALYSIS_CACHE_TTL;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) ->
                builder.withCacheConfiguration(
                        ANALYSIS_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .computePrefixWith(cacheName -> cacheName + "::")
                                .entryTtl(Duration.ofMillis(ANALYSIS_CACHE_TTL))
                                .disableCachingNullValues()
                                .serializeKeysWith(
                                        RedisSerializationContext.SerializationPair.fromSerializer(
                                                new StringRedisSerializer()))
                                .serializeValuesWith(
                                        RedisSerializationContext.SerializationPair.fromSerializer(
                                                new StringRedisSerializer())));
    }
}

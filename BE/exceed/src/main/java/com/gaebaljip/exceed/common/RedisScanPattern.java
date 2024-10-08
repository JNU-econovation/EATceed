package com.gaebaljip.exceed.common;

import static com.gaebaljip.exceed.common.RedisKeys.NOW_ANALYSIS_CACHE_NAME;

import java.time.LocalDate;

public class RedisScanPattern {
    public static String getAnalysisPattern(LocalDate now) {
        return NOW_ANALYSIS_CACHE_NAME + "*_" + now.toString().substring(0, 8) + "*";
    }

    public static String getPrefixAnalysisPattern(String prefix) {
        return NOW_ANALYSIS_CACHE_NAME + prefix + "_*";
    }
}

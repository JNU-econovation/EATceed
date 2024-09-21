package com.gaebaljip.exceed.common;

import static com.gaebaljip.exceed.common.RedisKeys.ANALYSIS_CACHE_KEY;

import java.time.LocalDate;

public class RedisScanPattern {
    public static String getAnalysisPattern(LocalDate now) {
        return ANALYSIS_CACHE_KEY + "*_" + now.toString().substring(0, 8) + "*";
    }

    public static String getPrefixAnalysisPattern(String prefix) {
        return ANALYSIS_CACHE_KEY + prefix + "_*";
    }
}

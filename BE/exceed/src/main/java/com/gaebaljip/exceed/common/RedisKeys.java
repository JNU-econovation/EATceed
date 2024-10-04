package com.gaebaljip.exceed.common;

public class RedisKeys {
    public static final String AUTO_COMPLETE_KEY = "autoComplete";
    public static final String NOW_ANALYSIS_CACHE_KEY = "nowAnalysis::";
    public static final String PAST_ANALYSIS_CACHE_KEY = "pastAnalysis::";
    public static final String PAST_ANALYSIS_CACHE_NAME = "pastAnalysis";
    public static final String NOW_ANALYSIS_CACHE_NAME = "nowAnalysis";
    public static final Long PAST_ANALYSIS_CACHE_TTL = 259200L; // 3일
    public static final Long NOW_ANALYSIS_CACHE_TTL = 1440L; // 1일
}

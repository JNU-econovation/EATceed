package com.gaebaljip.exceed

enum class ActiveTypeEnum(val krName: String, val engName: String) {
    NOT_ACTIVE("활동 안함", "NOT_ACTIVE"),
    LIGHTLY_ACTIVE("조금 움직임", "LIGHTLY_ACTIVE"),
    NORMAL_ACTIVE("보통", "NORMAL_ACTIVE"),
    VERY_ACTIVE("활동적임", "VERY_ACTIVE"),
    EXTREMELY_ACTIVE("매우 활동적임", "EXTREMELY_ACTIVE")
}
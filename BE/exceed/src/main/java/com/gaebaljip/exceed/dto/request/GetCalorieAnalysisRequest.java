package com.gaebaljip.exceed.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;

public record GetCalorieAnalysisRequest(
        @NotNull(message = "memberId을" + ValidationMessage.NOT_NULL) Long memberId,
        @NotNull(message = "날짜를 " + ValidationMessage.NOT_NULL) LocalDateTime date) {}

package com.gaebaljip.exceed.dto.request;

import com.gaebaljip.exceed.common.ValidationMessage;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record GetAnalysisRequest(@NotNull(message = "memberId을" + ValidationMessage.NOT_NULL) Long memberId,
                                 @NotNull(message = "날짜를 " + ValidationMessage.NOT_NULL) LocalDateTime date) {}

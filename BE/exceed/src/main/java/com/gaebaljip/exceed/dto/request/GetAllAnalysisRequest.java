package com.gaebaljip.exceed.dto.request;

import java.time.LocalDateTime;

public record GetAllAnalysisRequest(Long memberId, LocalDateTime dateTime) {
    public static GetAllAnalysisRequest of(Long memberId, LocalDateTime dateTime) {
        return new GetAllAnalysisRequest(memberId, dateTime);
    }
}

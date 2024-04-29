package com.gaebaljip.exceed.dto.request;

import java.time.LocalDateTime;

public record GetAnalysisRequest(Long memberId, LocalDateTime date) {}

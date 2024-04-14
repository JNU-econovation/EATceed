package com.gaebaljip.exceed.dto.request;

import java.time.LocalDate;

public record GetAnalysisRequest(Long memberId, LocalDate date) {}

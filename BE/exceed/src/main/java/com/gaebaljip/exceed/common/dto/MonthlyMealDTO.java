package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;

public record MonthlyMealDTO(
        @NotNull(message = "memberId을 " + ValidationMessage.NOT_NULL) Long memberId,
        @NotNull(message = "날짜를 " + ValidationMessage.NOT_NULL) LocalDate date) {}

package com.gaebaljip.exceed.application.port.in.nutritionist;

import java.time.LocalDate;

import com.gaebaljip.exceed.common.annotation.UseCase;

@UseCase
public interface ValidateSignUpBeforeMonthUsecase {

    void execute(Long memberId, LocalDate targetDate);
}

package com.gaebaljip.exceed.application.port.in.meal;

import java.time.LocalDateTime;

import com.gaebaljip.exceed.common.annotation.UseCase;

@UseCase
public interface ValidateBeforeSignUpDateUsecase {
    void execute(Long memberId, LocalDateTime dateTime);
}

package com.gaebaljip.exceed.application.port.in.member;

import com.gaebaljip.exceed.common.annotation.UseCase;

@UseCase
public interface UpdateCheckedUsecase {
    void execute(String email);
}

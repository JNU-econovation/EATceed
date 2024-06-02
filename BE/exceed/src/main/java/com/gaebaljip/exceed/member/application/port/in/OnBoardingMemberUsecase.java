package com.gaebaljip.exceed.member.application.port.in;

import org.springframework.stereotype.Component;

@Component
public interface OnBoardingMemberUsecase {
    void execute(OnBoardingMemberCommand command);
}

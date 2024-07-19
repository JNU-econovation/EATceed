package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

@Component
public interface OnBoardingMemberUsecase {
    void execute(OnBoardingMemberCommand command);

    boolean checkOnBoarding(OnBoardingMemberQuery query);
}

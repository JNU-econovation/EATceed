package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.response.OnBoardingMember;
import org.springframework.stereotype.Component;

@Component
public interface OnBoardingMemberUsecase {
    void execute(OnBoardingMemberCommand command);

}



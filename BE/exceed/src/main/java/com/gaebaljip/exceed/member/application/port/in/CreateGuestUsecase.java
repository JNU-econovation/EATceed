package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.response.CreateGuestResponse;
import org.springframework.stereotype.Component;

@Component
public interface CreateGuestUsecase {
    CreateGuestResponse execute(CreateMemberCommand command);

}



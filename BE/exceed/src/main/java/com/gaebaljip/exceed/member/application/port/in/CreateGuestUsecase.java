package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.response.CreateGuest;
import org.springframework.stereotype.Component;

@Component
public interface CreateGuestUsecase {
    CreateGuest execute(CreateMemberCommand command);

}



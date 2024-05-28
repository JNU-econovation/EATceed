package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.UpdateWeightResponse;
import org.springframework.stereotype.Component;

@Component
public interface UpdateWeightUsecase {

    UpdateWeightResponse execute(UpdateWeightCommand command);
}

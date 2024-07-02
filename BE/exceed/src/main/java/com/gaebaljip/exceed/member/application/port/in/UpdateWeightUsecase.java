package com.gaebaljip.exceed.member.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.UpdateWeightResponse;

@Component
public interface UpdateWeightUsecase {

    UpdateWeightResponse execute(UpdateWeightCommand command);
}

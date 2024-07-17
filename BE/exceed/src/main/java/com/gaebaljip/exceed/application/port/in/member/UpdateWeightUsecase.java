package com.gaebaljip.exceed.application.port.in.member;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.member.response.UpdateWeightResponse;

@Component
public interface UpdateWeightUsecase {

    UpdateWeightResponse execute(UpdateWeightCommand command);
}

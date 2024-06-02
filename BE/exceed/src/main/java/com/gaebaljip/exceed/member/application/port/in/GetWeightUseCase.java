package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.response.GetWeightResponse;

public interface GetWeightUseCase {
    GetWeightResponse execute(Long memberId);
}

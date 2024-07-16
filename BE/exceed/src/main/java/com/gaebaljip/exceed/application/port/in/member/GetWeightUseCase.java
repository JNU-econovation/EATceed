package com.gaebaljip.exceed.application.port.in.member;

import com.gaebaljip.exceed.dto.response.GetWeightResponse;

public interface GetWeightUseCase {
    GetWeightResponse execute(Long memberId);
}

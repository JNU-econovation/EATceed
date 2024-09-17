package com.gaebaljip.exceed.application.port.in.member;

import com.gaebaljip.exceed.adapter.in.member.response.GetWeightDTO;

public interface GetWeightUseCase {
    GetWeightDTO execute(Long memberId);
}

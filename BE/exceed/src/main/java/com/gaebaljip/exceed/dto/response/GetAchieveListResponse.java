package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

import java.util.List;

public record GetAchieveListResponse(List<GetAchieve> getAchieve) {

    @Builder
    public GetAchieveListResponse {
    }
}

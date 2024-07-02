package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record FoodDTO(Long id, String name) {

    @Builder
    public FoodDTO {}
}

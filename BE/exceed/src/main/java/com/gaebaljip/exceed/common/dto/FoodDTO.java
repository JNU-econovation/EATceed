package com.gaebaljip.exceed.common.dto;

import lombok.Builder;

public record FoodDTO(Long id, String name) {

    @Builder
    public FoodDTO {}
}

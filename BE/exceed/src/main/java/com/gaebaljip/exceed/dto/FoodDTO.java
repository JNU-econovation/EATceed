package com.gaebaljip.exceed.dto;

import lombok.Builder;

public record FoodDTO(Long id, String name) {

    @Builder
    public FoodDTO {}
}

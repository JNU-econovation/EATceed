package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record Food(Long id, String name) {

    @Builder
    public Food {}
}

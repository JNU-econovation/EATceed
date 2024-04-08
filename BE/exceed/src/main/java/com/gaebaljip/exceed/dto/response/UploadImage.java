package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record UploadImage(Long memberId, Long mealId, String fileName) {
    @Builder
    public UploadImage {}
}

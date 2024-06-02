package com.gaebaljip.exceed.meal.application.port.in;

import lombok.Builder;

@Builder
public record UploadImageCommand(Long memberId, Long mealId, String fileName) {

    public static UploadImageCommand of(Long memberId, Long mealId, String fileName) {
        return UploadImageCommand.builder()
                .mealId(mealId)
                .memberId(memberId)
                .fileName(fileName)
                .build();
    }
}

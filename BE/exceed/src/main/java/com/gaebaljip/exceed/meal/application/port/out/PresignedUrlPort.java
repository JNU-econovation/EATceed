package com.gaebaljip.exceed.meal.application.port.out;

import org.springframework.stereotype.Component;

@Component
public interface PresignedUrlPort {
    String query(Long mealId, Long memberId);

    String command(Long memberId, Long mealId, String fileName);
}

package com.gaebaljip.exceed.meal.application.port.in;

import org.springframework.stereotype.Component;

@Component
public interface GetPreSignedUrlUsecase {

    void execute(Long memberId, Long mealId);
}

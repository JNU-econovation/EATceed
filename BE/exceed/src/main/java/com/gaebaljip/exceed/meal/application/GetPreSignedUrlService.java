package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.meal.application.port.in.GetPreSignedUrlUsecase;
import org.springframework.stereotype.Service;

@Service
public class GetPreSignedUrlService implements GetPreSignedUrlUsecase {
    @Override
    public void execute(Long memberId, Long mealId) {

    }
}

package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.TargetMeal;
import org.springframework.stereotype.Component;

@Component
public interface GetTargetMealUsecase {
    TargetMeal execute(Long memberId);
}

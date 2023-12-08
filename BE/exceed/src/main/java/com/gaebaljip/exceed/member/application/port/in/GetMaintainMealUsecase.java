package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.dto.MaintainMeal;
import org.springframework.stereotype.Component;

@Component
public interface GetMaintainMealUsecase {
    MaintainMeal execute(Long memberId);
}

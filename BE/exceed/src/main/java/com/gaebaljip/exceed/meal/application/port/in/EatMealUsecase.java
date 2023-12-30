package com.gaebaljip.exceed.meal.application.port.in;

import org.springframework.stereotype.Component;

@Component
public interface EatMealUsecase {

    Long execute(EatMealCommand command);
}

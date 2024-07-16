package com.gaebaljip.exceed.application.port.in.meal;

import org.springframework.stereotype.Component;

@Component
public interface EatMealUsecase {

    Long execute(EatMealCommand command);
}

package com.gaebaljip.exceed.food.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.GetFoodResponse;
import com.gaebaljip.exceed.dto.response.GetFoodsAutoResponse;

@Component
public interface GetFoodQuery {
    GetFoodsAutoResponse execute(String prefix);

    GetFoodResponse execute(Long foodId);
}

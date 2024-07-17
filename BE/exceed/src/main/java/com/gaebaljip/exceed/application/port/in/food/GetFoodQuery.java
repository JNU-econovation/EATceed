package com.gaebaljip.exceed.application.port.in.food;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.in.food.response.GetFoodResponse;
import com.gaebaljip.exceed.adapter.in.food.response.GetFoodsAutoResponse;

@Component
public interface GetFoodQuery {
    GetFoodsAutoResponse execute(String prefix);

    GetFoodResponse execute(Long foodId);
}

package com.gaebaljip.exceed.food.application.in;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.GetPageableFood;

@Component
public interface GetFoodQuery {
    Slice<GetPageableFood> execute(String lastFoodId, String keyword, int size);
}

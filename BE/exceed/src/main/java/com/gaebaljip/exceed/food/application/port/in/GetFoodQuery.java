package com.gaebaljip.exceed.food.application.port.in;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.GetFoodResponse;
import com.gaebaljip.exceed.dto.response.GetFoodsAutoResponse;
import com.gaebaljip.exceed.dto.response.GetPageableFoodDTO;

@Component
public interface GetFoodQuery {
    Slice<GetPageableFoodDTO> execute(String lastFoodId, String keyword, int size);

    GetFoodsAutoResponse execute(String prefix);

    GetFoodResponse execute(Long foodId);
}

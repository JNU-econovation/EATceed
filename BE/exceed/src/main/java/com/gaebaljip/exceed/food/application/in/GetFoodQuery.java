package com.gaebaljip.exceed.food.application.in;

import com.gaebaljip.exceed.dto.response.GetFood;
import com.gaebaljip.exceed.dto.response.GetPageableFood;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public interface GetFoodQuery {
    GetFood execute(Long foodId);

    Slice<GetPageableFood> execute(String lastFoodId, int size);
}

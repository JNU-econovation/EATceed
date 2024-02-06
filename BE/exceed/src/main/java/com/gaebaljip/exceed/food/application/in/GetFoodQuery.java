package com.gaebaljip.exceed.food.application.in;

import com.gaebaljip.exceed.dto.response.GetFood;
import com.gaebaljip.exceed.dto.response.GetPageableFood;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public interface GetFoodQuery {
    Slice<GetPageableFood> execute(String lastFoodId, String keyword, int size);
}

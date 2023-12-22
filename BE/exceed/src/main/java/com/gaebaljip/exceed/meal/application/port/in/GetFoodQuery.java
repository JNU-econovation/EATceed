package com.gaebaljip.exceed.meal.application.port.in;

import com.gaebaljip.exceed.dto.response.GetFood;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface GetFoodQuery {
    GetFood execute(Long memberId, LocalDate date);
}

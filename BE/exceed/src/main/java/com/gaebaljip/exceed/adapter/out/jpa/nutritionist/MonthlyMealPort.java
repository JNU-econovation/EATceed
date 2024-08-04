package com.gaebaljip.exceed.adapter.out.jpa.nutritionist;

import com.gaebaljip.exceed.application.domain.nutritionist.MonthlyMeal;
import com.gaebaljip.exceed.common.dto.MonthlyMealDTO;

public interface MonthlyMealPort {
    MonthlyMeal query(MonthlyMealDTO monthlyMealDTO);
}

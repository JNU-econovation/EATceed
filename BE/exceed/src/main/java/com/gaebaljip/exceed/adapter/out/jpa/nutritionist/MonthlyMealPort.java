package com.gaebaljip.exceed.adapter.out.jpa.nutritionist;

import com.gaebaljip.exceed.common.dto.MonthlyMealDTO;
import com.gaebaljip.exceed.common.dto.MonthlyMealRecordDTO;

public interface MonthlyMealPort {
    MonthlyMealRecordDTO query(MonthlyMealDTO monthlyMealDTO);
}

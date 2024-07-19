package com.gaebaljip.exceed.adapter.in.meal.response;

import com.gaebaljip.exceed.common.dto.CurrentMealDTO;
import com.gaebaljip.exceed.common.dto.MaintainMealDTO;
import com.gaebaljip.exceed.common.dto.TargetMealDTO;

public record GetMealResponse(
        MaintainMealDTO maintainMealDTO,
        TargetMealDTO targetMealDTO,
        CurrentMealDTO currentMealDTO) {

    public static GetMealResponse of(
            MaintainMealDTO maintainMealDTO,
            TargetMealDTO targetMealDTO,
            CurrentMealDTO currentMealDTO) {
        return new GetMealResponse(maintainMealDTO, targetMealDTO, currentMealDTO);
    }
}

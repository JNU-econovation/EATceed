package com.gaebaljip.exceed.common.dto.response;

import com.gaebaljip.exceed.common.dto.CurrentMealDTO;
import com.gaebaljip.exceed.common.dto.TargetMealDTO;
import com.gaebaljip.exceed.common.dto.MaintainMealDTO;

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

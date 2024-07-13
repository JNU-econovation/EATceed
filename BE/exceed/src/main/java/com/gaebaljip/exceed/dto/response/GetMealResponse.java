package com.gaebaljip.exceed.dto.response;

import com.gaebaljip.exceed.dto.CurrentMealDTO;
import com.gaebaljip.exceed.dto.MaintainMealDTO;
import com.gaebaljip.exceed.dto.TargetMealDTO;

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

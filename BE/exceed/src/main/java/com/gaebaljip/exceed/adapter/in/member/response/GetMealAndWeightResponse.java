package com.gaebaljip.exceed.adapter.in.member.response;

import com.gaebaljip.exceed.common.dto.CurrentMealDTO;
import com.gaebaljip.exceed.common.dto.MaintainMealDTO;
import com.gaebaljip.exceed.common.dto.TargetMealDTO;

public record GetMealAndWeightResponse(
        MaintainMealDTO maintainMealDTO,
        TargetMealDTO targetMealDTO,
        GetWeightDTO getWeightDTO,
        CurrentMealDTO currentMealDTO) {

    public static GetMealAndWeightResponse of(
            MaintainMealDTO maintainMealDTO,
            TargetMealDTO targetMealDTO,
            GetWeightDTO getWeightDTO,
            CurrentMealDTO currentMealDTO) {
        return new GetMealAndWeightResponse(
                maintainMealDTO, targetMealDTO, getWeightDTO, currentMealDTO);
    }
}

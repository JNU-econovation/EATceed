package com.gaebaljip.exceed.dto.response;

public record GetMealResponse(
        MaintainMealDTO maintainMealDTO, TargetMealDTO targetMealDTO, CurrentMealDTO currentMealDTO) {}

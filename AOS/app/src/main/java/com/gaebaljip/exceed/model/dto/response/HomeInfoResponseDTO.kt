package com.gaebaljip.exceed.model.dto.response

import com.gaebaljip.exceed.model.dto.response.common.CurrentMeal
import com.gaebaljip.exceed.model.dto.response.common.MaintainMeal
import com.gaebaljip.exceed.model.dto.response.common.TargetMeal

data class HomeInfoResponseDTO(
    val maintainMeal: MaintainMeal,
    val targetMeal: TargetMeal,
    val currentMeal: CurrentMeal
)
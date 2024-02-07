package com.gaebaljip.exceed.model.dto.response

import com.gaebaljip.exceed.model.dto.response.common.CurrentMeal
import com.gaebaljip.exceed.model.dto.response.common.MaintainMeal
import com.gaebaljip.exceed.model.dto.response.common.TargetMeal

data class HomeInfoResponseDTO(
    val maintainMeal: HomeMealInfo,
    val targetMeal: HomeMealInfo,
    val currentMeal: HomeMealInfo
)

data class HomeMealInfo(
    val calorie : Double,
    val carbohydrate : Double,
    val protein : Double,
    val fat : Double
)
package com.gaebaljip.exceed.model.dto.request

data class OnboardingRequestDTO(
    val height: Double,
    val gender: Boolean,
    val weight: Double,
    val age: Int,
    val activity: String,
    val etc: String?
)
package com.gaebaljip.exceed.model.dto.response.common

data class CommonResponseDTO<T>(
    val success: Boolean,
    val response: T?,
    val error: ErrorStatusDTO?
)

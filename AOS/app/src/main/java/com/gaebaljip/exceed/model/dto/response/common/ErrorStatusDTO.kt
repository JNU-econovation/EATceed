package com.gaebaljip.exceed.model.dto.response.common

data class ErrorStatusDTO(
    val code: String,
    val message: String,
    val status: String
)
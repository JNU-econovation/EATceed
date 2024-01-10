package com.gaebaljip.exceed.screens.home

import androidx.lifecycle.ViewModel
import com.gaebaljip.exceed.model.dto.response.HomeInfoResponseDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _homeInfoState = MutableStateFlow<HomeInfoResponseDTO?>(null)
    val homeInfoState : StateFlow<HomeInfoResponseDTO?>
        get() = _homeInfoState

}
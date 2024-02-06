package com.gaebaljip.exceed.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaebaljip.exceed.model.dto.response.HomeInfoResponseDTO
import com.gaebaljip.exceed.model.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val mainRepository = MainRepository
    private val _homeInfoState = MutableStateFlow<HomeInfoResponseDTO?>(null)
    val homeInfoState : StateFlow<HomeInfoResponseDTO?>
        get() = _homeInfoState

    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = mainRepository.getHomeInfo()

            if(result!=null){
                _homeInfoState.update {
                    result
                }
            }
        }
    }

}
package com.gaebaljip.exceed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaebaljip.exceed.model.dto.request.ChattingRequestDTO
import com.gaebaljip.exceed.model.dto.response.ChattingResponseDTO
import com.gaebaljip.exceed.model.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {
    private val mainRepository = MainRepository

    private val _chatResponseData = MutableStateFlow<ChattingResponseDTO?>(null)
    val chatResponseData: StateFlow<ChattingResponseDTO?>
        get() = _chatResponseData

    private val _chatRequestData = MutableLiveData<Boolean?>(null)
    val chatRequestData: LiveData<Boolean?>
        get() = _chatRequestData

    fun sendQuestionData(question: String) {

        viewModelScope.launch {
            val result = mainRepository.createApi2(
                data = ChattingRequestDTO(
                    question
                )
            )

            result.onSuccess {
                _chatRequestData.value = true
                _chatResponseData.value = it!!.response
            }.onFailure { _chatRequestData.value = false }
        }
    }
}
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

    private val _chatResponseData = MutableStateFlow<List<ChattingResponseDTO>>(emptyList())
    val chatResponseData: StateFlow<List<ChattingResponseDTO>>
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

            result.onSuccess {data ->
                _chatResponseData.update { it.toMutableList().apply { add(data!!) } }
            }.onFailure { _chatRequestData.value = false }
        }
    }
}
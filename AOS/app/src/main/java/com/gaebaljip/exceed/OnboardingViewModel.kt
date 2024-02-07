package com.gaebaljip.exceed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaebaljip.exceed.model.dto.request.OnboardingRequestDTO
import com.gaebaljip.exceed.model.repository.MainRepository
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {
    private val mainRepository = MainRepository

    private val _onboardInfo = MutableLiveData<Boolean?>(null)
    val onboardInfo: LiveData<Boolean?>
        get() = _onboardInfo

    fun sendOnboardingData(
        height: Double,
        gender: Int,
        weight: Double,
        age: Int,
        activity: String,
        etc: String?
    ) {
        viewModelScope.launch {

            val result = mainRepository.createApi1(
                data = OnboardingRequestDTO(
                    height,
                    gender,
                    weight,
                    age,
                    activity,
                    etc
                )
            )

            result.onSuccess { _onboardInfo.value = true }.onFailure { _onboardInfo.value = false }
        }
    }
}
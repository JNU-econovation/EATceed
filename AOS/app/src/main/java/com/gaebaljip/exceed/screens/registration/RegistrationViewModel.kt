package com.gaebaljip.exceed.screens.registration

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.model.dto.request.FoodRegistrationRequestDTO
import com.gaebaljip.exceed.model.dto.response.FoodNameAndId
import com.gaebaljip.exceed.model.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    private val mainRepository = MainRepository

    private val _foodList = MutableStateFlow<List<FoodNameAndId>>(emptyList())
    val foodList : StateFlow<List<FoodNameAndId>>
        get() = _foodList

    private val _addedFoodList = MutableStateFlow<List<Pair<Int, FoodNameAndId>>>(emptyList())
    val addedFoodList : StateFlow<List<Pair<Int, FoodNameAndId>>>
        get() = _addedFoodList

    fun getNewData(){
        viewModelScope.launch(Dispatchers.IO){
            _foodList.update {
                it.toMutableList().apply {
                    addAll(mainRepository.getFoodListWith(foodList.value.lastOrNull()?.name, 20))
                }
            }
        }
    }

    fun addFood(item: FoodNameAndId) {
        _addedFoodList.update {
            it.toMutableList().apply {
                add(Pair((addedFoodList.value.lastOrNull()?.first ?: 0) + 1, item))
            }
        }
    }

    fun deleteFood(id: Int) {
        _addedFoodList.update {
            it.filter { it.first != id }
        }
    }

    suspend fun registerRequest(flex: Float, mealTypeEnum: MealTypeEnum, fileName: String) : String? {
        return mainRepository.registerRequest(FoodRegistrationRequestDTO(
            flex.toDouble(),
            addedFoodList.value.map { it.second.id },
            mealTypeEnum.name,
            fileName
        ))
    }

    suspend fun uploadFile(url: String, fileUri: Uri) : Boolean{
        return mainRepository.uploadFile(url, fileUri)
    }
}
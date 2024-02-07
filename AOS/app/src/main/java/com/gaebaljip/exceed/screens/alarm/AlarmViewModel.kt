package com.gaebaljip.exceed.screens.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.model.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AlarmViewModel : ViewModel() {
    private val mainRepository = MainRepository
    private val _dataList = MutableStateFlow<List<AlarmInfo>>(listOf())
    val dataList: StateFlow<List<AlarmInfo>>
        get() = _dataList

    private val _focusAlarm = MutableStateFlow<AlarmInfo?>(null)
    val focusAlarm: StateFlow<AlarmInfo?>
        get() = _focusAlarm

    fun getAllAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            _dataList.update {
                mainRepository.getAllAlarm()
                    .sortedWith(compareBy<AlarmInfo> { it.hour }.thenBy { it.minute })
            }
        }
    }

    fun changeFocus(item: AlarmInfo?) {
        _focusAlarm.update {
            item
        }
    }

    fun deleteAlarm() {
        _dataList.update {
            it.filter { alarm ->
                alarm != focusAlarm.value
            }
        }
        val focusAlarmID = requireNotNull(focusAlarm.value).id
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteAlarm(focusAlarmID)
        }
    }

    fun changeOrUpdateAlarm(hour: Int, minute: Int, weekNum: Int, mealTypeEnum: MealTypeEnum) {
        if (focusAlarm.value == null) {
            val alarm = AlarmInfo(
                id = UUID.randomUUID().toString(),
                hour = hour,
                minute = minute,
                mealType = mealTypeEnum,
                weekFlag = weekNum,
                isEnabled = true,
                imgDate = "",
                imgString = null
            )

            _dataList.update {
                it.toMutableList().apply {
                    add(alarm)
                    sortWith(compareBy<AlarmInfo> { it.hour }.thenBy { it.minute })
                }
            }
            viewModelScope.launch(Dispatchers.IO) {
                mainRepository.insertAlarm(
                    alarm
                )
            }
        } else {
            _dataList.update {
                it.map {
                    if (it == focusAlarm.value) {
                        it.copy(
                            hour = hour,
                            minute = minute,
                            mealType = mealTypeEnum,
                            weekFlag = weekNum,
                        )
                    } else {
                        it
                    }
                }.sortedWith(compareBy<AlarmInfo> { it.hour }.thenBy { it.minute })
            }

            val focusAlarmID = requireNotNull(focusAlarm.value).id
            viewModelScope.launch(Dispatchers.IO) {
                mainRepository.updateAlarm(
                    focusAlarmID,
                    hour,
                    minute,
                    weekNum,
                    mealTypeEnum
                )
            }
        }
    }

}
package com.gaebaljip.exceed.screens.alarm

import androidx.lifecycle.ViewModel
import com.gaebaljip.exceed.MealTypeEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class AlarmViewModel : ViewModel() {
    private val _dataList = MutableStateFlow<List<AlarmInfo>>(listOf())
    val dataList : StateFlow<List<AlarmInfo>>
        get() = _dataList

    private val _focusAlarm = MutableStateFlow<AlarmInfo?>(null)
    val focusAlarm : StateFlow<AlarmInfo?>
        get() = _focusAlarm

    fun changeFocus(item: AlarmInfo?) {
        _focusAlarm.update {
            item
        }
    }

}
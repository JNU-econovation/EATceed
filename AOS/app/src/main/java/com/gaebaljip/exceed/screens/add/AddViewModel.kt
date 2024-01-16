package com.gaebaljip.exceed.screens.add

import androidx.lifecycle.ViewModel
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.screens.alarm.AlarmInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class AddViewModel : ViewModel() {
    private val _dataList = MutableStateFlow<List<AlarmInfo>>(listOf())
    val dataList : StateFlow<List<AlarmInfo>>
        get() = _dataList
}
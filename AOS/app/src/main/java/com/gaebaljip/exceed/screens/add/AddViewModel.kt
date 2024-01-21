package com.gaebaljip.exceed.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaebaljip.exceed.model.repository.MainRepository
import com.gaebaljip.exceed.screens.alarm.AlarmInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class AddViewModel : ViewModel() {
    private val mainRepository = MainRepository
    private val _dataList = MutableStateFlow<List<AlarmInfo>>(listOf())
    val dataList: StateFlow<List<AlarmInfo>>
        get() = _dataList

    fun getListCurrentWeek(imgDate: String) {
        val cal = Calendar.getInstance()

        val weekNum = 1 shl (cal.get(Calendar.DAY_OF_WEEK) - 1)
        viewModelScope.launch(Dispatchers.IO) {
            _dataList.update {
                mainRepository.getAlarmListByCurrentWeek(weekNum, imgDate)
            }
        }
    }

    fun updateAlarm(id: String, imgString: String, imgDate: String) {
        _dataList.update {
            it.map { item ->
                if (item.id == id) {
                    item.copy(
                        imgString = imgString,
                        imgDate = imgDate
                    )
                } else {
                    item
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.updateAlarmImage(id, imgDate, imgString)
        }
    }
}
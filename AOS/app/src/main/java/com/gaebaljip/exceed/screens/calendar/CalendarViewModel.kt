package com.gaebaljip.exceed.screens.calendar

import androidx.lifecycle.ViewModel
import com.gaebaljip.exceed.model.dto.response.CalendarAchieveInfoDTO
import com.gaebaljip.exceed.model.dto.response.CalendarInfoResponseDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.Month
import java.util.Calendar

class CalendarViewModel : ViewModel() {

    private val _calendarData = MutableStateFlow<List<CalendarAchieveInfoDTO?>>(emptyList())
    val calendarData : StateFlow<List<CalendarAchieveInfoDTO?>>
        get() = _calendarData

}

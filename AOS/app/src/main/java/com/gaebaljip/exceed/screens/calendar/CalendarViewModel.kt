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


    val currentYear: Int
    val currentMonth: Int

    init {
        val calendar = Calendar.getInstance()
        currentYear = calendar.get(Calendar.YEAR)
        currentMonth = calendar.get(Calendar.MONTH) + 1

    }

    fun getData() {
        val lastDayOfMonth = Calendar.getInstance().apply {
            set(Calendar.YEAR, currentYear)
            set(Calendar.MONTH, currentMonth -1)
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        }.get(Calendar.DAY_OF_MONTH)

        val initialData = List(lastDayOfMonth+1) {dayOfMonth ->
            CalendarAchieveInfoDTO(
                isVisited = false,
                date = "$currentYear-${String.format("%02d", currentMonth)}-${String.format("%02d", dayOfMonth + 1)}",
                calorieRate = 0.0,
                proteinAchieve = false,
                fatAchieve = false,
                carbohydrateAchieve = false
            )
        }

        _calendarData.update {
            initialData
        }
    }

}

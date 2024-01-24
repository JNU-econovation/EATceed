package com.gaebaljip.exceed.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaebaljip.exceed.model.dto.response.CalendarAchieveInfoDTO
import com.gaebaljip.exceed.model.dto.response.CalendarInfoResponseDTO
import com.gaebaljip.exceed.model.repository.MainRepository
import com.gaebaljip.exceed.model.repository.remote.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Month
import java.util.Calendar
import java.util.Date

class CalendarViewModel : ViewModel() {

    private val _calendarData = MutableStateFlow<List<CalendarAchieveInfoDTO?>>(emptyList())
    val calendarData: StateFlow<List<CalendarAchieveInfoDTO?>>
        get() = _calendarData

    val currentYear: Int
    val currentMonth: Int

    init {
        val calendar = Calendar.getInstance()
        currentYear = calendar.get(Calendar.YEAR)
        currentMonth = calendar.get(Calendar.MONTH) + 1

    }

    fun getData(moveCount: Int) {
        val currentY = if (moveCount < 0) {
            currentYear + (moveCount.div(12)) - 1
        } else {
            currentYear + (moveCount.div(12))
        }
        val currentM = currentMonth + (moveCount.mod(12))

//        val lastDayOfMonth = Calendar.getInstance().apply {
//            set(Calendar.YEAR, currentY)
//            set(Calendar.MONTH, currentM -1)
//            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH)  )
//        }.get(Calendar.DAY_OF_MONTH)
//
//        val initialData = List(lastDayOfMonth) {dayOfMonth ->
//            CalendarAchieveInfoDTO(
//                isVisited = false,
//                date = "$currentY-${String.format("%02d", currentM)}-${String.format("%02d", dayOfMonth + 1)}",
//                calorieRate = 0.0,
//                proteinAchieve = false,
//                fatAchieve = false,
//                carbohydrateAchieve = false
//            )
//        }
//
//        _calendarData.update {
//            initialData
//        }


        val currentDate = "$currentY-${String.format("%02d", currentM)}-${String.format("%02d", 1)}"

        viewModelScope.launch {
            val result = MainRepository.updateCalendarData(currentDate)
            result.let {

                val dateTest = (result!!.toMutableList().apply{add(0, (result[0].copy(date = "$currentY-${String.format("%02d", currentM)}-00")))}.toList())
                _calendarData.update { dateTest }
//                val dateTest = (result!!.toMutableList().add(0, (result[0].copy(date = "$currentY-${String.format("%02d", currentM)}-00")))) as List<CalendarAchieveInfoDTO>)

            }

        }
    }

}

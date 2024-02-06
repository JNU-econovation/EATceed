package com.gaebaljip.exceed.screens.calendar

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.model.dto.response.CalendarAchieveInfoDTO
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import com.gaebaljip.exceed.ui.theme.Typography
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import kotlin.math.round
import kotlin.time.Duration.Companion.days

@Composable
fun CalendarScreen(calendarViewModel: CalendarViewModel = viewModel()) {
    val calendarData by calendarViewModel.calendarData.collectAsStateWithLifecycle()

    var moveCount by remember { mutableIntStateOf(0) }

    val currentM = calendarViewModel.currentMonth + (moveCount.mod(12))
    val currentY = if (moveCount < 0) {
        calendarViewModel.currentYear + (moveCount.div(12)) - 1
    } else {
        calendarViewModel.currentYear + (moveCount.div(12))
    }
    val monthList = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    val monthName = monthList[currentM - 1]

    LaunchedEffect(moveCount) {
        calendarViewModel.getData(moveCount)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBF8))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(643.dp)
                .background(
                    Color(0xFFFDAC53),
                    shape = RoundedCornerShape(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(10.dp),
                        bottomEnd = CornerSize(10.dp)
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(top = 64.dp, bottom = 52.dp)
                    .background(Color.White, shape = RoundedCornerShape(5.dp))

            ) {
                Column(modifier = Modifier.padding(bottom = 7.dp)) {
                    Header(
                        currentMonth = currentM,
                        currentYear = currentY,
                        currentMonthName = monthName
                    )
                    CalendarLayout(
                        calendarData,
                        currentYear = currentY,
                        currentMonth = currentM,
                        moveCount = moveCount
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .offset(0.dp, 70.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = "이전",
                        tint = colorResource(id = R.color.alarm_button_stroke_color),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                moveCount--
                                calendarViewModel.getData(moveCount)

                            }
                    )

                    Spacer(modifier = Modifier.size(30.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.forward_icon),
                        contentDescription = "다음",
                        tint = colorResource(id = R.color.alarm_button_stroke_color),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                moveCount++
                                calendarViewModel.getData(moveCount)

                            }
                    )
                }
                LaunchedEffect(currentM, currentY) {
                    calendarViewModel.getData(moveCount)
                }
            }
        }
    }
}


@Composable
fun Header(
    currentMonth: Int,
    currentYear: Int,
    currentMonthName: String
) {
    Column(
        modifier = Modifier.padding(8.dp)

    ) {
        Text(
            text = "$currentYear",
            style = Typography.headlineSmall,
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Box(modifier = Modifier.size(53.dp), contentAlignment = Alignment.BottomCenter) {
                Text(
                    text = "$currentMonth",
                    textAlign = TextAlign.Center,
                    style = Typography.headlineLarge
                )
            }
            Text(
                text = "$currentMonthName",
                modifier = Modifier.padding(start = 8.dp, bottom = 3.dp),
                style = Typography.headlineMedium,
                color = colorResource(id = R.color.calendar_month_color)
            )
        }

        Text(
            text = "눌러서 이달의 목표를 적어보세요",
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp, start = 4.dp),
            style = Typography.displaySmall,
            color = colorResource(id = R.color.calendar_goal_color)
        )
    }
}

@Composable
fun CalendarLayout(

    state: List<CalendarAchieveInfoDTO?>,
    currentMonth: Int,
    currentYear: Int,
    moveCount: Int

) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val columns = 6
            val rows = 7
            val cellWidth = size.width / rows
            val cellHeight = size.height / columns

            drawRoundRect(
                Color.LightGray,
                cornerRadius = CornerRadius(
                    x = 10.dp.toPx(),
                    y = 10.dp.toPx()
                ),
                style = Stroke(
                    width = 2f
                )
            )

            for (row in 1 until columns) {
                val y = row * cellHeight
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1f
                )
            }

            for (col in 1 until rows) {
                val x = col * cellWidth
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = 1f
                )
            }
        }

        // 여기 값 변경
        CalendarItems(
            currentDate = LocalDate.of(
                "$currentYear".toInt(),
                "$currentMonth".toInt(),
                1
            ),

            state,
            currentYear = currentYear,
            currentMonth = currentMonth,
            moveCount = moveCount,
        )

    }

}

@Composable
fun CalendarItems(
    currentDate: LocalDate,
    state: List<CalendarAchieveInfoDTO?>,
    currentYear: Int,
    currentMonth: Int,
    moveCount: Int,
) {
    val dayOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
    var firstDayOfWeek by remember {
        mutableIntStateOf(
            YearMonth.from(currentDate.withDayOfMonth(1)).atDay(1).dayOfWeek.value
        )
    }
    var lastDay by remember {
        mutableIntStateOf(
            YearMonth.from(currentDate.withDayOfMonth(1)).lengthOfMonth()
        )
    }
    var currentMonthDays by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    firstDayOfWeek = YearMonth.from(currentDate.withDayOfMonth(1)).atDay(1).dayOfWeek.value
    lastDay = YearMonth.from(currentDate.withDayOfMonth(1)).lengthOfMonth()
    currentMonthDays = IntRange(1, lastDay).toList()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .height(63.5.dp),
            verticalArrangement = Arrangement.Center,
            userScrollEnabled = false

        ) {
            itemsIndexed(dayOfWeek) { index, day ->
                val textColor = when (index) {
                    0 -> colorResource(id = R.color.calendar_sunday_color)
                    dayOfWeek.lastIndex -> colorResource(id = R.color.calendar_saturday_color)
                    else -> Color.Black
                }
                Text(
                    text = "$day",
                    fontSize = 22.sp,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            userScrollEnabled = false

        ) {
            for (i in 0 until (firstDayOfWeek % 7)) {
                item {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(10.dp),
                    ) {

                    }
                }
            }
            items(currentMonthDays) { days ->

                val firstDayOfMonth = YearMonth.of(currentYear, currentMonth).atDay(1)
                val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)

                val date = if (days <= lastDayOfMonth.dayOfMonth) {
                    firstDayOfMonth.withDayOfMonth(days)
                } else {
                    // 해당 월의 마지막 일을 넘어가면 다음 달로 이동합니다.
                    firstDayOfMonth.plusMonths(1).withDayOfMonth(days - lastDayOfMonth.dayOfMonth)
                }

                CalendarDays(
                    date = date,
                    isToday = date == LocalDate.now(),
                    state,
                    moveCount

                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDays(
    date: LocalDate,
    isToday: Boolean,
    state: List<CalendarAchieveInfoDTO?>,
    moveCount: Int
) {
    val dayInt = date.dayOfMonth
    var isShow by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    if (isShow) {
        ModalBottomSheet(
            onDismissRequest = { isShow = false }, sheetState = sheetState
        ) {
            CalenderDetailBoard()
        }
    }
    Box(
        modifier = Modifier
            .size(63.3.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.clickable {
                isShow = true
            }
        ) {
            Spacer(modifier = Modifier.weight(1.2f))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.6f)
                        .align(Alignment.BottomStart)
                        .offset(0.dp, (-1).dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (state.getOrNull(dayInt)?.carbohydrateAchieve == true) {
                                    Color(0xB3FFBABA)
                                } else {
                                    Color(0x00FFFFFF)
                                },
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.6f)
                        .align(Alignment.BottomEnd)
                        .offset(0.dp, (-1).dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (state.getOrNull(dayInt)?.proteinAchieve == true) {
                                    Color(0xB3BDC0FF)
                                } else {
                                    Color(0x00FFFFFF)
                                },
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.6f)
                        .align(Alignment.TopCenter)
                        .offset(0.dp, 1.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (state.getOrNull(dayInt)?.fatAchieve == true) {
                                    Color(0xB3BEFFB4)
                                } else {
                                    Color(0x00FFFFFF)
                                },
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }

                Text(
                    text = date.dayOfMonth.toString(),
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.offset(0.dp, 1.dp)
                )

            }
            Spacer(modifier = Modifier.weight(1f))

            val achieveRate = state.getOrNull(dayInt)?.calorieRate?.let { round(it) }.toString()

            val achieveRateString = buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                    append(achieveRate)
                }
                withStyle(style = SpanStyle(fontSize = 10.sp)) {
                    append(" %")
                }
            }
            Text(
                text = achieveRateString,
                style = Typography.labelMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(.5f))

        }
    }
}

@Composable
fun CalenderDetailBoard() {
    Box(
        modifier = Modifier
            .height(400.dp)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExceedTheme {
        CalendarScreen()
    }
}
package com.gaebaljip.exceed.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import com.gaebaljip.exceed.ui.theme.Typography
import org.intellij.lang.annotations.JdkConstants.VerticalScrollBarPolicy
import java.time.LocalDate

@Composable
fun CalendarScreen() {
    ColorBox()
}

@Composable
fun ColorBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBF8))
    ) {
        Box(
            modifier = Modifier
//                .padding(bottom = 207.dp)
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
                CalendarLayout(
                    onDayClick = {},
                    modifier = Modifier,
                    month = "December"
                )

            }
        }
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier.padding(8.dp)

    ) {
        Text(
            text = "2024",
            style = Typography.headlineSmall,
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.BottomCenter) {
                Text(
                    text = "1",
                    textAlign = TextAlign.Center,
                    style = Typography.headlineLarge
                )
            }
            Text(
                text = "January",
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
    modifier: Modifier = Modifier,
    onDayClick: (Int) -> Unit,
    month: String

) {
    Column(
        modifier = Modifier.padding(bottom = 7.dp),
    ) {
        Header()

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

            CalendarItems(currentDate = LocalDate.of(2024, 1, 1))

        }
    }

}

@Composable
fun CalendarItems(
    currentDate: LocalDate,
) {
    val dayOfWeek = listOf<String>("S", "M", "T", "W", "T", "F", "S")
    val firstDayOfWeek by remember { mutableStateOf(currentDate.dayOfWeek.value) }
    val lastDay by remember { mutableStateOf(currentDate.lengthOfMonth()) }
    val currentMonthDays by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            verticalArrangement = Arrangement.Center,
            userScrollEnabled = false

        ) {
            itemsIndexed(dayOfWeek) { index, days ->
                val textColor = when (index) {
                    0 -> colorResource(id = R.color.calendar_sunday_color)
                    dayOfWeek.lastIndex -> colorResource(id = R.color.calendar_saturday_color)
                    else -> Color.Black
                }
                Text(
                    text = "$days",
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
            for (i in 0 until firstDayOfWeek) {
                item {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(10.dp),
                    ) {

                    }
                }
            }
            items(currentMonthDays) { day ->
                val date = currentDate.withDayOfMonth(day)

                CalendarDays(
                    date = date,
                    isToday = date == LocalDate.now(),

                )

            }
        }
    }
}

@Composable
fun CalendarDays(
    date: LocalDate,
    isToday: Boolean,
) {

    Box(
        modifier = Modifier
            .size(63.5.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1.2f))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
            ) {

                Text(
                    text = date.dayOfMonth.toString(),
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.offset(0.dp, 1.dp)
                )

            }
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "150%",
                style = Typography.labelMedium,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.weight(.5f))

        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExceedTheme {
        ColorBox()

    }
}
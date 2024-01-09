package com.gaebaljip.exceed.screens

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import java.time.LocalDate

@Composable
fun ColorBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBF8))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 207.dp)
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 64.dp, bottom = 48.dp)
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
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "2024",
            fontSize = 18.sp
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "12",
                fontSize = 40.sp
            )
            Text(
                text = "December",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

        }

        Text(
            text = "눌러서 이달의 목표를 적어보세요",
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun CalendarLayout(
    modifier: Modifier = Modifier,
    onDayClick: (Int) -> Unit,
    month: String
    // year: Int,
    // month_int: Int
) {
    Column(
        modifier = Modifier.padding(bottom = 16.dp),
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
package com.gaebaljip.exceed.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import kotlin.math.round

@Composable
fun AchieveGauge(
    gaugeColor: Color,
    gaugeTextColor: Color,
    targetValue: Int,
    currentValue: Int,
) {
    val percentage = round(currentValue.toFloat() / targetValue * 100)
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(),
    ) {
        Column {
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth()
                        .background(
                            colorResource(id = R.color.gauge_background),
                            RoundedCornerShape(15.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(currentValue.toFloat() / targetValue)
                        .background(gaugeColor, RoundedCornerShape(15.dp))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "$currentValue / $targetValue",
                    color = gaugeTextColor,
                    fontWeight = FontWeight(500),
                    fontSize = 9.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$percentage%",
                    color = if (percentage >= 100)
                        gaugeTextColor
                    else
                        colorResource(id = R.color.gauge_ongoing_percentage),
                    fontWeight = FontWeight(500),
                    fontSize = 9.sp
                )

                Spacer(modifier = Modifier.width(7.dp))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExceedTheme {
        AchieveGauge(
            gaugeColor = colorResource(id = R.color.fat_color),
            gaugeTextColor = colorResource(id = R.color.gauge_percentage_fat_color),
            targetValue = 400, currentValue = 200
        )
    }
}
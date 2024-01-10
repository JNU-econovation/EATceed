package com.gaebaljip.exceed.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gaebaljip.exceed.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Home",
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Composable
fun PhysicsEngineComponent(
    count: Int,
    radius: Int,
    height: Int = LocalConfiguration.current.screenHeightDp / 2,
    width: Int = LocalConfiguration.current.screenWidthDp
) {
    val localDensity = LocalDensity.current
    val engine = remember {
        localDensity.run {
            Engine(
                height.dp.toPx().toDouble(),
                width.dp.toPx().toDouble()
            )
        }
    }
    var objList by remember {
        mutableStateOf(listOf<Obj>())
    }
    var running by remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    val scope2 = rememberCoroutineScope()
    val scope3 = rememberCoroutineScope()

    LaunchedEffect(true) {
        scope.launch {
            for (i in 1..count) {
                delay(150)
                engine.add(
                    Obj(
                        localDensity.run { radius.dp.toPx().toInt() },
                        Random.nextDouble() * 360,
                        -100.0,
                        (Random.nextInt(localDensity.run {
                            (width / 10 * 6).dp.toPx().toInt()
                        }) + localDensity.run { (width / 10 * 2).dp.toPx().toInt() }).toDouble(),
                        (Random.nextInt(500) + 200).toDouble(),
                        0.0,
                        0.0,
                        0.0,
                    )
                )
            }
            delay(1500)
            running = false
        }
    }

    LaunchedEffect(true) {
        scope3.launch {
            while (running) {
                delay(15)
                engine.update()
            }
        }
    }
    LaunchedEffect(true) {
        scope2.launch {
            while (running) {
                delay(15)
                withContext(Dispatchers.Main) {
                    objList = listOf()
                    objList = engine.get()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .height((height / 20 * 9).dp)
                .width((width / 10 * 6 + 16).dp)
                .offset((width / 10 * 2 - 8).dp, (height / 20 * 9 + 20).dp)
                .background(
                    colorResource(id = R.color.home_physics_background),
                    RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)
                )
                .border(
                    1.dp,
                    colorResource(id = R.color.home_physics_border),
                    RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)

                )
        )
        objList.forEachIndexed { i, item ->
            Text(
                modifier = Modifier
                    .height((radius * 2).dp)
                    .width((radius * 2).dp)
                    .offset(
                        LocalDensity.current.run {
                            (item.x)
                                .toInt()
                                .toDp() - (radius).dp
                        },
                        LocalDensity.current.run {
                            (item.y)
                                .toInt()
                                .toDp() - (radius).dp
                        })
                    .graphicsLayer(
                        rotationZ = item.rotate.toFloat()
                    ),
                text = if (item.isWall) "" else "🍊",
                fontSize = (radius * 2 - 10).sp
            )
        }
    }
}
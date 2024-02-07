package com.gaebaljip.exceed.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.model.dto.response.HomeInfoResponseDTO
import com.gaebaljip.exceed.ui.common.AchieveGauge
import com.gaebaljip.exceed.ui.theme.gmarket
import com.gaebaljip.exceed.ui.theme.pretendard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min
import kotlin.random.Random

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val homeState by homeViewModel.homeInfoState.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        homeViewModel.getData()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.home_background))
    ) {
        PhysicsEngineComponent(state = homeState, count = 14, radius = 23)

        GaugeBoardComponent(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter),
            state = homeState
        )

        PercentageComponent(
            Modifier.align(Alignment.TopCenter),
            homeState
        )

        TopBarComponent(
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun TopBarComponent(modifier: Modifier) {
    Row(modifier = modifier.offset(0.dp, 12.dp)) {
        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.share_icon),
            modifier = Modifier.clickable { /*TODO*/ },
            contentDescription = stringResource(R.string.home_share_btn_description)
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            painter = painterResource(id = R.drawable.settings_icon),
            modifier = Modifier.clickable { /*TODO*/ },
            contentDescription = stringResource(R.string.home_setting_btn_description)
        )
        Spacer(Modifier.width(8.dp))
    }
}

@Composable
fun PercentageComponent(modifier: Modifier, state: HomeInfoResponseDTO?) {
    var visibleState by remember {
        mutableStateOf(false)
    }
    if (state != null) visibleState = true

    AnimatedVisibility(
        visible = visibleState,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing)
        ),
        modifier = modifier
    ) {
        if (state != null) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = stringResource(R.string.home_target_calorie),
                    fontFamily = pretendard,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${(state.currentMeal.calorie / state.targetMeal.calorie * 100).toInt()}%",
                    fontFamily = gmarket,
                    fontSize = 37.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${state.currentMeal.calorie.toInt()} / ${state.targetMeal.calorie.toInt()}",
                    fontFamily = gmarket,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

}

@Composable
fun GaugeBoardComponent(modifier: Modifier, state: HomeInfoResponseDTO?) {
    Card(
        modifier = modifier.background(colorResource(id = R.color.home_board_background)),
        elevation = 10.dp

    ) {
        if (state != null) {
            Row {
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Spacer(Modifier.height(22.dp))
                    AchieveGauge(
                        title = stringResource(R.string.carbohydrate),
                        gaugeColor = colorResource(id = R.color.carbohydrate_color),
                        gaugeTextColor = colorResource(id = R.color.gauge_percentage_carbohydrate_color),
                        targetValue = state.targetMeal.carbohydrate.toInt(),
                        currentValue = state.currentMeal.carbohydrate.toInt(),
                    )

                    Spacer(Modifier.height(32.dp))
                    AchieveGauge(
                        title = stringResource(R.string.protein),
                        gaugeColor = colorResource(id = R.color.protein_color),
                        gaugeTextColor = colorResource(id = R.color.gauge_percentage_protein_color),
                        targetValue = state.targetMeal.protein.toInt(),
                        currentValue = state.currentMeal.protein.toInt(),
                    )

                    Spacer(Modifier.height(32.dp))
                    AchieveGauge(
                        title = stringResource(R.string.fat),
                        gaugeColor = colorResource(id = R.color.fat_color),
                        gaugeTextColor = colorResource(id = R.color.gauge_percentage_fat_color),
                        targetValue = state.targetMeal.fat.toInt(),
                        currentValue = state.currentMeal.fat.toInt(),
                    )
                }
                Spacer(Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun PhysicsEngineComponent(
    state: HomeInfoResponseDTO?,
    count: Int,
    radius: Int,
    height: Int = LocalConfiguration.current.screenHeightDp / 100 * 45,
    width: Int = LocalConfiguration.current.screenWidthDp,
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
    val screenUpdateScope = rememberCoroutineScope()
    val physicsUpdateScope = rememberCoroutineScope()

    LaunchedEffect(state) {
        if (state != null) {
            val percentageCount = (min(
                1.0,
                (state.currentMeal.calorie / state.targetMeal.calorie)
            ) * count).toInt()

            for (i in 1..percentageCount) {
                delay(150)
                engine.add(
                    Obj(
                        localDensity.run { radius.dp.toPx().toInt() },
                        Random.nextDouble() * 360,
                        -100.0,
                        (Random.nextInt(localDensity.run {
                            (width / 10 * 6).dp.toPx().toInt()
                        }) + localDensity.run {
                            (width / 10 * 2).dp.toPx().toInt()
                        }).toDouble(),
                        (Random.nextInt(500) + 200).toDouble(),
                        0.0,
                        0.0,
                        0.0,
                    )
                )
            }
            delay(5000)
            running = false
        }
    }

    LaunchedEffect(state) {
        if (state != null) {
            physicsUpdateScope.launch {
                while (running) {
                    delay(5)
                    engine.update()
                }
            }
        }
    }
    LaunchedEffect(state) {
        if (state != null) {
            screenUpdateScope.launch {
                while (running) {
                    delay(15)
                    withContext(Dispatchers.Main) {
                        objList = listOf()
                        objList = engine.get()
                    }
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
                fontSize = (radius * 2 - 20).sp
            )
        }
    }
}
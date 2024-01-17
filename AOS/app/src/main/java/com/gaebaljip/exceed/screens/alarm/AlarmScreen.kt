package com.gaebaljip.exceed.screens.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.ui.theme.Typography
import com.gaebaljip.exceed.ui.theme.pretendard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(alarmViewModel: AlarmViewModel = viewModel()) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val dataList by alarmViewModel.dataList.collectAsStateWithLifecycle()
    val focusItem by alarmViewModel.focusAlarm.collectAsStateWithLifecycle()

    LaunchedEffect(focusItem) {
        if (focusItem != null) showBottomSheet = true
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
                backgroundColor = colorResource(id = R.color.primary_color)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.alarm_background))
        ) {
            Spacer(modifier = Modifier.height(84.dp))
            AlarmListComponent(dataList) {
                alarmViewModel.changeFocus(it)
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    alarmViewModel.changeFocus(null)
                    showBottomSheet = false
                },
                sheetState = sheetState

            ) {
                AlarmSettingBoard(focusedItem = focusItem) {
                    scope.launch {
                        sheetState.hide()
                        alarmViewModel.changeFocus(null)
                        showBottomSheet = false
                    }
                }
            }
        }
    }
}

@Composable
fun AlarmListComponent(dataList: List<AlarmInfo>, onClick: (AlarmInfo) -> Unit) {
    LazyColumn {
        items(dataList) { item ->
            AlarmItem(item = item, onClick = onClick)
        }
    }
}

@Composable
fun AlarmItem(item: AlarmInfo, onClick: (AlarmInfo) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp, 6.dp)
            .height((LocalConfiguration.current.screenHeightDp / 8).dp)
            .clickable { onClick(item) },
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Spacer(Modifier.width(20.dp))
            Text(
                text = "${item.hour}:${item.minute}",
                style = Typography.displayMedium
            )
            Spacer(Modifier.width(40.dp))
            Text(
                text = stringResource(id = item.mealType.stringRes),
                style = Typography.labelLarge
            )
            Spacer(Modifier.weight(1f))
            AlarmItemWeekComponent(
                isChecked = ((item.weekFlag and (1 shl 0)) == (1 shl 0)),
                text = stringResource(id = R.string.week_sun)
            )
            AlarmItemWeekComponent(
                isChecked = ((item.weekFlag and (1 shl 1)) == (1 shl 1)),
                text = stringResource(id = R.string.week_mon)
            )
            AlarmItemWeekComponent(
                isChecked = ((item.weekFlag and (1 shl 2)) == (1 shl 2)),
                text = stringResource(id = R.string.week_tue)
            )
            AlarmItemWeekComponent(
                isChecked = ((item.weekFlag and (1 shl 3)) == (1 shl 3)),
                text = stringResource(id = R.string.week_wed)
            )
            AlarmItemWeekComponent(
                isChecked = ((item.weekFlag and (1 shl 4)) == (1 shl 4)),
                text = stringResource(id = R.string.week_thu)
            )
            AlarmItemWeekComponent(
                isChecked = ((item.weekFlag and (1 shl 5)) == (1 shl 5)),
                text = stringResource(id = R.string.week_fri)
            )
            AlarmItemWeekComponent(
                isChecked = ((item.weekFlag and (1 shl 6)) == (1 shl 6)),
                text = stringResource(id = R.string.week_sat)
            )
            Spacer(modifier = Modifier.width(20.dp))
        }

    }
}

@Composable
fun AlarmItemWeekComponent(isChecked: Boolean, text: String) {
    Text(
        text = "$text ",
        style = if (isChecked) Typography.titleLarge else Typography.titleSmall,
        color = if (isChecked) colorResource(id = R.color.primary_color) else Color.Unspecified,
        fontSize = 12.sp,
        fontWeight = if (isChecked) FontWeight.Medium else FontWeight.Normal
    )
}

@Composable
fun AlarmSettingWeekComponent(isChecked: Boolean, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .border(
                (0.5).dp,
                colorResource(id = R.color.primary_color),
                RoundedCornerShape(50.dp)
            )
            .background(
                if (isChecked) {
                    colorResource(id = R.color.primary_color)
                } else {
                    Color.Unspecified
                },
                RoundedCornerShape(50.dp)
            )
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = if (isChecked) Typography.titleLarge else Typography.titleSmall,
            color = if (isChecked) colorResource(id = R.color.reverse_font_color) else Color.Unspecified,
            fontSize = 12.sp,
            fontWeight = if (isChecked) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun AlarmSettingBoard(focusedItem: AlarmInfo?, onDismiss: () -> Unit) {
    var hour by remember { mutableStateOf(focusedItem?.hour ?: 8) }
    var minute by remember { mutableStateOf(focusedItem?.minute ?: 0) }
    var mealType by remember { mutableStateOf(focusedItem?.mealType) }
    var weekFlag by remember { mutableStateOf(focusedItem?.weekFlag ?: 0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (focusedItem != null) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription = "제거",
                    tint = colorResource(id = R.color.primary_color),
                    modifier = Modifier.clickable { /*TODO*/ })
                Spacer(modifier = Modifier.width(22.dp))
            }
        }
        Spacer(modifier = Modifier.height(67.dp))
        Row {
            TimePicker(
                maxNum = 24,
                initValue = hour,
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f),
            ) {
                hour = it
            }
            Text(
                text = ":",
                fontSize = 70.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = pretendard,
            )
            TimePicker(
                maxNum = 60,
                initValue = minute,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f),
            ) {
                minute = it
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            val enumValues = MealTypeEnum.values()
            enumValues.forEach {
                Text(
                    text = stringResource(id = it.stringRes),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = pretendard,
                    fontWeight = FontWeight(300),
                    color = if (mealType == it) {
                        colorResource(id = R.color.reverse_font_color)
                    } else {
                        Color.Unspecified
                    },
                    modifier = Modifier
                        .padding((4.5).dp, 0.dp)
                        .width(43.dp)
                        .border(
                            (0.5).dp,
                            colorResource(id = R.color.primary_color),
                            RoundedCornerShape(50.dp)
                        )
                        .background(
                            if (mealType == it) {
                                colorResource(id = R.color.primary_color)
                            } else {
                                Color.Unspecified
                            },
                            RoundedCornerShape(50.dp)
                        )
                        .clickable {
                            mealType = it
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(74.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(2f))
            AlarmSettingWeekComponent(
                isChecked = ((weekFlag and (1 shl 0)) == (1 shl 0)),
                text = stringResource(id = R.string.week_sun)
            ) {
                weekFlag = ((weekFlag) xor (1 shl 0))
            }
            Spacer(modifier = Modifier.weight(1f))
            AlarmSettingWeekComponent(
                isChecked = ((weekFlag and (1 shl 1)) == (1 shl 1)),
                text = stringResource(id = R.string.week_mon)
            ) {
                weekFlag = ((weekFlag) xor (1 shl 1))
            }
            Spacer(modifier = Modifier.weight(1f))
            AlarmSettingWeekComponent(
                isChecked = ((weekFlag and (1 shl 2)) == (1 shl 2)),
                text = stringResource(id = R.string.week_tue)
            ) {
                weekFlag = ((weekFlag) xor (1 shl 2))
            }
            Spacer(modifier = Modifier.weight(1f))
            AlarmSettingWeekComponent(
                isChecked = ((weekFlag and (1 shl 3)) == (1 shl 3)),
                text = stringResource(id = R.string.week_wed)
            ) {
                weekFlag = ((weekFlag) xor (1 shl 3))
            }
            Spacer(modifier = Modifier.weight(1f))
            AlarmSettingWeekComponent(
                isChecked = ((weekFlag and (1 shl 4)) == (1 shl 4)),
                text = stringResource(id = R.string.week_thu)
            ) {
                weekFlag = ((weekFlag) xor (1 shl 4))
            }
            Spacer(modifier = Modifier.weight(1f))
            AlarmSettingWeekComponent(
                isChecked = ((weekFlag and (1 shl 5)) == (1 shl 5)),
                text = stringResource(id = R.string.week_fri)
            ) {
                weekFlag = ((weekFlag) xor (1 shl 5))
            }
            Spacer(modifier = Modifier.weight(1f))
            AlarmSettingWeekComponent(
                isChecked = ((weekFlag and (1 shl 6)) == (1 shl 6)),
                text = stringResource(id = R.string.week_sat)
            ) {
                weekFlag = ((weekFlag) xor (1 shl 6))
            }
            Spacer(modifier = Modifier.weight(2f))
        }
        Spacer(modifier = Modifier.height(44.dp))
        Row {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clickable { onDismiss() }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "취소",
                    fontSize = 20.sp,
                    fontFamily = pretendard,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clickable(
                        enabled = mealType != null && weekFlag != 0
                    ) { /*TODO*/ }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "확인",
                    textDecoration = if (mealType == null || weekFlag == 0) TextDecoration.LineThrough else TextDecoration.None,
                    fontSize = 20.sp,
                    fontFamily = pretendard,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimePicker(
    maxNum: Int,
    initValue: Int,
    textAlign: TextAlign,
    modifier: Modifier,
    onChange: (Int) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = initValue)

    LaunchedEffect(pagerState) {
        onChange(pagerState.currentPage)
    }
    VerticalPager(
        pageCount = maxNum,
        state = pagerState,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(60)
        ),
        pageSize = PageSize.Fixed(100.dp),
        modifier = modifier.height(100.dp)
    ) { page ->
        // Our page content
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${if (page / 10 == 0) "0" else ""}$page",
            fontSize = 70.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = pretendard,
            textAlign = textAlign
        )
    }
}
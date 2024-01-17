package com.gaebaljip.exceed.screens.add

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.screens.alarm.AlarmInfo
import com.gaebaljip.exceed.ui.theme.Typography
import java.io.ByteArrayOutputStream
import java.util.Base64

@Composable
fun AddScreen(addViewModel: AddViewModel = viewModel()) {
    val dataList by addViewModel.dataList.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        addViewModel.getListCurrentWeek()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.alarm_background))
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        AddScreenAlarmListComponent(dataList) {
            //TODO Intent
        }
    }
}

@Composable
fun AddScreenAlarmListComponent(dataList: List<AlarmInfo>, onClick: (AlarmInfo) -> Unit) {
    LazyColumn {
        items(dataList) { item ->
            AddScreenAlarmItem(item = item, onClick = onClick)
        }
    }
}

fun changeStringToBitmap(imgString: String): Bitmap {
    val encodeByte = Base64.getDecoder().decode(imgString)
    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
}
@Composable
fun AddScreenAlarmItem(item: AlarmInfo, onClick: (AlarmInfo) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp, 6.dp)
            .height((LocalConfiguration.current.screenHeightDp / 8).dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            )
            {

                if (item.imgString == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.25f)
                            .clickable {
                                onClick(item)
                            }
                    ) {
                        Icon(
                            modifier = Modifier.align(Alignment.Center),
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "add",
                            tint = colorResource(id = R.color.font_color)
                        )
                    }
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.25f),
                        bitmap = changeStringToBitmap(item.imgString).asImageBitmap(),
                        contentDescription = null,
                    )

                }
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(colorResource(id = R.color.alarm_item_divider)))
                Spacer(Modifier.width(20.dp))
                Text(
                    text = "${if (item.hour/10==0) "0" else ""}${item.hour}:${if (item.minute/10==0) "0" else ""}${item.minute}",
                    style = Typography.displayMedium
                )
                Spacer(Modifier.width(40.dp))
                Text(
                    text = stringResource(id = item.mealType.stringRes),
                    style = Typography.labelLarge
                )
                Spacer(modifier = Modifier.width(20.dp))
            }

            if (item.imgString != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            colorResource(id = R.color.enable_color),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.check_icon),
                        contentDescription = null,
                        tint = colorResource(id = R.color.reverse_font_color)
                    )
                }
            }
        }
    }
}


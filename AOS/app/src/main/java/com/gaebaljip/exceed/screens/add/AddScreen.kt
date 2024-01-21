package com.gaebaljip.exceed.screens.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.RegistrationActivity
import com.gaebaljip.exceed.screens.alarm.AlarmInfo
import com.gaebaljip.exceed.ui.theme.Typography
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.util.Calendar

@Composable
fun AddScreen(addViewModel: AddViewModel = viewModel()) {
    val dataList by addViewModel.dataList.collectAsStateWithLifecycle()
    val result = remember { mutableStateOf<Uri?>(null) }
    val focusItem = remember { mutableStateOf<AlarmInfo?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it == null) {
            focusItem.value = null
        }
        result.value = it
    }

    val context = LocalContext.current
    val registrationLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = requireNotNull(it.data)
                val id = requireNotNull(intent.getStringExtra("id"))
                val uri = Uri.parse(requireNotNull(intent.getStringExtra("uri")))
                val imgString = bitmapToString(bitmapResize(uriToBitmap(context, uri)))
                val calendar = Calendar.getInstance()
                val imgDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${
                    calendar.get(Calendar.DAY_OF_MONTH)
                }"

                addViewModel.updateAlarm(id, imgString, imgDate)
            }

            focusItem.value = null
            result.value = null
        }

    LaunchedEffect(result.value) {
        if (result.value != null) {
            val intent = Intent(context, RegistrationActivity::class.java)
            intent.putExtra("id", requireNotNull(focusItem.value).id)
            intent.putExtra("uri", result.value.toString())
            intent.putExtra("mealType", requireNotNull(focusItem.value).mealType.name)
            registrationLauncher.launch(intent)
        }
    }

    LaunchedEffect(true) {
        val calendar = Calendar.getInstance()
        val imgDate = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${
            calendar.get(Calendar.DAY_OF_MONTH)
        }"
        addViewModel.getListCurrentWeek(imgDate)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.alarm_background))
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        AddScreenAlarmListComponent(dataList) {
            if (focusItem.value == null) {
                focusItem.value = it
                launcher.launch(
                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
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


fun bitmapToString(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos)
    val byte = baos.toByteArray()
    return Base64.getEncoder().encodeToString(byte)
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap {
    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
    return bitmap
}

fun bitmapResize(bitmap: Bitmap): Bitmap {
    val width: Int = bitmap.width
    val height: Int = bitmap.height
    val scaleWidth: Float = 1080.0F / width
    val scaleHeight: Float = 720.0F / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)

    return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
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
                            .fillMaxWidth(0.4f)
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
                            .fillMaxWidth(0.4f),
                        bitmap = changeStringToBitmap(item.imgString).asImageBitmap(),
                        contentDescription = null,
                    )

                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(colorResource(id = R.color.alarm_item_divider))
                )
                Spacer(Modifier.width(20.dp))
                Text(
                    text = "${if (item.hour / 10 == 0) "0" else ""}${item.hour}:${if (item.minute / 10 == 0) "0" else ""}${item.minute}",
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


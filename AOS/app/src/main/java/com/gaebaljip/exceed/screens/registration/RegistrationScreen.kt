package com.gaebaljip.exceed.screens.registration

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.Greeting
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import com.gaebaljip.exceed.ui.theme.pretendard
import java.io.ByteArrayOutputStream
import java.util.Base64

@Composable
fun RegistrationScreen(
    id: String,
    uri: Uri,
    mealType: MealTypeEnum,
    onFinished : (Boolean) -> Unit,
    registrationViewModel: RegistrationViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = bitmapResize(uriToBitmap(LocalContext.current, uri)).asImageBitmap(),
            contentDescription = "select image"
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clickable {
                        onFinished(false)
                    }
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
                    .clickable {
                        onFinished(true)
                    }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "등록",
                    fontSize = 20.sp,
                    fontFamily = pretendard,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
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
    val scaleWidth: Float = 1080.0F/width
    val scaleHeight : Float = 720.0F/height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)

    return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ExceedTheme {
        Greeting("Android")
    }
}
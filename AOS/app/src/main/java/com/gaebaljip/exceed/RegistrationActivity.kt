package com.gaebaljip.exceed

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gaebaljip.exceed.screens.registration.RegistrationScreen
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import java.io.ByteArrayOutputStream
import java.util.Base64

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = requireNotNull(intent.getStringExtra("id"))
        val uri = Uri.parse(requireNotNull(intent.getStringExtra("uri")))
        val mealType = getMealTypeByString(requireNotNull(intent.getStringExtra("mealType")))
        val onFinished : (Boolean) -> Unit = {
            val intent = Intent()
            if(it){
                intent.putExtra("id", id)
                intent.putExtra("uri", uri.toString())
                setResult(RESULT_OK, intent)
                finish()
            }else{
                setResult(RESULT_CANCELED, intent)
                finish()
            }

        }
        setContent {
            ExceedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistrationScreen(id, uri, mealType, onFinished)
                }
            }
        }
    }
}

fun getMealTypeByString(string: String): MealTypeEnum {
    val enum: MealTypeEnum = when (string) {
        MealTypeEnum.BREAKFAST.name -> MealTypeEnum.BREAKFAST
        MealTypeEnum.LAUNCH.name -> MealTypeEnum.LAUNCH
        MealTypeEnum.DINNER.name -> MealTypeEnum.DINNER
        MealTypeEnum.SNACK.name -> MealTypeEnum.SNACK
        else -> {
            MealTypeEnum.SNACK
        }
    }
    return enum
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
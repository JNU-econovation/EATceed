package com.gaebaljip.exceed.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gaebaljip.exceed.R


val gmarket = FontFamily(
    Font(R.font.gmarketsans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.gmarketsans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.gmarketsans_light, FontWeight.Light, FontStyle.Normal),)

val pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pretendard_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold, FontStyle.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(

    titleMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    labelMedium = TextStyle(
        fontFamily = gmarket,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = gmarket,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),

    displaySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = gmarket,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    bodySmall = TextStyle(
        fontFamily = gmarket,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    ),

    displayLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),

    displayMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    labelLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    titleSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    labelSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 70.sp
    ),

    titleLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
    
)
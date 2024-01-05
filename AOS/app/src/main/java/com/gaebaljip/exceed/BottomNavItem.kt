package com.gaebaljip.exceed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
) {
    object Home : BottomNavItem(
        "Home", R.drawable.ic_home, "home"
    )

    object Calendar : BottomNavItem(
        "Calendar", R.drawable.ic_calendar, "calendar"
    )

    object Add : BottomNavItem(
        "Add", R.drawable.ic_add, "add"
    )

    object Food : BottomNavItem(
        "Food", R.drawable.ic_food, "food"
    )

    object Alarm : BottomNavItem(
        "Alarm", R.drawable.ic_alarm, "alarm"
    )
}

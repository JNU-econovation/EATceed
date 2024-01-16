package com.gaebaljip.exceed.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .padding(16.dp)
            .width(130.dp)) {
            Text(text = "게스트 로그인")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .padding(16.dp)
            .width(130.dp)) {
            Text(text = "일반 로그인")
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}
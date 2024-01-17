package com.gaebaljip.exceed.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.gaebaljip.exceed.MainActivity
import com.gaebaljip.exceed.ui.theme.pretendard

@Composable
fun OnboardingScreen(navController: NavController) {

    var heightInput by remember { mutableStateOf(TextFieldValue()) }
    var weightInput by remember { mutableStateOf(TextFieldValue()) }
    var ageInput by remember { mutableStateOf(TextFieldValue()) }
    var etcInput by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = heightInput,
            onValueChange = { newValue -> heightInput = newValue },
            label = { Text(text = "키") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        OutlinedTextField(
            value = weightInput,
            onValueChange = { newValue -> weightInput = newValue },
            label = { Text(text = "몸무게") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        OutlinedTextField(
            value = ageInput,
            onValueChange = { newValue -> ageInput = newValue },
            label = { Text(text = "나이") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(end = 16.dp)

            ) {
                Text(text = "남성")
            }

            Button(
                onClick = { /*TODO*/ }, modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = "여성")
            }
        }

        Text(
            text = "활동 정도",
            modifier = Modifier.padding(16.dp),
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        ActivityTypeComponent()

        Text(
            text = "특이사항",
            modifier = Modifier.padding(16.dp),
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        OutlinedTextField(
            value = etcInput,
            onValueChange = { newValue -> etcInput = newValue },
            label = { Text(text = "특이사항") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            )
        Button(
            onClick = {
                navController.navigate("main") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }, modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp, top = 16.dp)
        ) {
            Text(text = "완료")
        }
    }
}

@Composable
private fun ActivityTypeComponent() {
    val activeType = listOf("활동 안함", "조금 움직임", "보통", "활동적임", "매우 활동적임")
    val selectedValue = remember { mutableStateOf("") }
    val isSelected: (String) -> Boolean = { selectedValue.value == it }
    val onChanged: (String) -> Unit = { selectedValue.value = it }

    Column(modifier = Modifier.padding(start = 16.dp)) {
        activeType.forEach { item ->
            Column {
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = isSelected(item),
                            onClick = { onChanged(item) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected(item),
                        onClick = { /*TODO*/ }
                    )
                    Text(text = item)

                }
            }
        }
    }
}
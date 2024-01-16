package com.gaebaljip.exceed.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun OnboardingScreen() {

    var heightInput by remember { mutableStateOf(TextFieldValue()) }
    var weightInput by remember { mutableStateOf(TextFieldValue()) }
    var ageInput by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = heightInput,
            onValueChange = { newValue -> heightInput = newValue },
            label = { Text(text = "키") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = weightInput,
            onValueChange = { newValue -> weightInput = newValue },
            label = { Text(text = "몸무게") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = weightInput,
            onValueChange = { newValue -> weightInput = newValue },
            label = { Text(text = "나이") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .padding(16.dp)) {
                Text(text = "남성")
            }

            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .padding(16.dp)) {
                Text(text = "여성")
            }
        }
        
    }
}

@file:OptIn(ExperimentalComposeUiApi::class)

package com.gaebaljip.exceed.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.FoodViewModel
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import com.gaebaljip.exceed.ui.theme.Typography
import com.gaebaljip.exceed.ui.theme.pretendard
import kotlinx.coroutines.launch

@Composable
fun FoodScreen() {
    ChatBotUI()

}

@Composable
fun ChatBotUI(foodViewModel: FoodViewModel = viewModel()) {

    val chatInfoState by foodViewModel.chatRequestData.observeAsState()
    val context = LocalContext.current
    
    val keyboardOnDone = LocalSoftwareKeyboardController.current

    LaunchedEffect(chatInfoState) {
        if (chatInfoState == true) {
            Toast.makeText(context, "채팅 전송 성공!", Toast.LENGTH_SHORT).show()
        }

    }

    var chatInput by remember { mutableStateOf("") }
    val getChat by foodViewModel.chatResponseData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(410.dp)
                        .padding(vertical = 8.dp)
                        .background(
                            color = colorResource(id = R.color.primary_background_color),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(5.dp))

                ) {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(getChat) { response ->
                            TextBoxItem(text = response.answer)
                        }
                    }

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {

                    TextField(
                        value = chatInput,
                        onValueChange = { newValue -> chatInput = newValue },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = false,
                        modifier = Modifier
                            .weight(4f),
                        textStyle = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        placeholder = { Text(text = "질문을 입력해보세요.", fontSize = 16.sp) },
                        keyboardActions = KeyboardActions(onDone = { keyboardOnDone?.hide() }),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xffFFF7EE),
                            unfocusedContainerColor = Color(0xffFFF7EE)
                        )
                    )

                    Spacer(modifier = Modifier.weight(0.2f))
                    Icon(
                        painter = painterResource(id = R.drawable.send_icon),
                        contentDescription = "보내기",
                        tint = colorResource(id = R.color.primary_color),

                        modifier = Modifier
                            .clickable {
                                foodViewModel.sendQuestionData(chatInput.toString())
                            }
                            .size(35.dp))
                    Spacer(modifier = Modifier.weight(0.05f))
                }

                Column {
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Row {
                        Spacer(modifier = Modifier.weight(0.5f))
                        Button(
                            onClick = {
                                val breakfast = "아침 메뉴 추천해 줘"
                                foodViewModel.sendQuestionData(breakfast)

                            }, modifier = Modifier
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary_color)
                            ),
                            border = BorderStroke(
                                1.dp,
                                colorResource(id = R.color.alarm_button_stroke_color)
                            )
                        ) {
                            Text(text = "아침", style = Typography.labelLarge, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.weight(0.5f))
                        Button(
                            onClick = {
                                val launch = "점심 메뉴 추천해 줘"
                                foodViewModel.sendQuestionData(launch)

                            }, modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary_color)
                            ),
                            border = BorderStroke(
                                1.dp,
                                colorResource(id = R.color.alarm_button_stroke_color)
                            )
                        ) {
                            Text(text = "점심", style = Typography.labelLarge, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row {
                        Spacer(modifier = Modifier.weight(0.5f))
                        Button(
                            onClick = {
                                val dinner = "저녁 메뉴 추천해 줘"
                                foodViewModel.sendQuestionData(dinner)

                            }, modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary_color)
                            ),
                            border = BorderStroke(
                                1.dp,
                                colorResource(id = R.color.alarm_button_stroke_color)
                            )
                        ) {
                            Text(text = "저녁", style = Typography.labelLarge, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.weight(0.5f))
                        Button(
                            onClick = {
                                val snack = "식사 중간에 먹을 간단한 간식 메뉴 추천해 줘"
                                foodViewModel.sendQuestionData(snack)

                            }, modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary_color)
                            ),
                            border = BorderStroke(
                                1.dp,
                                colorResource(id = R.color.alarm_button_stroke_color)
                            )
                        ) {
                            Text(text = "간식", style = Typography.labelLarge, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }

            }

        }
    }
}

@Composable
fun TextBoxItem(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFDAB9))
    ) {
        Text(
            text = text,
            fontFamily = pretendard,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )

    }
}
package com.gaebaljip.exceed.screens.registration

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaebaljip.exceed.Greeting
import com.gaebaljip.exceed.MealTypeEnum
import com.gaebaljip.exceed.R
import com.gaebaljip.exceed.model.dto.response.FoodNameAndId
import com.gaebaljip.exceed.ui.theme.ExceedTheme
import com.gaebaljip.exceed.ui.theme.pretendard
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    uri: Uri,
    mealType: MealTypeEnum,
    onFinished: (Boolean) -> Unit,
    registrationViewModel: RegistrationViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val foodList by registrationViewModel.foodList.collectAsStateWithLifecycle()
    val addedList by registrationViewModel.addedFoodList.collectAsStateWithLifecycle()
    var flex by remember { mutableStateOf(1.0F) }
    val context = LocalContext.current
    LaunchedEffect(true) {
        registrationViewModel.getNewData()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = stringResource(id = mealType.stringRes),
            fontSize = 20.sp,
            fontFamily = pretendard
        )
        Image(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            bitmap = bitmapResize(uriToBitmap(LocalContext.current, uri)).asImageBitmap(),
            contentDescription = "select image",
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.clickable {
                showBottomSheet = true
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "음식 추가",
                fontSize = 20.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "▼",
                fontSize = 12.sp,
                fontFamily = pretendard
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(addedList) {
                AddedItem(data = it) {
                    registrationViewModel.deleteFood(it)
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = "분량",
            modifier = Modifier.padding(20.dp, 0.dp),
            fontSize = 20.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(10.dp))
        FlexPicker(100, 9) {
            flex = it
        }
        Spacer(Modifier.height(10.dp))
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
                        scope.launch {
                            val fileName = queryName(context.contentResolver, uri)
                            val presignedUri = registrationViewModel.registerRequest(
                                flex,
                                mealType,
                                requireNotNull(fileName)
                            ) ?: return@launch

                            val result = registrationViewModel.uploadFile(presignedUri, uri)
                            if(result.not()) return@launch
                            onFinished(true)

                        }
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

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState

        ) {

            InfiniteList(
                listItems = foodList,
                onLoadMore = { registrationViewModel.getNewData() }
            ) {

                registrationViewModel.addFood(it)
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }
        }
    }
}

private fun queryName(resolver: ContentResolver, uri: Uri): String? {
    val returnCursor = resolver.query(uri, null, null, null, null)!!
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}

@Composable
fun AddedItem(data: Pair<Int, FoodNameAndId>, onDelete: (Int) -> Unit) {
    Row(
        modifier = Modifier.padding(0.dp, 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = data.second.name, fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onDelete(data.first) }) {
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "delete",
                tint = colorResource(id = R.color.primary_color)
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
    }
}

@Composable
fun InfiniteList(
    listItems: List<FoodNameAndId>,
    onLoadMore: () -> Unit,
    onClick: (FoodNameAndId) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState
    ) {
        items(listItems) {
            Box(modifier = Modifier.clickable {
                onClick(it)
            }
            ) {
                Text(
                    text = it.name,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }

        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    InfiniteListHandler(listState = listState) {
        onLoadMore()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlexPicker(
    maxNum: Int,
    initValue: Int,
    onChange: (Float) -> Unit
) {
    val pagerState = rememberPagerState(initValue)


    LaunchedEffect(pagerState.currentPage) {
        onChange((pagerState.currentPage + 1).toFloat() / 10)
    }
    HorizontalPager(
        pageCount = maxNum,
        state = pagerState,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(60)
        ),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            start = (LocalConfiguration.current.screenWidthDp / 5 * 2).dp,
            end = (LocalConfiguration.current.screenWidthDp / 5 * 2).dp
        )
    ) { page ->
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "${(page + 1).toFloat() / 10}",
            color = if (pagerState.currentPage == page) colorResource(id = R.color.primary_color) else Color.Unspecified,
            fontSize = 20.sp,
            fontWeight = if (pagerState.currentPage == page) FontWeight.Bold else FontWeight.Light,
            fontFamily = pretendard,
            textAlign = TextAlign.Center
        )
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
    val scaleWidth: Float = 1080.0F / width
    val scaleHeight: Float = 720.0F / height
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

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 5,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
            }
    }
}
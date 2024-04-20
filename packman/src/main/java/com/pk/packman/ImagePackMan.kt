package com.pk.packman

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ImagePackMan(url: String, viewModel: ImagePackManViewModel = hiltViewModel()) {

    val state by viewModel.list.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.updateBitmap(url)
    }

    when (state) {
        is ImagePackManUiState.Error -> {
            Log.d("TAG", "MyModelScreen: $state")
            val myLogo = BitmapFactory.decodeResource(context.resources, R.drawable.error)
            Image(
                bitmap = myLogo.asImageBitmap(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
            )
        }

        is ImagePackManUiState.Loading -> {
            val myLogo = BitmapFactory.decodeResource(context.resources, R.drawable.placeholder)
            Image(
                bitmap = myLogo.asImageBitmap(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = null,
            )
            Log.d("TAG", "MyModelScreen: $state")
        }

        is ImagePackManUiState.Success -> {
            val image = viewModel.getBitmap(url)
            if (image != null) {
                Log.d("TAG", "ImagePackMan: cache $url")
                Image(
                    bitmap = image.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentDescription = null,
                )
            }else{
                val myLogo = BitmapFactory.decodeResource(context.resources, R.drawable.placeholder)
                Log.d("TAG", "ImagePackMan: cache $url")
                Image(
                    bitmap = myLogo.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentDescription = null,
                )
            }
        }
    }
}
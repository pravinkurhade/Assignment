package com.pk.assignment.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pk.packman.ImagePackMan

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiDataState.collectAsStateWithLifecycle()
    when (state) {
        is HomeUiState.Error -> {
            Log.d("TAG", "MyModelScreen: $state")
            val data = ((state as HomeUiState.Error).msg)
            Text(
                text = data,
                modifier = modifier
            )
        }

        is HomeUiState.Loading -> {
            Text(
                text = "Loading ",
                modifier = modifier
            )
            Log.d("TAG", "MyModelScreen: $state")
        }

        is HomeUiState.Success -> {
            val data = ((state as HomeUiState.Success).data)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(data.size) { photo ->
                        val item = data[photo]
                        val thumbnail = item.thumbnail
                        val url = thumbnail.domain + "/" + thumbnail.basePath + "/10/" + thumbnail.key
                        Log.d("TAG", "HomeScreen: $url")
                        ImagePackMan(url)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}



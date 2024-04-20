package com.pk.packman

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pk.packman.init.PackMan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class ImagePackManViewModel @Inject constructor(application: Application): ViewModel() {

   private val packMan = PackMan(application)

    private val _list = MutableStateFlow<ImagePackManUiState>(ImagePackManUiState.Loading)
    val list : StateFlow<ImagePackManUiState> = _list

    fun updateBitmap(url: String) {
        if (packMan.has(url)){
            val image = packMan.get(url)
            _list.value = ImagePackManUiState.Success(data = image!!)
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val urlPath = URL(url)
                    val image = BitmapFactory.decodeStream(urlPath.openConnection().getInputStream())
                    packMan.put(url,image)
                    _list.value = ImagePackManUiState.Success(data = image)
                } catch (e: IOException) {
                    _list.value = ImagePackManUiState.Error(msg = e.message!!)
                }
            }
        }
    }

    fun getBitmap(url: String): Bitmap? {
      return  packMan.get(url)
    }
}

sealed interface ImagePackManUiState {
    data object Loading : ImagePackManUiState
    data class Error(val msg: String) : ImagePackManUiState
    data class Success(val data: Bitmap) : ImagePackManUiState
}
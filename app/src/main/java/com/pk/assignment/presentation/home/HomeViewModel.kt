package com.pk.assignment.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pk.assignment.data.common.Resources
import com.pk.assignment.domain.model.RecordItem
import com.pk.assignment.domain.use_cases.GetRecordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getRecordsUseCase: GetRecordsUseCase): ViewModel() {

    private val _uiDataState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiDataState: StateFlow<HomeUiState> = _uiDataState

    init {
        getStates()
    }

    private fun getStates() {
        getRecordsUseCase().onEach {
            when (it) {
                is Resources.Loading -> {
                    Log.d("TAG", "getStates: ")
                    _uiDataState.value = HomeUiState.Loading
                }

                is Resources.Success -> {
                    Log.d("TAG", "getStates: "+it.data)
                    _uiDataState.value = HomeUiState.Success(data = it.data!!)
                }

                is Resources.Error -> {
                    Log.d("TAG", "getStates: "+it.message)
                    _uiDataState.value = HomeUiState.Error(msg = it.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val msg: String) : HomeUiState
    data class Success(val data: List<RecordItem>) : HomeUiState
}
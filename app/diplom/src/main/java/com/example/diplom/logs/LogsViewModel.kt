package com.example.diplom.logs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Log
import kotlinx.coroutines.launch

class LogsViewModel : ViewModel() {
    private val _dataList = mutableStateOf(listOf<Log>())
    var dataList: State<List<Log>> = mutableStateOf(listOf<Log>())
        get() = _dataList

    init {
        viewModelScope.launch {
            _dataList.value = Dependencies.repository.getLogsList()
        }
    }
}
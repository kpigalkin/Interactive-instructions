package com.example.diplom.instructions

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.storage.Dependencies
import com.example.diplom.storage.models.Product
import kotlinx.coroutines.launch

class InstructionsViewModel: ViewModel() {
    private val _dataList = mutableStateOf(listOf<Product>())
    var dataList: State<List<Product>> = mutableStateOf(listOf<Product>())
        get() = _dataList

    private val _linkToOpen = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            Log.d("products", "${Dependencies.repository.getProductList()}")
            _dataList.value = Dependencies.repository.getProductList()
        }
    }

    val linkToOpen: LiveData<String>
        get() = _linkToOpen

    fun openLink(url: String) {
        _linkToOpen.value = url
    }

    fun linkOpened() {
        _linkToOpen.value = null
    }
}
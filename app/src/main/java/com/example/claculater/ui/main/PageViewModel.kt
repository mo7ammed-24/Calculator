package com.example.claculater.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = _index.map {
        if(it==1) "Bellow They Locked Apps" else "Bellow They Unlocked Apps"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}
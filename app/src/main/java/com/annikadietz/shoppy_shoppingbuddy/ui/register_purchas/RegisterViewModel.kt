package com.annikadietz.shoppy_shoppingbuddy.ui.register_purchas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _text = MutableLiveData<String>().apply {
        value = "the product you want to purchase"
    }
    val text: LiveData<String> = _text
}
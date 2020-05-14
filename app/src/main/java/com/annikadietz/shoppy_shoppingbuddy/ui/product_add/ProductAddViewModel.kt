package com.annikadietz.shoppy_shoppingbuddy.ui.product_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductAddViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "A product is missing? Add it now!"
    }
    val text: LiveData<String> = _text
}
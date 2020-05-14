package com.annikadietz.shoppy_shoppingbuddy.ui.product_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductSearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Find your product"
    }
    val text: LiveData<String> = _text
}
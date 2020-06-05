package com.annikadietz.shoppy_shoppingbuddy.ui.find_shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FindShoppingListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "A product is missing? Add it now!"
    }
    val text: LiveData<String> = _text
}
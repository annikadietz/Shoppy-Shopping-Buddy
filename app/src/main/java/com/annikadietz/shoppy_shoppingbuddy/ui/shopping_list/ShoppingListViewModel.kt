package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper

class ShoppingListViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Find your product"
    }
    private val _shoppingList = MutableLiveData<MutableList<Product>>().apply {
        // TODO: Get the actual shopping list and not all products!
        this.value = NewDatabaseHelper.getProducts()
    }

    val text: LiveData<String> = _text
    val shoppingList: MutableLiveData<MutableList<Product>> = _shoppingList
}
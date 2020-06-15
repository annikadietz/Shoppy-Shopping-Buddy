package com.annikadietz.shoppy_shoppingbuddy.Model

import android.os.Build
import androidx.annotation.RequiresApi

class ShoppingItem {
    var product: Product
    var price: Price
    var priceSuggestions: ArrayList<Price>

    constructor(product: Product, price: Price, priceSuggestions: ArrayList<Price>){
        this.product = product;
        this.price = price
        this.priceSuggestions = priceSuggestions
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(){
        this.product = Product();
        this.price = Price()
        this.priceSuggestions = arrayListOf()
    }
}
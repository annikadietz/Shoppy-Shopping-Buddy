package com.annikadietz.shoppy_shoppingbuddy.Model

import android.os.Build
import androidx.annotation.RequiresApi

class ShoppingItem {
    var product: Product
    var price: Price
    var shop: Shop
    var priceSuggestions: ArrayList<Price>

    constructor(product: Product, shop: Shop, price: Price, priceSuggestions: ArrayList<Price>){
        this.product = product
        this.shop = shop
        this.price = price
        this.priceSuggestions = priceSuggestions
    }
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(product: Product, shop: Shop, price: Double){
        this.product = product
        this.shop = shop
        this.price = Price(price)
        this.priceSuggestions = arrayListOf()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(){
        this.product = Product()
        this.shop = Shop()
        this.price = Price()
        this.priceSuggestions = arrayListOf()
    }
}
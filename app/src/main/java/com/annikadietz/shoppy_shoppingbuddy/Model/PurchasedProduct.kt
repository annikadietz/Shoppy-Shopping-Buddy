package com.annikadietz.shoppy_shoppingbuddy.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class PurchasedProduct {
    var buyedAt: Date
    var item: ShoppingItem

    constructor(buyedAt: Date, item: ShoppingItem){
        this.buyedAt = buyedAt
        this.item = item
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(){
        this.buyedAt = Date()
        this.item = ShoppingItem()
    }
}
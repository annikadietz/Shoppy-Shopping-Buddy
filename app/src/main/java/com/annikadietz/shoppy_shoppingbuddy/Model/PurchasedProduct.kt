package com.annikadietz.shoppy_shoppingbuddy.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class PurchasedProduct {
    var boughtAt: Date
    var item: ShoppingItem

    constructor(buyedAt: Date, item: ShoppingItem) {
        this.boughtAt = buyedAt
        this.item = item
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor() {
        this.boughtAt = Date()
        this.item = ShoppingItem()
    }
}
package com.annikadietz.shoppy_shoppingbuddy.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class Price {
    var lastConfirmed: String
    var price: Double
    var counter: Int

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(lastConfirmed: String, price: Double, counter: Int) {
        this.lastConfirmed = lastConfirmed
        this.price = price
        this.counter = counter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor() {
        this.lastConfirmed = LocalDateTime.now().toString()
        this.price = 0.0
        this.counter = 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(price: Double) {
        this.lastConfirmed = LocalDateTime.now().toString()
        this.price = price
        this.counter = 0
    }
}
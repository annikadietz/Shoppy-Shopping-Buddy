package com.annikadietz.shoppy_shoppingbuddy.Model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class SuggestionPrice {
    var price: Double?
    var counter: Int?
   lateinit var date: Timestamp

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(price: Double?, counter:Int?) {
        this.price = price
        this.counter = counter
       this.date= Timestamp(Date())
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        val formatted = date.format(formatter)
    }
    constructor() {
        this.price = null
        this.counter = null
    }

}
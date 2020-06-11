package com.annikadietz.shoppy_shoppingbuddy.Model

class SuggestionPrice {
    var price: Double
    var counter: Double

    constructor(price: Double) {
        this.price = price
        this.counter = 0.0
    }

    fun increaseCounter(): Double {
        this.counter++
        return counter
    }
}
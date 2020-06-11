package com.annikadietz.shoppy_shoppingbuddy.Model

class SuggestionPrice {
    var price: Double?
    var counter: Double?

    constructor(price: Double) {
        this.price = price
        this.counter = 0.0
    }
    constructor() {
        this.price = null
        this.counter = null
    }

}
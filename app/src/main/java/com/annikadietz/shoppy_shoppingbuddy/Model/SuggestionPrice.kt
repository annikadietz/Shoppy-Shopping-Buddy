package com.annikadietz.shoppy_shoppingbuddy.Model

class SuggestionPrice {
    var price: Double?
    var counter: Double?

    constructor(price: Double?,counter:Double?) {
        this.price = price
        this.counter = counter
    }
    constructor() {
        this.price = null
        this.counter = null
    }

}
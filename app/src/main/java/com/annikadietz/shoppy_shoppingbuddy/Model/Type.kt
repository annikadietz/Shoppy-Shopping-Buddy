package com.annikadietz.shoppy_shoppingbuddy.Model

class Type {
    val name: String?

    constructor(name: String) {
        this.name = name
    }

    constructor() {
        this.name = null
    }
}
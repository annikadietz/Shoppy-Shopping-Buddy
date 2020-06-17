package com.annikadietz.shoppy_shoppingbuddy.Model

class Product{
    val name: String
    val type: Type
    constructor(name:String, type: Type){
        this.name = name
        this.type = type
    }
    constructor(){
        this.name = ""
        this.type = Type()
    }
}
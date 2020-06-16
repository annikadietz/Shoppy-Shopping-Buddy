package com.annikadietz.shoppy_shoppingbuddy.Model

class Combination {
    var shops: ArrayList<Shop>
    var shoppingItems: ArrayList<ShoppingItem>
    var directions: Directions? = null
    constructor(shop: ArrayList<Shop>, shoppingItems: ArrayList<ShoppingItem>){
        this.shops = shop
        this.shoppingItems = shoppingItems
    }
    constructor(){
        this.shops = arrayListOf<Shop>()
        this.shoppingItems = arrayListOf<ShoppingItem>()
    }
}
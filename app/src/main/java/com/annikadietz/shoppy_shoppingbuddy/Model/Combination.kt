package com.annikadietz.shoppy_shoppingbuddy.Model

class Combination {
    var shops: ArrayList<Shop>?
    var prices: ArrayList<ProductInShop>?

    constructor(shop: ArrayList<Shop>, price: ArrayList<ProductInShop>?){
        this.shops = shop
        this.prices = price
    }
    constructor(){
        this.shops = null
        this.prices = null
    }
}
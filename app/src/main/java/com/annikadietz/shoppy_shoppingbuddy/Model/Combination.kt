package com.annikadietz.shoppy_shoppingbuddy.Model

class Combination {
    var shops: ArrayList<Shop>?
    var productsInShops: ArrayList<ProductInShop>?
    var directions: Directions? = null
    constructor(shop: ArrayList<Shop>, price: ArrayList<ProductInShop>?){
        this.shops = shop
        this.productsInShops = price
    }
    constructor(){
        this.shops = null
        this.productsInShops = null
    }
}
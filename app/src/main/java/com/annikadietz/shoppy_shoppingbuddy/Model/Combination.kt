package com.annikadietz.shoppy_shoppingbuddy.Model

class Combination {
    var shops: ArrayList<Shop>
    var productsInShops: ArrayList<ProductInShop>
    var directions: Directions? = null
    constructor(shop: ArrayList<Shop>, productsInShop: ArrayList<ProductInShop>){
        this.shops = shop
        this.productsInShops = productsInShop
    }
    constructor(){
        this.shops = arrayListOf<Shop>()
        this.productsInShops = arrayListOf<ProductInShop>()
    }
}
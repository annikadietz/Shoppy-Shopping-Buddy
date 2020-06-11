package com.annikadietz.shoppy_shoppingbuddy.Model

class CombinationWithPrices {
    var shops: ArrayList<Shop>?
    var productsInShopsWithPrices: ArrayList<ProductInShopWithPrices>
    var directions: Directions? = null
    constructor(shop: ArrayList<Shop>, productsInShopWithPrices: ArrayList<ProductInShopWithPrices>){
        this.shops = shop
        this.productsInShopsWithPrices = productsInShopWithPrices
    }
    constructor(){
        this.shops = null
        this.productsInShopsWithPrices = arrayListOf<ProductInShopWithPrices>()
    }
}

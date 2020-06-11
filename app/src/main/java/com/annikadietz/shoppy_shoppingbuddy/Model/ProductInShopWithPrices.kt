package com.annikadietz.shoppy_shoppingbuddy.Model

class ProductInShopWithPrices {
    var product: Product
    var shop: Shop
    var price: Double
    var suggestionPrices: ArrayList<SuggestionPrice?>

    constructor(product: Product, shop: Shop, price: Double, suggestionPrices: ArrayList<SuggestionPrice?>){
        this.product = product;
        this.shop = shop
        this.price = price
        this.suggestionPrices= suggestionPrices
    }

    constructor(){
        this.product = Product();
        this.shop = Shop()
        this.price = Double.MAX_VALUE
        this.suggestionPrices= arrayListOf<SuggestionPrice?>()
    }


}
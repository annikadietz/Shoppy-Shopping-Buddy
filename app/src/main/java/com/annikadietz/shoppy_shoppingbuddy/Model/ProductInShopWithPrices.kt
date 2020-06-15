package com.annikadietz.shoppy_shoppingbuddy.Model

class ProductInShopWithPrices {
    var product: Product
    var shop: Shop
    var price: SuggestionPrice
    var suggestionPrices: ArrayList<SuggestionPrice?>


    constructor(product: Product, shop: Shop, price: SuggestionPrice, suggestionPrices: ArrayList<SuggestionPrice?>){
        this.product = product;
        this.shop = shop
        this.price = price
        this.suggestionPrices= suggestionPrices
    }

    constructor(){
        this.product = Product();
        this.shop = Shop()
        this.price = SuggestionPrice()
        this.suggestionPrices= arrayListOf<SuggestionPrice?>()
    }


}
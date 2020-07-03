package com.annikadietz.shoppy_shoppingbuddy.Model

class ProductInShop {
    var product: Product
    var shop: Shop
    var price: Double

    constructor(product: Product, shop: Shop, price: Double) {
        this.product = product
        this.shop = shop
        this.price = price
    }

    constructor() {
        this.product = Product()
        this.shop = Shop()
        this.price = Double.MAX_VALUE
    }


}
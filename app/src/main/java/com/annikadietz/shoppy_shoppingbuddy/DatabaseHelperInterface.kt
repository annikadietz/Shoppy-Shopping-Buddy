package com.annikadietz.shoppy_shoppingbuddy

import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop

interface DatabaseHelperInterface {
    fun getShops(): MutableList<Shop>

    fun getProducts(): MutableList<Product>

    fun getProductsInShops(): MutableList<ProductInShop>
}
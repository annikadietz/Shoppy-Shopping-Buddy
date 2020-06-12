package com.annikadietz.shoppy_shoppingbuddy

import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop

interface DatabaseHelperInterface {
    fun getShops(): ArrayList<Shop>

    fun getProducts(): ArrayList<Product>

    fun getProductsInShops(): ArrayList<ProductInShop>

    fun getMyShops(): ArrayList<Shop>
}
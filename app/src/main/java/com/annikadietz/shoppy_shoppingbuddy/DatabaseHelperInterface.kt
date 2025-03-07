package com.annikadietz.shoppy_shoppingbuddy

import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.Model.ShoppingItem

interface DatabaseHelperInterface {
    fun getShops(): ArrayList<Shop>

    fun getProducts(): ArrayList<Product>

    fun getMyShops(): ArrayList<Shop>

    fun getMyShoppingList(): ArrayList<Product>
    fun getShoppingItems(): ArrayList<ShoppingItem>
    fun addProductToMyShoppingList(product: Product)
    fun deleteProductFormMyShoppingList(position: Product)
}

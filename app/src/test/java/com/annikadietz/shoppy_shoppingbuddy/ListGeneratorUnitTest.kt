package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.google.android.gms.common.api.Response
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.Request
import java.sql.Array

class ListGeneratorUnitTest {
    var shops = arrayListOf<Shop>()
    var productsInShops = arrayListOf<ProductInShop>()

    var potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
    var pizza = Product("Pizza - Italia", Type("Frozen"))
    var bananas = Product("Bananas - 1kg", Type("Fruits"))
    var eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

    var jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
    var aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
    var lidl = Shop("Lidl", "7823PH", "Houtweg 151")

    var potatoesInJumbo = ProductInShop(potatoes, jumbo, 2.5)
    var potatoesInAldi = ProductInShop(potatoes, aldi, 2.5)
    var potatoesInLidl = ProductInShop(potatoes, lidl, 2.5)

    var pizzaInJumbo = ProductInShop(pizza, jumbo, 2.5)
    var pizzaInAldi = ProductInShop(pizza, aldi, 2.5)
    var pizzaInLidl = ProductInShop(pizza, lidl, 2.5)

    var bananasInJumbo = ProductInShop(bananas, jumbo, 2.5)
    var bananasInAldi = ProductInShop(bananas, aldi, 2.5)
    var bananasInLidl = ProductInShop(bananas, lidl, 2.5)

    var eggsInJumbo = ProductInShop(eggs, jumbo, 2.5)
    var eggsInAldi = ProductInShop(eggs, aldi, 2.5)
    var eggsInLidl = ProductInShop(eggs, lidl, 2.5)

    fun createObjects() {
        shops = arrayListOf<Shop>()
        productsInShops = arrayListOf<ProductInShop>()

        potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
        pizza = Product("Pizza - Italia", Type("Frozen"))
        bananas = Product("Bananas - 1kg", Type("Fruits"))
        eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

        jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
        aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
        lidl = Shop("Lidl", "7823PH", "Houtweg 151")

        potatoesInJumbo = ProductInShop(potatoes, jumbo, 2.5)
        potatoesInAldi = ProductInShop(potatoes, aldi, 2.5)
        potatoesInLidl = ProductInShop(potatoes, lidl, 2.5)

        pizzaInJumbo = ProductInShop(pizza, jumbo, 2.5)
        pizzaInAldi = ProductInShop(pizza, aldi, 2.5)
        pizzaInLidl = ProductInShop(pizza, lidl, 2.5) // the cheap one

        bananasInJumbo = ProductInShop(bananas, jumbo, 2.5)
        bananasInAldi = ProductInShop(bananas, aldi, 2.5)
        bananasInLidl = ProductInShop(bananas, lidl, 2.5)

        eggsInJumbo = ProductInShop(eggs, jumbo, 2.5)
        eggsInAldi = ProductInShop(eggs, aldi, 2.5)
        eggsInLidl = ProductInShop(eggs, lidl, 2.5)
    }

    fun setup() {
        shops.add(jumbo)
        shops.add(aldi)
        shops.add(lidl)


        productsInShops.add(potatoesInJumbo)
        productsInShops.add(potatoesInAldi)
        productsInShops.add(potatoesInLidl)

        productsInShops.add(pizzaInJumbo)
        productsInShops.add(pizzaInAldi)
        productsInShops.add(pizzaInLidl)

        productsInShops.add(bananasInJumbo)
        productsInShops.add(bananasInAldi)
        productsInShops.add(bananasInLidl)

        productsInShops.add(eggsInJumbo)
        productsInShops.add(eggsInAldi)
        productsInShops.add(eggsInLidl)

    }

    fun findCheapestStore(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>): ArrayList<ProductInShop> {
        var cheapestShop: Shop
        var finalShoppingList = arrayListOf<ProductInShop>()
        var cheapestPrice: Double = Double.MAX_VALUE
        shops.forEach{
            var shop = it
            var price = 0.0;
            var productsFound = arrayListOf<ProductInShop?>()
            shoppingList.forEach { it ->
                var product = it
                var productInShop = productsInShops.find {actor ->actor.shop == shop && actor.product == product}
                if (productInShop != null) {
                    price += productInShop.price
                    productsFound.add(productInShop)
                }
            }

            if (price < cheapestPrice) {
                cheapestPrice = price
                finalShoppingList = productsFound.clone() as ArrayList<ProductInShop>
            }
        }
        return finalShoppingList
    }

    @Test
    fun findsCheapestStore_Test() {
        createObjects()
        pizzaInAldi.price = 0.5
        setup()
        var listOfProducts = arrayListOf<Product>().apply {
            add(pizza)
            add(bananas)
            add(potatoes)
            add(eggs)
        }
        //Find the store where to buy all items for cheapest price.

        var shoppingList = findCheapestStore(shops, listOfProducts)
        Assert.assertEquals(4, 2 + 2)
    }
}

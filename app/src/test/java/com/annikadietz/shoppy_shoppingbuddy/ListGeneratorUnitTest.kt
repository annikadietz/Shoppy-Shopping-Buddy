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
    var combos = arrayListOf<Combination>()

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

    var combo1 = Combination(arrayListOf(jumbo), arrayListOf())
    var combo2 = Combination(arrayListOf(aldi), arrayListOf())
    var combo3 = Combination(arrayListOf(lidl), arrayListOf())
    var combo4 = Combination(arrayListOf(jumbo, aldi), arrayListOf())
    var combo5 = Combination(arrayListOf(jumbo, lidl), arrayListOf())
    var combo6 = Combination(arrayListOf(aldi, lidl), arrayListOf())
    var combo7 = Combination(arrayListOf(jumbo, aldi, lidl), arrayListOf())

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

        combo4 = Combination(arrayListOf(jumbo, aldi), arrayListOf())

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

//        combos.add(combo1)
//        combos.add(combo2)
//        combos.add(combo3)
        combos.add(combo4)
//        combos.add(combo5)
//        combos.add(combo6)
//        combos.add(combo7)
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
        var shoppingList = arrayListOf<Product>().apply {
            add(pizza)
            add(bananas)
            add(potatoes)
            add(eggs)
        }
        //Find the store where to buy all items for cheapest price.

        var result = ListGenerator.findCheapestStore(shops, shoppingList, productsInShops)

        Assert.assertTrue(result.contains(pizzaInAldi))
        Assert.assertTrue(result.contains(bananasInAldi))
        Assert.assertTrue(result.contains(potatoesInAldi))
        Assert.assertTrue(result.contains(eggsInAldi))
    }

    @Test
    fun findAllPossibleStoreCombinations_Test() {
        createObjects()
        setup()

        var result = ListGenerator.findAllPossibleStoreCombinations(shops)
        var foundCombo1 = result.find { c -> c.shops!!.contains(jumbo) }
        var foundCombo2 = result.find { c -> c.shops!!.contains(aldi) }
        var foundCombo3 = result.find { c -> c.shops!!.contains(lidl) }
        var foundCombo4 = result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) }
        var foundCombo5 = result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(lidl) }
        var foundCombo6 = result.find { c -> c.shops!!.contains(lidl) && c.shops!!.contains(aldi) }
        var foundCombo7 = result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) && c.shops!!.contains(lidl)}

        Assert.assertTrue(foundCombo1 != null)
        Assert.assertTrue(foundCombo2 != null)
        Assert.assertTrue(foundCombo3 != null)
        Assert.assertTrue(foundCombo4 != null)
        Assert.assertTrue(foundCombo5 != null)
        Assert.assertTrue(foundCombo6 != null)
        Assert.assertTrue(foundCombo7 != null)
    }

    @Test
    fun findsPriceInShop_Test() {
        createObjects()
        setup()
        var result = ListGenerator.findPriceInShop(lidl, eggs, productsInShops)
        Assert.assertEquals(eggsInLidl, result)
        result = ListGenerator.findPriceInShop(aldi, eggs, productsInShops)
        Assert.assertEquals(eggsInAldi, result)
        result = ListGenerator.findPriceInShop(jumbo, pizza, productsInShops)
        Assert.assertEquals(pizzaInJumbo, result)
    }

    @Test
    fun findBestPriceInShopCombination_Test() {
        createObjects()
        eggsInAldi.price = 10.0
        eggsInJumbo.price = 5.0
        eggsInLidl.price = 1.0
        setup()
        var combination = Combination(arrayListOf(jumbo, aldi), arrayListOf())
        var result = ListGenerator.findBestPriceInShopCombination(eggs, combination, productsInShops)
        Assert.assertEquals(eggsInJumbo, result)

        combination = Combination(arrayListOf(jumbo, lidl, aldi), arrayListOf())
        result = ListGenerator.findBestPriceInShopCombination(eggs, combination, productsInShops)
        Assert.assertEquals(eggsInLidl, result)
    }

    @Test
    fun findCheapestStoreCombinations_Test() {
        createObjects()
        pizzaInJumbo.price = 1.0
        bananasInAldi.price = 1.0
        potatoesInJumbo.price = 1.0
        eggsInAldi.price = 1.0
        setup()

        var shoppingList = arrayListOf<Product>().apply {
            add(pizza)
            add(bananas)
            add(potatoes)
            add(eggs)
        }

        var result = ListGenerator.findCheapestStoreCombinations(shops, shoppingList, productsInShops)
        var combination = result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) }

        print(result)
        Assert.assertTrue(combination?.prices!!.contains(pizzaInJumbo))
        Assert.assertTrue(combination?.prices!!.contains(bananasInAldi))
        Assert.assertTrue(combination?.prices!!.contains(potatoesInJumbo))
        Assert.assertTrue(combination?.prices!!.contains(eggsInAldi))
    }

}

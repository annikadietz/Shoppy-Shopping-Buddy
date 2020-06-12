package com.annikadietz.shoppy_shoppingbuddy

import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.Model.Type
// If you change mock data make sure the tests still run correctly
class MockDatabaseHelper : DatabaseHelperInterface {
    override fun getShops(): MutableList<Shop> {
        var jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
        var aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
        var lidl = Shop("Lidl", "7823PH", "Houtweg 151")
        var list: MutableList<Shop> = mutableListOf(jumbo, aldi, lidl)

        return list
    }

    override fun getProducts(): MutableList<Product> {
        var potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
        var pizza = Product("Pizza - Italia", Type("Frozen"))
        var bananas = Product("Bananas - 1kg", Type("Fruits"))
        var eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

        var list: MutableList<Product> = mutableListOf(potatoes, pizza, bananas, eggs)
        return list
    }

    override fun getProductsInShops(): MutableList<ProductInShop> {
        var shops = getShops()
        var products = getProducts()

        var potatoes = products.find { product -> product.name == "Potatoes - 1kg" }
        var pizza = products.find { product -> product.name == "Pizza - Italia" }
        var bananas = products.find { product -> product.name == "Bananas - 1kg" }
        var eggs = products.find { product -> product.name == "Large eggs" }

        // jumbo
        var jumbo = shops.find { shop -> shop.name == "Jumbo" }
        var potatoesInJumbo = ProductInShop(potatoes!!, jumbo!!, 2.0)
        var pizzaInJumbo = ProductInShop(pizza!!, jumbo!!, 2.0)
        var bananasInJumbo = ProductInShop(bananas!!, jumbo!!, 2.0)
        var eggsInJumbo = ProductInShop(eggs!!, jumbo!!, 2.0)

        // aldi
        var aldi = shops.find { shop -> shop.name == "Aldi" }
        var potatoesInAldi = ProductInShop(potatoes!!, aldi!!, 2.0)
        var pizzaInAldi = ProductInShop(pizza!!, aldi!!, 2.0)
        var bananasInAldi = ProductInShop(bananas!!, aldi!!, 2.0)
        var eggsInAldi = ProductInShop(eggs!!, aldi!!, 2.0)

        // lidl
        var lidl = shops.find { shop -> shop.name == "Lidl" }
        var potatoesInLidl = ProductInShop(potatoes!!, lidl!!, 2.0)
        var pizzaInLidl = ProductInShop(pizza!!, lidl!!, 2.0)
        var bananasInLidl = ProductInShop(bananas!!, lidl!!, 2.0)
        var eggsInLidl = ProductInShop(eggs!!, lidl!!, 2.0)


        var productsInShops : MutableList<ProductInShop> = mutableListOf(
            potatoesInJumbo, pizzaInJumbo, bananasInJumbo, eggsInJumbo,
            potatoesInAldi, pizzaInAldi, bananasInAldi, eggsInAldi,
            potatoesInLidl, pizzaInLidl, bananasInLidl, eggsInLidl
        )
        return productsInShops
    }
}
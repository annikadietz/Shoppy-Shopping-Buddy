package com.annikadietz.shoppy_shoppingbuddy

import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop

// If you change mock data make sure the tests still run correctly
class MockDatabaseHelper : DatabaseHelperInterface {
    override fun getShops(): ArrayList<Shop> {
        var jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
        var aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
        var lidl = Shop("Lidl", "7823PH", "Houtweg 151")
        var list = arrayListOf<Shop>(jumbo, aldi, lidl)

        return list
    }

    override fun getProducts(): ArrayList<Product> {
        var potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
        var pizza = Product("Pizza - Italia", Type("Frozen"))
        var bananas = Product("Bananas - 1kg", Type("Fruits"))
        var eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

        var list = arrayListOf<Product>(potatoes, pizza, bananas, eggs)
        return list
    }

    override fun getShoppingItems(): ArrayList<ShoppingItem> {
        var shops = getShops()
        var products = getProducts()

        var potatoes = products.find { product -> product.name == "Potatoes - 1kg" }
        var pizza = products.find { product -> product.name == "Pizza - Italia" }
        var bananas = products.find { product -> product.name == "Bananas - 1kg" }
        var eggs = products.find { product -> product.name == "Large eggs" }

        // jumbo
        var jumbo = shops.find { shop -> shop.name == "Jumbo" }
        var potatoesInJumbo = ShoppingItem(potatoes!!, jumbo!!, 2.0)
        var pizzaInJumbo = ShoppingItem(pizza!!, jumbo!!, 2.0)
        var bananasInJumbo = ShoppingItem(bananas!!, jumbo!!, 2.0)
        var eggsInJumbo = ShoppingItem(eggs!!, jumbo!!, 2.0)

        // aldi
        var aldi = shops.find { shop -> shop.name == "Aldi" }
        var potatoesInAldi = ShoppingItem(potatoes!!, aldi!!, 2.0)
        var pizzaInAldi = ShoppingItem(pizza!!, aldi!!, 2.0)
        var bananasInAldi = ShoppingItem(bananas!!, aldi!!, 2.0)
        var eggsInAldi = ShoppingItem(eggs!!, aldi!!, 2.0)

        // lidl
        var lidl = shops.find { shop -> shop.name == "Lidl" }
        var potatoesInLidl = ShoppingItem(potatoes!!, lidl!!, 2.0)
        var pizzaInLidl = ShoppingItem(pizza!!, lidl!!, 2.0)
        var bananasInLidl = ShoppingItem(bananas!!, lidl!!, 2.0)
        var eggsInLidl = ShoppingItem(eggs!!, lidl!!, 2.0)


        var productsInShops = arrayListOf<ShoppingItem>(
            potatoesInJumbo, pizzaInJumbo, bananasInJumbo, eggsInJumbo,
            potatoesInAldi, pizzaInAldi, bananasInAldi, eggsInAldi,
            potatoesInLidl, pizzaInLidl, bananasInLidl, eggsInLidl
        )
        return productsInShops
    }

    override fun addProductToMyShoppingList(product: Product) {
        TODO("Not yet implemented")
    }

    override fun deleteProductFormMyShoppingList(position: Product) {
        TODO("Not yet implemented")
    }

    override fun getMyShops(): ArrayList<Shop> {
        var jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
        var aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
        var list: ArrayList<Shop> = arrayListOf(jumbo, aldi)

        return list
    }

    override fun getMyShoppingList(): ArrayList<Product> {
        return arrayListOf()
    }
}
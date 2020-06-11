package com.annikadietz.shoppy_shoppingbuddy
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop

class Dummy_data {

    var shops = arrayListOf<Shop>()
    var productsInShopsWithPrices = arrayListOf<ProductInShopWithPrices>()
    var combos = arrayListOf<Combination>()
    var combosWithPrices = arrayListOf<CombinationWithPrices>()

    var potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
    var pizza = Product("Pizza - Italia", Type("Frozen"))
    var bananas = Product("Bananas - 1kg", Type("Fruits"))
    var eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

    var jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
    var aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
    var lidl = Shop("Lidl", "7823PH", "Houtweg 151")

    var prices1 = SuggestionPrice(2.0)
    var prices1_1 = SuggestionPrice(2.5)
    var prices2 = SuggestionPrice(3.0)
    var prices2_2 = SuggestionPrice(3.5)
    var prices3 = SuggestionPrice(4.0)
    var prices3_3 = SuggestionPrice(2.0)
    var prices4 = SuggestionPrice(2.5)
    var prices4_4 = SuggestionPrice(3.0)
    var prices5 = SuggestionPrice(3.5)
    var prices5_5 = SuggestionPrice(4.0)
    var prices6 = SuggestionPrice(2.0)
    var prices6_6 = SuggestionPrice(2.5)
    var prices7 = SuggestionPrice(3.0)
    var prices7_7 = SuggestionPrice(3.5)
    var prices8 = SuggestionPrice(4.0)
    var prices8_8 = SuggestionPrice(6.0)
    var prices9 = SuggestionPrice(3.5)
    var prices9_9 = SuggestionPrice(4.0)
    var prices10 = SuggestionPrice(2.0)
    var prices10_10 = SuggestionPrice(2.5)
    var prices11 = SuggestionPrice(3.0)
    var prices11_11 = SuggestionPrice(3.5)
    var prices12 = SuggestionPrice(4.0)
    var prices12_12 = SuggestionPrice(6.0)

    var suggestionsPrices1 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices2 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices3 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices4 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices5 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices6 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices7 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices8 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices9 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices10 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices11 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices12 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()

    var potatoesInJumboWithPrices = ProductInShopWithPrices(potatoes, jumbo, 2.5, suggestionsPrices1)
    var potatoesInAldiWithPrices = ProductInShopWithPrices(potatoes, aldi, 2.5, suggestionsPrices2)
    var potatoesInLidlWithPrices = ProductInShopWithPrices(potatoes, lidl, 2.5, suggestionsPrices3)

    var pizzaInJumboWithPrices = ProductInShopWithPrices(pizza, jumbo, 2.5, suggestionsPrices4)
    var pizzaInAldiWithPrices = ProductInShopWithPrices(pizza, aldi, 2.5, suggestionsPrices5)
    var pizzaInLidlWithPrices = ProductInShopWithPrices(pizza, lidl, 2.5, suggestionsPrices6)

    var bananasInJumboWithPrices = ProductInShopWithPrices(bananas, jumbo, 2.5, suggestionsPrices7)
    var bananasInAldiWithPrices = ProductInShopWithPrices(bananas, aldi, 2.5, suggestionsPrices8)
    var bananasInLidlWithPrices = ProductInShopWithPrices(bananas, lidl, 2.5, suggestionsPrices9)

    var eggsInJumboWithPrices = ProductInShopWithPrices(eggs, jumbo, 2.5, suggestionsPrices10)
    var eggsInAldiWithPrices = ProductInShopWithPrices(eggs, aldi, 2.5, suggestionsPrices11)
    var eggsInLidlWithPrices = ProductInShopWithPrices(eggs, lidl, 2.5, suggestionsPrices12)

    var combo1WithPrices = CombinationWithPrices(arrayListOf(jumbo), arrayListOf())
    var combo2WithPrices = CombinationWithPrices(arrayListOf(aldi), arrayListOf())
    var combo3WithPrices = CombinationWithPrices(arrayListOf(lidl), arrayListOf())
    var combo4WithPrices = CombinationWithPrices(arrayListOf(jumbo, aldi), arrayListOf())
    var combo5WithPrices = CombinationWithPrices(arrayListOf(jumbo, lidl), arrayListOf())
    var combo6WithPrices = CombinationWithPrices(arrayListOf(aldi, lidl), arrayListOf())
    var combo7WithPrices = CombinationWithPrices(arrayListOf(jumbo, aldi, lidl), arrayListOf())

     constructor () {
        shops = arrayListOf<Shop>()
        productsInShopsWithPrices = arrayListOf<ProductInShopWithPrices>()

        potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
        pizza = Product("Pizza - Italia", Type("Frozen"))
        bananas = Product("Bananas - 1kg", Type("Fruits"))
        eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

        jumbo = Shop("Jumbo", "7824JA", "Kerspellaan%209")
        aldi = Shop("Aldi", "7824CP", "Peyserhof%202")
        lidl = Shop("Lidl", "7823PH", "Houtweg%20151")


        suggestionsPrices1 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices2 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices3 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices4 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices5 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices6 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices7 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices8 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices9 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices10 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices11 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices12 = arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()

        suggestionsPrices1.add(prices1)
        suggestionsPrices1.add(prices1_1)
        suggestionsPrices2.add(prices2)
        suggestionsPrices2.add(prices2_2)
        suggestionsPrices3.add(prices3)
        suggestionsPrices3.add(prices3_3)
        suggestionsPrices4.add(prices4)
        suggestionsPrices4.add(prices4_4)
        suggestionsPrices5.add(prices5)
        suggestionsPrices5.add(prices5_5)
        suggestionsPrices6.add(prices6)
        suggestionsPrices6.add(prices6_6)
        suggestionsPrices7.add(prices7)
        suggestionsPrices7.add(prices7_7)
        suggestionsPrices8.add(prices8_8)
        suggestionsPrices9.add(prices9)
        suggestionsPrices9.add(prices9_9)
        suggestionsPrices10.add(prices10)
        suggestionsPrices10.add(prices10_10)
        suggestionsPrices11.add(prices11)
        suggestionsPrices11.add(prices11_11)
        suggestionsPrices12.add(prices12)
        suggestionsPrices12.add(prices12_12)

        potatoesInJumboWithPrices = ProductInShopWithPrices(potatoes, jumbo, 2.5, suggestionsPrices1)
        potatoesInAldiWithPrices = ProductInShopWithPrices(potatoes, aldi, 2.5, suggestionsPrices2)
        potatoesInLidlWithPrices = ProductInShopWithPrices(potatoes, lidl, 2.5, suggestionsPrices3)

        pizzaInJumboWithPrices = ProductInShopWithPrices(pizza, jumbo, 2.5, suggestionsPrices4)
        pizzaInAldiWithPrices = ProductInShopWithPrices(pizza, aldi, 2.5, suggestionsPrices5)
        pizzaInLidlWithPrices = ProductInShopWithPrices(pizza, lidl, 2.5, suggestionsPrices6) // the cheap one

        bananasInJumboWithPrices = ProductInShopWithPrices(bananas, jumbo, 2.5, suggestionsPrices7)
        bananasInAldiWithPrices = ProductInShopWithPrices(bananas, aldi, 2.5, suggestionsPrices8)
        bananasInLidlWithPrices = ProductInShopWithPrices(bananas, lidl, 2.5, suggestionsPrices9)

        eggsInJumboWithPrices = ProductInShopWithPrices(eggs, jumbo, 2.5, suggestionsPrices9)
        eggsInAldiWithPrices = ProductInShopWithPrices(eggs, aldi, 2.5, suggestionsPrices10)
        eggsInLidlWithPrices = ProductInShopWithPrices(eggs, lidl, 2.5, suggestionsPrices11)


        combo1WithPrices = CombinationWithPrices(arrayListOf(aldi), arrayListOf())
        combo4WithPrices = CombinationWithPrices(arrayListOf(jumbo, aldi), arrayListOf())
        combo7WithPrices = CombinationWithPrices(arrayListOf(jumbo, aldi, lidl), arrayListOf())
    }

    fun setup() {
        shops.add(jumbo)
        shops.add(aldi)
        shops.add(lidl)

        productsInShopsWithPrices.add(potatoesInJumboWithPrices)
        productsInShopsWithPrices.add(potatoesInAldiWithPrices)
        productsInShopsWithPrices.add(potatoesInLidlWithPrices)

        productsInShopsWithPrices.add(pizzaInJumboWithPrices)
        productsInShopsWithPrices.add(pizzaInAldiWithPrices)
        productsInShopsWithPrices.add(pizzaInLidlWithPrices)

        productsInShopsWithPrices.add(bananasInJumboWithPrices)
        productsInShopsWithPrices.add(bananasInAldiWithPrices)
        productsInShopsWithPrices.add(bananasInLidlWithPrices)

        productsInShopsWithPrices.add(eggsInJumboWithPrices)
        productsInShopsWithPrices.add(eggsInAldiWithPrices)
        productsInShopsWithPrices.add(eggsInLidlWithPrices)

        combosWithPrices.add(combo1WithPrices)
        combosWithPrices.add(combo4WithPrices)
        combosWithPrices.add(combo7WithPrices)

    }
    fun productsInShopsWithPrices(){
        setup()
        addProductsInShopsWithPrices(this.productsInShopsWithPrices)
    }
    fun addProductsInShopsWithPrices(productsInShopWithPrices :ArrayList<ProductInShopWithPrices>) {
        productsInShopWithPrices.forEach{
                NewDatabaseHelper.db.collection("productsInShopsWithPrices")
                .add(it)
        }
    }
}
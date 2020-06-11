package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test

import org.mockito.Mockito.*
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class ListGeneratorUnitTest {
    var listGenerator = ListGenerator(mock(Context::class.java), "Hoitingeslag%2029,%207824%20KG")
    var shops = arrayListOf<Shop>()
    var productsInShops = arrayListOf<ProductInShop>()
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

    var suggestionsPrices1 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices2 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices3 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices4 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices5 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices6 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices7 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices8 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices9 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices10 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices11 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var suggestionsPrices12 =
        arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
    var potatoesInJumboWithPrices =
        ProductInShopWithPrices(potatoes, jumbo, 2.5, suggestionsPrices1)
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

    fun createObjects() {
        shops = arrayListOf<Shop>()
        productsInShops = arrayListOf<ProductInShop>()
        productsInShopsWithPrices = arrayListOf<ProductInShopWithPrices>()

        potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
        pizza = Product("Pizza - Italia", Type("Frozen"))
        bananas = Product("Bananas - 1kg", Type("Fruits"))
        eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

        jumbo = Shop("Jumbo", "7824JA", "Kerspellaan%209")
        aldi = Shop("Aldi", "7824CP", "Peyserhof%202")
        lidl = Shop("Lidl", "7823PH", "Houtweg%20151")

        combo1 = Combination(arrayListOf(aldi), arrayListOf())
        combo4 = Combination(arrayListOf(jumbo, aldi), arrayListOf())
        combo7 = Combination(arrayListOf(jumbo, aldi, lidl), arrayListOf())

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

        suggestionsPrices1 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices2 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices3 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices4 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices5 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices6 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices7 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices8 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices9 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices10 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices11 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()
        suggestionsPrices12 =
            arrayListOf<com.annikadietz.shoppy_shoppingbuddy.Model.SuggestionPrice?>()

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

        potatoesInJumboWithPrices =ProductInShopWithPrices(potatoes, jumbo, 2.5, suggestionsPrices1)
        potatoesInAldiWithPrices = ProductInShopWithPrices(potatoes, aldi, 2.5, suggestionsPrices2)
        potatoesInLidlWithPrices = ProductInShopWithPrices(potatoes, lidl, 2.5, suggestionsPrices3)

        pizzaInJumboWithPrices = ProductInShopWithPrices(pizza, jumbo, 2.5, suggestionsPrices4)
        pizzaInAldiWithPrices = ProductInShopWithPrices(pizza, aldi, 2.5, suggestionsPrices5)
        pizzaInLidlWithPrices =ProductInShopWithPrices(pizza, lidl, 2.5, suggestionsPrices6) // the cheap one

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

        combos.add(combo1)
//        combos.add(combo2)
//        combos.add(combo3)
        combos.add(combo4)
//        combos.add(combo5)
//        combos.add(combo6)
        combos.add(combo7)
        combosWithPrices.add(combo1WithPrices)
        combosWithPrices.add(combo4WithPrices)
        combosWithPrices.add(combo7WithPrices)

    }

    fun findCheapestStore(

        shops: ArrayList<Shop>,
        shoppingList: ArrayList<Product>
    ): ArrayList<ProductInShop> {
        var cheapestShop: Shop
        var finalShoppingList = arrayListOf<ProductInShop>()
        var cheapestPrice: Double = Double.MAX_VALUE
        shops.forEach {
            var shop = it
            var price = 0.0;
            var productsFound = arrayListOf<ProductInShop?>()
            shoppingList.forEach { it ->
                var product = it
                var productInShop =
                    productsInShops.find { actor -> actor.shop == shop && actor.product == product }
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

    //tested
    @Test
    fun findsCheapestStore_Test() {
        createObjects()
        pizzaInAldiWithPrices.price = 0.5
        pizzaInAldi.price = 0.5
        setup()
        var shoppingList = arrayListOf<Product>().apply {
            add(pizza)
            add(bananas)
            add(potatoes)
            add(eggs)
        }
        //Find the store where to buy all items for cheapest price.

        var result = listGenerator.findCheapestStore(shops, shoppingList, productsInShops)

        Assert.assertTrue(result.contains(pizzaInAldi))
        Assert.assertTrue(result.contains(bananasInAldi))
        Assert.assertTrue(result.contains(potatoesInAldi))
        Assert.assertTrue(result.contains(eggsInAldi))

        var result2 = listGenerator.findCheapestStoreWithPrices(
            shops,
            shoppingList,
            productsInShopsWithPrices
        )
        Assert.assertTrue(result2.contains(pizzaInAldiWithPrices))
        Assert.assertTrue(result2.contains(bananasInAldiWithPrices))
        Assert.assertTrue(result2.contains(potatoesInAldiWithPrices))
        Assert.assertTrue(result2.contains(eggsInAldiWithPrices))
    }
//tested
    @Test
    fun findAllPossibleStoreCombinations_Test() {
        createObjects()
        setup()

        var result = listGenerator.findAllPossibleStoreCombinations(shops)
//        var result = listGenerator.findAllPossibleStoreCombinationsWithPrices(shops)
        var foundCombo1 = result.find { c -> c.shops!!.contains(jumbo) }
        var foundCombo2 = result.find { c -> c.shops!!.contains(aldi) }
        var foundCombo3 = result.find { c -> c.shops!!.contains(lidl) }
        var foundCombo4 = result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) }
        var foundCombo5 = result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(lidl) }
        var foundCombo6 = result.find { c -> c.shops!!.contains(lidl) && c.shops!!.contains(aldi) }
        var foundCombo7 = result.find { c ->
            c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) && c.shops!!.contains(lidl)
        }

        Assert.assertTrue(foundCombo1 != null)
        Assert.assertTrue(foundCombo2 != null)
        Assert.assertTrue(foundCombo3 != null)
        Assert.assertTrue(foundCombo4 != null)
        Assert.assertTrue(foundCombo5 != null)
        Assert.assertTrue(foundCombo6 != null)
        Assert.assertTrue(foundCombo7 != null)


    }
//tested
    @Test
    fun findsPriceInShop_Test() {
        createObjects()
        setup()
        var result = listGenerator.findPriceInShop(lidl, eggs, productsInShops)
        Assert.assertEquals(eggsInLidl, result)
        result = listGenerator.findPriceInShop(aldi, eggs, productsInShops)
        Assert.assertEquals(eggsInAldi, result)
        result = listGenerator.findPriceInShop(jumbo, pizza, productsInShops)
        Assert.assertEquals(pizzaInJumbo, result)

        var result2 = listGenerator.findPriceInShopWithPrices(lidl, eggs, productsInShopsWithPrices)
        Assert.assertEquals(eggsInLidlWithPrices, result2)
        result2 = listGenerator.findPriceInShopWithPrices(aldi, eggs, productsInShopsWithPrices)
        Assert.assertEquals(eggsInAldiWithPrices, result2)
        result2 = listGenerator.findPriceInShopWithPrices(jumbo, pizza, productsInShopsWithPrices)
        Assert.assertEquals(pizzaInJumboWithPrices, result2)
    }
//tested
    @Test
    fun findBestPriceInShopCombination_Test() {
        createObjects()
        eggsInAldi.price = 10.0
        eggsInJumbo.price = 5.0
        eggsInLidl.price = 1.0

        eggsInAldiWithPrices.price = 10.0
        eggsInJumboWithPrices.price = 5.0
        eggsInLidlWithPrices.price = 1.0
        setup()
        var combination = Combination(arrayListOf(jumbo, aldi), arrayListOf())
        var result =
            listGenerator.findBestPriceInShopCombination(eggs, combination, productsInShops)
        Assert.assertEquals(eggsInJumbo, result)

        combination = Combination(arrayListOf(jumbo, lidl, aldi), arrayListOf())
        result = listGenerator.findBestPriceInShopCombination(eggs, combination, productsInShops)
        Assert.assertEquals(eggsInLidl, result)

        var combination2 = CombinationWithPrices(arrayListOf(jumbo, aldi), arrayListOf())
        var result2 =
            listGenerator.findBestPriceInShopCombinationWithPrices(eggs, combination2, productsInShopsWithPrices)
        Assert.assertEquals(eggsInJumboWithPrices, result2)

        combination2 = CombinationWithPrices(arrayListOf(jumbo, lidl, aldi), arrayListOf())
        result2 = listGenerator.findBestPriceInShopCombinationWithPrices(eggs, combination2, productsInShopsWithPrices)
        Assert.assertEquals(eggsInLidlWithPrices, result2)


    }
//tested
    @Test
    fun getCombinationsWithProductsInShops_Test() {
        createObjects()
        pizzaInJumbo.price = 1.0
        bananasInAldi.price = 1.0
        potatoesInJumbo.price = 1.0
        eggsInAldi.price = 1.0


        pizzaInJumboWithPrices.price = 1.0
        bananasInAldiWithPrices.price = 1.0
        potatoesInJumboWithPrices.price = 1.0
        eggsInAldiWithPrices.price = 1.0
        setup()

        var shoppingList = arrayListOf<Product>().apply {
            add(pizza)
            add(bananas)
            add(potatoes)
            add(eggs)
        }

        var result =
            listGenerator.getCombinationsWithProductsInShops(shops, shoppingList, productsInShops)
        var combination = result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) }

        print(result)
        Assert.assertTrue(combination?.productsInShops!!.contains(pizzaInJumbo))
        Assert.assertTrue(combination?.productsInShops!!.contains(bananasInAldi))
        Assert.assertTrue(combination?.productsInShops!!.contains(potatoesInJumbo))
        Assert.assertTrue(combination?.productsInShops!!.contains(eggsInAldi))


        var result2 =
            listGenerator.getCombinationsWithProductsInShopsWithPrices(shops, shoppingList, productsInShopsWithPrices)
        var combination2 = result2.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) }

        print(result2)

        Assert.assertTrue(combination2?.productsInShopsWithPrices!!.contains(pizzaInJumboWithPrices))
        Assert.assertTrue(combination2?.productsInShopsWithPrices!!.contains(bananasInAldiWithPrices))
        Assert.assertTrue(combination2?.productsInShopsWithPrices!!.contains(potatoesInJumboWithPrices))
        Assert.assertTrue(combination2?.productsInShopsWithPrices!!.contains(eggsInAldiWithPrices))
    }


    @Test
    fun findBestRoute_Test() {
        createObjects()
        setup()

        listGenerator.GoogleDirectionsService = MockGoogleDirectionsService()
        var shoppingList = arrayListOf<Product>().apply {
            add(pizza)
            add(bananas)
            add(potatoes)
            add(eggs)
        }
        val context: Context = mock(Context::class.java)

        //var result = listGenerator.getDirections(shops, shoppingList, productsInShops, "Hoitingeslag%2029,%207824%20KG", context)
        print("goof")
    }
//tested
    @Test
    fun getPriceFromCombination_Test() {
        createObjects()
        setup()
        potatoesInJumbo.price = 1.00
        eggsInJumbo.price = 2.00
        pizzaInJumbo.price = 2.00
        bananasInJumbo.price = 1.00

        combo4.productsInShops?.add(potatoesInJumbo)
        combo4.productsInShops?.add(eggsInJumbo)
        combo4.productsInShops?.add(pizzaInJumbo)
        combo4.productsInShops?.add(bananasInJumbo)

        potatoesInJumboWithPrices.price = 1.00
        eggsInJumboWithPrices.price = 2.00
        pizzaInJumboWithPrices.price = 2.00
        bananasInJumboWithPrices.price = 1.00

        combo4WithPrices.productsInShopsWithPrices?.add(potatoesInJumboWithPrices)
        combo4WithPrices.productsInShopsWithPrices?.add(eggsInJumboWithPrices)
        combo4WithPrices.productsInShopsWithPrices?.add(pizzaInJumboWithPrices)
        combo4WithPrices.productsInShopsWithPrices?.add(bananasInJumboWithPrices)

        val ans = listGenerator.getPriceFromCombination(combo4)
        Assert.assertEquals(ans, 6.00, 0.0001)

        val ans2 = listGenerator.getPriceFromCombinationWithPrices(combo4WithPrices)
        Assert.assertEquals(ans2, 6.00, 0.0001)
    }

    //tested
    @Test
    fun getCombinationWithBestPriceWithOneShop_Test() {
        createObjects()
        setup()

        var comboOne = Combination(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 0.50),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboOne_2 = Combination(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 0.40),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboTwo = Combination(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboThree = Combination(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var cheapestCombo = listGenerator.getCombinationWithBestPrice(
            arrayListOf(
                comboOne,
                comboTwo,
                comboThree,
                comboOne_2
            ), 1
        )

        Assert.assertEquals(cheapestCombo, comboOne_2)


    }
    //tested
    @Test
    fun getCombinationWithBestPriceWithTwoShop_Test() {
        createObjects()
        setup()

       var comboOne = Combination(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboTwo = Combination(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 0.50),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboTwo_2 = Combination(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 0.40),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboThree = Combination(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var cheapestCombo = listGenerator.getCombinationWithBestPrice(
            arrayListOf(
                comboOne,
                comboTwo,
                comboThree,
                comboTwo_2
            ), 2
        )

        Assert.assertEquals(cheapestCombo, comboTwo_2)

    }
    //tested
    @Test
    fun getCombinationWithBestPriceWithThreeShop_Test() {
        createObjects()
        setup()
      var  comboOne = Combination(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboTwo = Combination(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboThree = Combination(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 0.50),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var comboThree_2 = Combination(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShop(Product(), Shop(), 1.00),
                ProductInShop(Product(), Shop(), 0.40),
                ProductInShop(Product(), Shop(), 1.00)
            )
        )
        var cheapestCombo = listGenerator.getCombinationWithBestPrice(
            arrayListOf(
                comboOne,
                comboTwo,
                comboThree,
                comboThree_2
            ), 3
        )

        Assert.assertEquals(cheapestCombo, comboThree_2)
    }
//tested
    @Test
    fun getCombinationWithBestPriceWithOneShopWithPrices_Test() {
        createObjects()
        setup()


        var comboOne = CombinationWithPrices(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices1),
                ProductInShopWithPrices(Product(), Shop(), 0.50,suggestionsPrices2),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices3)
            )
        )
        var comboOne_2 = CombinationWithPrices(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices4),
                ProductInShopWithPrices(Product(), Shop(), 0.40,suggestionsPrices5),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices6)
            )
        )
        var comboTwo = CombinationWithPrices(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices7),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices8),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices9)
            )
        )
        var comboThree = CombinationWithPrices(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices10),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices11),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices12)
            )
        )
        var cheapestCombo = listGenerator.getCombinationWithBestPriceWithPrices(
            arrayListOf(
                comboOne,
                comboTwo,
                comboThree,
                comboOne_2
            ), 1
        )

        Assert.assertEquals(cheapestCombo, comboOne_2)

    }
    //tested
    @Test
    fun getCombinationWithBestPriceWithTwoShopWithPrices_Test() {
        createObjects()
        setup()

        var comboOne = CombinationWithPrices(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices1),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices2),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices3)
            )
        )
        var comboTwo = CombinationWithPrices(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices4),
                ProductInShopWithPrices(Product(), Shop(), 0.50,suggestionsPrices5),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices6)
            )
        )
        var comboTwo_2 = CombinationWithPrices(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices7),
                ProductInShopWithPrices(Product(), Shop(), 0.40,suggestionsPrices8),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices9)
            )
        )
        var comboThree = CombinationWithPrices(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices10),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices11),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices12)
            )
        )
        var cheapestCombo = listGenerator.getCombinationWithBestPriceWithPrices(
            arrayListOf(
                comboOne,
                comboTwo,
                comboThree,
                comboTwo_2
            ), 2
        )

        Assert.assertEquals(cheapestCombo, comboTwo_2)

    }
    //tested
    @Test
    fun getCombinationWithBestPriceWithThreeShopWithPrices_Test() {
        createObjects()
        setup()
        var  comboOne = CombinationWithPrices(
            arrayListOf(aldi),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices1),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices2),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices3)
            )
        )
        var comboTwo = CombinationWithPrices(
            arrayListOf(aldi, jumbo),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices4),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices5),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices6)
            )
        )
        var comboThree = CombinationWithPrices(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices7),
                ProductInShopWithPrices(Product(), Shop(), 0.50,suggestionsPrices8),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices9)
            )
        )
        var comboThree_2 = CombinationWithPrices(
            arrayListOf(aldi, jumbo, lidl),
            arrayListOf(
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices10),
                ProductInShopWithPrices(Product(), Shop(), 0.40,suggestionsPrices11),
                ProductInShopWithPrices(Product(), Shop(), 1.00,suggestionsPrices12)
            )
        )
        var cheapestCombo = listGenerator.getCombinationWithBestPriceWithPrices(
            arrayListOf(
                comboOne,
                comboTwo,
                comboThree,
                comboThree_2
            ), 3
        )

        Assert.assertEquals(cheapestCombo, comboThree_2)
    }

    @Test
    fun getDirections_Test() {
        listGenerator.GoogleDirectionsService = MockGoogleDirectionsService().apply {
            json = JSONObject(
                "{\n" +
                        "   \"routes\" : [\n" +
                        "      {\n" +
                        "         \"legs\" : [\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"0.5 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"2 min\",\n" +
                        "                  \"value\" : 50\n" +
                        "               }\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"0.5 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"1 min\",\n" +
                        "                  \"value\" : 50\n" +
                        "               }\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"2 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"1 min\",\n" +
                        "                  \"value\" : 46\n" +
                        "               }\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"1 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"1 min\",\n" +
                        "                  \"value\" : 46\n" +
                        "               }\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      }\n" +
                        "   ],\n" +
                        "   \"status\" : \"OK\"\n" +
                        "}"
            )
        }
        val combination = Combination(arrayListOf(Shop()), arrayListOf(ProductInShop()))
        listGenerator.getDirections(combination)

        Assert.assertEquals(combination.directions!!.distancetoTravel!!, 808.0, 0.001)
        Assert.assertEquals(combination.directions!!.timeToTravel!!, 192.0, 0.001)
    }

    @Test
    fun getFinalCombinations_Test() {
        listGenerator.GoogleDirectionsService = MockGoogleDirectionsService().apply {
            json = JSONObject(
                "{\n" +
                        "   \"routes\" : [\n" +
                        "      {\n" +
                        "         \"legs\" : [\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"0.5 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"2 min\",\n" +
                        "                  \"value\" : 50\n" +
                        "               }\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"0.5 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"1 min\",\n" +
                        "                  \"value\" : 50\n" +
                        "               }\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"2 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"1 min\",\n" +
                        "                  \"value\" : 46\n" +
                        "               }\n" +
                        "            },\n" +
                        "            {\n" +
                        "               \"distance\" : {\n" +
                        "                  \"text\" : \"1 km\",\n" +
                        "                  \"value\" : 202\n" +
                        "               },\n" +
                        "               \"duration\" : {\n" +
                        "                  \"text\" : \"1 min\",\n" +
                        "                  \"value\" : 46\n" +
                        "               }\n" +
                        "            }\n" +
                        "         ]\n" +
                        "      }\n" +
                        "   ],\n" +
                        "   \"status\" : \"OK\"\n" +
                        "}"
            )
        }
        val combo1 =
            Combination(arrayListOf(Shop()), arrayListOf(ProductInShop(bananas, lidl, 1.5)))
        val combo1_2 =
            Combination(arrayListOf(Shop()), arrayListOf(ProductInShop(bananas, lidl, 0.5)))
        val combo2 =
            Combination(arrayListOf(Shop(), Shop()), arrayListOf(ProductInShop(bananas, lidl, 1.5)))
        val combo2_2 =
            Combination(arrayListOf(Shop(), Shop()), arrayListOf(ProductInShop(bananas, lidl, 0.5)))
        val combo3 = Combination(
            arrayListOf(Shop(), Shop(), Shop()),
            arrayListOf(ProductInShop(bananas, lidl, 1.5))
        )
        val combo3_2 = Combination(
            arrayListOf(Shop(), Shop(), Shop()),
            arrayListOf(ProductInShop(bananas, lidl, 0.5))
        )
        val combinations = arrayListOf(combo1, combo2, combo3, combo1_2, combo2_2, combo3_2)
        listGenerator.getFinalCombinations(combinations)
        Assert.assertEquals(combo1_2, listGenerator.oneShopCombination)
        Assert.assertEquals(combo2_2, listGenerator.twoShopCombination)
        Assert.assertEquals(combo3_2, listGenerator.threeShopCombination)

        Assert.assertEquals(combo1_2.directions!!.timeToTravel!!, 192.0, 0.001)
        Assert.assertEquals(combo2_2.directions!!.timeToTravel!!, 192.0, 0.001)
        Assert.assertEquals(combo3_2.directions!!.timeToTravel!!, 192.0, 0.001)
    }


}

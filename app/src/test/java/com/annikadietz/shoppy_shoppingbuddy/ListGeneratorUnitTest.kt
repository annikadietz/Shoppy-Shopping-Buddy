package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.ui.your_list.ShopCombinationRecyclerAdapter
import com.annikadietz.shoppy_shoppingbuddy.ui.your_list.ShoppingListRecyclerAdapter
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test

import org.mockito.Mockito.*
import org.junit.runner.RunWith;
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class ListGeneratorUnitTest {
    var listGenerator = ListGenerator(MockActivity(), "Hoitingeslag%2029,%207824%20KG")
    var shops = arrayListOf<Shop>()
    var productsInShops = arrayListOf<ShoppingItem>()
    var combos = arrayListOf<Combination>()

    var potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
    var pizza = Product("Pizza - Italia", Type("Frozen"))
    var bananas = Product("Bananas - 1kg", Type("Fruits"))
    var eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))

    var jumbo = Shop("Jumbo", "7824JA", "Kerspellaan 9")
    var aldi = Shop("Aldi", "7824CP", "Peyserhof 2")
    var lidl = Shop("Lidl", "7823PH", "Houtweg 151")

    var potatoesInJumbo = ShoppingItem(potatoes, jumbo, 2.5)
    var potatoesInAldi = ShoppingItem(potatoes, aldi, 2.5)
    var potatoesInLidl = ShoppingItem(potatoes, lidl, 2.5)

    var pizzaInJumbo = ShoppingItem(pizza, jumbo, 2.5)
    var pizzaInAldi = ShoppingItem(pizza, aldi, 2.5)
    var pizzaInLidl = ShoppingItem(pizza, lidl, 2.5)

    var bananasInJumbo = ShoppingItem(bananas, jumbo, 2.5)
    var bananasInAldi = ShoppingItem(bananas, aldi, 2.5)
    var bananasInLidl = ShoppingItem(bananas, lidl, 2.5)

    var eggsInJumbo = ShoppingItem(eggs, jumbo, 2.5)
    var eggsInAldi = ShoppingItem(eggs, aldi, 2.5)
    var eggsInLidl = ShoppingItem(eggs, lidl, 2.5)

    var combo1 = Combination(arrayListOf(jumbo), arrayListOf())
    var combo2 = Combination(arrayListOf(aldi), arrayListOf())
    var combo3 = Combination(arrayListOf(lidl), arrayListOf())
    var combo4 = Combination(arrayListOf(jumbo, aldi), arrayListOf())
    var combo5 = Combination(arrayListOf(jumbo, lidl), arrayListOf())
    var combo6 = Combination(arrayListOf(aldi, lidl), arrayListOf())
    var combo7 = Combination(arrayListOf(jumbo, aldi, lidl), arrayListOf())

    fun createObjects() {
        shops = arrayListOf<Shop>()
        productsInShops = arrayListOf<ShoppingItem>()

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

        potatoesInJumbo = ShoppingItem(potatoes, jumbo, 2.5)
        potatoesInAldi = ShoppingItem(potatoes, aldi, 2.5)
        potatoesInLidl = ShoppingItem(potatoes, lidl, 2.5)

        pizzaInJumbo = ShoppingItem(pizza, jumbo, 2.5)
        pizzaInAldi = ShoppingItem(pizza, aldi, 2.5)
        pizzaInLidl = ShoppingItem(pizza, lidl, 2.5) // the cheap one

        bananasInJumbo = ShoppingItem(bananas, jumbo, 2.5)
        bananasInAldi = ShoppingItem(bananas, aldi, 2.5)
        bananasInLidl = ShoppingItem(bananas, lidl, 2.5)

        eggsInJumbo = ShoppingItem(eggs, jumbo, 2.5)
        eggsInAldi = ShoppingItem(eggs, aldi, 2.5)
        eggsInLidl = ShoppingItem(eggs, lidl, 2.5)
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

        combos.add(combo1)
//        combos.add(combo2)
//        combos.add(combo3)
        combos.add(combo4)
//        combos.add(combo5)
//        combos.add(combo6)
        combos.add(combo7)
    }

    fun findCheapestStore(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>): ArrayList<ShoppingItem> {
        var cheapestShop: Shop
        var finalShoppingList = arrayListOf<ShoppingItem>()
        var cheapestPrice: Double = Double.MAX_VALUE
        shops.forEach {
            var shop = it
            var price = 0.0;
            var productsFound = arrayListOf<ShoppingItem?>()
            shoppingList.forEach { it ->
                var product = it
                var productInShop =
                    productsInShops.find { actor -> actor.shop == shop && actor.product == product }
                if (productInShop != null) {
                    price += productInShop.price.price
                    productsFound.add(productInShop)
                }

                if (price < cheapestPrice) {
                    cheapestPrice = price
                    finalShoppingList = productsFound.clone() as ArrayList<ShoppingItem>
                }
            }
        }
            return finalShoppingList
        }

        @Test
        fun findsCheapestStore_Test() {
            createObjects()
            pizzaInAldi.price.price = 0.5
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
        }

        @Test
        fun findAllPossibleStoreCombinations_Test() {
            createObjects()
            setup()

            var result = listGenerator.findAllPossibleStoreCombinations(shops)
            var foundCombo1 = result.find { c -> c.shops!!.contains(jumbo) }
            var foundCombo2 = result.find { c -> c.shops!!.contains(aldi) }
            var foundCombo3 = result.find { c -> c.shops!!.contains(lidl) }
            var foundCombo4 =
                result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) }
            var foundCombo5 =
                result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(lidl) }
            var foundCombo6 =
                result.find { c -> c.shops!!.contains(lidl) && c.shops!!.contains(aldi) }
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
        }

        @Test
        fun findBestPriceInShopCombination_Test() {
            createObjects()
            eggsInAldi.price.price = 10.0
            eggsInJumbo.price.price = 5.0
            eggsInLidl.price.price = 1.0
            setup()
            var combination = Combination(arrayListOf(jumbo, aldi), arrayListOf())
            var result =
                listGenerator.findBestPriceInShopCombination(eggs, combination, productsInShops)
            Assert.assertEquals(eggsInJumbo, result)

            combination = Combination(arrayListOf(jumbo, lidl, aldi), arrayListOf())
            result =
                listGenerator.findBestPriceInShopCombination(eggs, combination, productsInShops)
            Assert.assertEquals(eggsInLidl, result)
        }

        @Test
        fun getCombinationsWithProductsInShops_Test() {
            createObjects()
            pizzaInJumbo.price.price = 1.0
            bananasInAldi.price.price = 1.0
            potatoesInJumbo.price.price = 1.0
            eggsInAldi.price.price = 1.0
            setup()

            var shoppingList = arrayListOf<Product>().apply {
                add(pizza)
                add(bananas)
                add(potatoes)
                add(eggs)
            }

            var adapter = ShopCombinationRecyclerAdapter({}, arrayListOf())
            var result = listGenerator.getCombinationsWithProductsInShops(
                shops,
                shoppingList,
                productsInShops,
                adapter
            )
            var combination =
                result.find { c -> c.shops!!.contains(jumbo) && c.shops!!.contains(aldi) }

            print(result)
            Assert.assertTrue(combination?.shoppingItems!!.contains(pizzaInJumbo))
            Assert.assertTrue(combination?.shoppingItems!!.contains(bananasInAldi))
            Assert.assertTrue(combination?.shoppingItems!!.contains(potatoesInJumbo))
            Assert.assertTrue(combination?.shoppingItems!!.contains(eggsInAldi))
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

        @Test
        fun getPriceFromCombination_Test() {
            createObjects()
            setup()
            potatoesInJumbo.price.price = 1.00
            eggsInJumbo.price.price = 2.00
            pizzaInJumbo.price.price = 2.00
            bananasInJumbo.price.price = 1.00

            combo4.shoppingItems?.add(potatoesInJumbo)
            combo4.shoppingItems?.add(eggsInJumbo)
            combo4.shoppingItems?.add(pizzaInJumbo)
            combo4.shoppingItems?.add(bananasInJumbo)
            val ans = listGenerator.getPriceFromCombination(combo4)
            Assert.assertEquals(ans, 6.00, 0.0001)
        }

        // TODO("Create 3 different testing")
        @Test
        fun getCombinationWithBestPrice_Test() {
            createObjects()
            setup()

            var comboOne = Combination(
                arrayListOf(aldi),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 0.50),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            var comboOne_2 = Combination(
                arrayListOf(aldi),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 0.40),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            var comboTwo = Combination(
                arrayListOf(aldi, jumbo),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            var comboThree = Combination(
                arrayListOf(aldi, jumbo, lidl),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00)
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

            comboOne = Combination(
                arrayListOf(aldi),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            comboTwo = Combination(
                arrayListOf(aldi, jumbo),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 0.50),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            var comboTwo_2 = Combination(
                arrayListOf(aldi, jumbo),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 0.40),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            comboThree = Combination(
                arrayListOf(aldi, jumbo, lidl),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            cheapestCombo = listGenerator.getCombinationWithBestPrice(
                arrayListOf(
                    comboOne,
                    comboTwo,
                    comboThree,
                    comboTwo_2
                ), 2
            )

            Assert.assertEquals(cheapestCombo, comboTwo_2)

            comboOne = Combination(
                arrayListOf(aldi),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            comboTwo = Combination(
                arrayListOf(aldi, jumbo),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            comboThree = Combination(
                arrayListOf(aldi, jumbo, lidl),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 0.50),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            var comboThree_2 = Combination(
                arrayListOf(aldi, jumbo, lidl),
                arrayListOf(
                    ShoppingItem(Product(), Shop(), 1.00),
                    ShoppingItem(Product(), Shop(), 0.40),
                    ShoppingItem(Product(), Shop(), 1.00)
                )
            )
            cheapestCombo = listGenerator.getCombinationWithBestPrice(
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
            val combination = Combination(arrayListOf(Shop()), arrayListOf(ShoppingItem()))
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
                Combination(arrayListOf(Shop()), arrayListOf(ShoppingItem(bananas, lidl, 1.5)))
            val combo1_2 =
                Combination(arrayListOf(Shop()), arrayListOf(ShoppingItem(bananas, lidl, 0.5)))
            val combo2 = Combination(
                arrayListOf(Shop(), Shop()),
                arrayListOf(ShoppingItem(bananas, lidl, 1.5))
            )
            val combo2_2 = Combination(
                arrayListOf(Shop(), Shop()),
                arrayListOf(ShoppingItem(bananas, lidl, 0.5))
            )
            val combo3 = Combination(
                arrayListOf(Shop(), Shop(), Shop()),
                arrayListOf(ShoppingItem(bananas, lidl, 1.5))
            )
            val combo3_2 = Combination(
                arrayListOf(Shop(), Shop(), Shop()),
                arrayListOf(ShoppingItem(bananas, lidl, 0.5))
            )
            val combinations = arrayListOf(combo1, combo2, combo3, combo1_2, combo2_2, combo3_2)

            var adapter = ShopCombinationRecyclerAdapter({}, arrayListOf())
            listGenerator.getFinalCombinations(combinations, adapter)
            Assert.assertEquals(combo1_2, listGenerator.oneShopCombination)
            Assert.assertEquals(combo2_2, listGenerator.twoShopCombination)
            Assert.assertEquals(combo3_2, listGenerator.threeShopCombination)

            Assert.assertEquals(combo1_2.directions!!.timeToTravel!!, 192.0, 0.001)
            Assert.assertEquals(combo2_2.directions!!.timeToTravel!!, 192.0, 0.001)
            Assert.assertEquals(combo3_2.directions!!.timeToTravel!!, 192.0, 0.001)
        }
}

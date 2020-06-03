package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import org.json.JSONObject

object ListGenerator {

    fun findCheapestStore(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>, products: ArrayList<ProductInShop>) : ArrayList<ProductInShop> {

        // TODO: Replace all this with actual data from the database!!!!

        //var cheapestShop: Shop
        var finalShoppingList = arrayListOf<ProductInShop>()
        var cheapestPrice: Double = Double.MAX_VALUE
        shops.forEach{
            var shop = it
            var price = 0.0;
            var productsFound = arrayListOf<ProductInShop?>()
            shoppingList.forEach { it ->
                var product = it
                var productInShop = products.find {p ->p.shop == shop && p.product == product}
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

    fun findAllPossibleStoreCombinations(shops: ArrayList<Shop>) : ArrayList<Combination> {
        var combos = arrayListOf<Combination>()

        addAllCombinations(combos, shops.toTypedArray())

        return combos
    }

    fun addAllCombinations(combos: ArrayList<Combination>, shops: Array<Shop>) {
        val n = shops.size
        for (r in 0 until shops.size + 1) {
            addCombinationToCombos(shops, n, r, combos)
        }
    }

    fun addCombinationToCombos(shops: Array<Shop>, n: Int, r: Int, combos: ArrayList<Combination>) {
        val data = Array(r) { Shop() }

        combinationParts(shops, data, 0, n - 1, 0, r, combos)
    }

    fun combinationParts(
        shops: Array<Shop>, data: Array<Shop>, start: Int,
        end: Int, index: Int, r: Int, combos: ArrayList<Combination>
    ) {
        if (index == r) {
            var combination = Combination(arrayListOf(), arrayListOf())
            for (j in 0 until r) {
                combination.shops?.add(data[j])
            }
            if(combination.shops!!.size > 0) {
                combos.add(combination)
            }
            return
        }
        var i = start
        while (i <= end && end - i + 1 >= r - index) {
            data[index] = shops[i]
            combinationParts(shops, data, i + 1, end, index + 1, r, combos)
            i++
        }
    }

    fun findCheapestStoreCombinations(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>, products: ArrayList<ProductInShop>) : ArrayList<Combination> {
        var combos = findAllPossibleStoreCombinations(shops)
        combos.forEach {
            var combination = it
            shoppingList.forEach {
                var product = it
                var price = findBestPriceInShopCombination(product, combination, products)
                combination.prices?.add(price)
            }
        }

        return combos
    }

    fun findBestPriceInShopCombination(product: Product, combination: Combination, products: ArrayList<ProductInShop>) : ProductInShop {
        var lowestProductInShop = ProductInShop(product, Shop("Fake shop", "Fake address", "Fake address"), Double.MAX_VALUE)
        combination.shops?.forEach {
            var priceInShop = findPriceInShop(it, product, products)
            if (priceInShop.price < lowestProductInShop.price) {
                if (priceInShop != null) {
                    lowestProductInShop = priceInShop
                }
            }
        }

        return lowestProductInShop
    }

    fun findPriceInShop(shop: Shop, product: Product, products: ArrayList<ProductInShop>) : ProductInShop {
        var result = products.find { p ->p.shop == shop && p.product == product }
        if (result != null) {
            return result
        }
        else {
            return ProductInShop(product, Shop("Fake shop", "Fake address", "Fake address"), Double.MAX_VALUE)
        }
    }

    fun sortedCombination(unsortedCombination: ArrayList<Combination>) : List<Combination> {
        var sorted = unsortedCombination.sortedBy { c -> c.prices?.sumByDouble { p -> p.price } }
        return sorted
    }

    fun findBestRoute(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>, products: ArrayList<ProductInShop>, currentPosition: String, context: Context)  {
        var cheapestCombinations = findCheapestStoreCombinations(shops, shoppingList, products)

        cheapestCombinations.forEach {
            var combinationShops = it.shops
            var origin = currentPosition
            var destination = currentPosition
            var waypoints = ""

            combinationShops?.forEachIndexed { index, it ->
                waypoints += it.streetAddress + " " + it.postCode
                if(index != combinationShops.lastIndex) {
                    waypoints += " | "
                }
            }

            val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${destination}&waypoints=${waypoints}&key=AIzaSyAvarQW1FBGHIf3Sr22AQva-J-1dPGHGOI"
            Log.w("URL", urlDirections)
            val directionsRequest = object : StringRequest(Request.Method.GET, urlDirections, Response.Listener<String> {
                    response ->
                val jsonResponse = JSONObject(response)
                val routes = jsonResponse.getJSONArray("routes")
                val legs = routes.getJSONObject(0).getJSONArray("legs")
                var totalDistance = 0;
                for (i in 0 until legs.length()) {
                    val distanceValue = legs.getJSONObject(i).getJSONObject("distance").getString("value")
                    Log.w("Distance", distanceValue)
                    totalDistance += distanceValue.toInt()
                }
                Log.w("Total Distance", totalDistance.toString())
                Log.w("JSON Response", jsonResponse.toString())
            }, Response.ErrorListener {
                    _ ->
            }){}
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(directionsRequest)
        }
    }
}
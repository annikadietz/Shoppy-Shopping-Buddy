package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information.ExpandableShoppingListAdapter
import kotlin.collections.ArrayList


class ListGenerator {
    var GoogleDirectionsService: GoogleDirectionsAPI = GoogleDirectionsService()
    var oneShopCombination: Combination = Combination()
    var twoShopCombination: Combination = Combination()
    var threeShopCombination: Combination = Combination()

    var oneShopCombinationWithPrices: CombinationWithPrices = CombinationWithPrices()
    var twoShopCombinationWithPrices: CombinationWithPrices = CombinationWithPrices()
    var threeShopCombinationWithPrices: CombinationWithPrices = CombinationWithPrices()

    var myLocation: String
    lateinit var context: Context
    constructor(context: Context, myLocation: String){
        this.context = context
        this.myLocation = myLocation
    }

    fun findCheapestStore(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>, productsInShops: ArrayList<ProductInShop>) : ArrayList<ProductInShop> {

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
                var productInShop = productsInShops.find { p ->p.shop.streetAddress == shop.streetAddress && p.shop.name == shop.name && p.product.name == product.name}
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
    //ToDo:after testing delete the old one
    fun findCheapestStoreWithPrices(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>, products: ArrayList<ProductInShopWithPrices>) : ArrayList<ProductInShopWithPrices> {

        // TODO: Replace all this with actual data from the database!!!!

        //var cheapestShop: Shop
        var finalShoppingList = arrayListOf<ProductInShopWithPrices>()
        var cheapestPrice: Double = Double.MAX_VALUE
        shops.forEach{
            var shop = it
            var price = 0.0;
            var productsFound = arrayListOf<ProductInShopWithPrices?>()
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
                finalShoppingList = productsFound.clone() as ArrayList<ProductInShopWithPrices>
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
//ToDo delete the old one after testing
fun findAllPossibleStoreCombinationsWithPrices(shops: ArrayList<Shop>) : ArrayList<CombinationWithPrices> {
    var combos = arrayListOf<CombinationWithPrices>()

    addAllCombinationsWithPrices(combos, shops.toTypedArray())
    return combos
}

    fun addAllCombinationsWithPrices(combos: ArrayList<CombinationWithPrices>, shops: Array<Shop>) {
        val n = shops.size
        for (r in 0 until shops.size + 1) {
            addCombinationToCombosWithPrices(shops, n, r, combos)
        }
    }

    fun addCombinationToCombosWithPrices(shops: Array<Shop>, n: Int, r: Int, combos: ArrayList<CombinationWithPrices>) {
        val data = Array(r) { Shop() }

        combinationPartsWithPrices(shops, data, 0, n - 1, 0, r, combos)
    }

    fun combinationPartsWithPrices(
        shops: Array<Shop>, data: Array<Shop>, start: Int,
        end: Int, index: Int, r: Int, combos: ArrayList<CombinationWithPrices>
    ) {
        if (index == r) {
            var combination = CombinationWithPrices(arrayListOf(), arrayListOf())
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
            combinationPartsWithPrices(shops, data, i + 1, end, index + 1, r, combos)
            i++
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getCombinationsWithProductsInShops(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>, products: ArrayList<ProductInShop>, listAdapter: ExpandableShoppingListAdapter) : ArrayList<Combination> {
        var combos = findAllPossibleStoreCombinations(shops)
        combos.forEach {
            var combination = it
            shoppingList.forEach {
                var product = it
                var price = findBestPriceInShopCombination(product, combination, products)
                combination.productsInShops?.add(price)
            }
        }
        getFinalCombinations(combos, listAdapter)

        return combos
    }
//ToDo delete the old function after testing
    @RequiresApi(Build.VERSION_CODES.N)
    fun getCombinationsWithProductsInShopsWithPrices(shops: ArrayList<Shop>, shoppingList: ArrayList<Product>, products: ArrayList<ProductInShopWithPrices>) : ArrayList<CombinationWithPrices> {
        var combos = findAllPossibleStoreCombinationsWithPrices(shops)
        combos.forEach {
            var combination = it
            shoppingList.forEach {
                var product = it
                var price = findBestPriceInShopCombinationWithPrices(product, combination, products)
                combination.productsInShopsWithPrices?.add(price)
            }
        }
        getFinalCombinationsWithPrices(combos)

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
    //ToDo delete after testing
    fun findBestPriceInShopCombinationWithPrices(product: Product, combination: CombinationWithPrices, products: ArrayList<ProductInShopWithPrices>) : ProductInShopWithPrices {
        var prices=ArrayList<SuggestionPrice?>()
        prices.add(SuggestionPrice(0.0,0.0))
        var lowestProductInShop = ProductInShopWithPrices(product, Shop("Fake shop", "Fake address", "Fake address"), Double.MAX_VALUE,prices)
        combination.shops?.forEach {
            var priceInShop = findPriceInShopWithPrices(it, product, products)
            if (priceInShop.price < lowestProductInShop.price) {
                if (priceInShop != null) {
                    lowestProductInShop = priceInShop
                }
            }
        }
        return lowestProductInShop
    }

    fun findPriceInShop(shop: Shop, product: Product, products: ArrayList<ProductInShop>) : ProductInShop {
        var result = products.find { p -> p.shop.streetAddress == shop.streetAddress && p.shop.name == shop.name && p.product.name == product.name }
        if (result != null) {
            return result
        }
        else {
            return ProductInShop(product, Shop("Fake shop", "Fake address", "Fake address"), Double.MAX_VALUE)
        }
    }
    //ToDo:after testing delete the old one
    fun findPriceInShopWithPrices(shop: Shop, product: Product, products: ArrayList<ProductInShopWithPrices>) : ProductInShopWithPrices {
        var result = products.find { p ->p.shop == shop && p.product == product }
        if (result != null) {
            return result
        }
        else {
           var prices=ArrayList<SuggestionPrice?>()
            prices.add(SuggestionPrice(0.0,0.0))
            return ProductInShopWithPrices(product, Shop("Fake shop", "Fake address", "Fake address"), Double.MAX_VALUE,prices)
        }
    }
    fun getPriceFromCombination (combination: Combination): Double {
        var fullPrice = 0.0
        combination.productsInShops?.forEach {
                fullPrice += it.price
        }
        return fullPrice
    }
    fun getCombinationWithBestPrice(combinations:ArrayList<Combination>, shopCount: Int) : Combination {
        var bestPriceCombination: Combination = Combination()
        bestPriceCombination.productsInShops = ArrayList<ProductInShop>()
        bestPriceCombination.productsInShops?.add(ProductInShop(Product(), Shop(), Double.MAX_VALUE))
        combinations.forEach{
            var combination = it
            if (combination.shops?.size == shopCount) {
                val new = getPriceFromCombination(combination)
                val old = getPriceFromCombination(bestPriceCombination)
                if (new <= old){
                    bestPriceCombination = combination

                }
            }
        }

        return bestPriceCombination
    }
//ToDo delete the old with prices
    fun getPriceFromCombinationWithPrices (combination: CombinationWithPrices): Double {
        var fullPrice = 0.0
        combination.productsInShopsWithPrices?.forEach {
            fullPrice += it.price
        }
        return fullPrice
    }
    fun getCombinationWithBestPriceWithPrices(combinations:ArrayList<CombinationWithPrices>, shopCount: Int) : CombinationWithPrices {
        var bestPriceCombination: CombinationWithPrices = CombinationWithPrices()
        bestPriceCombination.productsInShopsWithPrices = ArrayList<ProductInShopWithPrices>()
        bestPriceCombination.productsInShopsWithPrices?.add(ProductInShopWithPrices(Product(), Shop(), Double.MAX_VALUE,arrayListOf(SuggestionPrice(0.0,0.0))))
        combinations.forEach{
            var combination = it
            if (combination.shops?.size == shopCount) {
                val new = getPriceFromCombinationWithPrices(combination)
                val old = getPriceFromCombinationWithPrices(bestPriceCombination)
                if (new <= old){
                    bestPriceCombination = combination
                }
            }
        }

        return bestPriceCombination
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun getFinalCombinations(combinations: ArrayList<Combination>, listAdapter: ExpandableShoppingListAdapter){
        oneShopCombination = getCombinationWithBestPrice(combinations, 1)
        twoShopCombination = getCombinationWithBestPrice(combinations, 2)
        threeShopCombination = getCombinationWithBestPrice(combinations, 3)

        val apiCallThread = Thread(Runnable {
            try {
                getDirections(oneShopCombination)
                getDirections(twoShopCombination)
                getDirections(threeShopCombination)
                listAdapter.notifyDataSetChanged();
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        apiCallThread.start()
    }
    //ToDo delete the old one after testing
    @RequiresApi(Build.VERSION_CODES.N)
    fun getFinalCombinationsWithPrices(combinations: ArrayList<CombinationWithPrices>){
        oneShopCombinationWithPrices = getCombinationWithBestPriceWithPrices(combinations, 1)
        twoShopCombinationWithPrices = getCombinationWithBestPriceWithPrices(combinations, 2)
        threeShopCombinationWithPrices = getCombinationWithBestPriceWithPrices(combinations, 3)

        val apiCallThread = Thread(Runnable {
            try {
                getDirectionsWithPrices(oneShopCombinationWithPrices)
                getDirectionsWithPrices(twoShopCombinationWithPrices)
                getDirectionsWithPrices(threeShopCombinationWithPrices)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        apiCallThread.start()
    }
    // Takes a combination and adds directions to it
    @RequiresApi(Build.VERSION_CODES.N)
    fun getDirections(combination: Combination)  {
        var shops = combination.shops
        var origin = myLocation
        var destination = myLocation
        var waypoints = ""

        shops?.forEachIndexed { index, it ->
            waypoints += it.streetAddress + "%20" + it.postCode
            if(index != shops.lastIndex) {
                waypoints += "%20|%20"
            }
        }

        val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${destination}&waypoints=optimize:true|${waypoints}&key=AIzaSyAvarQW1FBGHIf3Sr22AQva-J-1dPGHGOI"
        var jsonResponse = GoogleDirectionsService.getDirections(urlDirections)

        val routes = jsonResponse.getJSONArray("routes")
        val legs = routes.getJSONObject(0)
            .getJSONArray("legs")
        var totalDistance: Double = 0.0;
        var totalTime: Double = 0.0;
        for (i in 0 until legs.length()) {
            val distanceValue = legs.getJSONObject(i).getJSONObject("distance").getString("value")
            val timeValue = legs.getJSONObject(i).getJSONObject("duration").getString("value")
            totalDistance += distanceValue.toInt()
            totalTime += timeValue.toInt()
        }

        combination.directions = Directions(totalDistance, totalTime)

        }

//Todo delete the old one after testing

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDirectionsWithPrices(combination: CombinationWithPrices)  {
        var shops = combination.shops
        var origin = myLocation
        var destination = myLocation
        var waypoints = ""

        shops?.forEachIndexed { index, it ->
            waypoints += it.streetAddress + "%20" + it.postCode
            if(index != shops.lastIndex) {
                waypoints += "%20|%20"
            }
        }

        val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${destination}&waypoints=optimize:true|${waypoints}&key=AIzaSyAvarQW1FBGHIf3Sr22AQva-J-1dPGHGOI"
        var jsonResponse = GoogleDirectionsService.getDirections(urlDirections)

        val routes = jsonResponse.getJSONArray("routes")
        val legs = routes.getJSONObject(0)
            .getJSONArray("legs")
        var totalDistance: Double = 0.0;
        var totalTime: Double = 0.0;
        for (i in 0 until legs.length()) {
            val distanceValue = legs.getJSONObject(i).getJSONObject("distance").getString("value")
            val timeValue = legs.getJSONObject(i).getJSONObject("duration").getString("value")
            totalDistance += distanceValue.toInt()
            totalTime += timeValue.toInt()
        }

        combination.directions = Directions(totalDistance, totalTime)
        print("done")
//            val directionsRequest = object : StringRequest(Request.Method.GET, urlDirections, Response.Listener<String> {
//                    response ->
//                val jsonResponse = JSONObject(response)
//                val routes = jsonResponse.getJSONArray("routes")
//                val legs = routes.getJSONObject(0).getJSONArray("legs")
//                var totalDistance = 0;
//                for (i in 0 until legs.length()) {
//                    val distanceValue = legs.getJSONObject(i).getJSONObject("distance").getString("value")
//                    totalDistance += distanceValue.toInt()
//                }
//            }, Response.ErrorListener {
//                    _ ->
//            }){}
//            val requestQueue = Volley.newRequestQueue(context)
//            requestQueue.add(directionsRequest)
    }

}
package com.annikadietz.shoppy_shoppingbuddy

import android.util.Log
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase

object NewDatabaseHelper {
    var db = Firebase.firestore
    lateinit private var shops: MutableList<Shop>
    lateinit private var products: MutableList<Product>
    lateinit private var suggestionPrices: ArrayList<SuggestionPrice?>
    lateinit var productsInShops: ArrayList<ProductInShop>
    lateinit var productsInShopsWithPrices: ArrayList<ProductInShopWithPrices>

    fun subscribeShops() {
        productsInShops = ArrayList()
        db.collection("shops")
            .get()
            .addOnSuccessListener { result ->
                shops = (result.toObjects(Shop::class.java))
                subscribeProducts()
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun subscribeProducts() {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                products = result.toObjects(Product::class.java)
                subscribeProductInShop()
             //   subscribeProductInShopWithPrices()
               // subscribePrices()
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

//    //to add the prices
//    fun subscribePrices() {
//        db.collection("prices")
//            .get()
//            .addOnSuccessListener { result ->
//                suggestionPrices = result.toObjects(SuggestionPrice::class.java)
//                subscribeProductInShopWithPrices()
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Prices", "Error getting documents.", exception)
//            }
//    }

    fun subscribeProductInShop() {
        db.collection("productsInShops")
            .get()
            .addOnSuccessListener { result ->
                productsInShops = ArrayList<ProductInShop>()
                result.forEach {
                    var exampleShop = it["shop"] as HashMap<String, String>
                    var exampleProduct = it["product"] as HashMap<String, String>
                    var price = it.getField<Double>("price")

                    var shop = shops.find { s -> s.name == exampleShop["name"] && s.postCode == exampleShop["postCode"] && s.streetAddress == exampleShop["streetAddress"] }
                    var product = products.find { p -> p.name == exampleProduct["name"]}
                    if(product != null && shop != null && price != null) {
                        productsInShops.add(ProductInShop(product, shop, price))
                    }
                }
                Log.w("productsIn", productsInShops.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }
    fun subscribeProductInShopWithPrices() {
        db.collection("productsInShopsWithPrices")
            .get()
            .addOnSuccessListener { result ->
                productsInShopsWithPrices = ArrayList<ProductInShopWithPrices>()
                result.forEach {
                   var examplePrices = it["suggestionPrice"] as ArrayList<SuggestionPrice?>
                    var exampleShop = it["shop"] as HashMap<String, String>
                    var exampleProduct = it["product"] as HashMap<String, String>
                    examplePrices.forEach{
                        var price = it?.price
                        var counter = it?.counter
                        var prices=SuggestionPrice(price,counter)
                        suggestionPrices.add(prices)
                    }
                    var price = it.getField<Double>("price")
                    var shop = shops.find { s -> s.name == exampleShop["name"] && s.postCode == exampleShop["postCode"] && s.streetAddress == exampleShop["streetAddress"] }
                    var product = products.find { p -> p.name == exampleProduct["name"]}

                    if(product != null && shop != null && price != null) {
                        productsInShopsWithPrices.add(ProductInShopWithPrices(product, shop, price,examplePrices))
                    }
                }
                Log.w("productsIn", productsInShopsWithPrices.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun getShops() : MutableList<Shop> {
        return shops
    }

    fun getProducts() : MutableList<Product> {
        return products
    }
    fun getPrices() : ArrayList<SuggestionPrice?> {
        return suggestionPrices
    }

    fun getProductsInShops() : MutableList<ProductInShop> {
        return productsInShops
    }
    fun getProductsInShopsWithPrices() : MutableList<ProductInShopWithPrices> {
        return productsInShopsWithPrices
    }
}
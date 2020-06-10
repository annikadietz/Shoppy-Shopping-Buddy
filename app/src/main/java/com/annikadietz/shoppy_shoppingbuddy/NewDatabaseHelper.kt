package com.annikadietz.shoppy_shoppingbuddy

import android.util.Log
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase

object NewDatabaseHelper : DatabaseHelperInterface {
    var db = Firebase.firestore
    lateinit private var shops: MutableList<Shop>
    private var products = arrayListOf<Product>()
    lateinit var productsInShops: ArrayList<ProductInShop>

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
            .addOnSuccessListener { results ->
                Log.w("listener", "Got results")
                products.clear()
                results.forEach { product -> products.add(product.toObject(Product::class.java)) }
                subscribeProductInShop()
                Log.w("listener", products.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

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

    override fun getShops() : MutableList<Shop> {
        return shops
    }

    override fun getProducts() : ArrayList<Product> {
        return products
    }

    override fun getProductsInShops() : MutableList<ProductInShop> {
        return productsInShops
    }
}
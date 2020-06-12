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
    private var shops = arrayListOf<Shop>()
    private var products = arrayListOf<Product>()
    var productsInShops = arrayListOf<ProductInShop>()

    fun subscribeShops() {
        db.collection("shops")
            .get()
            .addOnSuccessListener { results ->
                shops.clear()
                results.forEach { shop -> shops.add(shop.toObject(Shop::class.java)) }
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun subscribeProducts() {
        db.collection("products")
            .get()
            .addOnSuccessListener { results ->
                products.clear()
                results.forEach { product -> products.add(product.toObject(Product::class.java)) }
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun subscribeProductInShop() {
        db.collection("productsInShops")
            .get()
            .addOnSuccessListener { results ->
                productsInShops.clear()
                results.forEach { product -> productsInShops.add(product.toObject(ProductInShop::class.java)) }
            }
            .addOnFailureListener { exception ->
                Log.w("productsInShops", "Error getting documents.", exception)
            }
    }
//get shops
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
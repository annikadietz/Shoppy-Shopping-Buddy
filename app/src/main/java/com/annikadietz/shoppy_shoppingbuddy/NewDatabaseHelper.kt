package com.annikadietz.shoppy_shoppingbuddy

import android.util.Log
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import java.io.Serializable

object NewDatabaseHelper : DatabaseHelperInterface {
    var db = Firebase.firestore
    lateinit private var shops: MutableList<Shop>
    private var myShops = arrayListOf<Shop>()
    private var products = arrayListOf<Product>()
    lateinit var productsInShops: ArrayList<ProductInShop>
    lateinit var uid: String

    fun subscribeShops() {
        productsInShops = ArrayList()
        db.collection("shops")
            .get()
            .addOnSuccessListener { result ->
                shops = (result.toObjects(Shop::class.java))
                subscribeProducts()
                subscribeMyShops()
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

    fun subscribeMyShops() {
        db.collection("myShops")
            .get()
            .addOnSuccessListener { result ->
                myShops = ArrayList<Shop>()
                result.forEach {
                    if (it["uid"] == uid) {
                        var exampleShop = it["shop"] as HashMap<String, String>
                        var shop = shops.find { s -> s.name == exampleShop["name"] && s.postCode == exampleShop["postCode"] && s.streetAddress == exampleShop["streetAddress"] }
                        if (shop != null) {
                            myShops.add(shop)
                        }
                    }
                }
                Log.w("productsIn", myShops.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun deleteMyShop(shop : Shop) {
        db.collection("myShops")
            .get()
            .addOnSuccessListener { result ->
                productsInShops = ArrayList<ProductInShop>()
                result.forEach {
                    if (it["uid"] == uid) {
                        var dbShop = it["shop"] as HashMap<String, String>
                        if(dbShop["name"] == shop.name && dbShop["streetAddress"] == shop.streetAddress && dbShop["postCode"] == shop.postCode) {
                            it.id
                            db.collection("myShops").document(it.id)
                                .delete()
                                .addOnSuccessListener {
                                    subscribeMyShops()
                                    Log.d("delete my shops", "DocumentSnapshot successfully deleted!")
                                }
                                .addOnFailureListener { e -> Log.w("delete my shops", "Error deleting document", e) }
                        }
                    }
                }
                Log.w("productsIn", myShops.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun addMyShop(shop : Shop) {
        val docData: HashMap<String, Any> = hashMapOf(
            "shop" to hashMapOf(
                "name" to shop.name,
                "postCode" to shop.postCode,
                "streetAddress" to shop.streetAddress
            ),
            "uid" to uid
        )

        db.collection("myShops").document()
            .set(docData)
            .addOnSuccessListener {
                subscribeMyShops()
                Log.d("add my shops", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.w("add my shops", "Error writing document", e) }
    }

    override fun getMyShops(): ArrayList<Shop> {
        return myShops
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
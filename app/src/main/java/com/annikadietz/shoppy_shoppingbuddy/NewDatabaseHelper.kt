package com.annikadietz.shoppy_shoppingbuddy

import android.util.Log
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object NewDatabaseHelper : DatabaseHelperInterface {
    private var db = Firebase.firestore
    private var shops = arrayListOf<Shop>()
    private var myShops = arrayListOf<Shop>()
    private var products = arrayListOf<Product>()
    private var productsInShops= arrayListOf<ProductInShop>()
    var uid: String = ""

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

    fun subscribeMyShops() {
    db.collection("myShops")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener { results ->
                    myShops.clear()
                    results.forEach {
                        var shopParameters = it["shop"] as HashMap<String, String>
                        var shop = Shop(shopParameters["name"]!!, shopParameters["postCode"]!!, shopParameters["streetAddress"]!!)
                        myShops.add(shop)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("shops", "Error getting documents.", exception)
                }
    }

 var shopsInDatabase = db.collection("myShops")
            .whereEqualTo("shop.name", shop.name)
            .whereEqualTo("shop.postCode", shop.postCode)
            .whereEqualTo("shop.streetAddress", shop.streetAddress)
            .whereEqualTo("uid", uid)
            .get()
        shopsInDatabase.addOnSuccessListener {
            it.forEach {
                db.collection("myShops").document(it.id).delete()
            }
        }

    fun addMyShop(shop : Shop) {
        val myShop: HashMap<String, Any> = hashMapOf(
            "shop" to shop,
            "uid" to uid
        )
        db.collection("myShops").document()
            .set(myShop)
            .addOnSuccessListener {
                Log.d("add my shops", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> Log.w("add my shops", "Error writing document", e) }
    }

    override fun getMyShops(): ArrayList<Shop> {
        return myShops
    }

    override fun getShops() : ArrayList<Shop> {
        return shops
    }

    override fun getProducts() : ArrayList<Product> {
        return products
    }

    override fun getProductsInShops() : ArrayList<ProductInShop> {
        return productsInShops
    }
}

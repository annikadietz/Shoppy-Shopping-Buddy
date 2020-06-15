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
    private var productsInShops = arrayListOf<ProductInShop>()
    private var myShoppingList = arrayListOf<Product>()
    var uid: String = ""

    fun subscribeShops() {
        db.collection("shops")
            .addSnapshotListener { results, e ->

                shops.clear()
                results?.forEach { shop -> shops.add(shop.toObject(Shop::class.java)) }
            }

    }

    fun subscribeProducts() {
        db.collection("products")
            .addSnapshotListener { results, e ->
                products.clear()
                results?.forEach { product -> products.add(product.toObject(Product::class.java)) }
            }

    }

    fun subscribeProductInShop() {
        db.collection("productsInShops")
            .addSnapshotListener { results, e ->
                productsInShops.clear()
                results?.forEach { product -> productsInShops.add(product.toObject(ProductInShop::class.java)) }
            }

    }

    fun subscribeMyShops() {
        db.collection("myShops")
            .whereEqualTo("uid", uid)
            .addSnapshotListener { results, e ->
                myShops.clear()
                results?.forEach {
                    var shopParameters = it["shop"] as HashMap<String, String>
                    var shop = Shop(
                        shopParameters["name"]!!,
                        shopParameters["postCode"]!!,
                        shopParameters["streetAddress"]!!
                    )
                    myShops.add(shop)
                }
            }

    }

    fun subscribeMyShoppingList() {
        db.collection("shoppingLists")
            .document(uid)
            .collection("shoppingList")
            .addSnapshotListener { results, e ->
                myShoppingList.clear()
                results?.forEach { product -> myShoppingList.add(product.toObject(Product::class.java)) }
            }

    }

    fun deleteMyShop(shop: Shop) {
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
    }

    fun addMyShop(shop: Shop) {
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

    override fun getShops(): ArrayList<Shop> {
        return shops
    }

    override fun getProducts(): ArrayList<Product> {
        return products
    }

    override fun getProductsInShops(): ArrayList<ProductInShop> {
        return productsInShops
    }

    fun addProductToMyShoppingList(product: Product) {
        db.collection("shoppingLists")
            .document(uid)
            .collection("shoppingList")
            .add(product)
    }
    fun deleteProductFormMyShoppingList(product: Product) {
        var matchingShoppingListEntries = db.collection("shoppingLists")
            .document(uid)
            .collection("shoppingList")
            .whereEqualTo("name", product.name)
            .whereEqualTo("type.name", product.type?.name)
            .get()
        matchingShoppingListEntries.addOnSuccessListener {
            it.forEach {
                db.collection("shoppingLists")
                    .document(uid)
                    .collection("shoppingList")
                    .document(it.id)
                    .delete()
            }
        }
    }

    override fun getMyShoppingList(): ArrayList<Product> {
        return myShoppingList
    }
}

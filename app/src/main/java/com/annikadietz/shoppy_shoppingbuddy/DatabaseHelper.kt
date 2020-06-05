package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import com.google.firebase.firestore.*


object DatabaseHelper {
    var db = Firebase.firestore
    lateinit private var shops: MutableList<DocumentSnapshot>
    lateinit private var products: MutableList<DocumentSnapshot>
    lateinit private var categories: MutableList<DocumentSnapshot>

    fun writeNewProduct(name: String, category: DocumentSnapshot, price: Double, lastConfirmed: Date, shop: DocumentSnapshot, context: Context) {
        val product = hashMapOf(
            "Name" to name,
            "Price" to price,
            "Category" to category.reference,
            "LastConfirmed" to lastConfirmed,
            "ShopReference" to shop.reference
        )

        db.collection("Product")
            .add(product)
            .addOnSuccessListener {
                Log.d("ProductAdding", "DocumentSnapshot added with ID: ${it.id}")
                Toast.makeText(context, "Product was added.", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Log.d("ProductAdding", "DocumentSnapshot added with ID: ${it.message}")
                Toast.makeText(context, "There was a problem adding your product. Please try again later.", Toast.LENGTH_LONG).show()
            }
    }

    fun subscribeShops() {
        db.collection("Shop")
            .get()
            .addOnSuccessListener { result ->
                shops = result.documents
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun subscribeProducts() {
        db.collection("Product")
            .get()
            .addOnSuccessListener { result ->
                products = result.documents
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun subscribeCategories() {
        db.collection("Category")
            .get()
            .addOnSuccessListener { result ->
                categories = result.documents
            }
            .addOnFailureListener { exception ->
                Log.w("categories", "Error getting documents.", exception)
            }
    }

    fun getShops(): MutableList<DocumentSnapshot> {
        return shops
    }

    fun getProducts(): MutableList<DocumentSnapshot> {
        return products
    }

    fun getCategories(): MutableList<DocumentSnapshot> {
        return categories
    }

    fun search(searchTerm: String, view: LinearLayout) {
        view.removeAllViews()
        var productResultsUnsorted: MutableList<DocumentSnapshot> = mutableListOf()
        var productResults: List<DocumentSnapshot> = mutableListOf()

        products.forEach {
            var shop = it.toObject(Shop::class.java)
            if(shop?.Name?.toLowerCase()?.contains(searchTerm.toLowerCase())!!) {
                productResultsUnsorted.add(it)
            }
        }

        productResults = productResultsUnsorted.sortedBy { it["Price"] as Double }

        productResults.forEach {
            val productDocument = it
            val shopReference = it["ShopReference"] as DocumentReference
            val categoryReference = it["Category"] as DocumentReference

            var product = ProductOld()
            product.Category = productDocument["Category"] as DocumentReference
            product.Name = productDocument["Name"].toString()
            product.Price = productDocument["Price"].toString().toDouble()
            product.ShopReference = productDocument["ShopReference"] as DocumentReference
            var shop: Shop? = Shop()
            var category: Category? = Category()
            shops.forEach {
                if (it.id == shopReference.id) {
                    shop = it.toObject(Shop::class.java)
                }
            }

            categories.forEach {
                if (it.id == categoryReference.id) {
                    category = it.toObject(Category::class.java)
                }
            }

            writeLine(view, product!!, shop!!, category!!)
        }
    }

    private fun writeLine(view: LinearLayout, product: ProductOld, shop: Shop, category: Category) {
        val firstRowLayout = LinearLayout(view.context)
        firstRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val secondRowLayout = LinearLayout(view.context)
        secondRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val thirdRowLayout = LinearLayout(view.context)
        thirdRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val fourthRowLayout = LinearLayout(view.context)
        fourthRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)

        val productNameLabel = TextView(view.context)
        val productPriceLabel = TextView(view.context)
        val shopNameLabel = TextView(view.context)
        val shopAddressLabel = TextView(view.context)

        productNameLabel.text = product.Name + " (" + category.Name + ")"
        productPriceLabel.text = product.Price.toString() + "€"
        shopNameLabel.text = shop.Name
        shopAddressLabel.text = shop.Address

        firstRowLayout.addView(productNameLabel)
        firstRowLayout.addView(productPriceLabel)

        secondRowLayout.addView(shopNameLabel)

        thirdRowLayout.addView(shopAddressLabel)

        fourthRowLayout.addView(TextView(view.context))

        view.addView(firstRowLayout)
        view.addView(secondRowLayout)
        view.addView(thirdRowLayout)
        view.addView(fourthRowLayout)
    }
}

class ProductOld {
    var Name: String? = ""
    lateinit var Category: DocumentReference
    var Price: Double? = -1.0
    // var LastConfirmed: Date = Date("01-01-2000"),
    lateinit var ShopReference: DocumentReference

    constructor(){}
}

class Shop {
    var Name: String? = ""
    var Address: String? = ""

    constructor() {}
}

class Category {
    var Name: String? = ""

    constructor() {}
}
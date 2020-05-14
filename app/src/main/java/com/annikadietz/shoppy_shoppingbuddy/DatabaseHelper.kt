package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import android.content.Intent
import com.google.firebase.firestore.*
import com.google.firebase.firestore.local.ReferenceSet
import com.google.firebase.firestore.model.DocumentKey
import com.google.firebase.firestore.model.ResourcePath
import com.google.firebase.storage.FirebaseStorage
import java.lang.ref.Reference


object DatabaseHelper {
    var db = Firebase.firestore
    private var shops: MutableList<Shop> = mutableListOf()
    private var products: MutableList<Product> = mutableListOf()

    fun writeNewProduct(name: String, category: String, price: Double, lastConfirmed: Date, shop: String, context: Context) {
            db.collection("Shop")
                .whereEqualTo("Address", shop)
                .get()
                .addOnSuccessListener {
                    val product = hashMapOf(
                        "Name" to name,
                        "Price" to price,
                        "Category" to category,
                        "LastConfirmed" to lastConfirmed,
                        "ShopReference" to it.documents.first().reference
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


    }

    fun subscribeShops() {
        shops = mutableListOf()
        db.collection("Shop")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    shops.add(document.toObject(Shop::class.java))
                }
            }
            .addOnFailureListener { exception ->
                println("shops exception: " + exception)
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun subscribeProducts() {
        products = mutableListOf()
        db.collection("Product")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var product = Product()
                    product.Category = document["Category"].toString()
                    product.Name = document["Name"].toString()
                    product.Price = document["Price"].toString().toDouble()
                    product.ShopReference = document["ShopReference"] as DocumentReference
                    products.add(product)
                    println("products: " + products)
                    println("product: " + product.ShopReference.id)

                }
            }
            .addOnFailureListener { exception ->
                Log.w("shops", "Error getting documents.", exception)
            }
    }

    fun getShops(): MutableList<Shop> {
        return shops
    }

    fun getProducts(): MutableList<Product> {
        return products
    }

    fun search(searchTerm: String, view: LinearLayout) {
        view.removeAllViews()
        var productResultsUnsorted: MutableList<Product> = mutableListOf()
        var productResults: List<Product> = mutableListOf()

        products.forEach {
            if(it.Name?.toLowerCase()?.contains(searchTerm.toLowerCase())!!) {
                productResultsUnsorted.add(it)
            }
        }

        productResults = productResultsUnsorted.sortedBy { it.Price}

        productResults.forEach {
            val product = it
            db.collection("Shop")
                .document(it.ShopReference.id)
                .get()
                .addOnSuccessListener {
                    val shop = it.toObject(Shop::class.java)
                    if (shop != null) {
                        writeLine(view, product, shop)
                    }
                }
                .addOnFailureListener {
                    writeLine(view, product, Shop())
                }
        }
    }

    private fun writeLine(view: LinearLayout, product: Product, shop: Shop) {
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

        productNameLabel.text = product.Name
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

class Product {
    var Name: String? = ""
    var Category: String? = ""
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
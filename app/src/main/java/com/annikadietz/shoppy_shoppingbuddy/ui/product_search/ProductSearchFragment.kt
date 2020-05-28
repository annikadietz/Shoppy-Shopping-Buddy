package com.annikadietz.shoppy_shoppingbuddy.ui.product_search

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.R
import org.json.JSONObject
import kotlin.random.Random
import kotlin.random.nextInt

class ProductSearchFragment : Fragment() {

    private lateinit var productSearchViewModel: ProductSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productSearchViewModel =
            ViewModelProviders.of(this).get(ProductSearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_product_search, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        productSearchViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val searchTermTextField: TextView = root.findViewById(R.id.search_product_text_field)
        val searchProductButton: Button = root.findViewById(R.id.search_product_button)
        val searchResultLayout: LinearLayout = root.findViewById(R.id.search_result_layout)

        searchProductButton.setOnClickListener {
            DatabaseHelper.search(searchTermTextField.text.toString(), searchResultLayout)
        }
        var products = arrayListOf<Product>()
        var shops = arrayListOf<Shop>()
        DatabaseHelper.db.collection("products").get().addOnSuccessListener {
            Log.w("product", it.count().toString())
            it.forEach {
                products.add(it.toObject(Product::class.java))
            }
            Log.w("Objects", products.count().toString())
        }.addOnCompleteListener {
            DatabaseHelper.db.collection("shops").get().addOnSuccessListener {
                it.forEach {
                    shops.add(it.toObject(Shop::class.java))
                }

                val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin=${shops.get(1).streetAddress + " " + shops.get(1).postCode}&destination=${shops.get(2).streetAddress + " " + shops.get(2).postCode}&waypoints=${shops.get(4).streetAddress + " " + shops.get(4).postCode + "|" + shops.get(3).streetAddress + " " + shops.get(3).postCode}&key=AIzaSyAvarQW1FBGHIf3Sr22AQva-J-1dPGHGOI"
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
                val requestQueue = Volley.newRequestQueue(this.context)
                requestQueue.add(directionsRequest)


//                products.forEach{
//                    var product = it
//                    shops.forEach{
//                        var shop = it
//                        var price = Random.nextInt(IntRange(3,10)).toDouble()
//                        var productInShop = ProductInShop(product, shop, price)
//                        Log.w("pprice", price.toString())
//                        DatabaseHelper.db.collection("productsInShops").add(productInShop);
//                    }
//                }
            }
        }

        //https://maps.googleapis.com/maps/api/directions/json?origin=Fichtenweg 18 53340 meckenheim&destination=Kerspellaan 9 7824JA Emmen&key=AIzaSyAvarQW1FBGHIf3Sr22AQva-J-1dPGHGOI&units=metric



        return root
    }
}
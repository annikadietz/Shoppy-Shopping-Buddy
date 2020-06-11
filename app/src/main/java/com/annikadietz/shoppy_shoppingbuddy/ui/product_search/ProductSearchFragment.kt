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





        return root
    }
}
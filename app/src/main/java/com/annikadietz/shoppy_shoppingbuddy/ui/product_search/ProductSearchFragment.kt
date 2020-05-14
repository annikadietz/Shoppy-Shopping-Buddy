package com.annikadietz.shoppy_shoppingbuddy.ui.product_search

import android.os.Bundle
import android.provider.ContactsContract
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
import com.annikadietz.shoppy_shoppingbuddy.R

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
        return root
    }
}
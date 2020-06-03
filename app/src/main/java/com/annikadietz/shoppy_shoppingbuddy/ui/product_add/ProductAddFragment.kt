package com.annikadietz.shoppy_shoppingbuddy.ui.product_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*
import kotlin.collections.HashMap


class ProductAddFragment : Fragment() {

    private lateinit var productAddViewModel: ProductAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productAddViewModel =
            ViewModelProviders.of(this).get(ProductAddViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_product_add, container, false)
        val textView: TextView = root.findViewById(R.id.text_product_add)
        productAddViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val productNameTextField: TextView = root.findViewById(R.id.product_name_text_field)
        val productPriceTextField: TextView = root.findViewById(R.id.product_price_text_field)

        val category_spinner: Spinner = root.findViewById(R.id.category_spinner)
        val categoryList = DatabaseHelper.getCategories()
        val categorySpinnerArray = arrayOfNulls<String>(categoryList.size)
        val categoryMap = HashMap<Int, DocumentSnapshot>()
        for (i in 0 until categoryList.size) {
            categoryMap[i] = categoryList[i]
            categorySpinnerArray[i] = categoryList[i]["Name"].toString()
        }

        val categoryArrayAdapter: ArrayAdapter<String?> = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, categorySpinnerArray)
        categoryArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        category_spinner.adapter = categoryArrayAdapter

        val shop_spinner: Spinner = root.findViewById<Spinner>(R.id.select_shop_spinner)
        val shopList = DatabaseHelper.getShops()
        val shopSpinnerArray = arrayOfNulls<String>(shopList.size)
        val shopSpinnerMap = HashMap<Int, DocumentSnapshot>()
        for (i in 0 until shopList.size) {
            shopSpinnerMap[i] = shopList[i]
            shopSpinnerArray[i] = shopList[i]["Name"].toString() + " - " + shopList[i]["Address"].toString()
        }

        val shopArrayAdapter: ArrayAdapter<String?> = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, shopSpinnerArray)
        shopArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shop_spinner.adapter = shopArrayAdapter

        val addProductButton: Button = root.findViewById(R.id.add_product_button)
        addProductButton.setOnClickListener {
            DatabaseHelper.writeNewProduct(productNameTextField.text.toString(), categoryMap[shop_spinner.selectedItemPosition]!!, productPriceTextField.text.toString().toDouble(), Calendar.getInstance().time, shopSpinnerMap[shop_spinner.selectedItemPosition]!!, this.requireContext())
        }
        return root
    }
}
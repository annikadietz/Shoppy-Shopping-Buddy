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
import com.annikadietz.shoppy_shoppingbuddy.Shop
//import com.annikadietz.shoppy_shoppingbuddy.Shop
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


        val spinner: Spinner = root.findViewById<Spinner>(R.id.select_shop_spinner)
        val shopList = DatabaseHelper.getShops()
        val spinnerArray = arrayOfNulls<String>(shopList.size)
        val spinnerMap = HashMap<Int, Shop?>()
        for (i in 0 until shopList.size) {
            spinnerMap[i] = shopList[i]
            spinnerArray[i] = shopList[i].Name + " - " + shopList[i].Address
        }

        val aa: ArrayAdapter<String?> = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, spinnerArray)
        aa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = aa

        val addProductButton: Button = root.findViewById(R.id.add_product_button)
        addProductButton.setOnClickListener {
            DatabaseHelper.writeNewProduct(productNameTextField.text.toString(), "Category", productPriceTextField.text.toString().toDouble(), Calendar.getInstance().time, spinnerMap[spinner.selectedItemPosition]?.Address.toString(), this.requireContext())
        }
        return root
    }
}
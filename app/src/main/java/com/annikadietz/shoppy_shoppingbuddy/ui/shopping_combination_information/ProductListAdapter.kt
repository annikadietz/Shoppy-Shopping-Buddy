package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.R
import kotlinx.android.synthetic.main.single_product_list_item.view.*

class ProductListAdapter(private val context: Context,
                    private val dataSource: ArrayList<ProductInShop>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): ProductInShop {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.single_product_list_item, parent, false)
        rowView.product_list_item_name.text = getItem(position).product.name
        rowView.product_list_item_price.text = getItem(position).price.toString()

        return rowView
    }
}
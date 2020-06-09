package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.annikadietz.shoppy_shoppingbuddy.CalculationHelper
import com.annikadietz.shoppy_shoppingbuddy.ListGenerator
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.Model.Directions
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.R
import kotlinx.android.synthetic.main.shop_information_line.view.*
import kotlinx.android.synthetic.main.shopping_combination_list_group.view.*
import kotlinx.android.synthetic.main.shopping_combination_list_item.view.*
import kotlinx.android.synthetic.main.single_product_list_item.view.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class ExpandableShoppingListAdapter(val _context: Context,  var _listDataHeader: ArrayList<Combination>, private val _listChild: List<Combination> // header titles
    // child data in format of header title, child title
) : BaseExpandableListAdapter() {


    var _listDataHeaderFiltered: ArrayList<Combination>
    var _listDataHeaderOriginal = ArrayList<Combination>()

    init {
        _listDataHeaderFiltered = _listDataHeader
        _listDataHeaderOriginal.addAll(_listDataHeader)
    }

    override fun getChild(groupPosition: Int, childPosititon: Int): Combination {
        return _listDataHeaderFiltered[groupPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val combination = getChild(groupPosition, childPosition)
        val products = combination.productsInShops

        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.shopping_combination_list_item, null)
        }

        var product_list_layout: LinearLayout? = convertView?.product_list_layout
        product_list_layout?.removeAllViews()

        combination.shops?.forEach {shop ->
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var shopView: View = infalInflater.inflate(R.layout.shop_information_line, null)
            shopView.shop_name.text = shop.name
            shopView.shop_address.text = shop.streetAddress + ", " + shop.postCode
            product_list_layout?.addView(shopView)

            products.forEach {
                if(it.shop == shop) {
                    val infalInflater = this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val productView = infalInflater.inflate(R.layout.single_product_list_item, null)

                    productView.product_list_item_name.text = it.product.name + " " + it.shop.name
                    productView.product_list_item_price.text = it.price.toString() + "€"
                    product_list_layout?.addView(productView)
                }
            }
        }

        return convertView!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Combination {
        return this._listDataHeaderFiltered[groupPosition]
    }

    override fun getGroupCount(): Int {
        return this._listDataHeaderFiltered.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val combination = getGroup(groupPosition)
        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.shopping_combination_list_group, null)
        }

        if (groupPosition % 2 == 1) {
            convertView?.setBackgroundResource(R.color.colorGrey)
        } else {
            convertView?.setBackgroundResource(R.color.colorLightGrey)
        }

        //convertView!!.shopping_combination_list_group.text = headerTitle
        if (convertView != null) {
            // TODO: Function to calculate distance
            convertView.distance_text.text = "Distance: " + combination.directions?.distancetoTravel.toString() + "km"
            convertView.shop_count_text.text = "Amount Shops: " + combination.shops?.size.toString()
            convertView.total_price_text.text = "Price: " + combination.productsInShops.sumByDouble { p -> p.price }.toString() + "€"
        }

        return convertView!!
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
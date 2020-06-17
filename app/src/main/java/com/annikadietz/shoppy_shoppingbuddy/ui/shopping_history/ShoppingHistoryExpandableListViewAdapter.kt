package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.annikadietz.shoppy_shoppingbuddy.Model.PurchasedProduct
import com.annikadietz.shoppy_shoppingbuddy.R
import kotlinx.android.synthetic.main.shopping_history_group_header.view.*
import kotlinx.android.synthetic.main.shopping_history_line.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShoppingHistoryExpandableListViewAdapter(val _context: Context, var _listDataHeader: ArrayList<ShoppingHistoryFragment.DatePurchasedCollection>, val _startDate: Date, val _endDate: Date
) : BaseExpandableListAdapter() {


    var _listDataHeaderFiltered: ArrayList<ShoppingHistoryFragment.DatePurchasedCollection>
    var _listDataHeaderOriginal = ArrayList<ShoppingHistoryFragment.DatePurchasedCollection>()

    init {
        _listDataHeaderFiltered = filterData()
        _listDataHeaderOriginal.addAll(_listDataHeader)
    }

    fun filterData() : ArrayList<ShoppingHistoryFragment.DatePurchasedCollection> {
        var filtered = arrayListOf<ShoppingHistoryFragment.DatePurchasedCollection>()
        _listDataHeader.forEach {
            if(it.date > _startDate && it.date < _endDate) {
                filtered.add(it)
            }
        }
        return filtered
    }

    override fun getChild(groupPosition: Int, childPosititon: Int): ArrayList<PurchasedProduct> {
        return _listDataHeaderFiltered[groupPosition].purchased
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val children = getChild(groupPosition, childPosition)

        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.shopping_history_item, null)
        }

        var product_list_layout: LinearLayout? = convertView?.findViewById(R.id.shopped_products_layout)
        product_list_layout?.removeAllViews()

        children.forEach {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val lineView = infalInflater.inflate(R.layout.shopping_history_line, null)

            lineView!!.product_name.text = it.item.product.name
            lineView!!.product_price.text = it.item.price.price.toString() + "€"
            lineView!!.product_type.text = it.item.product.type.name
            lineView!!.purchased_in_shop.text = it.item.shop.name + " (" + it.item.shop.streetAddress + ")"

            product_list_layout?.addView(lineView)
        }
        return convertView!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): ShoppingHistoryFragment.DatePurchasedCollection {
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
        val datePurchasedCollection = getGroup(groupPosition)
        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.shopping_history_group_header, null)
        }

        val myFormat = "dd-MM-YYYY" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        convertView!!.date_text_view.text = sdf.format(datePurchasedCollection.date)
        convertView!!.total_items.text = "Items bought: " + datePurchasedCollection.purchased.size
        convertView!!.total_amount.text = "Total price: " + datePurchasedCollection.purchased.sumByDouble { product -> product.item.price.price } + "€"

        return convertView!!

    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
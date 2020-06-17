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
import java.util.*
import kotlin.collections.ArrayList

class ShoppingHistoryExpandableListViewAdapter(val _context: Context, var _listDataHeader: ArrayList<ShoppingHistoryFragment.DatePurchasedCollection>, private val _listChild: List<ShoppingHistoryFragment.DatePurchasedCollection>
) : BaseExpandableListAdapter() {


    var _listDataHeaderFiltered: ArrayList<ShoppingHistoryFragment.DatePurchasedCollection>
    var _listDataHeaderOriginal = ArrayList<ShoppingHistoryFragment.DatePurchasedCollection>()

    init {
        _listDataHeaderFiltered = _listDataHeader
        _listDataHeaderOriginal.addAll(_listDataHeader)
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

    override fun getGroup(groupPosition: Int): Date {
        return this._listDataHeaderFiltered[groupPosition].date
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
        val headerTitle = getGroup(groupPosition).toString()
        if (convertView == null) {
            val infalInflater = this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.shopping_history_group_header, null)
        }

        convertView!!.date_text_view.text = headerTitle

        return convertView!!
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
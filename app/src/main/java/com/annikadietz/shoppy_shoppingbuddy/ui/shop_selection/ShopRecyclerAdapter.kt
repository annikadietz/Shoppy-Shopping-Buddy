package com.annikadietz.shoppy_shoppingbuddy.ui.shop_selection

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R

class ShopRecyclerAdapter : RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder>, Filterable {
    var logTag = "RecyclerAdapter.onCreateViewHolder"
    var count = 0
    lateinit var selectedShops: List<Shop>
    lateinit var shops: List<Shop>
    lateinit var shopsFiltered: ArrayList<Shop>
    lateinit var databaseHelper: DatabaseHelper
    var selectedType = ""

    private val shopFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults? {
            var searchResults = arrayListOf<Shop>()
            if (constraint.isNotEmpty() && selectedType.isEmpty()) {
                shops.forEach {
                    if (it.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        searchResults.add(it)
                    }
                }
                return FilterResults().apply { values = searchResults }
            } else {
                return FilterResults().apply { values = shops }
            }
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            shopsFiltered.clear()
            shopsFiltered.addAll(results.values as Collection<Shop>)
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return shopFilter
    }

    constructor(
        shopList: List<Shop>,
        selectedShopList: List<Shop>,
        dbh: DatabaseHelper
    ) : super() {
        shops = shopList
        selectedShops = selectedShopList
        shopsFiltered = ArrayList(shops)
        databaseHelper = dbh
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.w(logTag, "onCreateViewHolder " + count++)
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var view: View = layoutInflater.inflate(R.layout.shop_row_item, parent, false)
        var viewHolder: ViewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return shopsFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var shop = shopsFiltered[position]
        holder.shopName.text = shop.name
        holder.shopAddress.text = shop.streetAddress + " " + shop.postCode
        if (selectedShops.any { selectedShop -> selectedShop.name == shop.name && selectedShop.postCode == shop.postCode && selectedShop.streetAddress == shop.streetAddress }) {
            holder.checkBox.isChecked = true
        }


        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                databaseHelper.addMyShop(shop)
            } else {
                databaseHelper.deleteMyShop(shop)
            }
        }
    }

    // Represents a single row in the RecyclerView
    class ViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        lateinit var checkBox: CheckBox
        lateinit var shopName: TextView
        lateinit var shopAddress: TextView

        constructor(itemView: View) : super(itemView) {
            checkBox = itemView.findViewById(R.id.shop_checkbox)
            shopName = itemView.findViewById(R.id.shop_name)
            shopAddress = itemView.findViewById(R.id.shop_address)
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            this.checkBox.isChecked = !this.checkBox.isChecked
            this.checkBox.callOnClick()
        }
    }
}

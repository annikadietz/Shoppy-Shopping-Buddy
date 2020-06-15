package com.annikadietz.shoppy_shoppingbuddy.ui.product_search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.R

public class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>, Filterable {
    var logTag = "RecyclerAdapter.onCreateViewHolder"
    var count = 0
    lateinit var products: List<Product>
    lateinit var productsFiltered: ArrayList<Product>
    var selectedType = ""
    private val productFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults? {
            var searchResults = arrayListOf<Product>()
            var selectedItem = selectedType
            if(constraint.isNotEmpty() && selectedType.isEmpty()){
                products.forEach {
                    if(it.name!!.toLowerCase().contains( constraint.toString().toLowerCase())){
                        searchResults.add(it)
                    }
                }
                return FilterResults().apply { values = searchResults }
            } else if (constraint.isEmpty() && selectedType.isNotEmpty()) {
                products.forEach {
                    if(it.type?.name?.toLowerCase() == selectedType.toLowerCase()){
                        searchResults.add(it)
                    }
                }
                return FilterResults().apply { values = searchResults }
            } else if (constraint.isNotEmpty() && selectedType.isNotEmpty()) {
                products.forEach {
                    if(it.type?.name?.toLowerCase() == selectedType.toLowerCase() && it.name!!.toLowerCase().contains( constraint.toString().toLowerCase())){
                        searchResults.add(it)
                    }
                }
                return FilterResults().apply { values = searchResults }
            } else {
                return FilterResults().apply { values = products }
            }
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            productsFiltered.clear()
            productsFiltered.addAll(results.values as Collection<Product>)
            notifyDataSetChanged()
        }
    }
    override fun getFilter(): Filter {
        return productFilter
    }
    constructor(productList: List<Product>) : super() {
        products = productList
        productsFiltered = ArrayList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.w(logTag, "onCreateViewHolder " + count++)
        var layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        var view: View = layoutInflater.inflate(R.layout.fragment_product_row_item, parent, false)
        var viewHolder: ViewHolder = ViewHolder(view);
        return viewHolder
    }

    override fun getItemCount(): Int {
        return productsFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = productsFiltered[position].name
        holder.typeText.text = productsFiltered[position].type!!.name
    }
    // Represents a single row in the RecyclerView
    class ViewHolder : RecyclerView.ViewHolder{
        lateinit var addButton: Button
        lateinit var nameText: TextView
        lateinit var typeText: TextView
        constructor(itemView: View) : super(itemView) {
            addButton = itemView.findViewById(R.id.add_button)
            nameText = itemView.findViewById(R.id.product_name)
            typeText = itemView.findViewById(R.id.product_type)
        }

    }


}
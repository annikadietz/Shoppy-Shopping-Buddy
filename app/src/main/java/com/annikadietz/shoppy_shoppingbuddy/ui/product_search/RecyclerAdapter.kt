package com.annikadietz.shoppy_shoppingbuddy.ui.product_search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelperInterface
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R

public class RecyclerAdapter(var databaseHelper: DatabaseHelperInterface): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable {
    var logTag = "RecyclerAdapter.onCreateViewHolder"
    var count = 0
    lateinit var products: List<Product>
    lateinit var productsFiltered: ArrayList<Product>
    var selectedType = ""
    var myShoppingList = databaseHelper.getMyShoppingList()

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
    init {
        products = databaseHelper.getProducts()
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
        var productInPosition = productsFiltered[position]
        holder.nameText.text = productInPosition.name
        holder.typeText.text = productInPosition.type!!.name

        if(myShoppingList.any{ product -> product.name == productInPosition.name && product.type?.name == productInPosition.type?.name }) {
            holder.checkBox.isChecked = true
        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                databaseHelper.addProductToMyShoppingList(productInPosition)
            }
            else {
                databaseHelper.deleteProductFormMyShoppingList(productInPosition)
            }
        }
    }
    // Represents a single row in the RecyclerView
    class ViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        lateinit var checkBox: CheckBox
        lateinit var nameText: TextView
        lateinit var typeText: TextView
        constructor(itemView: View) : super(itemView) {
            checkBox = itemView.findViewById(R.id.is_in_list)
            nameText = itemView.findViewById(R.id.product_name)
            typeText = itemView.findViewById(R.id.product_type)
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            this.checkBox.isChecked = !this.checkBox.isChecked
            this.checkBox.callOnClick()
        }
    }


}
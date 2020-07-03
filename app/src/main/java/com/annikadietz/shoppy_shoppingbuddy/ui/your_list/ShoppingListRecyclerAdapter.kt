package com.annikadietz.shoppy_shoppingbuddy.ui.your_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R

class ShoppingListRecyclerAdapter(
    var shoppingList: ArrayList<Product>,
    var updateCombos: () -> Unit
) : RecyclerView.Adapter<ShoppingListRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var view: View =
            layoutInflater.inflate(R.layout.fragment_shopping_list_row_item, parent, false)
        var viewHolder: ViewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productInPosition = shoppingList[position]

        holder.name.text = productInPosition.name
        holder.type.text = productInPosition.type.name
        holder.deleteButton.setOnClickListener {
            DatabaseHelper.deleteProductFormMyShoppingList(productInPosition)
            shoppingList.remove(productInPosition)
            notifyDataSetChanged()
            updateCombos.invoke()
        }
    }

    // Represents a single row in the RecyclerView
    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var name: TextView
        lateinit var type: TextView
        lateinit var deleteButton: Button

        constructor(itemView: View) : super(itemView) {
            name = itemView.findViewById(R.id.product_name)
            type = itemView.findViewById(R.id.product_type)
            deleteButton = itemView.findViewById(R.id.delete_button)
        }
    }


}
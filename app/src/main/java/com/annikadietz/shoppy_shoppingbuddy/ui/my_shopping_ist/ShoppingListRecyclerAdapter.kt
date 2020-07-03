package com.annikadietz.shoppy_shoppingbuddy.ui.my_shopping_ist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R

class ShoppingListRecyclerAdapter :
    RecyclerView.Adapter<ShoppingListRecyclerAdapter.ViewHolder>() {

    var myShoppingList = DatabaseHelper.getMyShoppingList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var view: View = layoutInflater.inflate(R.layout.fragment_product_row_item, parent, false)
        var viewHolder: ViewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return myShoppingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var productInPosition = myShoppingList[position]
        holder.nameText.text = productInPosition.name
        holder.typeText.text = productInPosition.type.name

        if (myShoppingList.any { product -> product.name == productInPosition.name && product.type.name == productInPosition.type.name }) {
            holder.checkBox.isChecked = true
        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                DatabaseHelper.addProductToMyShoppingList(productInPosition)
            } else {
                DatabaseHelper.deleteProductFormMyShoppingList(productInPosition)
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
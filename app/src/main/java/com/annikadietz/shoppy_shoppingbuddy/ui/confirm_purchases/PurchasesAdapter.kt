package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.Model.ShoppingItem
import com.annikadietz.shoppy_shoppingbuddy.R
import java.time.Duration
import java.time.LocalDateTime

class PurchasesAdapter(var shoppingItems: ArrayList<ShoppingItem>) :
    RecyclerView.Adapter<PurchasesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(
            R.layout.fragment_shopping_list_row_item_with_price,
            parent,
            false
        )

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var itemInPosition = shoppingItems[position]
        holder.productName.text = itemInPosition.product.name
        holder.productType.text = itemInPosition.product.type.name
        holder.productPrice.text = "€ " + itemInPosition.price.price.toString()
        var lastConfirmed = LocalDateTime.parse(itemInPosition.price.lastConfirmed)
        var period: Duration = Duration.between(lastConfirmed, LocalDateTime.now())
        if (period.toDays() < 1) {
            if (period.toMinutes().toInt() < 10 && period.toHours().toInt() < 1) {
                holder.lastConfirmed.text = "A few minutes ago"
            } else if (period.toHours().toInt() < 1) {
                holder.lastConfirmed.text = period.toMinutes().toString() + " minutes ago"
            } else if (period.toHours().toInt() == 1) {
                holder.lastConfirmed.text = period.toHours().toString() + " hour ago"
            } else {
                holder.lastConfirmed.text = period.toHours().toString() + " hours ago"
            }
        } else if (period.toDays().toInt() == 1) {
            holder.lastConfirmed.text = "A day ago"
        } else {
            holder.lastConfirmed.text = period.toDays().toString() + " days ago"
        }

    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        var productName: TextView
        var productType: TextView
        var productPrice: TextView
        var lastConfirmed: TextView

        constructor(itemView: View) : super(itemView) {
            productName = itemView.findViewById(R.id.product_name)
            productType = itemView.findViewById(R.id.product_type)
            productPrice = itemView.findViewById(R.id.product_price)
            lastConfirmed = itemView.findViewById(R.id.last_confirmed)
        }
    }
}







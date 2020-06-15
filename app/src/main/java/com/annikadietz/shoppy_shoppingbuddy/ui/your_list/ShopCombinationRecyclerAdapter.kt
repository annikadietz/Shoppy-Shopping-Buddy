package com.annikadietz.shoppy_shoppingbuddy.ui.your_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.R
import kotlin.math.roundToInt
import kotlin.math.roundToLong

public class ShopCombinationRecyclerAdapter(var listener: (Combination) -> Unit,
                                            var combinations: ArrayList<Combination>): RecyclerView.Adapter<ShopCombinationRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        var view: View = layoutInflater.inflate(R.layout.fragment_combo, parent, false)
        var viewHolder: ViewHolder = ViewHolder(view, combinations, listener);

        return viewHolder
    }



    override fun getItemCount(): Int {
        return combinations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var comboInPosition = combinations[position]
        var name = when(comboInPosition.shops.size) {
            1 -> "One Shop"
            2 -> "Two Shops"
            3 -> "Three Shops"
            else -> "More shops"
        }
        var shops = ""
        comboInPosition.shops.forEachIndexed { index, shop ->
            shops += shop.streetAddress
            if (index < comboInPosition.shops.size - 1 ){
                shops += " > "
            }
        }

        var distancetoTravel = comboInPosition.
        directions?.
        distancetoTravel?.
        div(1000)

        var distancetoTravelString = "%.2f".format(distancetoTravel) + " km"

        var timeToTravel = comboInPosition.
        directions?.
        timeToTravel?.
        div(60)?.toInt()
        var timeToTravelString = "$name - $timeToTravel min"

        var price = 0.0
        comboInPosition.productsInShops.forEach {
            price += it.price
        }

        var priceString = "€" + "%.2f".format(price)
        holder.name.text = timeToTravelString
        holder.shops.text = shops
        holder.price.text = price.toString()
        holder.distance.text = distancetoTravelString

    }
    // Represents a single row in the RecyclerView
    class ViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        lateinit var name: TextView
        lateinit var shops: TextView
        lateinit var price: TextView
        lateinit var distance: TextView
        lateinit var combinations: ArrayList<Combination>
        lateinit var listener: (Combination) -> Unit
        constructor(itemView: View, combinations: ArrayList<Combination>, listener: (Combination) -> Unit) : super(itemView) {
            this.combinations = combinations
            this.listener = listener
            name = itemView.findViewById(R.id.name)
            shops = itemView.findViewById(R.id.shops)
            price = itemView.findViewById(R.id.price)
            distance = itemView.findViewById(R.id.distance)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.invoke(combinations[this.adapterPosition])
        }
    }


}
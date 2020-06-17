package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.Model.PurchasedProduct
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import java.util.*

class ShoppingHistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_shopping_history, container, false)

        // TODO: Add filter option here
        var spinner = root.findViewById<Spinner>(R.id.time_span_selector)

        var list = root.findViewById<ExpandableListView>(R.id.shopping_history_list)
        var myPurchasedProducts = NewDatabaseHelper.getPurchasedProducts()
        var calendar = Calendar.getInstance()


        var dates = arrayListOf<Date>()
        var purchasedProducts = arrayListOf<DatePurchasedCollection>()

        myPurchasedProducts.forEach {
            calendar.setTime(it.buyedAt)
            var cal2 = Calendar.getInstance()
            var result = purchasedProducts.find { purchased -> cal2.setTime(purchased.date)
                cal2.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR) &&
                        cal2.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
            }
            if (result == null) {
                //dates.add(it.buyedAt)
                var purchased = DatePurchasedCollection(it.buyedAt)
                purchased.purchased.add(it)
                purchasedProducts.add(purchased)
            }
            else {
                result.purchased.add(it)
            }
        }

        var adapter = ShoppingHistoryExpandableListViewAdapter(this.requireContext(), purchasedProducts, purchasedProducts)
        list.setAdapter(adapter)

        return root
    }

    class DatePurchasedCollection {
        var date: Date
        var purchased: ArrayList<PurchasedProduct>

        constructor(date: Date) {
            this.date = date
            this.purchased = arrayListOf()
        }
    }
}
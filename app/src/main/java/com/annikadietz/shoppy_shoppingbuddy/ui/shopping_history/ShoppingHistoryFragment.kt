package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_history

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.Model.PurchasedProduct
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import java.text.SimpleDateFormat
import java.util.*


class ShoppingHistoryFragment : Fragment() {
    lateinit var purchasedProducts: ArrayList<DatePurchasedCollection>
    lateinit var list: ExpandableListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_shopping_history, container, false)

        var startDatePicker = root.findViewById<EditText>(R.id.start_date)
        var endDatePicker = root.findViewById<EditText>(R.id.end_date)
        var startCalendar = Calendar.getInstance()
        var endCalendar = Calendar.getInstance()

        startCalendar.set(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), 1)
        updateLabel(startDatePicker, startCalendar)
        updateLabel(endDatePicker, endCalendar)

        val startDateListener =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                startCalendar.set(Calendar.YEAR, year)
                startCalendar.set(Calendar.MONTH, monthOfYear)
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(startDatePicker, startCalendar)
                updateList(startCalendar, endCalendar)
            }

        val endDateListener =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                endCalendar.set(Calendar.YEAR, year)
                endCalendar.set(Calendar.MONTH, monthOfYear)
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(endDatePicker, endCalendar)
                updateList(startCalendar, endCalendar)
            }

        startDatePicker.setOnClickListener {
            DatePickerDialog(this.requireContext(), startDateListener, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get((Calendar.DATE))).show()
        }

        endDatePicker.setOnClickListener {
            DatePickerDialog(this.requireContext(), endDateListener, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get((Calendar.DATE))).show()
        }

        list = root.findViewById<ExpandableListView>(R.id.shopping_history_list)
        var myPurchasedProducts = NewDatabaseHelper.getPurchasedProducts()
        var calendar = Calendar.getInstance()


        purchasedProducts = arrayListOf<DatePurchasedCollection>()

        myPurchasedProducts.forEach {
            calendar.setTime(it.boughtAt)
            var cal2 = Calendar.getInstance()
            var result = purchasedProducts.find { purchased -> cal2.setTime(purchased.date)
                cal2.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR) &&
                        cal2.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
            }
            if (result == null) {
                var purchased = DatePurchasedCollection(it.boughtAt)
                purchased.purchased.add(it)
                purchasedProducts.add(purchased)
            }
            else {
                result.purchased.add(it)
            }
        }



        updateList(startCalendar, endCalendar)

        return root
    }

    fun updateList(startCalendar: Calendar, endCalendar: Calendar) {
        var adapter = ShoppingHistoryExpandableListViewAdapter(this.requireContext(), purchasedProducts, startCalendar.time, endCalendar.time)
        list.setAdapter(adapter)
    }

    private fun updateLabel(text: EditText, calendar: Calendar) {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        text.setText(sdf.format(calendar.time))
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
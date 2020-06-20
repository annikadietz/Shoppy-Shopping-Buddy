package com.annikadietz.shoppy_shoppingbuddy.ui.your_list

import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.ListGenerator
import com.annikadietz.shoppy_shoppingbuddy.MainActivity
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.material.textfield.TextInputEditText


class YourListFragment(val listener: (Combination) -> Unit, val activity: MainActivity) : Fragment() {
    var listGenerator = ListGenerator(this.activity,NewDatabaseHelper.address)
    lateinit var recyclerAdapter: ShopCombinationRecyclerAdapter
    lateinit var yourAddressField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_your_list, container, false)
        yourAddressField = root.findViewById<TextInputEditText>(R.id.your_address_text_input)
        updateAddressField()
        var updateButton = root.findViewById<Button>(R.id.update_with_address)
        updateButton.setOnClickListener {
            listGenerator.myLocation = yourAddressField.text.toString()
            NewDatabaseHelper.address = yourAddressField.text.toString()
            updateCombos()
        }

        var updateLocationButton = root.findViewById<Button>(R.id.update_location_button)
        updateLocationButton.setOnClickListener {
            this.activity.updateLocation()
        }

        recyclerAdapter = ShopCombinationRecyclerAdapter({combo:Combination ->listener(combo) }, listGenerator.combos)
        updateCombos()
        var comboRecyclerView = root.findViewById<RecyclerView>(R.id.combos)
        comboRecyclerView.layoutManager = LinearLayoutManager(this.context)
        comboRecyclerView.adapter = recyclerAdapter


        var shoppingListRecyclerAdapter = ShoppingListRecyclerAdapter(NewDatabaseHelper.getMyShoppingList(), {updateCombos()})
        var shoppingListRecyclerView = root.findViewById<RecyclerView>(R.id.product_list)
        shoppingListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        shoppingListRecyclerView.adapter = shoppingListRecyclerAdapter
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAddressField() {
        if(this::yourAddressField.isInitialized) {
            yourAddressField.setText(NewDatabaseHelper.address)
        }
        listGenerator.myLocation = NewDatabaseHelper.address
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateCombos(){
        listGenerator.getCombinationsWithProductsInShops(
            NewDatabaseHelper.getMyShops(),
            NewDatabaseHelper.getMyShoppingList(),
            NewDatabaseHelper.getShoppingItems(),
            recyclerAdapter)
    }
}
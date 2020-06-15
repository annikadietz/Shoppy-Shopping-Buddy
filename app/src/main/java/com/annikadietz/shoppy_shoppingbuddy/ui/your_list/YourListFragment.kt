package com.annikadietz.shoppy_shoppingbuddy.ui.your_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.ListGenerator
import com.annikadietz.shoppy_shoppingbuddy.MainActivity
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R

class YourListFragment(val listener: (Combination) -> Unit, val activity: MainActivity) : Fragment() {
    var listGenerator = ListGenerator(this.activity,"Hoitingeslag%2029,%207824%20KG")
    lateinit var recyclerAdapter: ShopCombinationRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_your_list, container, false)

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

    fun updateCombos(){
        listGenerator.getCombinationsWithProductsInShops(
            NewDatabaseHelper.getMyShops(),
            NewDatabaseHelper.getMyShoppingList(),
            NewDatabaseHelper.getProductsInShops(),
            recyclerAdapter)
    }
}
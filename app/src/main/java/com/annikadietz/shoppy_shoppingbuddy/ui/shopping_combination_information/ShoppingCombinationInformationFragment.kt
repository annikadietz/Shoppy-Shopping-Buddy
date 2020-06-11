package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelperInterface
import com.annikadietz.shoppy_shoppingbuddy.ListGenerator
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import kotlin.collections.ArrayList


/**
 * A fragment representing a list of Items.
 */
class ShoppingCombinationInformationFragment : Fragment() {

    private lateinit var listGenerator: ListGenerator
    private lateinit var databaseHelper: DatabaseHelperInterface
    lateinit var viewModel: ShoppingCombinationInformationViewModel
    private lateinit var shoppingList: ArrayList<Product>
    private lateinit var shops: ArrayList<Shop>
    private lateinit var productsInShops: ArrayList<ProductInShop>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =
            inflater.inflate(R.layout.fragment_shopping_combination_information, container, false)

        // TODO get my real location
        listGenerator = ListGenerator(root.context, "Hoitingeslag%2029,%207824%20KG")
        databaseHelper = NewDatabaseHelper
        viewModel = ShoppingCombinationInformationViewModel(listGenerator, databaseHelper)

        fillArrayLists()

        var topCombinations = viewModel.getTopThreeCombinations()
        var adapter = ExpandableShoppingListAdapter(root.context, topCombinations, topCombinations)

        listGenerator.getCombinationsWithProductsInShops(shops, shoppingList, productsInShops, adapter)

        topCombinations = viewModel.getTopThreeCombinations()
        adapter = ExpandableShoppingListAdapter(root.context, topCombinations, topCombinations)

        var listView = root.findViewById<ExpandableListView>(R.id.list_shopping_combinations)

        listView.setAdapter(adapter)
        return root
    }

    private fun fillArrayLists() {
        shoppingList = viewModel.findShoppingList()
        shops = viewModel.findShops()
        productsInShops = viewModel.findProductsInShops()
    }

}
package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.ListGenerator
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set


/**
 * A fragment representing a list of Items.
 */
class ShoppingCombinationInformationFragment : Fragment() {

    private var columnCount = 1
    private lateinit var listGenerator: ListGenerator
    lateinit var listAdapter: ExpandableListAdapter
    lateinit var expListView: ExpandableListView
    lateinit var listDataHeader: ArrayList<String>
    lateinit var listDataChild: HashMap<String, List<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =
            inflater.inflate(R.layout.fragment_shopping_combination_information, container, false)
        listGenerator = ListGenerator(root.context, "Hoitingeslag%2029,%207824%20KG")

        var shoppingList = ArrayList<Product>(NewDatabaseHelper.getProducts())
        var shops = ArrayList<Shop>(NewDatabaseHelper.getShops())
        var productsInShops = ArrayList<ProductInShop>(NewDatabaseHelper.getProductsInShops())

        var combinations: ArrayList<Combination> = listGenerator.getCombinationsWithProductsInShops(shops, shoppingList, productsInShops)
        var topThreeCombinations: ArrayList<Combination> = ArrayList(3)
        topThreeCombinations.add(listGenerator.oneShopCombination)
        topThreeCombinations.add(listGenerator.twoShopCombination)
        topThreeCombinations.add(listGenerator.threeShopCombination)

        var listView = root.findViewById<ExpandableListView>(R.id.list_shopping_combinations)


        prepareListData()
        var adapter = ExpandableShoppingListAdapter(this.requireContext(), topThreeCombinations, topThreeCombinations)
        listView.setAdapter(adapter)

        return root
    }

    private fun prepareListData() {
        listDataHeader = ArrayList<String>()
        listDataChild = HashMap()

        // Adding child data
        listDataHeader.add("Top 250")
        listDataHeader.add("Now Showing")
        listDataHeader.add("Coming Soon..")

        // Adding child data
        val top250: MutableList<String> = ArrayList()
        top250.add("The Shawshank Redemption")
        top250.add("The Godfather")
        top250.add("The Godfather: Part II")
        top250.add("Pulp Fiction")
        top250.add("The Good, the Bad and the Ugly")
        top250.add("The Dark Knight")
        top250.add("12 Angry Men")
        val nowShowing: MutableList<String> = ArrayList()
        nowShowing.add("The Conjuring")
        nowShowing.add("Despicable Me 2")
        nowShowing.add("Turbo")
        nowShowing.add("Grown Ups 2")
        nowShowing.add("Red 2")
        nowShowing.add("The Wolverine")
        val comingSoon: MutableList<String> = ArrayList()
        comingSoon.add("2 Guns")
        comingSoon.add("The Smurfs 2")
        comingSoon.add("The Spectacular Now")
        comingSoon.add("The Canyons")
        comingSoon.add("Europa Report")
        listDataChild[listDataHeader[0]] = top250 // Header, Child data
        listDataChild[listDataHeader[1]] = nowShowing
        listDataChild[listDataHeader[2]] = comingSoon
    }
}
package com.annikadietz.shoppy_shoppingbuddy.ui.find_shopping_list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.annikadietz.shoppy_shoppingbuddy.*
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import java.util.*


class FindShoppingListFragment : Fragment() {

    private lateinit var findShoppingListViewModel: FindShoppingListViewModel
    private lateinit var listGenerator: ListGenerator
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findShoppingListViewModel =
            ViewModelProviders.of(this).get(FindShoppingListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_find_shopping_list, container, false)
        listGenerator = ListGenerator(root.context, "Hoitingeslag%2029,%207824%20KG")
        // TODO: Get the right shopping list and not all Products.
        var shoppingList = ArrayList<Product>(NewDatabaseHelper.getProducts())
        var shops = ArrayList<Shop>(NewDatabaseHelper.getShops())
        var productsInShops = ArrayList<ProductInShop>(NewDatabaseHelper.getProductsInShops())

        var layoutShoppingList = root.findViewById<LinearLayout>(R.id.shopping_list_layout)

        shoppingList.forEach {
            val productLabel = TextView(layoutShoppingList.context)
            productLabel.text = it.name
            layoutShoppingList.addView(productLabel)
        }

        val oneShopButton = root.findViewById<Button>(R.id.find_best_single_shop_button)
        val shopCombinationButton = root.findViewById<Button>(R.id.find_best_shop_combination)

        var linearLayout = root.findViewById<LinearLayout>(R.id.results_layout)

        oneShopButton.setOnClickListener {
            var results = listGenerator.findCheapestStore(shops, shoppingList, productsInShops)
            writeLine(linearLayout, results)

            //var testResult = listGenerator.findBestRoute(shops, shoppingList, productsInShops, "Hoitingeslag 29, 7824 KG", this.requireContext())
            print("test")
        }

        shopCombinationButton.setOnClickListener {
            var result = listGenerator.getCombinationsWithProductsInShops(shops, shoppingList, productsInShops)
            var sortedResult = listGenerator.sortedCombination(result)

            linearLayout.removeAllViews()
            sortedResult.forEach {
                Log.w("Test", it.productsInShops?.first()?.price.toString())
                writeLine(linearLayout, it)
            }
        }

        return root
    }

    private fun writeLine(view: LinearLayout, combination: Combination) {
        val firstRowLayout = LinearLayout(view.context)
        firstRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val secondRowLayout = LinearLayout(view.context)
        secondRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val thirdRowLayout = LinearLayout(view.context)
        thirdRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val fourthRowLayout = LinearLayout(view.context)
        fourthRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)

        val shopsNamesLabel = TextView(view.context)
        val productsPriceLabel = TextView(view.context)
        val shopNameLabel = TextView(view.context)
        val shopAddressLabel = TextView(view.context)

        var shopNames = ""
        var priceSum = 0.0

        combination.shops?.forEach {
            shopNames += it.name + " " + "(${it.streetAddress}) "
        }

        combination.productsInShops?.forEach {
            priceSum += it.price
        }

        shopsNamesLabel.text = shopNames
        productsPriceLabel.text = priceSum.toString() + "€"

        firstRowLayout.addView(shopsNamesLabel)
        secondRowLayout.addView(productsPriceLabel)

//        secondRowLayout.addView(shopNameLabel)
//
//        thirdRowLayout.addView(shopAddressLabel)
//
//        fourthRowLayout.addView(TextView(view.context))

        view.addView(firstRowLayout)
        view.addView(secondRowLayout)
//        view.addView(thirdRowLayout)
//        view.addView(fourthRowLayout)
    }

    private fun writeLine(view: LinearLayout, productsInShop: ArrayList<ProductInShop>) {
        view.removeAllViews()
        val firstRowLayout = LinearLayout(view.context)
        firstRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val secondRowLayout = LinearLayout(view.context)
        secondRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val thirdRowLayout = LinearLayout(view.context)
        thirdRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)
        val fourthRowLayout = LinearLayout(view.context)
        fourthRowLayout.setHorizontalGravity(LinearLayout.HORIZONTAL)

        val shopNamesLabel = TextView(view.context)
        val productsPriceLabel = TextView(view.context)
        val shopNameLabel = TextView(view.context)
        val shopAddressLabel = TextView(view.context)

        var shopNames = "${productsInShop.first().shop.name} (${productsInShop.first().shop.streetAddress})"
        var priceSum = 0.0

        productsInShop.forEach {
            priceSum += it.price
        }

        shopNamesLabel.text = shopNames
        productsPriceLabel.text = priceSum.toString() + "€"

        firstRowLayout.addView(shopNamesLabel)
        secondRowLayout.addView(productsPriceLabel)

//        secondRowLayout.addView(shopNameLabel)
//
//        thirdRowLayout.addView(shopAddressLabel)
//
//        fourthRowLayout.addView(TextView(view.context))

        view.addView(firstRowLayout)
        view.addView(secondRowLayout)
//        view.addView(thirdRowLayout)
//        view.addView(fourthRowLayout)
    }
}
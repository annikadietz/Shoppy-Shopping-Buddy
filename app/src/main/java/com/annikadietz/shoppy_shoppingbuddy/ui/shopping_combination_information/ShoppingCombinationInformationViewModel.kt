package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information

import android.view.View
import android.widget.ExpandableListView
import androidx.lifecycle.ViewModel
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelperInterface
import com.annikadietz.shoppy_shoppingbuddy.ListGenerator
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R

class ShoppingCombinationInformationViewModel(lg: ListGenerator, dbh: DatabaseHelperInterface) : ViewModel() {
    var databaseHelper: DatabaseHelperInterface = dbh
    var listGenerator = lg

    fun findShoppingList(): ArrayList<Product> {
        return ArrayList(databaseHelper.getProducts())
    }

    fun findShops(): ArrayList<Shop> {
        return ArrayList(databaseHelper.getShops())
    }

    fun findProductsInShops(): ArrayList<ProductInShop> {
        return ArrayList(databaseHelper.getProductsInShops())
    }

    fun getTopThreeCombinations(): ArrayList<Combination> {
        var topThreeCombinations: ArrayList<Combination> = ArrayList(3)
        topThreeCombinations.add(listGenerator.oneShopCombination)
        topThreeCombinations.add(listGenerator.twoShopCombination)
        topThreeCombinations.add(listGenerator.threeShopCombination)
        return topThreeCombinations
    }
}
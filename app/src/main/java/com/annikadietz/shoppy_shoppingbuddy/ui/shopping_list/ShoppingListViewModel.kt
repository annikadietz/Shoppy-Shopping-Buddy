package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_list

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.*
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelperInterface
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information.ShoppingCombinationInformationFragment

class ShoppingListViewModel : ViewModel() {
    var databaseHelper : DatabaseHelperInterface = NewDatabaseHelper

    private val _text = MutableLiveData<String>().apply {
        value = "Find your product"
    }
    private val _shoppingList = MutableLiveData<MutableList<Product>>().apply {
        // TODO: Get the actual shopping list and not all products!
        this.value = databaseHelper.getProducts()
    }

    val text: LiveData<String> = _text
    val shoppingList: MutableLiveData<MutableList<Product>> = _shoppingList

    fun initiateList(root: View, owner: LifecycleOwner) : View {
        var shoppingListListView = root.findViewById<ListView>(R.id.shopping_list_view)
        val shoppingListObserver = Observer<MutableList<Product>> { newProductList ->
            val listItems = arrayOfNulls<String>(newProductList.size)
            for (i in 0 until newProductList.size) {
                val product = newProductList[i]
                listItems[i] = product.name
            }
            val adapter = ArrayAdapter(root.context, android.R.layout.simple_list_item_1, listItems)
            shoppingListListView.adapter = adapter
        }

        this.shoppingList.observe(owner, shoppingListObserver)
        return shoppingListListView
    }

    fun initiateButton(root: View, fragmentManager: FragmentManager) : Button {
        var shopButton = root.findViewById<Button>(R.id.find_shopping_options_button)
        shopButton.setOnClickListener(View.OnClickListener {
            var fragment = ShoppingCombinationInformationFragment()
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        })
        return  shopButton
    }
}
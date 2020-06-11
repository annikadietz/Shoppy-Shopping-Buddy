package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.R
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information.ShoppingCombinationInformationFragment


class ShoppingList : Fragment() {

    private lateinit var shoppingListViewModel: ShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        var shoppingListListView = root.findViewById<ListView>(R.id.shopping_list_view)
        val shoppingListObserver = Observer<MutableList<Product>> { newProductList ->
            val listItems = arrayOfNulls<String>(newProductList.size)
            for (i in 0 until newProductList.size) {
                val product = newProductList[i]
                listItems[i] = product.name
            }
            val adapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, listItems)
            shoppingListListView.adapter = adapter
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        shoppingListViewModel.shoppingList.observe(this, shoppingListObserver)

        var shop_now_button = root.findViewById<Button>(R.id.find_shopping_options_button)
        shop_now_button.setOnClickListener(View.OnClickListener {
            var fragment = ShoppingCombinationInformationFragment()
            replaceFragment(fragment)
        })

        return root
    }

    fun replaceFragment(someFragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, someFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
//package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_list
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.ListView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentTransaction
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import com.annikadietz.shoppy_shoppingbuddy.Model.Product
//import com.annikadietz.shoppy_shoppingbuddy.R
//import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information.ShoppingCombinationInformationFragment
//
//
//class ShoppingListFragment : Fragment() {
//
//    private lateinit var shoppingListViewModel: ShoppingListViewModel
//    private lateinit var shoppingListListView: View
//    private lateinit var shopNowButton: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        shoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
//        shoppingListListView = shoppingListViewModel.initiateList(root, this)
//        shopNowButton = shoppingListViewModel.initiateButton(root, this.requireFragmentManager())
//        return root
//    }
//}
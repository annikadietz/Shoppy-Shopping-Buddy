package com.annikadietz.shoppy_shoppingbuddy.ui.shop_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.annikadietz.shoppy_shoppingbuddy.MainActivity
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R

/**
 * A simple [Fragment] subclass.
 * Use the [ShopSelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopSelectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: ShopRecyclerAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var searchView: SearchView
    var viewModel = ShopSelectionViewModel()
    var databaseHelper = NewDatabaseHelper

    private lateinit var shops: List<Shop>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_shop_selection, container, false)
        searchView = root.findViewById(R.id.searchViewShop)
        recyclerView = root.findViewById(R.id.recyclerViewShop)

        shops = databaseHelper.getShops()

        recyclerAdapter = ShopRecyclerAdapter(shops, databaseHelper.getMyShops(), databaseHelper)

        recyclerView.layoutManager = LinearLayoutManager(this.context)

        var dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = recyclerAdapter

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            recyclerAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }

        searchView = root.findViewById(R.id.searchViewShop)
        searchView.isIconifiedByDefault = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                recyclerAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })

        return root
    }
}
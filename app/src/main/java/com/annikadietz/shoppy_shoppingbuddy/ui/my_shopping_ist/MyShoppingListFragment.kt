package com.annikadietz.shoppy_shoppingbuddy.ui.my_shopping_ist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelperInterface
import com.annikadietz.shoppy_shoppingbuddy.R


class MyShoppingListFragment(dbh: DatabaseHelperInterface) : Fragment() {
    var databaseHelper: DatabaseHelperInterface = dbh
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: ShoppingListRecyclerAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_my_shopping_list, container, false)
        setUp()
        return root
    }

    fun setUp() {
        recyclerView = root.findViewById(R.id.recyclerView1)

        var dividerItemDecoration =
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerAdapter = ShoppingListRecyclerAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = recyclerAdapter

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout1)

        swipeRefreshLayout.setOnRefreshListener {
            recyclerAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}

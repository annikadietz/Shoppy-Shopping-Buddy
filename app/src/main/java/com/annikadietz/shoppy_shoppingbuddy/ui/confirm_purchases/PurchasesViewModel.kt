package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Canvas
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*

class PurchasesViewModel : ViewModel() {
    var moviesList = ArrayList<String>()

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: PurchasesAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    lateinit var deletedMovie: String
    var archivedMovies = ArrayList<String>()

    fun initList() {
        // todo use actual list provided after chosing combo
        moviesList.add("Pizza")
        moviesList.add("Eggs")
        moviesList.add("Potato")
        moviesList.add("Ham")
    }

    fun setupSwipeRefresh(root: View) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            /// TODO add back grocery list if fucked up
            moviesList.add("Pizza")
            moviesList.add("Eggs")
            moviesList.add("Potato")
            moviesList.add("Ham")
            recyclerAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    fun setupSwipingDeleteAndArchieve(simpleCallback: ItemTouchHelper.SimpleCallback) {
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
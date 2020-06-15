package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class PurchasesViewModel : ViewModel() {
    var shoppingList = ArrayList<String>()

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: PurchasesAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    lateinit var deletedMovie: String
    var archivedMovies = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun initList() {
        // todo use actual list provided after choosing combo
        shoppingList.add("Pizza")
        shoppingList.add("Eggs")
        shoppingList.add("Potato")
        shoppingList.add("Ham")
        confirmPurchase()
    }

    fun setupSwipeRefresh(root: View) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            /// TODO add back grocery list if fucked up
            shoppingList.add("Pizza")
            shoppingList.add("Eggs")
            shoppingList.add("Potato")
            shoppingList.add("Ham")
            recyclerAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    fun setupSwipingDeleteAndConfirm(simpleCallback: ItemTouchHelper.SimpleCallback) {
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun confirmPurchase() {

        val t: Timestamp = Timestamp(Date())

    }
}
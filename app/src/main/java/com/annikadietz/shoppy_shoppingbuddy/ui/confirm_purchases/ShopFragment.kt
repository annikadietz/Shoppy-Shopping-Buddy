package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.annikadietz.shoppy_shoppingbuddy.Model.*
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class ShopFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var container: LinearLayout
    lateinit var recyclerAdapter: PurchasesAdapter
    lateinit var firstShop: ConstraintLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var shoppingItems = NewDatabaseHelper.getShoppingItems()
    private lateinit var root: View
    var combo = Combination()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
//recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerAdapter = PurchasesAdapter(combo)
//        recyclerView.layoutManager = LinearLayoutManager(this.context)
//        recyclerView.adapter = recyclerAdapter
//        val dividerItemDecoration =
//            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
//        recyclerView.addItemDecoration(dividerItemDecoration)
//        var shoppingItem = ShoppingItem(Product(
//            "",
//            Type()),
//            Shop("Jumbo","RT75774","Gadkkd 122"),
//            Price(4.2)
//        )
//        firstShop = root.findViewById<ConstraintLayout>(R.id.first_shop)
//        setUpShop(shoppingItem)


       //setupSwipeRefresh(root)
        //setupSwipingDeleteAndConfirm(simpleCallback)

        return root
    }

    fun setUpShop(shoppingItem: ShoppingItem){
        var shopDirectionsFragment = ShopDirectionsManager(
            "Hoitingeslag%2029,%207824%20KG",
            shoppingItem.shop,
            root,
            R.id.first_shop_name,
            R.id.first_shop_address,
            R.id.first_shop_directions
        )


    }

    private var simpleCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {

//                        Snackbar.make(
//                            purchasesViewModel.recyclerView,
//                            purchasesViewModel.deletedMovie!!,
//                            Snackbar.LENGTH_LONG
//                        )
//                            .setAction("Undo") {
//                                purchasesViewModel.shoppingList.add(
//                                    position,
//                                    purchasesViewModel.deletedMovie
//                                )
//                                purchasesViewModel.recyclerAdapter.notifyItemInserted(position)
//                            }.show()
                    }
                    ItemTouchHelper.RIGHT -> {
//                        val movieName = purchasesViewModel.shoppingList[position]

//                        Snackbar.make(
//                            purchasesViewModel.recyclerView,
//                            "$movieName, Archived.",
//                            Snackbar.LENGTH_LONG
//                        )
//                            .setAction("Undo") {
//
//
//                            }.show()
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    activity,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(activity!!.applicationContext, R.color.colorAccent)
                    )
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(activity!!.applicationContext, R.color.colorPrimaryDark)
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_archive_black_24dp)
                    .setActionIconTint(
                        ContextCompat.getColor(
                            recyclerView.context,
                            android.R.color.white
                        )
                    )
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

    fun setupSwipingDeleteAndConfirm(simpleCallback: ItemTouchHelper.SimpleCallback) {
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        var combo: Combination
        NewDatabaseHelper.getMyCombo().addOnSuccessListener {
            combo = it.toObject(Combination::class.java)!!
            if (combo.shops.size > 0){
                var shop = combo.shops[0]
                setUpFirstShop(shop)
                var shoppingItems = arrayListOf<ShoppingItem>()
                combo.shoppingItems.forEach {
                    if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                        shoppingItems.add(it)
                    }
                }
                setUpRecyclerView(root.findViewById(R.id.first_shop_recyclerview), shoppingItems)
            }
            if (combo.shops.size > 1){
                var shop = combo.shops[1]
                setUpSecondShop(shop)
                var shoppingItems = arrayListOf<ShoppingItem>()
                combo.shoppingItems.forEach {
                    if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                        shoppingItems.add(it)
                    }
                }
                setUpRecyclerView(root.findViewById(R.id.second_shop_recyclerview), shoppingItems)
            }
            if (combo.shops.size > 2){
                var shop = combo.shops[2]
                setUpThirdShop(shop)
                var shoppingItems = arrayListOf<ShoppingItem>()
                combo.shoppingItems.forEach {
                    if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                        shoppingItems.add(it)
                    }
                }
                setUpRecyclerView(root.findViewById(R.id.third_shop_recyclerview), shoppingItems)
            }
        }



        recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerAdapter = PurchasesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = recyclerAdapter
//        val dividerItemDecoration =
//            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
//        recyclerView.addItemDecoration(dividerItemDecoration)

        setupSwipingDeleteAndConfirm()
        return root
    }

    fun setUpFirstShop(shop: Shop){
        ShopDirectionsManager(
            "Hoitingeslag%2029,%207824%20KG",
            shop,
            root,
            R.id.first_shop_name,
            R.id.first_shop_address,
            R.id.first_shop_directions
        )
    }

    fun setUpRecyclerView(recyclerView: RecyclerView, shoppingItems: ArrayList<ShoppingItem>){
        recyclerView.adapter = PurchasesAdapter(shoppingItems)
        var dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

    }

    fun setUpSecondShop(shop: Shop){
        ShopDirectionsManager(
            "Hoitingeslag%2029,%207824%20KG",
            shop,
            root,
            R.id.second_shop_name,
            R.id.second_shop_address,
            R.id.second_shop_directions
        )
    }

    fun setUpThirdShop(shop: Shop){
        ShopDirectionsManager(
            "Hoitingeslag%2029,%207824%20KG",
            shop,
            root,
            R.id.third_shop_name,
            R.id.third_shop_address,
            R.id.third_shop_directions
        )
    }

    fun setupSwipingDeleteAndConfirm() {
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
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

}
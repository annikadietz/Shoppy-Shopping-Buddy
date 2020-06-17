package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.Model.ShoppingItem
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class ShopFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var container: LinearLayout
    lateinit var recyclerAdapter: PurchasesAdapter
    lateinit var firstShop: ConstraintLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var shoppingItems = NewDatabaseHelper.getShoppingItems()
    private lateinit var root: View
    private lateinit var shoppingItemDeleted: ShoppingItem


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
                val shop = combo.shops[0]
                setUpFirstShop(shop, "Hoitingeslag%2029,%207824%20KG")
                val shoppingItems = arrayListOf<ShoppingItem>()
                combo.shoppingItems.forEach {
                    if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                        shoppingItems.add(it)
                    }
                }
                setUpRecyclerView(root.findViewById(R.id.first_shop_recyclerview), shoppingItems)
            }
            if (combo.shops.size > 1){
                val shop = combo.shops[1]
                setUpSecondShop(shop, combo.shops[0].streetAddress + ", " + combo.shops[0].postCode)
                val shoppingItems = arrayListOf<ShoppingItem>()
                combo.shoppingItems.forEach {
                    if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                        shoppingItems.add(it)
                    }
                }
                setUpRecyclerView(root.findViewById(R.id.second_shop_recyclerview), shoppingItems)
            }
            if (combo.shops.size > 2){
                val shop = combo.shops[2]
                setUpThirdShop(shop, combo.shops[1].streetAddress + ", " + combo.shops[1].postCode)
                val shoppingItems = arrayListOf<ShoppingItem>()
                combo.shoppingItems.forEach {
                    if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                        shoppingItems.add(it)
                    }
                }
                setUpRecyclerView(root.findViewById(R.id.third_shop_recyclerview), shoppingItems)
            }
        }

        return root
    }

    private fun setUpFirstShop(shop: Shop, startingLoc: String){
        ShopDirectionsManager(
            startingLoc,
            shop,
            root,
            R.id.first_shop_name,
            R.id.first_shop_address,
            R.id.first_shop_directions
        )
    }

    private fun setUpSecondShop(shop: Shop, startingLoc: String?){
        ShopDirectionsManager(
            startingLoc,
            shop,
            root,
            R.id.second_shop_name,
            R.id.second_shop_address,
            R.id.second_shop_directions
        )
    }

    private fun setUpThirdShop(shop: Shop, startingLoc: String){
        ShopDirectionsManager(
            startingLoc,
            shop,
            root,
            R.id.third_shop_name,
            R.id.third_shop_address,
            R.id.third_shop_directions
        )
    }

    fun setUpRecyclerView(recyclerView: RecyclerView, shoppingItems: ArrayList<ShoppingItem>){
        val pa = PurchasesAdapter(shoppingItems)
        recyclerView.adapter = pa
        val dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(this.context)


        setupSwipingDeleteAndConfirm(recyclerView, shoppingItems, pa)
    }

    private fun setupSwipingDeleteAndConfirm(recyclerView: RecyclerView, shoppingItems: ArrayList<ShoppingItem>, pa: PurchasesAdapter) {
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {


                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            Log.w("IndexP", position.toString())
                        shoppingItemDeleted = shoppingItems[position]
                        shoppingItems.removeAt(position)
                            pa.notifyItemRemoved(position)
//
                        Snackbar.make(
                            root,
                            shoppingItemDeleted.product.name,
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                shoppingItems.add(
                                    position,
                                    shoppingItemDeleted
                                )
                                pa.notifyItemInserted(position)
                            }.show()
                        }
                        ItemTouchHelper.RIGHT -> {
                        val shoppingItemCurrent = shoppingItems[position]
                        NewDatabaseHelper.confirmPurchase(shoppingItemCurrent)
                        NewDatabaseHelper.confirmPrice(shoppingItemCurrent)

                        shoppingItems.removeAt(position)
                        pa.notifyItemRemoved(position)
                        Snackbar.make(
                            root,
                            "${shoppingItemCurrent.product.name} confirmed",
                            Snackbar.LENGTH_LONG
                        )
//                            .setAction("Undo") {
//
//
//                            }
//
                            .show()
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

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

//    private var simpleCallback: ItemTouchHelper.SimpleCallback =
//        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                when (direction) {
//                    ItemTouchHelper.LEFT -> {
//                        Log.w("IndexP", position.toString())
////                        shoppingItem = moviesList.get(position)
////                        moviesList.removeAt(position)
////                        recyclerAdapter.notifyItemRemoved(position)
////
////                        Snackbar.make(
////                            root,
////                            purchasesViewModel.deletedMovie!!,
////                            Snackbar.LENGTH_LONG
////                        )
////                            .setAction("Undo") {
////                                purchasesViewModel.shoppingList.add(
////                                    position,
////                                    purchasesViewModel.deletedMovie
////                                )
////                                purchasesViewModel.recyclerAdapter.notifyItemInserted(position)
////                            }.show()
//                    }
//                    ItemTouchHelper.RIGHT -> {
////                        val movieName = purchasesViewModel.shoppingList[position]
//
////                        Snackbar.make(
////                            purchasesViewModel.recyclerView,
////                            "$movieName, Archived.",
////                            Snackbar.LENGTH_LONG
////                        )
////                            .setAction("Undo") {
////
////
////                            }.show()
//                    }
//                }
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                RecyclerViewSwipeDecorator.Builder(
//                    activity,
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//                    .addSwipeLeftBackgroundColor(
//                        ContextCompat.getColor(activity!!.applicationContext, R.color.colorAccent)
//                    )
//                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
//                    .addSwipeRightBackgroundColor(
//                        ContextCompat.getColor(activity!!.applicationContext, R.color.colorPrimaryDark)
//                    )
//                    .addSwipeRightActionIcon(R.drawable.ic_archive_black_24dp)
//                    .setActionIconTint(
//                        ContextCompat.getColor(
//                            recyclerView.context,
//                            android.R.color.white
//                        )
//                    )
//                    .create()
//                    .decorate()
//                super.onChildDraw(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//            }
//        }

}
package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
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
import com.google.android.material.textfield.TextInputEditText
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
        NewDatabaseHelper.getMyCombo().addOnSuccessListener {

            if (it != null) {
                var combo = it.toObject(Combination::class.java)
                if (combo != null) {
                    if (combo!!.shops.size > 0){
                        val shop = combo!!.shops[0]
                        setUpFirstShop(shop, NewDatabaseHelper.address)
                        val shoppingItems = arrayListOf<ShoppingItem>()
                        combo!!.shoppingItems.forEach {
                            if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                                shoppingItems.add(it)
                            }
                        }
                        setUpRecyclerView(root.findViewById(R.id.first_shop_recyclerview), shoppingItems)
                    }
                    if (combo!!.shops.size > 1){
                        val shop = combo!!.shops[1]
                        setUpSecondShop(shop, combo!!.shops[0].streetAddress + ", " + combo!!.shops[0].postCode)
                        val shoppingItems = arrayListOf<ShoppingItem>()
                        combo!!.shoppingItems.forEach {
                            if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                                shoppingItems.add(it)
                            }
                        }
                        setUpRecyclerView(root.findViewById(R.id.second_shop_recyclerview), shoppingItems)
                    }
                    if (combo!!.shops.size > 2){
                        val shop = combo!!.shops[2]
                        setUpThirdShop(shop, combo!!.shops[1].streetAddress + ", " + combo!!.shops[1].postCode)
                        val shoppingItems = arrayListOf<ShoppingItem>()
                        combo!!.shoppingItems.forEach {
                            if(it.shop.name == shop.name && it.shop.streetAddress == shop.streetAddress&& it.shop.postCode == shop.postCode){
                                shoppingItems.add(it)
                            }
                        }
                        setUpRecyclerView(root.findViewById(R.id.third_shop_recyclerview), shoppingItems)
                    }
                }
            }
        }

        return root
    }

    private fun setUpFirstShop(shop: Shop, startingLoc: String) {
        ShopDirectionsManager(
            startingLoc,
            shop,
            root,
            R.id.first_shop_name,
            R.id.first_shop_address,
            R.id.first_shop_directions
        )
    }

    private fun setUpSecondShop(shop: Shop, startingLoc: String?) {
        ShopDirectionsManager(
            startingLoc,
            shop,
            root,
            R.id.second_shop_name,
            R.id.second_shop_address,
            R.id.second_shop_directions
        )
    }

    private fun setUpThirdShop(shop: Shop, startingLoc: String) {
        ShopDirectionsManager(
            startingLoc,
            shop,
            root,
            R.id.third_shop_name,
            R.id.third_shop_address,
            R.id.third_shop_directions
        )
    }

    fun setUpRecyclerView(recyclerView: RecyclerView, shoppingItems: ArrayList<ShoppingItem>) {
        val pa = PurchasesAdapter(shoppingItems)
        recyclerView.adapter = pa
        val dividerItemDecoration =
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(this.context)


        setupSwipingDeleteAndConfirm(recyclerView, shoppingItems, pa)
    }

    private fun setupSwipingDeleteAndConfirm(
        recyclerView: RecyclerView,
        shoppingItems: ArrayList<ShoppingItem>,
        pa: PurchasesAdapter
    ) {
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {


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
                            // TODO maybe even use these last 4 lines before direction, because its the same code anyway
                            Log.w("IndexP", position.toString())
                            shoppingItemDeleted = shoppingItems[position]
                            shoppingItems.removeAt(position)
                            pa.notifyItemRemoved(position)

                            NewDatabaseHelper.deleteProductFormMyShoppingList(shoppingItemDeleted.product)
//
                            Snackbar.make(
                                root,
                                shoppingItemDeleted.product.name,
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("Undo") {
                                    NewDatabaseHelper.addProductToMyShoppingList(shoppingItemDeleted.product)
                                    shoppingItems.add(
                                        position,
                                        shoppingItemDeleted
                                    )
                                    pa.notifyItemInserted(position)
                                }.show()
                        }
                        ItemTouchHelper.RIGHT -> {
                            shoppingItemDeleted = shoppingItems[position]
                            val shoppingItemCurrent = shoppingItems[position]
                            shoppingItems.removeAt(position)
                            pa.notifyItemRemoved(position)

                            NewDatabaseHelper.deleteProductFormMyShoppingList(shoppingItemDeleted.product)


                            AlertDialog.Builder(context)
                                .setTitle("Is the price really €${shoppingItemCurrent.price.price} ?")
                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(R.string.confirm_dialog,
                                    DialogInterface.OnClickListener { dialog, which ->
                                        NewDatabaseHelper.confirmPurchase(shoppingItemCurrent)
                                        NewDatabaseHelper.confirmPrice(shoppingItemCurrent)

                                    }) // A null listener allows the button to dismiss the dialog and take no further action. (currently where the DialogInterface is)
                                .setNegativeButton(
                                    R.string.un_confirm_dialog,
                                    DialogInterface.OnClickListener { dialog, which ->
                                        // Continue with delete operation
                                        val input = EditText(context);
                                        input.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                                        input.setRawInputType(Configuration.KEYBOARD_12KEY)

                                        val dialog: AlertDialog = AlertDialog.Builder(context)
                                            .setTitle("Please enter correct price for ${shoppingItemCurrent.product.name}")
                                            .setView(input)
                                            .setPositiveButton(
                                                R.string.confirm_dialog,
                                                DialogInterface.OnClickListener { dialog, which ->
                                                    // TODO api and method beneath this seem sketch

                                                    Log.w(
                                                        "newprice",
                                                        shoppingItemCurrent.price.price.toString()
                                                    )

                                                    val newPrice =
                                                        input.text.toString().toDouble()

                                                    Log.w("oldprice", newPrice.toString())
                                                    NewDatabaseHelper.requestPriceChange(
                                                        shoppingItemCurrent, newPrice
                                                    )

                                                })
                                            .setNegativeButton(
                                                "Cancel",
                                                DialogInterface.OnClickListener { dialog, which ->
                                                    NewDatabaseHelper.addProductToMyShoppingList(shoppingItemDeleted.product)
                                                    shoppingItems.add(
                                                        position,
                                                        shoppingItemDeleted
                                                    )
                                                    pa.notifyItemInserted(position)
                                                }).create()

                                        dialog.show()
                                        input.doOnTextChanged { text, start, count, after ->
                                            var textString = text.toString()
                                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                                                textString.toDoubleOrNull() != null
                                        }
                                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                                    })
                                .show()
                        }
                    }
                }

                val EditText.doubleValue: Double
                    get() = text.toString().toDoubleOrNull() ?: 0.0

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
                            ContextCompat.getColor(
                                activity!!.applicationContext,
                                R.color.colorAccent
                            )
                        )
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                activity!!.applicationContext,
                                R.color.colorPrimaryDark
                            )
                        )
                        .addSwipeRightActionIcon(
                            R.drawable.ic_baseline_shopping_basket_24
                        )
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
}
package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.R
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class PurchasesFragment : Fragment(), RecyclerViewClickInterface {
    private lateinit var purchasesViewModel: PurchasesViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        purchasesViewModel =
            ViewModelProviders.of(this).get(PurchasesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_confirm_items, container, false)
        purchasesViewModel.initList()


        // I did it like this because passing view or recycle life is not optimal to pass to ViewModel
        purchasesViewModel.recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        purchasesViewModel.recyclerAdapter = PurchasesAdapter(purchasesViewModel.shoppingList, this)
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        purchasesViewModel.recyclerView.adapter = purchasesViewModel.recyclerAdapter
        val dividerItemDecoration =
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        purchasesViewModel.recyclerView.addItemDecoration(dividerItemDecoration)

        purchasesViewModel.setupSwipeRefresh(root)
        purchasesViewModel.setupSwipingDeleteAndArchieve(simpleCallback)


        return root
    }

    override fun onItemClick(position: Int) {
//        val intent = Intent(activity, NewActivity::class.java)
//        intent.putExtra("MOVIE_NAME", moviesList.get(position))
//        startActivity(intent)


        AlertDialog.Builder(context)
            .setTitle("Confirm price")
            .setMessage("We want to keep  the prices as up to date as possible. \n\nDoes the price ${purchasesViewModel.shoppingList[position]} match") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(R.string.confirm_dialog,
                DialogInterface.OnClickListener { dialog, which ->
                    // Continue with delete operation
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(R.string.un_confirm_dialog, null)
            .setIcon(android.R.drawable.checkbox_on_background)
            .show()


        // anther version

//        var text = "";
//
//        val builder = AlertDialog.Builder(activity)
//        builder.setTitle("Title")
//
//        // Set up the input
//        val input = EditText(activity)
//        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        input.inputType = InputType.TYPE_CLASS_TEXT
//        builder.setView(input)
//
//        // Set up the buttons
//        builder.setPositiveButton(
//            "OK"
//        ) { dialog, which -> text = input.text.toString() }
//        builder.setNegativeButton(
//            "Cancel"
//        ) { dialog, which -> dialog.cancel() }
//
//        builder.show()
    }

    override fun onLongItemClick(position: Int) {
//        moviesList.remove(position);
//        recyclerAdapter.notifyItemRemoved(position);
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
                        purchasesViewModel.deletedMovie = purchasesViewModel.shoppingList[position]
                        purchasesViewModel.shoppingList.removeAt(position)
                        purchasesViewModel.recyclerAdapter.notifyItemRemoved(position)
                        Snackbar.make(
                            purchasesViewModel.recyclerView,
                            purchasesViewModel.deletedMovie!!,
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                purchasesViewModel.shoppingList.add(
                                    position,
                                    purchasesViewModel.deletedMovie
                                )
                                purchasesViewModel.recyclerAdapter.notifyItemInserted(position)
                            }.show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        val movieName = purchasesViewModel.shoppingList[position]
                        purchasesViewModel.archivedMovies.add(movieName)
                        purchasesViewModel.shoppingList.removeAt(position)
                        purchasesViewModel.recyclerAdapter.notifyItemRemoved(position)
                        Snackbar.make(
                            purchasesViewModel.recyclerView,
                            "$movieName, Archived.",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                purchasesViewModel.archivedMovies.removeAt(
                                    purchasesViewModel.archivedMovies.lastIndexOf(
                                        movieName
                                    )
                                )
                                purchasesViewModel.shoppingList.add(position, movieName)
                                purchasesViewModel.recyclerAdapter.notifyItemInserted(position)
                            }.show()
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
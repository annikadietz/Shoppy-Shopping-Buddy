package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.R

class PurchasesAdapter(
    var moviesList: List<String>,
    recyclerViewClickInterface: RecyclerViewClickInterface
) :
    RecyclerView.Adapter<PurchasesAdapter.ViewHolder>() {

    private val recyclerViewClickInterface: RecyclerViewClickInterface = recyclerViewClickInterface

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.confirm_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.rowCountTextView.text = position.toString()
        holder.textView.text = moviesList[position]
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //        var imageView: ImageView
        var textView: TextView
        var rowCountTextView: TextView

        init {
//            imageView = itemView.findViewById(R.id.imageView)
            textView = itemView.findViewById(R.id.textView)
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView)
            itemView.setOnClickListener { recyclerViewClickInterface.onItemClick(adapterPosition) }
            itemView.setOnLongClickListener { //                    moviesList.remove(getAdapterPosition());
                //                    notifyItemRemoved(getAdapterPosition());
                recyclerViewClickInterface.onLongItemClick(adapterPosition)
                true
            }
        }
    }

    companion object {
        private const val TAG = "RecyclerAdapter"
    }

}







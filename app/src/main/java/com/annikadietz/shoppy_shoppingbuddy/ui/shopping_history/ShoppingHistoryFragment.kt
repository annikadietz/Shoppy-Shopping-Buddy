package com.annikadietz.shoppy_shoppingbuddy.ui.shopping_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.R

class ShoppingHistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        return root
    }
}
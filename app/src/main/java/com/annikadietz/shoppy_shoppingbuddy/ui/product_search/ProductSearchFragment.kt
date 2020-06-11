package com.annikadietz.shoppy_shoppingbuddy.ui.product_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R


class ProductSearchFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var searchView: SearchView
    lateinit var typeSpinner: Spinner
    var productTypes = arrayListOf<String>()
    lateinit var selectedType: String
    private lateinit var products: List<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_product_search, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)

        var dividerItemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        products = NewDatabaseHelper.getProducts()

        recyclerAdapter = RecyclerAdapter(products)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = recyclerAdapter

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            recyclerAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }

        searchView = root.findViewById(R.id.searchView)
        searchView.isIconifiedByDefault = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                recyclerAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        typeSpinner = root.findViewById(R.id.typeSpinner)
        productTypes = NewDatabaseHelper.productTypes

        var typeAdapter = this.context?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item, productTypes)
        }
        typeAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeAdapter?.add("")
        typeSpinner.adapter = typeAdapter
        typeSpinner.setSelection(0)
        typeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                selectedType = productTypes[position]
                recyclerAdapter.selectedType = selectedType
                recyclerAdapter.filter.filter(searchView.query)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        return root
    }
}

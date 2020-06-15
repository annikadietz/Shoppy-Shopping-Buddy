package com.annikadietz.shoppy_shoppingbuddy.ui.product_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.annikadietz.shoppy_shoppingbuddy.DatabaseHelperInterface
import com.annikadietz.shoppy_shoppingbuddy.ListGenerator
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper
import com.annikadietz.shoppy_shoppingbuddy.R


class ProductSearchFragment(dbh: DatabaseHelperInterface) : Fragment() {
    var databaseHelper: DatabaseHelperInterface = dbh
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var searchView: SearchView
    lateinit var typeSpinner: Spinner
    var productTypes = arrayListOf<String>()
    lateinit var selectedType: String
    private var products: List<Product> = databaseHelper.getProducts()
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_product_search, container, false)
        setUp()
        return root
    }

    fun setUp(){
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

        fillProductTypes()
        typeSpinner = root.findViewById(R.id.typeSpinner)

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
                                        view: View?, position: Int, id: Long) {
                selectedType = productTypes[position]
                recyclerAdapter.selectedType = selectedType
                recyclerAdapter.filter.filter(searchView.query)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

    }

    fun SearchView.showKeyboard() {
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun SearchView.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun fillProductTypes(){
        products.forEach {
            var productType = it.type?.name
            if (productType != null) {
                if (productTypes.find { it == productType } == null) {
                    productTypes.add(productType)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fillProductTypes()
        searchView.showKeyboard()
    }
}

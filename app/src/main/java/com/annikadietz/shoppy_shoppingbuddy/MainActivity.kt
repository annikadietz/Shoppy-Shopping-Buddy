package com.annikadietz.shoppy_shoppingbuddy

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.ProductSearchFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_list.ShoppingListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity()  {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNav: BottomNavigationView
    private var productSearchFragment = ProductSearchFragment()
    private var shoppingListFragment = ShoppingListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, productSearchFragment).commit()
        DatabaseHelper.subscribeShops()
        DatabaseHelper.subscribeProducts()
        DatabaseHelper.subscribeCategories()

        NewDatabaseHelper.subscribeShops()
//        NewDatabaseHelper.subscribeProducts()
//        NewDatabaseHelper.subscribeProductInShop()

    }

    private var navListener = BottomNavigationView.OnNavigationItemSelectedListener(object :
        BottomNavigationView.OnNavigationItemSelectedListener, (MenuItem) -> Boolean {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            return true
        }
        override fun invoke(menuItem: MenuItem): Boolean {
            var selectedFragment: Fragment = when(menuItem.itemId) {
                R.id.nav_search -> productSearchFragment
                R.id.nav_shoppingList -> shoppingListFragment
                R.id.nav_yourShops -> productSearchFragment
                R.id.nav_shop -> productSearchFragment
                else -> productSearchFragment
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            return true
        }
    })

}

package com.annikadietz.shoppy_shoppingbuddy

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.ProductSearchFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.shop_selection.ShopSelectionFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_list.ShoppingListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity()  {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNav: BottomNavigationView
    private var productSearchFragment = ProductSearchFragment()
    private var shoppingListFragment = ShoppingListFragment()
    private var shopSelectionFragment = ShopSelectionFragment()

    var uid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uid = intent.getStringExtra("UID")

        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, productSearchFragment).commit()
        DatabaseHelper.subscribeShops()
        DatabaseHelper.subscribeProducts()
        DatabaseHelper.subscribeCategories()

        NewDatabaseHelper.subscribeShops()
        NewDatabaseHelper.subscribeProducts()
        NewDatabaseHelper.subscribeProductInShop()
        NewDatabaseHelper.subscribeMyShops()
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
                R.id.nav_yourShops -> shopSelectionFragment
                R.id.nav_shop -> productSearchFragment
                else -> productSearchFragment
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            return true
        }
    })

    fun onLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, AuthActivity::class.java))
    }

}

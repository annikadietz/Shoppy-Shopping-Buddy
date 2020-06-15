package com.annikadietz.shoppy_shoppingbuddy

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.ui.my_shopping_ist.MyShoppingListFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.ProductSearchFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.shop_selection.ShopSelectionFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.your_list.YourListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity()  {
    private lateinit var bottomNav: BottomNavigationView
    private var productSearchFragment = ProductSearchFragment(NewDatabaseHelper)
    private var yourListFragment = YourListFragment({combo:Combination ->openGoShopping(combo) }, this@MainActivity)
    private var shopSelectionFragment = ShopSelectionFragment()
    private var myShoppingListFragment = MyShoppingListFragment(NewDatabaseHelper)


    var uid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uid = intent.getStringExtra("UID")

        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, productSearchFragment).commit()

        NewDatabaseHelper.subscribeShops()
        NewDatabaseHelper.subscribeProducts()
        NewDatabaseHelper.subscribeProductInShop()
        NewDatabaseHelper.subscribeMyShops()
        NewDatabaseHelper.subscribeMyShoppingList()
    }

    private var navListener = BottomNavigationView.OnNavigationItemSelectedListener(object :
        BottomNavigationView.OnNavigationItemSelectedListener, (MenuItem) -> Boolean {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            return true
        }
        override fun invoke(menuItem: MenuItem): Boolean {
            var selectedFragment: Fragment = when(menuItem.itemId) {
                R.id.nav_search -> productSearchFragment
                R.id.nav_shoppingList -> yourListFragment
                R.id.nav_yourShops -> shopSelectionFragment
                R.id.nav_shop -> myShoppingListFragment
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

    fun openGoShopping(combo: Combination) {
        print(combo.shops.size)
        // TODO: 15/06/2020 Make this function set the combination in the your list fragment
        bottomNav.selectedItemId = R.id.nav_search
    }


}

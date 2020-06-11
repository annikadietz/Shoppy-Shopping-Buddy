package com.annikadietz.shoppy_shoppingbuddy

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.ProductSearchFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_list.ShoppingListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


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

        // TODO use correct UID
        NewDatabaseHelper.subscribeShops("Hmfo4r4SrqVG1Kp1ZzduRFwajRW2")


//<<<<<<< HEAD
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
//        val navView: NavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_product_search, R.id.nav_product_add, R.id.nav_shopping_list, R.id.shop_selection
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//=======
//>>>>>>> master
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

    fun onLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, AuthActivity::class.java))
    }

}

package com.annikadietz.shoppy_shoppingbuddy

import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases.ShopFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.ProductSearchFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.shop_selection.ShopSelectionFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_history.ShoppingHistoryFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.your_list.YourListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private var productSearchFragment = ProductSearchFragment(DatabaseHelper)

    @RequiresApi(Build.VERSION_CODES.O)
    private var yourListFragment =
        YourListFragment({ combo: Combination -> openGoShopping(combo) }, this@MainActivity)
    private var shopSelectionFragment = ShopSelectionFragment()
    private var purchasesFragment = ShopFragment()
    private var historyFragment = ShoppingHistoryFragment()

    var uid: String = ""


    private var locationManager: LocationManager? = null

    private val locationListener: LocationListener = object : LocationListener {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onLocationChanged(location: Location) {
            var address = Geocoder(this@MainActivity.applicationContext).getFromLocation(
                location.latitude,
                location.longitude,
                1
            ).first()
            DatabaseHelper.address = address.getAddressLine(0)
            yourListFragment.updateAddressField()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uid = intent.getStringExtra("UID")

        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, productSearchFragment).commit()

        DatabaseHelper.subscribeShops()
        DatabaseHelper.subscribeProducts()
        DatabaseHelper.subscribeShoppingItems()
        DatabaseHelper.subscribeMyShops()
        DatabaseHelper.subscribeMyShoppingList()
        DatabaseHelper.subscribeMyShoppingItems()
        DatabaseHelper.subscribeShoppedProducts()

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        updateLocation()
    }

    private var navListener = BottomNavigationView.OnNavigationItemSelectedListener(object :
        BottomNavigationView.OnNavigationItemSelectedListener, (MenuItem) -> Boolean {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            return true
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun invoke(menuItem: MenuItem): Boolean {
            var selectedFragment: Fragment = when (menuItem.itemId) {
                R.id.nav_search -> productSearchFragment
                R.id.nav_shoppingList -> yourListFragment
                R.id.nav_yourShops -> shopSelectionFragment
                R.id.nav_shop -> purchasesFragment
                R.id.nav_history -> historyFragment
                else -> productSearchFragment
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment).commit()
            return true
        }
    })

    @RequiresApi(Build.VERSION_CODES.O)
    fun openGoShopping(combo: Combination) {
        print(combo.shops.size)
        DatabaseHelper.saveMyCombo(combo)
        bottomNav.selectedItemId = R.id.nav_shop
    }

    fun updateLocation() {
        try {
            // Request location updates
            locationManager?.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null)
        } catch (ex: SecurityException) {
            Log.d("location_security", "Security Exception, no location available")
        }
    }

}

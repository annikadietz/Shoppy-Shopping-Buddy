package com.annikadietz.shoppy_shoppingbuddy

import android.R
import android.view.LayoutInflater
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.ProductSearchFragment
import com.annikadietz.shoppy_shoppingbuddy.ui.product_search.RecyclerAdapter
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class ProductSearchFragmentUnitTest {

    @Test
    fun fillProductTypes_test() {
        var fragment = ProductSearchFragment(MockDatabaseHelper())

        fragment.fillProductTypes()

        assertTrue(fragment.productTypes.contains("Vegetables"))
        assertTrue(fragment.productTypes.contains("Frozen"))
        assertTrue(fragment.productTypes.contains("Fruits"))
        assertTrue(fragment.productTypes.contains("Dairy, eggs, and butter"))
        assertFalse(fragment.productTypes.contains("Daibutter"))
    }

    @Test
    fun filter_test() {
        var dbh = MockDatabaseHelper()
        var adapter = RecyclerAdapter(dbh.getProducts())

        adapter.filter.filter("pizza")
        assertTrue(adapter.productsFiltered[0].name?.contains("Pizza")!! && adapter.productsFiltered.size == 1)
        adapter.filter.filter("p")
        assertTrue(adapter.productsFiltered[0].name?.contains("Potatoes")!! && adapter.productsFiltered.size == 2)
        assertTrue(adapter.productsFiltered[1].name?.contains("Pizza")!! && adapter.productsFiltered.size == 2)
    }

    @Test
    fun filterWithType_test() {
        var dbh = MockDatabaseHelper()
        var adapter = RecyclerAdapter(dbh.getProducts())
        adapter.selectedType = "Frozen"
        adapter.filter.filter("pizza")
        assertTrue(adapter.productsFiltered[0].name?.contains("Pizza")!! && adapter.productsFiltered.size == 1)
        adapter.filter.filter("p")
        assertTrue(adapter.productsFiltered[0].name?.contains("Pizza")!! && adapter.productsFiltered.size == 1)
    }
}

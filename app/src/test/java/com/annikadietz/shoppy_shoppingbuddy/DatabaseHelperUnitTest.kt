package com.annikadietz.shoppy_shoppingbuddy

import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.Type
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DatabaseHelperUnitTest {

    @Test
    fun fillProductTypes_test() {
        var potatoes = Product("Potatoes - 1kg", Type("Vegetables"))
        var pizza = Product("Pizza - Italia", Type("Frozen"))
        var bananas = Product("Bananas - 1kg", Type("Fruits"))
        var eggs = Product("Large eggs", Type("Dairy, eggs, and butter"))
        var mockProducts = arrayListOf<Product>().apply {
            add(potatoes)
            add(pizza)
            add(bananas)
            add(eggs)
        }
        NewDatabaseHelper.setProducts(mockProducts)
        NewDatabaseHelper.fillProductTypes()
        NewDatabaseHelper.setProducts(mockProducts)
    }
}

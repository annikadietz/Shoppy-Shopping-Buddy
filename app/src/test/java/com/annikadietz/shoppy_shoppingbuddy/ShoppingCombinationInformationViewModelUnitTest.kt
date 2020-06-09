package com.annikadietz.shoppy_shoppingbuddy

import android.content.Context
import com.annikadietz.shoppy_shoppingbuddy.Model.Combination
import com.annikadietz.shoppy_shoppingbuddy.Model.Product
import com.annikadietz.shoppy_shoppingbuddy.Model.ProductInShop
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.ui.shopping_combination_information.ShoppingCombinationInformationViewModel
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*
import kotlin.collections.ArrayList

class ShoppingCombinationInformationViewModelUnitTest {
    val describedClass = ShoppingCombinationInformationViewModel(
        ListGenerator(Mockito.mock(Context::class.java), "Hoitingeslag%2029,%207824%20KG"), MockDatabaseHelper())

    @Test
    fun findShoppingList_Test() {
        var result: ArrayList<Product> = describedClass.findShoppingList()
        // TODO correct shopping list here
        var expectedArray: Array<Product> = describedClass.databaseHelper.getProducts().toTypedArray()

        result.forEachIndexed{ index, it ->
            Assert.assertEquals(it.name, expectedArray[index].name)
        }
    }

    @Test
    fun findShops_Test() {
        var result: ArrayList<Shop> = describedClass.findShops()
        var expectedArray: Array<Shop> = describedClass.databaseHelper.getShops().toTypedArray()

        result.forEachIndexed{ index, it ->
            Assert.assertEquals(it.name, expectedArray[index].name)
            Assert.assertEquals(it.postCode, expectedArray[index].postCode)
            Assert.assertEquals(it.streetAddress, expectedArray[index].streetAddress)
        }
    }

    @Test
    fun findProductsInShops_Test() {
        var result: ArrayList<ProductInShop> = describedClass.findProductsInShops()
        var expectedArray: Array<ProductInShop> = describedClass.databaseHelper.getProductsInShops().toTypedArray()

        result.forEachIndexed{ index, it ->
            Assert.assertEquals(it.price, expectedArray[index].price, 0.000001)
            Assert.assertEquals(it.product.name, expectedArray[index].product.name)
            Assert.assertEquals(it.shop.name, expectedArray[index].shop.name)
        }
    }

    @Test
    fun getTopThreeCombinations_Test() {
        var result: ArrayList<Combination> = describedClass.getTopThreeCombinations()
        var expectedArray: Array<Combination> = arrayOf(describedClass.listGenerator.oneShopCombination, describedClass.listGenerator.twoShopCombination, describedClass.listGenerator.threeShopCombination)

        Assert.assertArrayEquals(result.toArray(), expectedArray)
    }

}
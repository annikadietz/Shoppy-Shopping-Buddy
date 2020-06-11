package com.annikadietz.shoppy_shoppingbuddy.ui.shop_selection

import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.NewDatabaseHelper

class ShopSelectionViewModel {
    var myShops: ArrayList<Shop> = ArrayList(NewDatabaseHelper.getMyShops())

}
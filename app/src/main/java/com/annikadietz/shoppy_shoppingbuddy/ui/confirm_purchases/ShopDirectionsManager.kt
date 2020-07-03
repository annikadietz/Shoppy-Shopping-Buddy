package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop

//Hoitingeslag%2029,%207824%20KG
class ShopDirectionsManager(
    var startingLocation: String?,
    var shop: Shop,
    var root: View,
    var shopNameId: Int,
    var shopAddressId: Int,
    var shopDirectionsId: Int
) {
    init {
        val shopName = root.findViewById<TextView>(shopNameId)
        val shopAddress = root.findViewById<TextView>(shopAddressId)
        val shopDirectionsButton = root.findViewById<Button>(shopDirectionsId)
        shopName.text = shop.name
        shopAddress.text = shop.streetAddress
        shopDirectionsButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=${startingLocation}&daddr=${shop.streetAddress}, ${shop.postCode}")
            )
            root.context.startActivity(intent)
        }
    }
}

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
){
   init {
       var shopName = root.findViewById<TextView>(shopNameId)
       var shopAddress = root.findViewById<TextView>(shopAddressId)
       var shopDirectionsButton = root.findViewById<Button>(shopDirectionsId)
       shopName.text = shop.name
       shopAddress.text = shop.streetAddress
       shopDirectionsButton.setOnClickListener {
           val intent = Intent(
               Intent.ACTION_VIEW,
               Uri.parse("http://maps.google.com/maps?saddr=${startingLocation}&daddr=${shop.streetAddress}, ${shop.postCode}")
           )
           root.context.startActivity(intent)
//           var googleDirectionsService = GoogleDirectionsService()
//           var url =
//               "https://maps.googleapis.com/maps/api/directions/json?origin=${startingLocation}&destination=${shop.streetAddress}%20${shop.postCode}"
//
//           print(googleDirectionsService.getDirections(url))
       }
   }
}

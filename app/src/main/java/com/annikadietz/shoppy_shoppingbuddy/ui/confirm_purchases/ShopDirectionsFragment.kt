package com.annikadietz.shoppy_shoppingbuddy.ui.confirm_purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annikadietz.shoppy_shoppingbuddy.GoogleDirectionsService
import com.annikadietz.shoppy_shoppingbuddy.Model.Address
import com.annikadietz.shoppy_shoppingbuddy.Model.Shop
import com.annikadietz.shoppy_shoppingbuddy.R
//Hoitingeslag%2029,%207824%20KG
class ShopDirectionsManager(
    var startingLocation: String,
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
           var googleDirectionsService = GoogleDirectionsService()
           var url =
               "https://maps.googleapis.com/maps/api/directions/json?origin=${startingLocation}&destination=${shop.streetAddress}%20${shop.postCode}"

           print(googleDirectionsService.getDirections(url))
       }
   }
}

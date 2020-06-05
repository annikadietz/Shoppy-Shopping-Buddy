package com.annikadietz.shoppy_shoppingbuddy

import org.json.JSONObject

interface GoogleDirectionsAPI {
    fun getDirections(url: String): JSONObject
}
package com.annikadietz.shoppy_shoppingbuddy

import org.json.JSONObject

class MockGoogleDirectionsService: GoogleDirectionsAPI {
    var counter = 0
    var json: JSONObject = JSONObject()
    override fun getDirections(url: String): JSONObject {

        return json


    }
}
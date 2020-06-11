package com.annikadietz.shoppy_shoppingbuddy

import org.json.JSONObject

object CalculationHelper {
    fun calculateTotalDistance(directions: JSONObject): HashMap<String, Double> {
        var totalDistanceMap = HashMap<String, Double>()
        var totalKilometers = 0.0
        var totalTime = 0.0
        val routes = directions.getJSONArray("routes")
        val legs = routes.getJSONObject(0).getJSONArray("legs")

        for (i in 0 until legs.length()) {
            var distance =  legs.getJSONObject(i).getJSONObject("distance").getJSONObject("value").toString()
            var time = legs.getJSONObject(i).getJSONObject("duration").getJSONObject("value").toString()
            totalKilometers += distance.toDouble()
            totalTime += time.toDouble()
        }

        totalDistanceMap["distacne"] = totalKilometers
        totalDistanceMap["time"] = totalTime

        return totalDistanceMap
    }
}
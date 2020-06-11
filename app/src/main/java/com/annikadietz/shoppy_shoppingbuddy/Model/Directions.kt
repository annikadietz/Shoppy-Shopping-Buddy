package com.annikadietz.shoppy_shoppingbuddy.Model

import androidx.lifecycle.LiveData

class Directions {
    var distancetoTravel: Double?
    var timeToTravel: Double?
    constructor(distancetoTravel: Double, timeToTravel: Double){
        this.distancetoTravel = distancetoTravel
        this.timeToTravel = timeToTravel
    }
    constructor(){
        this.distancetoTravel = null
        this.timeToTravel = null
    }
}

package com.annikadietz.shoppy_shoppingbuddy.Model

class Shop {
    var name:String?
    var postCode: String?
    var streetAddress: String?

    constructor(name:String, postCode: String, streetAddress: String){
        this.name = name
        this.postCode = postCode
        this.streetAddress = streetAddress
    }
    constructor(){
        this.name = null
        this.postCode = null
        this.streetAddress = null
    }
}
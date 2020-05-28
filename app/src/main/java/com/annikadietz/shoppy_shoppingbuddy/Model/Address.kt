package com.annikadietz.shoppy_shoppingbuddy.Model

class Address {
    var postCode: String?
    var streetAddress: String?

    constructor(postCode: String, streetAddress: String){
        this.postCode = postCode
        this.streetAddress = streetAddress
    }
    constructor(){
        this.postCode = null
        this.streetAddress = null
    }
}
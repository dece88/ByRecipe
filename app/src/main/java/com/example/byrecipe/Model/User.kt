package com.example.byrecipe.Model

class User {
    var email:String?=null
    var password:String?=null
    var fullname:String?=null
    var noPhone:String?=null
    var address:String?=null
    var gender:String?=null
    var age:Int=0
    var image:String?=null

    constructor(){}

    constructor(email:String, password:String, fullname:String, noPhone:String, address:String, gender:String, age:Int, image:String){
        this.email = email
        this.password = password
        this.fullname = fullname
        this.noPhone = noPhone
        this.address = address
        this.gender = gender
        this.age = age
        this.image = image
    }
}
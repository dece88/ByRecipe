package com.example.byrecipe.Model

import android.os.Parcel
import android.os.Parcelable

class User(
    var email: String?,
    var password: String?,
    var fullname: String?,
    var noPhone: String?,
    var address: String?,
    var gender: String?,
    var age:Int?,
    var code: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(fullname)
        parcel.writeString(noPhone)
        parcel.writeString(address)
        parcel.writeString(gender)
        parcel.writeValue(age)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}
package com.example.byrecipe.Model

import android.os.Parcel
import android.os.Parcelable


class Recipe(
    var nama:String?,
    var ingredients:String?,
    var tahapan:String?,
    var waktu:String?,
    var category:String?,
    var owner: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
        parcel.writeString(ingredients)
        parcel.writeString(tahapan)
        parcel.writeString(waktu)
        parcel.writeString(category)
        parcel.writeString(owner)
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
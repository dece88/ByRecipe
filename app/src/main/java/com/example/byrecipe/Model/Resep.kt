package com.example.byrecipe.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Resep(
    var nama:String,
    var waktu:String,
    var foto:String
) : Parcelable
package com.csci448.betr

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

//Class extends Parcelable so it can be sent over intent
@Parcelize
data class User (var username: String = "", var password: String = "",
                 var friendList: List<String> = listOf(), var betList: List<Bet> = listOf(), var profilePic: String = "") : Parcelable
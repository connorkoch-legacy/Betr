package com.csci448.betr

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//Class extends Parcelable so it can be sent over intent
@Parcelize
data class Bet(var betText: String = "", var betCreator: String = "",
               var betAcceptor: String = "", var betAmount: Double = 0.0,
               var dateStart: String = "", var dateEnd: String = "") : Parcelable
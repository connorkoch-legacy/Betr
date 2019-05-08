package com.csci448.betr

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

//Class extends Parcelable so it can be sent over intent
@Parcelize
data class Bet(var betText: String = "", var betCreator: String = "",
               var betAcceptor: String = "", var betAmount: Double = 0.0,
               var dateStart: String = "", var dateEnd: String = "", var winner: String? = null, var accepted: Int = 0) : Parcelable, Comparable<Bet> {

    //This needs to be overridden so a priority queue can order bets by start date
    override fun compareTo(other: Bet): Int {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US)

        val cal = Calendar.getInstance()

        cal.time = sdf.parse(this.dateStart)
        var dateStartThis = cal.time
        cal.time = sdf.parse(other.dateStart)
        var dateStartOther = cal.time

        //should order start dates from new to old
        return dateStartOther.compareTo(dateStartThis)
    }
}
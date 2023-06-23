package com.satmatgroup.shreeram.activities_aeps

import android.os.Parcel
import android.os.Parcelable

data class MiniStatementModel (
    val amount: String,
    val date: String,
    val narration: String,
    val txnType: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(amount)
        parcel.writeString(date)
        parcel.writeString(narration)
        parcel.writeString(txnType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MiniStatementModel> {
        override fun createFromParcel(parcel: Parcel): MiniStatementModel {
            return MiniStatementModel(parcel)
        }

        override fun newArray(size: Int): Array<MiniStatementModel?> {
            return arrayOfNulls(size)
        }
    }
}
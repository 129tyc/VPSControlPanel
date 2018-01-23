package com.tyc129.vpscontrolpanel.data.api

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Code on 2017/12/3 0003.
 * @author 谈永成
 * @version 1.0
 */
data class ApiParameter(
        val pKey: String,
        val pName: String,
        val pDescription: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pKey)
        parcel.writeString(pName)
        parcel.writeString(pDescription)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ApiParameter> {
        override fun createFromParcel(parcel: Parcel): ApiParameter {
            return ApiParameter(parcel)
        }

        override fun newArray(size: Int): Array<ApiParameter?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (other !is ApiParameter)
            return false
        return other.pKey == pKey
    }

    override fun hashCode(): Int {
        return pKey.hashCode()
    }
}
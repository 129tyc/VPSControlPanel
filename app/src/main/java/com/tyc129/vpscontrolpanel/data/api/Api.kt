package com.tyc129.vpscontrolpanel.data.api

import android.os.Parcel
import android.os.Parcelable

typealias ReturnsMap = Map<ApiParameter, String>
/**
 * Created by Code on 2017/11/29 0029.
 * @author 谈永成
 * @version 1.0
 */
data class Api(
        val aId: String,
        val aName: String,
        val aDescription: String,
        val callName: String,
        val params: List<ApiParameter>,
        val returns: List<ApiParameter>) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            listOf<ApiParameter>(),
            listOf<ApiParameter>()) {
        parcel.readList(params, ClassLoader.getSystemClassLoader())
        parcel.readList(returns, ClassLoader.getSystemClassLoader())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(aId)
        parcel.writeString(aName)
        parcel.writeString(aDescription)
        parcel.writeString(callName)
        parcel.writeList(params)
        parcel.writeList(returns)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Api> {
        override fun createFromParcel(parcel: Parcel): Api {
            return Api(parcel)
        }

        override fun newArray(size: Int): Array<Api?> {
            return arrayOfNulls(size)
        }
    }

    fun requireReturnsParam(key: String) = returns.firstOrNull { key == it.pKey }
    fun requireRequestParam(key: String) = params.firstOrNull { key == it.pKey }
}
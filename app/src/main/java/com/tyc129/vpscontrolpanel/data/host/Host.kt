package com.tyc129.vpscontrolpanel.data.host

import android.os.Parcel
import android.os.Parcelable
import com.tyc129.vpscontrolpanel.data.api.ApiVendorEnum

/**
 * Created by Code on 2017/11/28 0028.
 * @author 谈永成
 * @version 1.0
 */
data class Host(val hId: String,
                val hName: String,
                val hIp: String,
                val vId: String,
                val vKey: String,
                val apiVendor: ApiVendorEnum,
                val vpsVendor: VPSVendorEnum) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readSerializable() as ApiVendorEnum,
            parcel.readSerializable() as VPSVendorEnum)


    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(hId)
        p0?.writeString(hName)
        p0?.writeString(hIp)
        p0?.writeString(vId)
        p0?.writeString(vKey)
        p0?.writeSerializable(apiVendor)
        p0?.writeSerializable(vpsVendor)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Host> {
        override fun createFromParcel(parcel: Parcel): Host {
            return Host(parcel)
        }

        override fun newArray(size: Int): Array<Host?> {
            return arrayOfNulls(size)
        }
    }
}
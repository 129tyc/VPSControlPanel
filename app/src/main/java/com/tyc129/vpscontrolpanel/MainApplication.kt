package com.tyc129.vpscontrolpanel

import android.app.Application
import com.tyc129.vpscontrolpanel.data.api.ApiVendor
import com.tyc129.vpscontrolpanel.data.api.kiwivm.VendorKiwiVM
import com.tyc129.vpscontrolpanel.data.host.HostCenter
import com.tyc129.vpscontrolpanel.data.host.HostCenterImpl

/**
 * Created by Code on 2017/11/30 0030.
 * @author 谈永成
 * @version 1.0
 */
class MainApplication : Application() {
    lateinit var kiwiVM: ApiVendor

    override fun onCreate() {
        super.onCreate()
        kiwiVM = VendorKiwiVM(applicationContext)
    }

    companion object {
        val hostCenter: HostCenter = HostCenterImpl()
    }

}
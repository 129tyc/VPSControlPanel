package com.tyc129.vpscontrolpanel.data.host

import android.util.Log
import com.tyc129.vpscontrolpanel.data.api.ApiVendorEnum

/**
 * Created by Code on 2017/11/30 0030.
 * @author 谈永成
 * @version 1.0
 */
class HostCenterImpl : HostCenter {
    private val hostList: MutableList<Host> = mutableListOf()

    init {
        hostList.add(
                Host(
                        "1",
                        "test",
                        "192.168.1.1",
                        "550314",
                        "private_akTUSqaj5f8XqEUtctFhiHPY",
                        ApiVendorEnum.KIWIVM,
                        VPSVendorEnum.BANDWAGON))
        hostList.add(
                Host(
                        "2",
                        "test2",
                        "127.0.0.1",
                        "2231",
                        "1th6546h5", ApiVendorEnum.KIWIVM,
                        VPSVendorEnum.BANDWAGON))
    }

    override fun getHosts(): List<Host> {
        return hostList.toList()
    }

    override fun addHost(host: Host) {
        hostList.add(host)
    }

    override fun removeHost(host: Host) {
        hostList.remove(host)
    }
}
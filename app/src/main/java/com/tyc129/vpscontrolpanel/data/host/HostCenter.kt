package com.tyc129.vpscontrolpanel.data.host

/**
 * Created by Code on 2017/11/30 0030.
 * @author 谈永成
 * @version 1.0
 */
interface HostCenter {
    fun getHosts(): List<Host>
    fun addHost(host: Host)
    fun removeHost(host: Host)
}
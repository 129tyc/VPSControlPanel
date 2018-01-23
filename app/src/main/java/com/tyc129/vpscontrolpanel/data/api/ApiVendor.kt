package com.tyc129.vpscontrolpanel.data.api

/**
 * Created by Code on 2017/12/3 0003.
 * @author 谈永成
 * @version 1.0
 */
interface ApiVendor {

    fun applyEngine(): ApiEngine

    fun applyApiList(): List<Api>

    fun applyFuncList(): List<ApiFunc>

    fun requireApi(aId: String): Api?
}
package com.tyc129.vpscontrolpanel.data.api

import android.content.Context
import android.view.LayoutInflater
import com.tyc129.vpscontrolpanel.data.host.Host

/**
 * Created by Code on 2017/12/3 0003.
 * TODO 添加API功能信息
 * @author 谈永成
 * @version 1.0
 */
interface ApiFunc {

    fun getInfo(): ApiFuncInfo

    fun refresh(host: Host)

    fun applyDisplayItem(): ItemView
}
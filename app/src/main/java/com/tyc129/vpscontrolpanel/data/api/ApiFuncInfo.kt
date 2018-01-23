package com.tyc129.vpscontrolpanel.data.api

/**
 * Created by Code on 2017/12/8 0008.
 * @author 谈永成
 * @version 1.0
 */
data class ApiFuncInfo(
        val name: String,
        val iconId: Int) {
    companion object {
        const val ICON_ID_INVALID = -1
    }
}
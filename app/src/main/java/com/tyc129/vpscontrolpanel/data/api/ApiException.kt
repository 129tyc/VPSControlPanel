package com.tyc129.vpscontrolpanel.data.api

/**
 * Created by Code on 2017/12/4 0004.
 * @author 谈永成
 * @version 1.0
 */
abstract class ApiException(val code: Int) : RuntimeException() {

    override val message: String?
        get() = getExceptionMessage(code)

    abstract fun getExceptionMessage(code: Int): String

}
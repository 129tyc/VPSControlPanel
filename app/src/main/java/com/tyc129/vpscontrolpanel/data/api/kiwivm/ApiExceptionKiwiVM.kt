package com.tyc129.vpscontrolpanel.data.api.kiwivm

import com.tyc129.vpscontrolpanel.data.api.ApiException

/**
 * Created by Code on 2017/12/4 0004.
 * @author 谈永成
 * @version 1.0
 */
class ApiExceptionKiwiVM(code: Int) : ApiException(code) {
    companion object {
        const val KEY_ERROR_CODE = "error"
        const val CODE_OK = 0
        const val CODE_CAN_NOT_PARSE = -1
        const val CODE_API_NO_FOUND = -2
        const val CODE_RETURN_DATA_ILLEGAL = -3
        const val CODE_AUTHENTICATION_FAILURE = 700005
        const val CODE_INVALID_REQUEST = 799999
        const val CODE_SUPPORT_OVZ = 766644
    }

    override fun getExceptionMessage(code: Int): String {
        return when (code) {
            CODE_CAN_NOT_PARSE -> "No Api to parse data"
            CODE_AUTHENTICATION_FAILURE -> "authentication failure"
            CODE_INVALID_REQUEST -> "API: invalid request"
            CODE_SUPPORT_OVZ -> "This function is only supported on OVZ hypervisor"
            CODE_API_NO_FOUND -> "api not found"
            CODE_RETURN_DATA_ILLEGAL->"return data illegal"
            else -> "Error Code = " + code
        }
    }
}
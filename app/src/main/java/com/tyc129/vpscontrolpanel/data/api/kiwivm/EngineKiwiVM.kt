package com.tyc129.vpscontrolpanel.data.api.kiwivm

import android.util.Log
import com.google.gson.JsonObject
import com.tyc129.vpscontrolpanel.data.api.*
import com.tyc129.vpscontrolpanel.data.host.Host
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Code on 2017/12/4 0004.
 * @author 谈永成
 * @version 1.0
 */
class EngineKiwiVM(private val serviceKiwiVM: ServiceKiwiVM) : ApiEngine {

    private val queue = ArrayDeque<Api>()

    private val transformer: ObservableTransformer<JsonObject, ReturnsMap> = ObservableTransformer {
        it.flatMap({
            Log.v(EngineKiwiVM::class.simpleName, it.toString())
            val code = it.get(ApiExceptionKiwiVM.KEY_ERROR_CODE).asInt
            if (code == ApiExceptionKiwiVM.CODE_OK)
                return@flatMap createDataObservable(it)
            else
                return@flatMap Observable.error<ReturnsMap>(ApiExceptionKiwiVM(code))

        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun executeApi(api: Api, host: Host, params: Map<String, String>, observer: Observer<ReturnsMap>): Boolean {
        val query = mutableMapOf("veid" to host.vId,
                "api_key" to host.vKey)
        query.putAll(params)
        serviceKiwiVM
                .executeApi(api.callName, query)
                .compose(transformer)
                .subscribe(observer)
        queue.addLast(api)
        return true
    }

    private fun createDataObservable(data: JsonObject):
            Observable<ReturnsMap> {
        if (queue.isNotEmpty()) {
            val result = mutableMapOf<ApiParameter, String>()
            queue.removeFirst()
                    .returns
                    .filter { data.get(it.pKey) != null }
                    .forEach({
                        result.put(it, data.get(it.pKey).toString())
                    })
            return Observable.create({
                try {
                    it.onNext(result)
                    it.onComplete()
                } catch (e: Exception) {
                    it.onError(e)
                }
            })
        } else
            return Observable.error(ApiExceptionKiwiVM(ApiExceptionKiwiVM.CODE_CAN_NOT_PARSE))

    }

}

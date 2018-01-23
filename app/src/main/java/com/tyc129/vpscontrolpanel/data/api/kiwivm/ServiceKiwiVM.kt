package com.tyc129.vpscontrolpanel.data.api.kiwivm

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by Code on 2017/12/1 0001.
 * @author 谈永成
 * @version 1.0
 */
interface ServiceKiwiVM {

    @GET("v1/{api}")
    fun executeApi(@Path("api") callName: String,
                   @QueryMap params: Map<String, String>): Observable<JsonObject>
}
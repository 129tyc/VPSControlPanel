package com.tyc129.vpscontrolpanel.data.api

import com.tyc129.vpscontrolpanel.data.host.Host
import io.reactivex.Observable
import io.reactivex.Observer
import java.nio.channels.spi.AbstractSelectionKey

/**
 * Created by Code on 2017/12/1 0001.
 * @author 谈永成
 * @version 1.0
 */
interface ApiEngine {

    fun executeApi(api: Api, host: Host, params: Map<String, String>,
                   observer: Observer<ReturnsMap>): Boolean
}
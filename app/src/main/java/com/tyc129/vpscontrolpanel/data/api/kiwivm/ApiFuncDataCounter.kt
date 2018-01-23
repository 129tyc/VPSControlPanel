package com.tyc129.vpscontrolpanel.data.api.kiwivm

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.tyc129.vpscontrolpanel.R
import com.tyc129.vpscontrolpanel.data.api.*
import com.tyc129.vpscontrolpanel.data.api.ApiFuncInfo.Companion.ICON_ID_INVALID
import com.tyc129.vpscontrolpanel.data.host.Host
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Code on 2017/12/8 0008.
 * @author 谈永成
 * @version 1.0
 */
class ApiFuncDataCounter(context: Context,
                         private val apiEngine: ApiEngine,
                         private val vendor: ApiVendor) : ApiFunc {
    private val itemView = ItemView(View.inflate(context, R.layout.api_func_data_counter, null))
    private val progressBar = itemView.content.findViewById(R.id.progressDataCounter) as ProgressBar
    private val textView = itemView.content.findViewById(R.id.textDataCounter) as TextView
    private val info = ApiFuncInfo("DataCounter", ICON_ID_INVALID)
    private val observer = DataCounterObserver()
    private val api = vendor.requireApi("5")
    private val paramPlanData = api?.requireReturnsParam("plan_monthly_data")
    private val paramDataCounter = api?.requireReturnsParam("data_counter")
    private var plan = 0L
    private var data = 0L

    init {
        textView.text = "No Data"
    }

    override fun getInfo(): ApiFuncInfo {
        return info
    }

    override fun refresh(host: Host) {
        if (api == null) throw ApiExceptionKiwiVM(ApiExceptionKiwiVM.CODE_API_NO_FOUND)
        apiEngine.executeApi(api, host, mapOf(), observer)
    }

    override fun applyDisplayItem(): ItemView {
        return itemView
    }

    inner class DataCounterObserver : Observer<ReturnsMap> {
        override fun onError(e: Throwable) {
            progressBar.visibility = View.INVISIBLE
            textView.text = "RefreshError:${e.message}"
        }

        override fun onComplete() {
            textView.text = "${data}MB/${plan}MB"
            progressBar.max = 100
            progressBar.progress = (data.toDouble() / plan.toDouble() * 100.0).toInt()
            progressBar.visibility = View.VISIBLE
        }

        override fun onSubscribe(d: Disposable) {
            progressBar.visibility = View.INVISIBLE
            textView.text = "Refreshing..."
        }

        override fun onNext(t: ReturnsMap) {
            processData(t)
        }

        private fun processData(t: ReturnsMap) {
            if (paramDataCounter == null || paramPlanData == null ||
                    !t.containsKey(paramDataCounter) ||
                    !t.containsKey(paramPlanData))
                throw ApiExceptionKiwiVM(ApiExceptionKiwiVM.CODE_API_NO_FOUND)
            val d = t[paramDataCounter]?.toLongOrNull()
            val p = t[paramPlanData]?.toLongOrNull()
            if (d == null || p == null)
                throw ApiExceptionKiwiVM(ApiExceptionKiwiVM.CODE_RETURN_DATA_ILLEGAL)
            data = d / (1024 * 1024)
            plan = p / (1024 * 1024)
        }

    }
}
package com.tyc129.vpscontrolpanel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tyc129.vpscontrolpanel.data.api.ApiVendor
import com.tyc129.vpscontrolpanel.data.host.Host
import kotlinx.android.synthetic.main.layout_activity_api_func.*

class ApiFuncActivity : AppCompatActivity() {
    private lateinit var host: Host
    private lateinit var vendor: ApiVendor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_api_func)
        host = intent.getParcelableExtra("HOST")
        vendor = (application as MainApplication).kiwiVM
        list_ApiFunc.addView(vendor.applyFuncList()[0].applyDisplayItem().content)
        vendor.applyFuncList()[0].applyDisplayItem().content.setOnClickListener {
            vendor.applyFuncList()[0].refresh(host)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        list_ApiFunc.removeAllViews()
    }
}

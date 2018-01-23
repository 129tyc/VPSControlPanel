package com.tyc129.vpscontrolpanel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.SimpleAdapter
import android.widget.Toast
import com.tyc129.vpscontrolpanel.data.api.Api
import com.tyc129.vpscontrolpanel.data.api.ApiParameter
import com.tyc129.vpscontrolpanel.data.api.ApiVendor
import com.tyc129.vpscontrolpanel.data.api.ReturnsMap
import com.tyc129.vpscontrolpanel.data.host.Host
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_activity_api.*

class ApiActivity : AppCompatActivity() {
    private val mapList: MutableList<Map<String, Any>> = mutableListOf()
    private lateinit var adapter: SimpleAdapter
    private lateinit var host: Host
    private lateinit var vendor: ApiVendor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_api)
        adapter = SimpleAdapter(this, mapList, R.layout.item_list_api,
                arrayOf("name_api", "description_api"), intArrayOf(R.id.name_api, R.id.description_api))
        host = intent.getParcelableExtra("HOST")
        vendor = (application as MainApplication).kiwiVM
        list_api.onItemClickListener = AdapterView.OnItemClickListener { _, _, p, _ ->
            showParamsDialog(vendor.applyApiList()[p])
        }
    }

    override fun onResume() {
        super.onResume()
        getApi()
        list_api.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun getApi() {
        mapList.clear()
        vendor.applyApiList()
                .forEach {
                    mapList.add(mapOf("name_api" to it.aName,
                            "description_api" to it.aDescription))
                }
    }

    private fun showParamsDialog(api: Api) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val params: MutableMap<String, String> = mutableMapOf()
        if (api.params.isEmpty()) {
            builder.setMessage("Sure?")
                    .setTitle("Warning")
            builder.setPositiveButton("Execute", { _, _ ->
                Log.v("Test", api.toString())
                doExecute(api, host, params)
            })
        } else {
            builder.setTitle("Input Params")
            val group = LinearLayout(this)
            group.orientation = LinearLayout.VERTICAL
            group.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
            for (param in api.params) {
                val view: View = LayoutInflater.from(this).inflate(R.layout.item_param, null)
                val edit: TextInputLayout = view.findViewById(R.id.value_param) as TextInputLayout
                edit.hint = param.pName
                group.addView(view)
            }
            builder.setView(group)
            builder.setPositiveButton("Execute", { _, _ ->
                for (i in 0 until group.childCount) {
                    val view = group.getChildAt(i).findViewById(R.id.value_param) as TextInputLayout
                    params.put(api.params[i].pKey, view.editText.toString())
                }
                doExecute(api, host, params)
            })
        }
        builder.create().show()
    }

    private fun doExecute(api: Api, host: Host, params: Map<String, String>) {
        Log.v("Test", "doExecute" + api.toString() + params.toString())
        val result = mutableMapOf<ApiParameter, String>()

        vendor.applyEngine().executeApi(api, host, params, object : Observer<ReturnsMap> {
            override fun onNext(t: ReturnsMap) {
                result.putAll(t)
            }

            override fun onError(e: Throwable) {
                Toast.makeText(this@ApiActivity, "failed:${e.message}", Toast.LENGTH_SHORT)
                        .show()
            }

            override fun onSubscribe(d: Disposable) {
                Toast.makeText(this@ApiActivity, "Waiting", Toast.LENGTH_SHORT)
                        .show()
            }

            override fun onComplete() {
                val intent = Intent(this@ApiActivity, DetailActivity::class.java)
                intent.putExtra("RESULT", result as HashMap)
                startActivity(intent)
            }

        })
    }
}

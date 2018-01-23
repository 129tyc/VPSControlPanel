package com.tyc129.vpscontrolpanel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.layout_activity_main.*

class MainActivity : AppCompatActivity() {
    private val mapList: MutableList<Map<String, Any>> = mutableListOf()
    private var adapter: SimpleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)
        adapter = SimpleAdapter(this, mapList, R.layout.item_list_hosts,
                arrayOf("name_host", "ip_host"), intArrayOf(R.id.name_host, R.id.ip_host))
        list_hosts.onItemClickListener = AdapterView.OnItemClickListener { _, _, p2, _ ->
            val intent = Intent(this, ApiFuncActivity::class.java)
            intent.putExtra("HOST", MainApplication.hostCenter.getHosts()[p2])
            startActivity(intent)
        }
        list_hosts.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, p2, _ ->
            val intent = Intent(this, ApiActivity::class.java)
            intent.putExtra("HOST", MainApplication.hostCenter.getHosts()[p2])
            startActivity(intent)
            return@OnItemLongClickListener true
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
        list_hosts.adapter = if (adapter != null) adapter else return
        adapter?.notifyDataSetChanged() ?: return
        list_hosts.requestFocusFromTouch()
    }

    private fun getData() {
        mapList.clear()
        MainApplication
                .hostCenter
                .getHosts()
                .forEach {
                    mapList.add(mapOf("name_host" to it.hName, "ip_host" to it.hIp))
                }
    }
}

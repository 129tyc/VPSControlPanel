package com.tyc129.vpscontrolpanel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.SimpleAdapter
import com.tyc129.vpscontrolpanel.data.api.ApiParameter
import kotlinx.android.synthetic.main.layout_activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val mapList: MutableList<Map<String, Any>> = mutableListOf()
    private var adapter: SimpleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_detail)
        adapter = SimpleAdapter(this, mapList, R.layout.item_list_detail,
                arrayOf("name_detail", "value_detail"), intArrayOf(R.id.name_detail, R.id.value_detail))
        processData(intent)
    }

    override fun onResume() {
        super.onResume()
        list_details.adapter = if (adapter != null) adapter else return
        adapter?.notifyDataSetChanged() ?: return
        list_details.requestFocusFromTouch()
    }

    private fun processData(intent: Intent) {
        mapList.clear()
        val result = intent.getSerializableExtra("RESULT") as HashMap<*, *>
        for ((param, value) in result) {
            mapList.add(mapOf("name_detail" to (param as ApiParameter).pName, "value_detail" to value))
        }
    }
}

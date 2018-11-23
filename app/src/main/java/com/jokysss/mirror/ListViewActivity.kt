package com.jokysss.mirror

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_list_view.*

class ListViewActivity : AppCompatActivity() {
    var data = mutableListOf<MutableMap<String, String>>()
    lateinit var adapter: SimpleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        initData()
        adapter = SimpleAdapter(this, data, R.layout.simple_item, arrayOf("name", "sort"), intArrayOf(R.id.name, R.id.sort))
        listView.adapter = adapter
    }

    private fun initData() {
        for (i in 0..50) {
            var item = mutableMapOf<String, String>()
            item["name"] = "ITEM-$i"
            item["sort"] = "$i"
            data.add(item)
        }
    }
}

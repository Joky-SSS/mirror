package com.jokysss.mirror

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.SimpleAdapter
import com.jokysss.mirror.widget.SwipeMenuListView
import kotlinx.android.synthetic.main.activity_list_view.*

class ListViewActivity : AppCompatActivity() {
    var data = mutableListOf<MutableMap<String, String>>()
    lateinit var adapter: SimpleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initData()
        adapter = SimpleAdapter(this, data, R.layout.simple_item, arrayOf("name", "sort"), intArrayOf(R.id.name, R.id.sort))
        listView.adapter = adapter
        listView.removeListener = object : SwipeMenuListView.OnItemRemoveListener {
            override fun onItemRemoved(position: Int) {
                data.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initData() {
        for (i in 0..50) {
            var item = mutableMapOf<String, String>()
            item["name"] = "ITEM-$i"
            item["sort"] = "$i"
            data.add(item)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}

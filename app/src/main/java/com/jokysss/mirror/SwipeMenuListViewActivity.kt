package com.jokysss.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.jokysss.mirror.widget.SwipeMenuListView
import kotlinx.android.synthetic.main.activity_swipe_menu_list_view.*

class SwipeMenuListViewActivity : AppCompatActivity() {
    var adapter = SwipeMenuAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_menu_list_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ -> Toast.makeText(this@SwipeMenuListViewActivity, "OnItemClick position : $position", Toast.LENGTH_SHORT).show() }
        listView.onMenuClickListener = object : SwipeMenuListView.IOnMenuClickListener {
            override fun onMenuClick(menuView: View, position: Int) {
                val id = if (menuView.id == R.id.look) "look" else "delete"
                Toast.makeText(this@SwipeMenuListViewActivity, "onMenuClick viewId : $id , position : $position", Toast.LENGTH_SHORT).show()
            }
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

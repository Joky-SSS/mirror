package com.jokysss.mirror

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bt_view.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, MainActivity::class.java)) }
        bt_scroll.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, ScrollActivity::class.java)) }
        bt_page.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, PageScrollActivity::class.java)) }
        bt_list.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, ListViewActivity::class.java)) }
        bt_slide_menu.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, SlideMenuActivity::class.java)) }
        bt_swipe_menu.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, SwipeMenuListViewActivity::class.java)) }
        bt_tab_icon.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, TabIconActivity::class.java)) }
        bt_test.setOnClickListener { v -> startActivity(Intent(this@HomeActivity, TestActivity::class.java)) }
    }
}

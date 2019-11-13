package com.jokysss.mirror

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bt_view.setOnClickListener { startActivity(Intent(this@HomeActivity, MainActivity::class.java)) }
        bt_scroll.setOnClickListener { startActivity(Intent(this@HomeActivity, ScrollActivity::class.java)) }
        bt_page.setOnClickListener { startActivity(Intent(this@HomeActivity, PageScrollActivity::class.java)) }
        bt_list.setOnClickListener { startActivity(Intent(this@HomeActivity, ListViewActivity::class.java)) }
        bt_slide_menu.setOnClickListener { startActivity(Intent(this@HomeActivity, SlideMenuActivity::class.java)) }
        bt_swipe_menu.setOnClickListener { startActivity(Intent(this@HomeActivity, SwipeMenuListViewActivity::class.java)) }
        bt_tab_icon.setOnClickListener { startActivity(Intent(this@HomeActivity, TabIconActivity::class.java)) }
        bt_test.setOnClickListener { startActivity(Intent(this@HomeActivity, TestActivity::class.java)) }
    }
}

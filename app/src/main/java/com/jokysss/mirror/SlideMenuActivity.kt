package com.jokysss.mirror

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_slide_menu.*

class SlideMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bt_test.setOnClickListener { Toast.makeText(this@SlideMenuActivity, "text", Toast.LENGTH_SHORT).show() }
        bt_toggle.setOnClickListener { mSlideMenu.toggle() }
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

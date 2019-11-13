package com.jokysss.mirror

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_scroll.*

class ScrollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //        findViewById(R.id.bt_scrollby).setOnClickListener(v->mTextView.scrollBy(-5, 0));
        //        findViewById(R.id.bt_scrollto).setOnClickListener(v->mTextView.scrollTo(mTextView.getScrollX()-5, 0));
        bt_scrollby.setOnClickListener { sll_container.startScroll() }
        bt_scrollto.setOnClickListener { sll_container.stopScroll() }
        bt_reset.setOnClickListener {
            sll_container.stopScroll()
            sll_container.scrollTo(0, 0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

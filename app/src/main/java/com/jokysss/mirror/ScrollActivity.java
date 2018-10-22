package com.jokysss.mirror;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.jokysss.mirror.widget.ScrollLinearLayout;

public class ScrollActivity extends AppCompatActivity {
    TextView mTextView;
    ScrollLinearLayout mScrollLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.tv_scroller);
        mScrollLinearLayout = findViewById(R.id.sll_container);
        //        findViewById(R.id.bt_scrollby).setOnClickListener(v->mTextView.scrollBy(-5, 0));
        //        findViewById(R.id.bt_scrollto).setOnClickListener(v->mTextView.scrollTo(mTextView.getScrollX()-5, 0));
        findViewById(R.id.bt_scrollby).setOnClickListener(v -> mScrollLinearLayout.startScroll());
        findViewById(R.id.bt_scrollto).setOnClickListener(v -> mScrollLinearLayout.stopScroll());
        findViewById(R.id.bt_reset).setOnClickListener(v -> {
            mScrollLinearLayout.stopScroll();
            mScrollLinearLayout.scrollTo(0, 0);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.jokysss.mirror;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.bt_view).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, MainActivity.class)));
        findViewById(R.id.bt_scroll).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ScrollActivity.class)));
        findViewById(R.id.bt_page).setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, PageScrollActivity.class)));
    }
}

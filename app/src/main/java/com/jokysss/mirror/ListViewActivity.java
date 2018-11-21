package com.jokysss.mirror;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jokysss.mirror.widget.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		SwipeMenuListView swipeMenuListView = findViewById(R.id.swipelistview);
		List<String> data = getData();
	}

	private List<String> getData() {
		List<String> data = new ArrayList<>();

		return data;
	}

}

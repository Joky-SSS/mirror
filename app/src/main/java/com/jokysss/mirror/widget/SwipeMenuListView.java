package com.jokysss.mirror.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.ListView;

public class SwipeMenuListView extends ListView {

	private int touchSlop;

	public SwipeMenuListView(Context context) {
		this(context, null);
	}

	public SwipeMenuListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeMenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		ViewConfiguration configuration = ViewConfiguration.get(context);
		touchSlop = configuration.getScaledTouchSlop();
	}
}

package com.jokysss.mirror.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewConfiguration
import android.widget.ListView

class SwipeMenuListView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ListView(context, attrs, defStyleAttr) {

    private var touchSlop: Int = 0

    init {
        init(context)
    }

    private fun init(context: Context) {
        val configuration = ViewConfiguration.get(context)
        touchSlop = configuration.scaledTouchSlop
    }
}

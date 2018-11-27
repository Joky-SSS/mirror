package com.jokysss.mirror.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import java.util.*

class SwipeMenuLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    //右侧菜单宽度总和(最大滑动距离)
    var rightMenuWidth: Int = 0
    //滑动判定临界值（右侧菜单宽度的40%） 手指抬起时，超过了展开，没超过收起menu
    var limit: Int = 0
    private var mContentView: View? = null
    val childSet = HashSet<View>()
    var contentWidth: Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        rightMenuWidth = 0//由于ViewHolder的复用机制，每次这里要手动恢复初始值
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                if (i > 0) {//第一个布局是Left item，从第二个开始才是RightMenu
                    rightMenuWidth += childView.measuredWidth
                    childSet.add(childView)
                } else {
                    mContentView = childView
                    contentWidth = childView.measuredWidth
                }
            }
        }
        limit = rightMenuWidth * 4 / 10//滑动判断的临界值
    }

    companion object {
        private val TAG = "SwipeMenuLayout"
    }
}

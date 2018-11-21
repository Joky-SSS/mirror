package com.jokysss.mirror.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import java.util.HashSet;
import java.util.Set;

public class SwipeMenuLayout extends ViewGroup {
	private static final String TAG = "SwipeMenuLayout";

	private int mHeight;//自己的高度
	//右侧菜单宽度总和(最大滑动距离)
	private int mRightMenuWidths;

	//滑动判定临界值（右侧菜单宽度的40%） 手指抬起时，超过了展开，没超过收起menu
	private int mLimit;

	private View mContentView;//存储contentView(第一个View)

	//存储的是当前正在展开的View
	private static SwipeMenuLayout mViewCache;

	private SwipeMenuLayout self;
	private Set<View> mChildSet = new HashSet<>();
	private int mTouchSlop;
	private OverScroller mScroller;

	public SwipeMenuLayout(Context context) {
		this(context, null);
	}

	public SwipeMenuLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	/**
	 * 返回ViewCache
	 *
	 * @return
	 */
	public static SwipeMenuLayout getViewCache() {
		return mViewCache;
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		self = this;
		ViewConfiguration vc = ViewConfiguration.get(context);
		mTouchSlop = vc.getScaledTouchSlop();
		mScroller = new OverScroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		setClickable(true);//令自己可点击，从而获取触摸事件

		mRightMenuWidths = 0;//由于ViewHolder的复用机制，每次这里要手动恢复初始值
		mHeight = 0;
		int contentWidth = 0;//适配GridLayoutManager，将以第一个子Item(即ContentItem)的宽度为控件宽度
		int childCount = getChildCount();

		//为了子View的高，可以matchParent(参考的FrameLayout 和LinearLayout的Horizontal)
		final boolean measureMatchParentChildren = MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
		boolean isNeedMeasureChildHeight = false;

		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			//令每一个子View可点击，从而获取触摸事件
			childView.setClickable(true);
			if (childView.getVisibility() != GONE) {
				//后续计划加入上滑、下滑，则将不再支持Item的margin
				measureChild(childView, widthMeasureSpec, heightMeasureSpec);
				final MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
				mHeight = Math.max(mHeight, childView.getMeasuredHeight()/* + lp.topMargin + lp.bottomMargin*/);
				if (measureMatchParentChildren && lp.height == LayoutParams.MATCH_PARENT) {
					isNeedMeasureChildHeight = true;
				}
				if (i > 0) {//第一个布局是Left item，从第二个开始才是RightMenu
					mRightMenuWidths += childView.getMeasuredWidth();
					mChildSet.add(childView);
				} else {
					mContentView = childView;
					contentWidth = childView.getMeasuredWidth();
				}
			}
		}
		setMeasuredDimension(getPaddingLeft() + getPaddingRight() + contentWidth, mHeight + getPaddingTop() + getPaddingBottom());//宽度取第一个Item(Content)的宽度
		mLimit = mRightMenuWidths * 4 / 10;//滑动判断的临界值
		if (isNeedMeasureChildHeight) {//如果子View的height有MatchParent属性的，设置子View高度
			forceUniformHeight(childCount, widthMeasureSpec);
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	/**
	 * 给MatchParent的子View设置高度
	 *
	 * @param count
	 * @param widthMeasureSpec
	 * @see android.widget.LinearLayout# 同名方法
	 */
	private void forceUniformHeight(int count, int widthMeasureSpec) {
		// Pretend that the linear layout has an exact size. This is the measured height of
		// ourselves. The measured height should be the max height of the children, changed
		// to accommodate the heightMeasureSpec from the parent
		int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);//以父布局高度构建一个Exactly的测量参数
		for (int i = 0; i < count; ++i) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
				if (lp.height == LayoutParams.MATCH_PARENT) {
					int oldWidth = lp.width;//measureChildWithMargins 这个函数会用到宽，所以要保存一下
					lp.width = child.getMeasuredWidth();
					// Remeasure with new dimensions
					measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
					lp.width = oldWidth;
				}
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		int left = 0 + getPaddingLeft();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			if (childView.getVisibility() != GONE) {
				if (i == 0) {//第一个子View是内容 宽度设置为全屏
					childView.layout(left, getPaddingTop(), left + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());
					left = left + childView.getMeasuredWidth();
				} else {
					childView.layout(left, getPaddingTop(), left + childView.getMeasuredWidth(), getPaddingTop() + childView.getMeasuredHeight());
					left = left + childView.getMeasuredWidth();
				}
			}
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		if (this == mViewCache) {
			mViewCache = null;
		}
		super.onDetachedFromWindow();
	}


	public View getContentView() {
		return mContentView;
	}
}

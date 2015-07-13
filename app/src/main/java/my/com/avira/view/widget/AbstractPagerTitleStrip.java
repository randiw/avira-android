package my.com.avira.view.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by randiwaranugraha on 7/1/15.
 */
public abstract class AbstractPagerTitleStrip extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final int MAX_SETTLE_DURATION = 600; // ms

    private LinearLayout container;

    private int currentPage;
    private int previousPage;
    private int titleView;
    private int highlightColor;
    private int normalColor;

    private String[] titles;
    public long lastTapUpTime;

    private ViewPager viewPager;
    private OnTitleStripEventListener eventListener;

    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    });

    private OnTouchListener onContainerTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent ev) {
            boolean ret = gestureDetector.onTouchEvent(ev);

            // handle new event after view pager is done with scrolling
            long fromLastTouch = System.currentTimeMillis() - lastTapUpTime;

            if (ret && fromLastTouch >= MAX_SETTLE_DURATION) {
                // this is for touching on title item
                lastTapUpTime = System.currentTimeMillis();
                int titleIndex = (int) v.getTag();
                if (eventListener != null) {
                    eventListener.onTitleClicked(titleIndex);
                }
            } else if (viewPager != null && fromLastTouch >= MAX_SETTLE_DURATION) {
                // delegate touch event to underlying view pager, this is for swiping on title item
                viewPager.onTouchEvent(ev);
            }

            return true;
        }
    };

    public AbstractPagerTitleStrip(Context context) {
        super(context);
        init();
    }

    public AbstractPagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbstractPagerTitleStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        container = new LinearLayout(getContext());
        addView(container, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public LinearLayout getContainer() {
        return container;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        container.removeAllViews();

        PagerAdapter adapter = viewPager.getAdapter();
        int count = getCount();

        titles = new String[count];
        for (int i = 0; i < count; ++i) {
            titles[i] = adapter.getPageTitle(i).toString();
        }

        setCurrentPage(-1);
        setPreviousPage(-1);

        viewPager.setOnPageChangeListener(this);
        initViewPager(viewPager);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public int getCount() {
        return viewPager.getAdapter().getCount();
    }

    protected TextView createTitleView(LayoutInflater inflater, int index) {
        TextView tvTitle = (TextView) inflater.inflate(titleView, container, false);
        tvTitle.setText(titles[index]);
        tvTitle.setTag(index);

        return tvTitle;
    }

    protected void removeTitle(int titleViewIndex) {
        View tvTitle = container.getChildAt(titleViewIndex);
        container.removeView(tvTitle);
        tvTitle.setOnClickListener(null);
    }

    protected TextView getTitle(int index) {
        return (TextView) container.getChildAt(index);
    }

    protected void addTitle(TextView titleView, int titleViewIndex) {
        if (titleViewIndex >= 0) {
            container.addView(titleView, titleViewIndex);
        } else {
            container.addView(titleView);
        }
        titleView.setOnTouchListener(onContainerTouchListener);
    }

    protected abstract void initViewPager(ViewPager viewPager);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            return;
        }

        if (getChildCount() > 0) {
            final View child = getChildAt(0);
            int width = getMeasuredWidth();
            if (child.getMeasuredWidth() < width) {
                final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();

                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), lp.height);
                width -= getPaddingLeft();
                width -= getPaddingRight();
                int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();

        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

        childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom(), lp.height);
        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.leftMargin + lp.rightMargin, MeasureSpec.UNSPECIFIED);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    public OnTitleStripEventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(OnTitleStripEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public interface OnTitleStripEventListener {
        void onTitleClicked(int titleIndex);
    }

    protected void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    protected int getCurrentPage() {
        return currentPage;
    }

    protected void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    protected int getPreviousPage() {
        return previousPage;
    }

    protected void movePage(int position) {
        previousPage = currentPage;
        currentPage = position;
    }

    public void setTitleView(@LayoutRes int titleView) {
        this.titleView = titleView;
    }

    public void setTitleColor(@ColorRes int highlightColor, @ColorRes int normalColor) {
        this.highlightColor = highlightColor;
        this.normalColor = normalColor;
    }

    public int getHighlightColor() {
        return highlightColor;
    }

    public int getNormalColor() {
        return normalColor;
    }
}
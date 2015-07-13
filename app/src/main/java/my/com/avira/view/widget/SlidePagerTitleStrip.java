package my.com.avira.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by randiwaranugraha on 7/1/15.
 */
public class SlidePagerTitleStrip extends AbstractPagerTitleStrip {

    private static final String TAG = SlidePagerTitleStrip.class.getSimpleName();

    public SlidePagerTitleStrip(Context context) {
        super(context);
    }

    public SlidePagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidePagerTitleStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initViewPager(ViewPager viewPager) {
        int count = getCount();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < count; ++i) {
            TextView childTextView = createTitleView(inflater, i);
            addTitle(childTextView, -1);
        }

        getTitle(0).setTextColor(getResources().getColor(getHighlightColor()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (getCurrentPage() < 0) {
            movePage(position);
            return;
        }

        float move = 0;
        for (int i = 0; i < position; i++) {
            move -= getTitle(i).getWidth();
        }

        if (position <= getCurrentPage()) {
            move -= getTitle(position).getWidth() * positionOffset;
        } else {
            move += getTitle(position).getWidth() * positionOffset;
        }

        getContainer().setX(move);
    }

    @Override
    public void onPageSelected(int position) {
        movePage(position);

        for (int i = 0; i < getCount(); i++) {
            if (i == position) {
                getTitle(i).setTextColor(getResources().getColor(getHighlightColor()));
            } else {
                getTitle(i).setTextColor(getResources().getColor(getNormalColor()));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
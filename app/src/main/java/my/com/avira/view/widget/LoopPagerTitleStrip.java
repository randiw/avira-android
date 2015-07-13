package my.com.avira.view.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;

/**
 * Created by randiwaranugraha on 6/30/15.
 */
public class LoopPagerTitleStrip extends AbstractPagerTitleStrip {

    public LoopPagerTitleStrip(Context context) {
        super(context);
    }

    public LoopPagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoopPagerTitleStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getCount() {
        PagerAdapter adapter = getViewPager().getAdapter();
        if (adapter instanceof InfinitePagerAdapter) {
            return ((InfinitePagerAdapter) adapter).getRealCount();
        }
        return super.getCount();
    }

    @Override
    protected void initViewPager(ViewPager viewPager) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView[] children = new TextView[getCount()];
        for (int i = 0; i < getCount(); ++i) {
            children[i] = createTitleView(inflater, i);
        }

        addTitle(createTitleView(inflater, getCount() - 1), 0);
        for (TextView view : children) {
            addTitle(view, -1);
        }
        addTitle(createTitleView(inflater, 0), -1);

        // dont have width measured at this moment so do some calculation.
        TextView firstTitle = getTitle(0);
        float initX = firstTitle.getPaint().measureText(firstTitle.getText().toString()) + firstTitle.getPaddingLeft() + firstTitle.getPaddingRight();
        getContainer().setX(-initX);
        getTitle(1).setTextColor(getResources().getColor(getHighlightColor()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // prevent first unexpected call to this callback
        if (getCurrentPage() < 0) {
            movePage(position);
            return;
        }

        float move = 0;
        if (getCurrentPage() <= position) {
            // swiping right to left
            move = -getTitle(1).getWidth() * positionOffset - getTitle(0).getWidth();
        } else {
            // swiping left to right
            move = -getTitle(0).getWidth() * positionOffset;
        }

        getContainer().setX(move);
    }

    @Override
    public void onPageSelected(int position) {
        movePage(position);

        int offScreenX = getTitle(0).getWidth();
        int titleCount = getCount();
        // it can be a multiple-page change
        int moveOffset = Math.abs(getCurrentPage() - getPreviousPage());
        if (getCurrentPage() > getPreviousPage()) {
            offScreenX = 0;
            // swiped right to left
            for (int i = 0; i < moveOffset; ++i) {
                int lastTitleIndex = ((int) getTitle(titleCount + 1).getTag() + 1) % titleCount;
                removeTitle(0);
                addTitle(createTitleView(LayoutInflater.from(getContext()), lastTitleIndex), -1);

                offScreenX += getTitle(0).getWidth();
            }
        } else if (getCurrentPage() < getPreviousPage()) {
            offScreenX = 0;
            // swiped left to right
            for (int i = 0; i < moveOffset; ++i) {
                int firstTitleIndex = ((int) getTitle(0).getTag() + titleCount - 1) % titleCount;
                offScreenX += getContainer().findViewWithTag(firstTitleIndex).getWidth();
                removeTitle(getContainer().getChildCount() - 1);
                addTitle(createTitleView(LayoutInflater.from(getContext()), firstTitleIndex), 0);
            }
        }

        for (int i = 0; i < titleCount + 2; ++i) {
            if (i == 1) {
                getTitle(i).setTextColor(getResources().getColor(getHighlightColor()));
            } else {
                getTitle(i).setTextColor(getResources().getColor(getNormalColor()));
            }
        }

        getContainer().setX(-offScreenX);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
package my.com.avira.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Date;

import butterknife.Bind;
import my.com.avira.ImageItem;
import my.com.avira.R;
import my.com.avira.fragments.AviraFragment;
import my.com.avira.fragments.BaseMainFragment;
import my.com.avira.fragments.CompanyFragment;
import my.com.avira.fragments.GuideFragment;
import my.com.avira.fragments.NewsFragment;
import my.com.avira.fragments.TimelineFragment;
import my.com.avira.helper.DataHelper;
import my.com.avira.view.widget.SlidePagerTitleStrip;


public class MainActivity extends BaseActivity {

    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.titlestrip) SlidePagerTitleStrip titleStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout(R.layout.activity_main);

        boolean is_installed = DataHelper.getBoolean("is_installed");
        if(!is_installed) {
            DataHelper.saveData("is_installed", true);
        }

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);

        titleStrip.setTitleView(R.layout.view_title_strip);
        titleStrip.setTitleColor(R.color.yellow_cream_can, R.color.gray_silver_sand);
        titleStrip.setViewPager(viewPager);
    }

    private class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AviraFragment.newInstance(getApplicationContext());

                case 1:
                    return NewsFragment.newInstance(getApplicationContext());

                case 2:
                    return TimelineFragment.newInstance(getApplicationContext());

                case 3:
                    return GuideFragment.newInstance(getApplicationContext());

                case 4:
                    return CompanyFragment.newInstance(getApplicationContext());
            }

            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            BaseMainFragment baseMainFragment = (BaseMainFragment) getItem(position);
            return baseMainFragment.getTitle();
        }
    }
}
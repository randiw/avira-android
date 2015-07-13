package my.com.avira.activities;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

/**
 * Created by randiwaranugraha on 4/6/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected void setupLayout(@LayoutRes int layout) {
        setContentView(layout);
        ButterKnife.bind(this);
    }
}
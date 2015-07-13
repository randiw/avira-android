package my.com.avira.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import my.com.avira.R;

/**
 * Created by randiwaranugraha on 7/1/15.
 */
public class GuideFragment extends BaseMainFragment {

    public static GuideFragment newInstance(Context context) {
        GuideFragment guideFragment = new GuideFragment();
        guideFragment.setTitle(context.getString(R.string.buyers_guide_title));
        return guideFragment;
    }

    @Override
    protected View setupLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
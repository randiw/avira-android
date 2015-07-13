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
public class AviraFragment extends BaseMainFragment {

    public static AviraFragment newInstance(Context context) {
        AviraFragment aviraFragment = new AviraFragment();
        aviraFragment.setTitle(context.getString(R.string.avira_title));
        return aviraFragment;
    }

    @Override
    protected View setupLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_avira, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
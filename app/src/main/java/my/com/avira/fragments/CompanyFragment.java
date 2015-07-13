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
public class CompanyFragment extends BaseMainFragment {

    public static CompanyFragment newInstance(Context context) {
        CompanyFragment companyFragment = new CompanyFragment();
        companyFragment.setTitle(context.getString(R.string.eno_title));
        return companyFragment;
    }

    @Override
    protected View setupLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
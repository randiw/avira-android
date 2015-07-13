package my.com.avira.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import butterknife.Bind;
import butterknife.OnClick;
import my.com.avira.R;

/**
 * Created by randiwaranugraha on 7/1/15.
 */
public class NewsFragment extends BaseMainFragment {

    public static NewsFragment newInstance(Context context) {
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setTitle(context.getString(R.string.news_title));
        return newsFragment;
    }

    @Bind(R.id.share) ShareButton share;

    @Override
    protected View setupLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Sharing Avira")
                .setContentUrl(Uri.parse("http://www.avira-medini.com.my/"))
                .setContentDescription("Test for sharing Avira.")
                .build();

        share.setShareContent(content);
    }

}
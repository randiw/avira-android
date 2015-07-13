package my.com.avira.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnTouch;
import my.com.avira.R;
import my.com.avira.RecordDao;
import my.com.avira.controller.API;
import my.com.avira.repository.provider.RecordProvider;
import my.com.avira.view.adapter.DateListAdapter;
import my.com.avira.view.adapter.ImageListAdapter;

/**
 * Created by randiwaranugraha on 7/1/15.
 */
public class TimelineFragment extends BaseMainFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = TimelineFragment.class.getSimpleName();
    private static final int QUERY_RECORD = 1;

    public static TimelineFragment newInstance(Context context) {
        TimelineFragment timelineFragment = new TimelineFragment();
        timelineFragment.setTitle(context.getString(R.string.timeline_title));
        return timelineFragment;
    }

    @Bind(R.id.datelist) ListView dateList;
    @Bind(R.id.imagelist) ListView imageList;

    private View clickSource;
    private View touchSource;

    private DateListAdapter dateListAdapter;
    private ImageListAdapter imageListAdapter;

    private LoaderManager loaderManager;

    @Override
    protected View setupLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        API.get(API.Action.TIMELINES, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                loaderManager.restartLoader(QUERY_RECORD, null, TimelineFragment.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        dateListAdapter = new DateListAdapter(getActivity());
        dateList.setAdapter(dateListAdapter);
        dateList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(clickSource == view) {
                    int movedTop = view.getChildAt(0).getTop();
                    int movedSyncTop = movedTop * imageList.getChildAt(0).getHeight() / view.getChildAt(0).getHeight();
                    imageList.setSelectionFromTop(firstVisibleItem, movedSyncTop);
                }
            }
        });

        imageListAdapter = new ImageListAdapter(getActivity());
        imageList.setAdapter(imageListAdapter);
        imageList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(clickSource == view) {
                    int movedTop = view.getChildAt(0).getTop();
                    int movedSyncTop = movedTop * dateList.getChildAt(0).getHeight() / view.getChildAt(0).getHeight();
                    dateList.setSelectionFromTop(firstVisibleItem, movedSyncTop);
                }
            }
        });

        loaderManager = getLoaderManager();
        loaderManager.initLoader(QUERY_RECORD, null, this);
    }

    @OnTouch({R.id.imagelist, R.id.datelist})
    public boolean scrollImageList(View view, MotionEvent motionEvent) {
        if(touchSource == null) {
            touchSource = view;
        }

        if(view == touchSource) {
            if(view.getId() == R.id.imagelist) dateList.dispatchTouchEvent(motionEvent);
            if(view.getId() == R.id.datelist) imageList.dispatchTouchEvent(motionEvent);
            if(MotionEvent.ACTION_UP == motionEvent.getAction()) {
                clickSource = view;
                touchSource = null;
            }
        }

        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(QUERY_RECORD == id) {
            return new CursorLoader(getActivity(), RecordProvider.CONTENT_URI, null, null, null, RecordDao.Properties.Date.columnName + " DESC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(QUERY_RECORD == loader.getId()) {
            dateListAdapter.swapCursor(data);
            dateListAdapter.notifyDataSetChanged();

            imageListAdapter.swapCursor(data);
            imageListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(QUERY_RECORD == loader.getId()) {
            dateListAdapter.swapCursor(null);
            dateListAdapter.notifyDataSetChanged();

            imageListAdapter.swapCursor(null);
            imageListAdapter.notifyDataSetChanged();
        }
    }
}
package my.com.avira.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import my.com.avira.Photo;
import my.com.avira.R;
import my.com.avira.Record;
import my.com.avira.helper.DateHelper;
import my.com.avira.repository.PhotoRepository;

/**
 * Created by randiwaranugraha on 7/2/15.
 */
public class DateListAdapter extends RecordCursorAdapter {

    private DateHelper dateHelper;

    public DateListAdapter(Context context) {
        super(context);
        dateHelper = new DateHelper("d LLL yy", Locale.ENGLISH);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date_list, viewGroup, false);

        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        int position = cursor.getPosition();

        Record record = getItem(position);

        boolean isTop = position == 0;
        holder.top.setVisibility(isTop ? View.GONE : View.VISIBLE);


        Date date = record.getDate();
        String dateText = dateHelper.fromDate(date);
        holder.date.setText(dateText.startsWith("0") ? dateText.substring(1) : dateText);
        holder.date.setVisibility(View.VISIBLE);

        if(position > 0) {
            Record previousItem = getItem(position - 1);
            if(record.getDate() == previousItem.getDate()) {
                holder.date.setVisibility(View.GONE);
            }
        }

        List<Photo> photos = PhotoRepository.findByRecord(record.getRecord_id());
        int photosCount = photos.size();

        boolean isBottom = position == cursor.getCount() - 1;
        holder.bottom.setVisibility(isBottom && photosCount == 1 ? View.GONE : View.VISIBLE);

        holder.container.setVisibility(View.GONE);
        if(photosCount > 1) {
            if(photosCount > 3) {
                photosCount = 3;
            }

            holder.container.setVisibility(View.VISIBLE);
            holder.container.removeAllViews();
            for(int i = 0; i < photosCount - 1; i++) {
                View dateView = LayoutInflater.from(context).inflate(R.layout.item_single_date, null);
                holder.container.addView(dateView);

                if(i == photosCount - 2 && isBottom) {
                    LinearLayout bottom = ButterKnife.findById(dateView, R.id.bottom);
                    bottom.setVisibility(View.GONE);
                }
            }
        }
    }

    static class ViewHolder {

        @Bind(R.id.top) LinearLayout top;
        @Bind(R.id.bottom) LinearLayout bottom;
        @Bind(R.id.date) TextView date;
        @Bind(R.id.container) LinearLayout container;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
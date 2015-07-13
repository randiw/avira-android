package my.com.avira.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import my.com.avira.ImageItem;
import my.com.avira.R;
import my.com.avira.Record;
import my.com.avira.helper.DateHelper;

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

        holder.top.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        holder.bottom.setVisibility(position == cursor.getCount() - 1 ? View.GONE : View.VISIBLE);

        Date date = record.getDate();
        String dateText = dateHelper.fromDate(date);
        holder.date.setText(dateText.startsWith("0") ? dateText.substring(1) : dateText);
        holder.date.setVisibility(View.VISIBLE);

//        if(position > 0) {
//            Record previousItem = getItem(position - 1);
//            if(record.getRecord_id() == previousItem.getArticle_id()) {
//                holder.date.setVisibility(View.GONE);
//            }
//        }
    }

    static class ViewHolder {

        @Bind(R.id.top) LinearLayout top;
        @Bind(R.id.bottom) LinearLayout bottom;
        @Bind(R.id.date) TextView date;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
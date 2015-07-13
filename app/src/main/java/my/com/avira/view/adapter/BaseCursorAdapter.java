package my.com.avira.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;

/**
 * Created by randiwaranugraha on 7/3/15.
 */
public abstract class BaseCursorAdapter extends CursorAdapter {

    public BaseCursorAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public int getCount() {
        Cursor cursor = getCursor();
        if (cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }
}
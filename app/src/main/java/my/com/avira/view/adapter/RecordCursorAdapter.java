package my.com.avira.view.adapter;

import android.content.Context;
import android.database.Cursor;

import my.com.avira.Record;
import my.com.avira.repository.RecordRepository;

/**
 * Created by randiwaranugraha on 7/3/15.
 */
public abstract class RecordCursorAdapter extends BaseCursorAdapter {

    public RecordCursorAdapter(Context context) {
        super(context);
    }

    @Override
    public Record getItem(int position) {
        Cursor cursor = getCursor();
        if (!cursor.moveToPosition(position)) {
            return null;
        }

        Record record = RecordRepository.createInstance(cursor);
        return record;
    }
}
package my.com.avira.repository;

import android.database.Cursor;

import java.util.Date;
import java.util.List;

import my.com.avira.AppController;
import my.com.avira.Record;
import my.com.avira.RecordDao;
import my.com.avira.tools.RepoTools;

/**
 * Created by randiwaranugraha on 7/10/15.
 */
public class RecordRepository {

    public static void save(Record record) {
        Record oldRecord = find(record.getRecord_id());
        if(oldRecord != null) {
            record.setId(oldRecord.getId());
        }

        getRecordDao().insertOrReplace(record);
    }

    public static Record find(int record_id) {
        List<Record> records = getRecordDao().queryBuilder().where(RecordDao.Properties.Record_id.eq(record_id)).limit(1).list();
        if (!RepoTools.isRowAvailable(records)) {
            return null;
        }

        Record record = records.get(0);
        return record;
    }

    public static void clear() {
        getRecordDao().deleteAll();
    }

    public static Record createInstance(Cursor cursor) {
        long id = RepoTools.getLong(cursor, RecordDao.Properties.Id.columnName);
        int record_id = RepoTools.getInt(cursor, RecordDao.Properties.Record_id.columnName);
        String description = RepoTools.getString(cursor, RecordDao.Properties.Description.columnName);
        String date_time = RepoTools.getString(cursor, RecordDao.Properties.Date_time.columnName);
        long created_at = RepoTools.getLong(cursor, RecordDao.Properties.Created_at.columnName);
        long updated_at = RepoTools.getLong(cursor, RecordDao.Properties.Updated_at.columnName);
        String photos = RepoTools.getString(cursor, RecordDao.Properties.Photos.columnName);
        Date date = RepoTools.getDate(cursor, RecordDao.Properties.Date.columnName);

        Record record = new Record(id, record_id, description, date_time, created_at, updated_at, photos, date);
        return record;
    }

    private static RecordDao getRecordDao() {
        return AppController.getInstance().getDaoSession().getRecordDao();
    }
}
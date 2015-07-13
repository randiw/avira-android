package my.com.avira.repository.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import my.com.avira.DaoSession;
import my.com.avira.RecordDao;

/**
 * Created by randiwaranugraha on 7/10/15.
 */
public class RecordProvider extends ContentProvider {

    public static final String TAG = RecordProvider.class.getSimpleName();

    public static final String AUTHORITY = "my.com.avira.repository.provider.RecordProvider";
    private static final String TABLE_NAME = RecordDao.TABLENAME;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public static final int RECORD = 0;
    public static final int RECORD_ID = 1;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, RECORD);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", RECORD_ID);
    }

    private static DaoSession daoSession;

    public static void setDaoSession(DaoSession daoSession) {
        RecordProvider.daoSession = daoSession;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        int uriType = URI_MATCHER.match(uri);
        switch (uriType) {
            case RECORD:
                break;

            case RECORD_ID:
                queryBuilder.appendWhere(RecordDao.Properties.Id.columnName + "=" + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalStateException("Unknown URI " + uri.toString());
        }

        Cursor cursor = queryBuilder.query(daoSession.getDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("This content provider is readonly");
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        throw new UnsupportedOperationException("This content provider is readonly");
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException("This content provider is readonly");
    }
}

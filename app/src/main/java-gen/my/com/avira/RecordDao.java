package my.com.avira;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import my.com.avira.Record;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RECORD.
*/
public class RecordDao extends AbstractDao<Record, Long> {

    public static final String TABLENAME = "RECORD";

    /**
     * Properties of entity Record.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Record_id = new Property(1, Integer.class, "record_id", false, "RECORD_ID");
        public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property Date_time = new Property(3, String.class, "date_time", false, "DATE_TIME");
        public final static Property Created_at = new Property(4, Long.class, "created_at", false, "CREATED_AT");
        public final static Property Updated_at = new Property(5, Long.class, "updated_at", false, "UPDATED_AT");
        public final static Property Photos = new Property(6, String.class, "photos", false, "PHOTOS");
        public final static Property Date = new Property(7, java.util.Date.class, "date", false, "DATE");
    };


    public RecordDao(DaoConfig config) {
        super(config);
    }
    
    public RecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RECORD' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'RECORD_ID' INTEGER," + // 1: record_id
                "'DESCRIPTION' TEXT," + // 2: description
                "'DATE_TIME' TEXT," + // 3: date_time
                "'CREATED_AT' INTEGER," + // 4: created_at
                "'UPDATED_AT' INTEGER," + // 5: updated_at
                "'PHOTOS' TEXT," + // 6: photos
                "'DATE' INTEGER);"); // 7: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RECORD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Record entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer record_id = entity.getRecord_id();
        if (record_id != null) {
            stmt.bindLong(2, record_id);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }
 
        String date_time = entity.getDate_time();
        if (date_time != null) {
            stmt.bindString(4, date_time);
        }
 
        Long created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindLong(5, created_at);
        }
 
        Long updated_at = entity.getUpdated_at();
        if (updated_at != null) {
            stmt.bindLong(6, updated_at);
        }
 
        String photos = entity.getPhotos();
        if (photos != null) {
            stmt.bindString(7, photos);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(8, date.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Record readEntity(Cursor cursor, int offset) {
        Record entity = new Record( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // record_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // date_time
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // created_at
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // updated_at
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // photos
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // date
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Record entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRecord_id(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDate_time(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCreated_at(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setUpdated_at(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setPhotos(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Record entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Record entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
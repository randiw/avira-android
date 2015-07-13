package my.com.avira;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import my.com.avira.Photo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PHOTO.
*/
public class PhotoDao extends AbstractDao<Photo, Long> {

    public static final String TABLENAME = "PHOTO";

    /**
     * Properties of entity Photo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Photo_id = new Property(1, Integer.class, "photo_id", false, "PHOTO_ID");
        public final static Property Record_id = new Property(2, Integer.class, "record_id", false, "RECORD_ID");
        public final static Property Caption = new Property(3, String.class, "caption", false, "CAPTION");
        public final static Property Url = new Property(4, String.class, "url", false, "URL");
        public final static Property Created_at = new Property(5, Long.class, "created_at", false, "CREATED_AT");
        public final static Property Updated_at = new Property(6, Long.class, "updated_at", false, "UPDATED_AT");
    };


    public PhotoDao(DaoConfig config) {
        super(config);
    }
    
    public PhotoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PHOTO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'PHOTO_ID' INTEGER," + // 1: photo_id
                "'RECORD_ID' INTEGER," + // 2: record_id
                "'CAPTION' TEXT," + // 3: caption
                "'URL' TEXT," + // 4: url
                "'CREATED_AT' INTEGER," + // 5: created_at
                "'UPDATED_AT' INTEGER);"); // 6: updated_at
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PHOTO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Photo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer photo_id = entity.getPhoto_id();
        if (photo_id != null) {
            stmt.bindLong(2, photo_id);
        }
 
        Integer record_id = entity.getRecord_id();
        if (record_id != null) {
            stmt.bindLong(3, record_id);
        }
 
        String caption = entity.getCaption();
        if (caption != null) {
            stmt.bindString(4, caption);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(5, url);
        }
 
        Long created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindLong(6, created_at);
        }
 
        Long updated_at = entity.getUpdated_at();
        if (updated_at != null) {
            stmt.bindLong(7, updated_at);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Photo readEntity(Cursor cursor, int offset) {
        Photo entity = new Photo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // photo_id
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // record_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // caption
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // url
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // created_at
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6) // updated_at
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Photo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhoto_id(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setRecord_id(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setCaption(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCreated_at(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setUpdated_at(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Photo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Photo entity) {
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

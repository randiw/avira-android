package my.com.avira.repository;

import android.util.Log;

import java.util.List;

import my.com.avira.AppController;
import my.com.avira.Photo;
import my.com.avira.PhotoDao;
import my.com.avira.tools.RepoTools;

/**
 * Created by randiwaranugraha on 7/10/15.
 */
public class PhotoRepository {

    public static final String TAG = PhotoRepository.class.getSimpleName();

    public static void save(Photo photo) {
        Log.d(TAG, "photoId: " + photo.getPhoto_id());
        Photo oldPhoto = find(photo.getPhoto_id());
        if(oldPhoto != null) {
            photo.setId(oldPhoto.getId());
        }

        getPhotoDao().insertOrReplace(photo);
    }

    public static void save(Photo photo, int record_id) {
        photo.setRecord_id(record_id);
        save(photo);
    }

    public static Photo find(int photo_id) {
        List<Photo> photos = getPhotoDao().queryBuilder().where(PhotoDao.Properties.Photo_id.eq(photo_id)).limit(1).list();
        if (!RepoTools.isRowAvailable(photos)) {
            return null;
        }

        Photo photo = photos.get(0);
        return photo;
    }

    public static List<Photo> findByRecord(int record_id) {
        List<Photo> photos = getPhotoDao().queryBuilder().where(PhotoDao.Properties.Record_id.eq(record_id)).list();
        if (!RepoTools.isRowAvailable(photos)) {
            return null;
        }

        return photos;
    }

    public static void clear() {
        getPhotoDao().deleteAll();
    }

    private static PhotoDao getPhotoDao() {
        return AppController.getInstance().getDaoSession().getPhotoDao();
    }
}
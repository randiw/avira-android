package my.com.avira.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import my.com.avira.ImageItem;
import my.com.avira.Photo;
import my.com.avira.R;
import my.com.avira.Record;
import my.com.avira.repository.PhotoRepository;

/**
 * Created by randiwaranugraha on 7/3/15.
 */
public class ImageListAdapter extends RecordCursorAdapter {

    private static final String TAG = ImageListAdapter.class.getSimpleName();

    public ImageListAdapter(Context context) {
        super(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_list, parent, false);

        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        int position = cursor.getPosition();

        Record record = getItem(position);
        List<Photo> photos = PhotoRepository.findByRecord(record.getRecord_id());
        int photoShown = 0;
        if(photos.size() > 3) {
            photoShown = 3;
            holder.more.setVisibility(View.VISIBLE);
            holder.more.setText("View all " + photos.size() + " images");
        } else {
            photoShown = photos.size();
            holder.more.setVisibility(View.GONE);
        }

        holder.photoContainer.removeAllViews();
        Log.d(TAG, "photo Shown: " + photoShown);
        for(int i = 0; i < photoShown; i++) {
            Photo photo = photos.get(i);
            View photoView = LayoutInflater.from(context).inflate(R.layout.item_single_photo, null);

            ImageView image = ButterKnife.findById(photoView, R.id.image);
            Glide.with(context).load(photo.getUrl()).fitCenter().centerCrop().into(image);

            if(photo.getCaption() != null) {
                TextView title = ButterKnife.findById(photoView, R.id.title);
                title.setText(photo.getCaption());
            }

            holder.photoContainer.addView(photoView);
        }
    }

    static class ViewHolder {

        @Bind(R.id.photo_container) LinearLayout photoContainer;
        @Bind(R.id.more) TextView more;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
package my.com.avira.controller.request;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import my.com.avira.Photo;
import my.com.avira.Record;
import my.com.avira.helper.DateHelper;
import my.com.avira.repository.PhotoRepository;
import my.com.avira.repository.RecordRepository;

/**
 * Created by randiwaranugraha on 7/10/15.
 */
public class GetTimelineRequest extends GetJSONRequest {

    public GetTimelineRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected void handleDataResponse(JSONObject json) throws JSONException {
        JSONArray timelines = json.getJSONArray("timelines");

        List<Record> records = buildRecords(timelines.toString());
        for(Record record : records) {
            RecordRepository.save(record);

            int record_id = record.getRecord_id();
            String photoString = record.getPhotos();
            List<Photo> photos = buildPhotos(photoString);
            for(Photo photo : photos) {
                PhotoRepository.save(photo, record_id);
            }
        }
    }

    private List<Record> buildRecords(String timelines) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Record.class, new RecordDeserializer());

        final Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Record>>() {}.getType();
        List<Record> records = gson.fromJson(timelines, listType);

        return records;
    }

    private List<Photo> buildPhotos(String photos) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Photo.class, new PhotoDeserializer());

        final Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Photo>>() {}.getType();
        List<Photo> photoList = gson.fromJson(photos, listType);

        return photoList;
    }

    private class RecordDeserializer implements JsonDeserializer<Record> {

        @Override
        public Record deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = (JsonObject) json;
            int record_id = object.get("id").getAsInt();

            String description = null;
            if(!object.get("description").isJsonNull()) {
                description = object.get("description").getAsString();
            }

            String date_time = object.get("date_time").getAsString();
            long created_at = object.get("created_at").getAsLong();
            long updated_at = object.get("updated_at").getAsLong();

            JsonArray photoArray = object.get("photos").getAsJsonArray();
            String photos = photoArray.toString();

            DateHelper dateHelper = new DateHelper("dd/MM/yyyy");
            Date date = dateHelper.toDate(date_time);

            Record record = new Record();
            record.setRecord_id(record_id);
            record.setDescription(description);
            record.setDate_time(date_time);
            record.setCreated_at(created_at);
            record.setUpdated_at(updated_at);
            record.setPhotos(photos);
            record.setDate(date);

            return record;
        }
    }

    private class PhotoDeserializer implements JsonDeserializer<Photo> {

        @Override
        public Photo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = (JsonObject) json;
            int photo_id = object.get("id").getAsInt();
            String caption = object.get("caption").getAsString();
            String url = object.get("url").getAsString();
            long created_at = object.get("created_at").getAsLong();
            long updated_at = object.get("updated_at").getAsLong();

            Photo photo = new Photo();
            photo.setPhoto_id(photo_id);
            photo.setCaption(caption);
            photo.setUrl(url);
            photo.setCreated_at(created_at);
            photo.setUpdated_at(updated_at);

            return photo;
        }
    }
}
package my.com.avira.controller;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import my.com.avira.controller.request.BaseJSONRequest;
import my.com.avira.controller.request.GetJSONRequest;
import my.com.avira.controller.request.GetTimelineRequest;
import my.com.avira.tools.Connect;

/**
 * Created by randiwaranugraha on 7/10/15.
 */
public class API {

    public static final String TAG = API.class.getSimpleName();

    private static final String URL_STAGING = "https://avirastaging.herokuapp.com";
    private static final String API_VERSION = "/api/v1/";

    public static void get(String action, HashMap<String, String> parameters, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = getUrl(action);

        BaseJSONRequest jsonRequest;
        if(Action.TIMELINES.equals(action)) {
            jsonRequest = new GetTimelineRequest(url, listener, errorListener);
        } else {
            jsonRequest = new GetJSONRequest(url, listener, errorListener) {
                @Override
                protected void handleDataResponse(JSONObject json) throws JSONException {

                }
            };
        }

        Connect connect = new Connect();
        connect.execute(jsonRequest);
    }

    public static String expandParameter(HashMap<String, String> parameters) {
        StringBuilder builder = new StringBuilder();

        builder.append("?");
        int total = parameters.size();
        int i = 0;
        for(String key : parameters.keySet()) {
            String value = parameters.get(key);
            builder.append(key + "=" + value);
            i++;
            if(i < total) {
                builder.append("&");
            }
        }

        return builder.toString();
    }

    public static String getUrl(String action) {
        StringBuilder url = new StringBuilder();
        url.append(URL_STAGING);
        url.append(API_VERSION);
        url.append(action);

        return url.toString();
    }

    public class Action {
        public static final String TIMELINES = "timelines";
    }
}
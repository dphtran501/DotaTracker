package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A class to unpack JSON files and load their information into the application's data models.
 *
 * @author Derek Tran
 * @version 1.0
 * @since December 2, 2017
 */
public class HTTPRequestService
{
    // TODO: Implementation of GSONLoader class
    private static final String TAG = "GSONLoader";
    private static Gson gson = new Gson();

    public static List<Match> getMatchDetails(long steamId32) {
        String url = "http://68.4.78.45:8080/dotaweb/fetch/refresh?steamId32=" + steamId32;

        return getMatchDetails(steamId32);
    }

    private List<Match> returnMatchDetails(String responseBody) {
        return gson.fromJson(responseBody, new TypeToken<List<Match>>(){}.getType());
    }

    private void getMatchJSON(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                returnMatchDetails(responseBody.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Error retrieving match json", error);
                return;
            }
        });
    }

    
}

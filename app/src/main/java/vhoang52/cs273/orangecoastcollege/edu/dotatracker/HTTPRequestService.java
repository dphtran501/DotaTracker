package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import java.util.ArrayList;
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
    private static String BASE_URL = "http://68.4.78.45:8080/dotaweb/";

    // User objects

    public static boolean postUserID(long steamId64) {
        String url = BASE_URL + "register?steamId64=" + steamId64;
        AsyncHttpClient client = new AsyncHttpClient();
        final List<Integer> whyAmIDoingItThisWay = new ArrayList<>();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                whyAmIDoingItThisWay.add(1);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                whyAmIDoingItThisWay.clear();
            }
        });

        return (whyAmIDoingItThisWay.size() > 0);
    }

    public static List<User> getUserSummaries(long steamId64) {
        String url = BASE_URL + "fetch/playersummary?steamId64=" + steamId64;
        final List<User> userList = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                List<User> tempList = gson.fromJson(new String(bytes), new TypeToken<List<User>>(){}.getType());
                userList.addAll(tempList);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG, "Error retrieving user json", throwable);
            }
        });

        return userList;
    }

    // Match objects

    public static List<Match> getMatchDetails(long steamId32) {
        String url = BASE_URL + "fetch/refresh?steamId32=" + steamId32;

        final List<Match> matchList = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Match> tempList = gson.fromJson(new String(responseBody), new TypeToken<List<Match>>() {}.getType());
                matchList.addAll(tempList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Error retrieving match json", error);
            }
        });

        return matchList;
    }
    
}

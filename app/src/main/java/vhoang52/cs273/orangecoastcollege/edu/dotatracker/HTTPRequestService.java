package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import java.lang.reflect.Type;
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
    private static final String TAG = "HTTPRequestService";
    private static Gson gson = new Gson();
    private static String BASE_URL = "http://68.4.78.45:8080/dotaweb/";

    private static HTTPRequestService mService;
    private static long mCurrentUserId;
    private static User mCurrentUser;
    private static List<Match> mMatchesList;

    public HTTPRequestService() {}

    public static HTTPRequestService getInstance() {
        if (mService == null) {
            mService = new HTTPRequestService();
        }

        mMatchesList = new ArrayList<>();
        return mService;
    }

    // User objects
    public interface UserRegistrationCallback {
        void onSuccess();
        void onFailure();
    }

    public long getmCurrentUserId() {
        return mCurrentUserId;
    }

    public void setmCurrentUserId(long mCurrentUserId) {
        HTTPRequestService.mCurrentUserId = mCurrentUserId;
        Log.i(TAG, "mCurrentUserId is now set to->" + mCurrentUserId);
    }

    public User getmCurrentUser() {
        return mCurrentUser;
    }

    public void setmCurrentUser(User mCurrentUser) {
        HTTPRequestService.mCurrentUser = mCurrentUser;
        Log.i(TAG, "mCurrentUser is now set to->" + mCurrentUser.toString());
    }

    public List<Match> getmMatchesList() {
        return mMatchesList;
    }

    public void setmMatchesList(List<Match> mMatchesList) {
        HTTPRequestService.mMatchesList = mMatchesList;
    }

    public static void postUserID(final long steamId64, final UserRegistrationCallback callback) {
        String url = BASE_URL + "register?steamId64=" + steamId64;
        Log.i(TAG, "POST url->" + url);

        AsyncHttpClient client = new AsyncHttpClient();
        final List<Integer> whyAmIDoingItThisWay = new ArrayList<>();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "onSuccess callback received. mCurrentUser->" + steamId64);
                mService.setmCurrentUserId(steamId64);
                callback.onSuccess();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG, "Failed to register id->" + steamId64 + "; Current user is still->" + mService.getmCurrentUserId());
                callback.onFailure();
            }
        });
    }

    public interface JSONStringCallback {
        void onSuccess();
        void onFailure();
    }

    public static void getUserSummaries(long steamId64, final JSONStringCallback callback) {
        String url = BASE_URL + "fetch/playersummary?steamId64=" + steamId64;
        Log.i(TAG, "GET url->" + url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                List<User> userList = gson.fromJson(response, new TypeToken<List<User>>() {}.getType());
                mService.setmCurrentUser(userList.get(0));
                Log.i(TAG, "Successfully retrieved a response; mCurrentUser->" + mCurrentUser.toString());
                callback.onSuccess();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG, "Error retrieving user json", throwable);
                callback.onFailure();
            }
        });
    }

    // Match objects
    public interface MatchListCallback {
        void onSuccess();
        void onFailure();
    }

    public static void getMatchDetails(long steamId32, final MatchListCallback callback) {
        String url = BASE_URL + "fetch/refresh?steamId32=" + steamId32;
        Log.i(TAG, "GET url->" + url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Match> tempList = gson.fromJson(new String(responseBody), new TypeToken<List<Match>>(){}.getType());
                mService.setmMatchesList(tempList);

                Log.i(TAG, "Successfully retrieved match list from server; match list size->" + tempList.size());
                callback.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Error retrieving match list", error);
                callback.onFailure();
            }
        });
    }

}

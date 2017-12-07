package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * HTTPRequestService
 * <p>
 * Singleton class to send and receive asynchronous requests to and from a server.
 * Also keeps track of the current User and their ID across all activities.
 */
public class HTTPRequestService {
    // TODO: Implementation of GSONLoader class
    private static final String TAG = "HTTPRequestService";
    private static Gson gson = new Gson();
    private static String BASE_URL = "http://68.4.78.45:8080/dotaweb/";

    private static HTTPRequestService mService;
    private static long mCurrentUserId;
    private static User mCurrentUser;
    private static List<Match> mMatchesList;

    /**
     * Not to be called outside of getInstance()
     */
    private HTTPRequestService() {
    }

    /**
     * Gets the one and only instance of HTTPRequestService
     *
     * @return
     */
    public static HTTPRequestService getInstance() {
        if (mService == null) {
            mService = new HTTPRequestService();
        }

        mMatchesList = new ArrayList<>();
        return mService;
    }

    /**
     * I also realize after creating these interfaces that I didn't need to make 3, but these
     * interfaces are used to provide ways to interacting with the information after the client
     * receives a response from the server (for example, new users with no cached information
     * will take at least 27 seconds (not accounting for latency and processing time) to
     * receive a refresh of 25 match objects
     */
    public interface UserRegistrationCallback {
        void onSuccess();

        void onFailure();
    }

    /**
     * Gets the current user's ID
     */
    public long getmCurrentUserId() {
        return mCurrentUserId;
    }

    /**
     * Sets the current user ID
     */
    public void setmCurrentUserId(long mCurrentUserId) {
        HTTPRequestService.mCurrentUserId = mCurrentUserId;
        Log.i(TAG, "mCurrentUserId is now set to->" + mCurrentUserId);
    }

    /**
     * Gets the current user
     */
    public User getmCurrentUser() {
        return mCurrentUser;
    }

    /**
     * Sets the current user
     */
    public void setmCurrentUser(User mCurrentUser) {
        HTTPRequestService.mCurrentUser = mCurrentUser;
        Log.i(TAG, "mCurrentUser is now set to->" + mCurrentUser.toString());
    }

    /**
     * Gets the most recently retrieved matches list
     */
    public List<Match> getmMatchesList() {
        return mMatchesList;
    }

    /**
     * Sets the recently retrieved matches list
     */
    public void setmMatchesList(List<Match> mMatchesList) {
        HTTPRequestService.mMatchesList = mMatchesList;
    }

    /**
     * Notifies the server to cache information for the new user (if not already exists)
     *
     * @param steamId64 the user's steamId. Server is capable of receiving both 32 and 64 bit format
     *                  steam ids and convert automatically
     */
    public static void postUserID(final long steamId64, final UserRegistrationCallback callback) {
        String url = BASE_URL + "register?steamId64=" + steamId64;
        Log.i(TAG, "POST url->" + url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "onSuccess callback received. mCurrentUser->" + steamId64);
                mService.setmCurrentUserId(steamId64);
                callback.onSuccess();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG, "Failed to register id->" + steamId64
                        + "; Current user is still->" + mService.getmCurrentUserId());
                callback.onFailure();
            }
        });
    }

    /**
     * Callback interface to handle async results for getUserSummaries
     * <p>
     * see UserRegistrationCallback interface
     */
    public interface JSONStringCallback {
        void onSuccess();

        void onFailure();
    }

    /**
     * Retrieves user information after they register. Object is saved locally for faster recall.
     * After service completes, user object will be available via static method getmCurrentUser();
     *
     * @param steamId64 User's steam id
     */
    public static void getUserSummaries(long steamId64, final JSONStringCallback callback) {
        String url = BASE_URL + "fetch/playersummary?steamId64=" + steamId64;
        Log.i(TAG, "GET url->" + url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                List<User> userList = gson.fromJson(response, new TypeToken<List<User>>() {
                }.getType());
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

    /**
     * Callback for handling the results from getMatchDetails()
     * <p>
     * See UserRegistrationCallback interface
     */
    public interface MatchListCallback {
        void onSuccess();

        void onFailure();
    }

    /**
     * Retrieves the latest 25 matches for the user. Results will be available once the async request
     * completes via static method getmMatchesList()
     *
     * @param steamId32 user's steam id. the API requests 32 bit steam id,
     *                  but man-in-the-middle server can automatically convert 32<->64
     */
    public static void getMatchDetails(long steamId32, final MatchListCallback callback) {
        String url = BASE_URL + "fetch/refresh?steamId32=" + steamId32;
        Log.i(TAG, "GET url->" + url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Match> tempList = gson.fromJson(new String(responseBody), new TypeToken<List<Match>>() {
                }.getType());
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

    public static void loadProfileImage(String url, final ImageView imageView, final ProfileImageCallback callback) {
        Ion.with(imageView).load(url).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                callback.onSuccess(imageView.getDrawable());
            }
        });
        Log.i(TAG, "Loading avatar image->" + url);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null);
    }

    public interface ProfileImageCallback {
        public void onSuccess(Drawable drawable);
    }

}

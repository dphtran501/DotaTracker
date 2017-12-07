package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TabLayout mTabs;
    ViewPager mViewPager;
    MainActivityPagerAdapter mMainActivityPagerAdapter;
    private HTTPRequestService mService;
    private DBHelper mDBHelper;
    private long startMillis;
    private int count;

    private long mCurrentUserId;
    private User mCurrentUser;

    public static Uri getUriFromResource(Context context, int resID) {
        Resources res = context.getResources();
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resID) + "/"
                + res.getResourceTypeName(resID) + "/"
                + res.getResourceEntryName(resID);
        return Uri.parse(uri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = DBHelper.getInstance(this);
        mService = HTTPRequestService.getInstance();


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("CurrentUserId", Context.MODE_PRIVATE);
        mCurrentUserId = Long.parseLong(getResources().getString(R.string.currentUserId));
        Log.i(TAG, "UserID from sharedPreferences->" + mCurrentUserId);
        mService.setmCurrentUserId(mCurrentUserId);


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mMainActivityPagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainActivityPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkCurrentUser();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        checkCurrentUser();


        mTabs = (TabLayout) findViewById(R.id.tabLayout);
        mTabs.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
    }

    private void checkCurrentUser() {
        mCurrentUserId = mService.getmCurrentUserId();
        mCurrentUser = mDBHelper.getUser(mCurrentUserId);
        Log.i(TAG, "is mCurrentUser null->" + (mCurrentUser == null));

        if (mCurrentUser != null) {
            mService.setmCurrentUserId(mCurrentUserId);
            mService.setmCurrentUser(mCurrentUser);
            Log.i(TAG, "mCurrentUserTest->" + mCurrentUser.toString());
        } else {
            User user = new User(114611, 3, 1, "system", 0, "http://steamcommunity.com/id/system787/", "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/e5/e5b121b929c0d352264e2bb370f8a8fcc645acbc_full.jpg");
            mService.setmCurrentUser(user);
            mCurrentUser = user;

            HTTPRequestService.getUserSummaries(mCurrentUserId, new HTTPRequestService.JSONStringCallback() {
                @Override
                public void onSuccess() {
                    Log.i(TAG, "Successfully retrieved user->" + mCurrentUserId);
                    mCurrentUser = mService.getmCurrentUser();
                    mCurrentUserId = mService.getmCurrentUserId();
                    Log.i(TAG, "mCurrentUserTest->" + mCurrentUser.toString());
                    mDBHelper.addUser(mCurrentUser);
                }

                @Override
                public void onFailure() {
                    Log.e(TAG, "Failed to retrieve user->" + mCurrentUserId);
                }
            });
        }


        mMainActivityPagerAdapter.notifyDataSetChanged();
        Log.i(TAG, "mMainActivityPagerAdapter notified");
    }


    public void secretTap(View view) {
        long time = System.currentTimeMillis();

        if (startMillis == 0 || (time - startMillis > 3000)) {
            startMillis = time;
            count = 1;
        } else {
            count++;
        }

        if (count == 5) {
            startActivity(new Intent(this, FluffActivity.class));
        }
    }
}


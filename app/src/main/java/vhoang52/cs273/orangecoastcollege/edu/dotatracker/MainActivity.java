package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabs;
    ViewPager mViewPager;
    MainActivityPagerAdapter mMainActivityPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewPager);
        mMainActivityPagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainActivityPagerAdapter);
        mTabs = findViewById(R.id.tabLayout);
        mTabs.setupWithViewPager(mViewPager);

    }

    public static Uri getUriFromResource(Context context, int resID) {
        Resources res = context.getResources();
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resID) + "/"
                + res.getResourceTypeName(resID) + "/"
                + res.getResourceEntryName(resID);
        return Uri.parse(uri);
    }
}

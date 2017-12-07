package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by vincenthoang on 10/31/17.
 */

public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Change User";
        }
        if (position == 2) {
            title = "Activity Feed";
        }
        if (position == 1) {
            title = "Account";
        }
        return title;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            Fragment fragment = new LoginActivity();
            return fragment;
        } else if (position == 1) {
            AccountActivity fragment = new AccountActivity();
            return fragment;
        } else if (position == 2) {
            MatchesOverviewActivity fragment = new MatchesOverviewActivity();
            return fragment;
        }

        return null;
    }

   // @Override
   // public int getItemPosition(Object object) {
   //     return POSITION_NONE;
   // }
}

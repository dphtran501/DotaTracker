package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Helper class to represent each page in MatchDetailsActivity's ViewPager as a Fragment that's
 * persistently kept in the fragment manager as long as the user can return to the page.
 *
 * @author Derek Tran
 * @version 1.0
 * @since December 1, 2017
 */

public class MatchDetailsActivityPagerAdapter extends FragmentPagerAdapter
{
    // Will store selected match
    private Bundle fragmentBundle;

    public MatchDetailsActivityPagerAdapter(FragmentManager fm, Bundle data)
    {
        super(fm);
        fragmentBundle = data;
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
        switch (position)
        {
            case 0:
                title = "Radiant";
                break;
            case 1:
                title = "Versus";
                break;
            case 2:
                title = "Dire";
                break;
        }
        return title;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position The position of the tab item associated with the Fragment.
     */
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new RadiantMatchDetailsFragment();
                break;
            case 1:
                fragment = new VersusMatchDetailsFragment();
                break;
            case 2:
                fragment = new DireMatchDetailsFragment();
                break;
        }

        // Pass data (Match) to fragment
        fragment.setArguments(fragmentBundle);

        return fragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() { return 3; }
}

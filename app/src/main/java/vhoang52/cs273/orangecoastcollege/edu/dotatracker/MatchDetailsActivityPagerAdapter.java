package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

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
    public MatchDetailsActivityPagerAdapter(FragmentManager fm) { super(fm); }

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
                fragment = new VersusMatchDetailsFragmet();
                break;
            case 2:
                fragment = new DireMatchDetailsFragment();
                break;
        }

        return fragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() { return 3; }
}

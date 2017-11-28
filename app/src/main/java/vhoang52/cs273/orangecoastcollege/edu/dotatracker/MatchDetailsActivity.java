package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity display the match statistics of a single match, specifically the match selected
 * in <code>MatchesOverviewActivity</code>. The user has the ability to view the statistics of their
 * team, the statistics of their opponent team, and a comparison of each team's overall statistics.
 * In addition, the score and duration of the match will be displayed.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 22, 2017
 */
public class MatchDetailsActivity extends AppCompatActivity
{
    private TabLayout matchDetailTabLayout;
    private ViewPager matchDetailViewPager;
    // TODO: adapter class for viewpager

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
    }
}

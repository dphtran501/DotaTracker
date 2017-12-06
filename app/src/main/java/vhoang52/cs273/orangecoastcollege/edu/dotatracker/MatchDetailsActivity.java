package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
    // Selected match from MatchesOverviewActivity
    Match selectedMatch;
    // Scoreboard widgets
    private TextView radiantScoreTextView;
    private TextView direScoreTextView;
    private TextView durationTextView;
    private TextView lastPlayedTextView;
    // Tab Layout and ViewPager widget
    private TabLayout matchDetailTabLayout;
    private ViewPager matchDetailViewPager;
    private MatchDetailsActivityPagerAdapter matchDetailsActivityPagerAdapter;

    /**
     * Initializes <code>MatchDetailsActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        // Retrieve Match passed by MatchesOverviewActivity
        selectedMatch = getIntent().getExtras().getParcelable("SelectedMatch");

        // Set up and connect to scoreboard widgets
        radiantScoreTextView = (TextView) findViewById(R.id.matchDetailRadiantScoreTextView);
        direScoreTextView = (TextView) findViewById(R.id.matchDetailDireScoreTextView);
        durationTextView = (TextView) findViewById(R.id.matchDetailDurationTextView);
        lastPlayedTextView = (TextView) findViewById(R.id.matchDetailLastPlayedTextView);
        setScoreBoard();

        // Set up and connect to ViewPager
        matchDetailViewPager = (ViewPager) findViewById(R.id.matchDetailViewPager);
        // Pass selected match to pager adapter
        Bundle bundle = new Bundle();
        bundle.putParcelable("Match", selectedMatch);
        matchDetailsActivityPagerAdapter = new MatchDetailsActivityPagerAdapter(getSupportFragmentManager(), bundle);
        matchDetailViewPager.setAdapter(matchDetailsActivityPagerAdapter);

        // Set up and connect TabLayout to ViewPager
        matchDetailTabLayout = (TabLayout) findViewById(R.id.matchDetailTabLayout);
        matchDetailTabLayout.setupWithViewPager(matchDetailViewPager);
        // Show middle tab ("Versus") on activity creation
        matchDetailViewPager.setCurrentItem(1);
    }

    private void setScoreBoard()
    {
        // Set scores
        if (selectedMatch.isRadiantWin())
        {
            radiantScoreTextView.setTextColor(Color.GREEN);
            direScoreTextView.setTextColor(Color.RED);
        }
        else
        {
            radiantScoreTextView.setTextColor(Color.RED);
            direScoreTextView.setTextColor(Color.GREEN);
        }

        radiantScoreTextView.setText(String.valueOf(selectedMatch.getRadiantScore()));
        direScoreTextView.setText(String.valueOf(selectedMatch.getDireScore()));

        // Set duration and last played
        durationTextView.setText(durationToString(selectedMatch.getDuration()));
        lastPlayedTextView.setText(getTimeSinceLastPlay(selectedMatch.getStartTime()));
    }

    // Convert duration values (in seconds) to String for TextViews (e.g. 1230 to 20:30)
    @NonNull
    private String durationToString(int duration)
    {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }

    // Get length of time since match start as a String
    private String getTimeSinceLastPlay(long matchStartTime)
    {
        String lastPlay = null;

        long secondsSinceLastPlay = System.currentTimeMillis() / 1000L - matchStartTime;

        if (secondsSinceLastPlay / (12L * 30L * 24L * 60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (12L * 30L * 24L * 60L * 60L) + " years";
        else if (secondsSinceLastPlay / (30L * 24L * 60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (30L * 24L * 60L * 60L) + " months";
        else if (secondsSinceLastPlay / (24L * 60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (24L * 60L * 60L) + " days";
        else if (secondsSinceLastPlay / (60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (60L * 60L) + " hours";
        else if (secondsSinceLastPlay / 60L >= 1L)
            lastPlay = secondsSinceLastPlay / 60L + " minutes";
        else lastPlay = secondsSinceLastPlay + " seconds";

        return lastPlay;
    }
}

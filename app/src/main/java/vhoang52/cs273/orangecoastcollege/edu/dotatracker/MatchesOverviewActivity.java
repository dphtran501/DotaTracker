package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * This activity displays a user's game profile as well as his overall match statistics over a
 * certain number of matches. The statistics include accumulated wins and losses, the overall winrate,
 * and the averages of certain in-game states over a certain number of matches, such as kills and
 * assists.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 27, 2017
 */
public class MatchesOverviewActivity extends AppCompatActivity
{
    // Database
    private DBHelper db;
    // Player profile widgets
    private ImageView playerImageView;
    private Uri playerImageURI;
    private TextView playerNameTextView;
    // Player stats widgets
    private TextView winsTextView;
    private TextView lossesTextView;
    private TextView winRateTextView;
    private TextView avgWinRateTextView;
    private TextView avgKillsTextView;
    private TextView avgDeathsTextView;
    private TextView avgAssistsTextView;
    private TextView avgGPMTextView;
    private TextView avgXPMTextView;
    private TextView avgLastHitsTextView;
    private TextView avgHeroDMGTextView;
    private TextView avgHeroHealTextView;
    private TextView avgTowerDMGTextView;
    private TextView avgDurationTextView;
    // Label widgets
    private TextView averagesLabelTextView;
    private TextView recentMatchesTextView;
    // ListView
    private List<Match> matchList;
    // TODO: adapter class instantiation
    private ListView matchListView;

    /**
     * Initializes <code>MatchesOverviewActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_overview);
    }
}

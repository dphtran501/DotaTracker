package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
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
    private MatchListAdapter matchListAdapter;
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

        // Connect to database
        db = new DBHelper(this);

        // Connect to player profile widgets
        playerImageView = (ImageView) findViewById(R.id.playerImageView);
        playerNameTextView = (TextView) findViewById(R.id.playerNameTextView);
        // Connect to player stats widgets
        winsTextView = (TextView) findViewById(R.id.winsTextView);
        lossesTextView = (TextView) findViewById(R.id.lossesTextView);
        winRateTextView = (TextView) findViewById(R.id.winRateTextView);
        avgWinRateTextView = (TextView) findViewById(R.id.avgWinRateTextView);
        avgKillsTextView = (TextView) findViewById(R.id.avgKillsTextView);
        avgDeathsTextView = (TextView) findViewById(R.id.avgDeathsTextView);
        avgAssistsTextView = (TextView) findViewById(R.id.avgAssistsTextView);
        avgGPMTextView = (TextView) findViewById(R.id.avgGPMTextView);
        avgXPMTextView = (TextView) findViewById(R.id.avgXPMTextView);
        avgLastHitsTextView = (TextView) findViewById(R.id.avgLastHitsTextView);
        avgHeroDMGTextView = (TextView) findViewById(R.id.avgHeroDMGTextView);
        avgHeroHealTextView = (TextView) findViewById(R.id.avgHeroHealTextView);
        avgTowerDMGTextView = (TextView) findViewById(R.id.avgTowerDMGTextView);
        avgDurationTextView = (TextView) findViewById(R.id.avgDurationTextView);
        // Connect to label widgets
        averagesLabelTextView = (TextView) findViewById(R.id.averagesLabelTextView);
        recentMatchesTextView = (TextView) findViewById(R.id.recentMatchesTextView);
        // Connect to ListView
        matchList = db.getAllMatches();
        matchListAdapter = new MatchListAdapter(this, R.layout.match_list_item, matchList);
        matchListView.setAdapter(matchListAdapter);

        // Set default player profile image
        playerImageURI = getURIFromResource(this, R.drawable.steam_icon);
        playerImageView.setImageURI(playerImageURI);

    }

    /**
     * Get URI to any resource type within an Android Studio project. Method is public static to
     * allow other classes to use it as a helper function.
     *
     * @param context The current context
     * @param resID   The resource identifier of the drawable
     * @return Uri to resource by given ID
     * @throws Resources.NotFoundException If the given ID does not exist.
     */
    public static Uri getURIFromResource(Context context, int resID) throws Resources.NotFoundException
    {
        Resources res = context.getResources();
        // Build a String in the form:
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(resID)
                + "/" + res.getResourceTypeName(resID) + "/" + res.getResourceEntryName(resID);
        // Parse to Uri object
        return Uri.parse(uri);
    }

}

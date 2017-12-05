package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
    // User and related matches lists
    private User user;
    private List<Long> matchIDList;
    private List<Long> recentMatchIDList;
    // TODO: Number of matches shown defaulted to 25 for now; allow choice of number later?
    private int numOfMatchesShown = 25; // Number of recent matches to show on screen

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
    private List<MatchPlayer> recentMatchStatsList;
    private List<Match> recentMatchList;
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
        db = DBHelper.getInstance(this);

        // TODO: Need to retrieve selected user in order to populate data properly
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
        matchIDList = db.getPlayerMatchIDs(user.getSteamId32());
        recentMatchIDList = matchIDList.subList(Math.max(matchIDList.size() - numOfMatchesShown, 0),
                matchIDList.size());
        recentMatchList = new ArrayList<>();
        recentMatchStatsList = new ArrayList<>();
        for (Long matchID : recentMatchIDList)
        {
            recentMatchList.add(getMatch(matchID));
            recentMatchStatsList.add(db.getMatchPlayer(matchID, user.getSteamId32()));
        }
        matchListAdapter = new MatchListAdapter(this, R.layout.match_list_item, recentMatchStatsList);
        matchListView = (ListView) findViewById(R.id.recentMatchesListView);
        matchListView.setAdapter(matchListAdapter);

        // Link widgets to stat data
        // TODO: Make functions to set image view
        // Set default player profile image
        /*
        playerImageURI = getURIFromResource(this, R.drawable.steam_icon);
        playerImageView.setImageURI(playerImageURI);
        */
        playerNameTextView.setText(user.getPersonaName());
        setOverallStatsWidgets();
        setAverageStatsWidgets();
        averagesLabelTextView.setText(getString(R.string.average_stats_label, numOfMatchesShown));
        recentMatchesTextView.setText(getString(R.string.recent_matches_label, numOfMatchesShown));

    }

    private void setOverallStatsWidgets()
    {
        // Get all user matches (not just recent)
        List<Match> matchList = new ArrayList<>();
        for (Long matchID : matchIDList) matchList.add(getMatch(matchID));

        // Find overall wins and losses
        int numOfWins = 0;
        int numOfLosses = 0;
        for (Match match : matchList)
        {
            int numOfMatchPlayers = match.getMatchPlayerList().size();
            boolean isUserFound = false;
            int i = 0;
            // Search for user in each match's list of players and check if they're part of winning team
            while(!isUserFound && i < numOfMatchPlayers)
            {
                MatchPlayer matchPlayer = match.getMatchPlayerList().get(i);
                if (matchPlayer.getAccountId() == user.getSteamId32())
                {
                    isUserFound = true;
                    if ((match.isRadiantWin() && !matchPlayer.isDire()) ||
                            (!match.isRadiantWin() && matchPlayer.isDire()))
                        numOfWins++;
                    else numOfLosses++;
                }
                else i++;
            }
        }

        // Set overall wins, losses, and winrate
        winsTextView.setText(statToString(numOfWins));
        lossesTextView.setText(statToString(numOfLosses));
        winRateTextView.setText(percentToString((double) numOfWins / (numOfWins + numOfLosses), 2));
    }

    private void setAverageStatsWidgets()
    {
        int totalWins = 0, totalLosses = 0, totalKills = 0, totalDeaths = 0, totalAssists = 0,
                totalGPM = 0, totalXPM = 0, totalLH = 0, totalHD = 0, totalHH = 0, totalTD = 0,
                totalDuration = 0;

        // Retrieve stat totals from all recent user matches
        for (Match match : recentMatchList)
        {
            totalDuration += match.getDuration();

            int numOfMatchPlayers = match.getMatchPlayerList().size();
            boolean isUserFound = false;
            int i = 0;
            // Search for user in each match's list of players
            while(!isUserFound && i < numOfMatchPlayers)
            {
                MatchPlayer matchPlayer = match.getMatchPlayerList().get(i);
                if (matchPlayer.getAccountId() == user.getSteamId32())
                {
                    isUserFound = true;
                    if ((match.isRadiantWin() && !matchPlayer.isDire()) ||
                            (!match.isRadiantWin() && matchPlayer.isDire()))
                        totalWins++;
                    else totalLosses++;

                    totalKills += matchPlayer.getKills();
                    totalDeaths += matchPlayer.getDeaths();
                    totalAssists += matchPlayer.getAssists();
                    totalGPM += matchPlayer.getGPM();
                    totalXPM += matchPlayer.getXPM();
                    totalLH += matchPlayer.getLastHits();
                    totalHD += matchPlayer.getHeroDamage();
                    totalHH += matchPlayer.getHeroHealing();
                    totalTD += matchPlayer.getTowerDamage();
                }
                else i++;
            }
        }

        // Calculate average stats and set textviews
        avgWinRateTextView.setText(percentToString((double) totalWins / (totalWins + totalLosses), 2));
        avgKillsTextView.setText(statToString(Math.round((float) totalKills / recentMatchList.size())));
        avgDeathsTextView.setText(statToString(Math.round((float) totalDeaths / recentMatchList.size())));
        avgAssistsTextView.setText(statToString(Math.round((float) totalAssists / recentMatchList.size())));
        avgGPMTextView.setText(statToString(Math.round((float) totalGPM / recentMatchList.size())));
        avgXPMTextView.setText(statToString(Math.round((float) totalXPM / recentMatchList.size())));
        avgLastHitsTextView.setText(statToString(Math.round((float) totalLH / recentMatchList.size())));
        avgHeroDMGTextView.setText(statToString(Math.round((float) totalHD / recentMatchList.size())));
        avgHeroHealTextView.setText(statToString(Math.round((float) totalHH / recentMatchList.size())));
        avgTowerDMGTextView.setText(statToString(Math.round((float) totalTD / recentMatchList.size())));
        avgDurationTextView.setText(durationToString(Math.round((float) totalDuration / recentMatchList.size())));

    }

    // Convert percent value to String for TextViews (e.g. 0.3158 to 31.58%)
    private String percentToString(double fractionValue, int maxFractionDigits)
    {
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(maxFractionDigits);
        return percent.format(fractionValue);
    }

    // Convert value to String for TextViews (e.g. 13400 to 13.4k)
    private String statToString(int statValue)
    {
        String textViewString = null;
        // Converts values over 1000 to shorter strings (e.g. 13400 to 13.4k)
        if (statValue >= 1000L)
        {
            DecimalFormat oneDP = new DecimalFormat("#.#");
            textViewString = oneDP.format((double) statValue / 1000) + "k";
        }
        else textViewString = String.valueOf(statValue);

        return textViewString;
    }

    // Convert duration values (in seconds) to String for TextViews (e.g. 1230 to 20:30)
    @NonNull
    private String durationToString(int duration)
    {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }

    // Retrieves the match with the specified match ID from the database. Unlike the DBHelper version,
    // this method also sets the match's list of players to the list of all players who played in the match.
    private Match getMatch(long matchID)
    {
        Match match = db.getMatch(matchID);
        match.setMatchPlayerList(db.getMatchPlayers(matchID));
        return match;
    }

    // TODO: Launcher function for MatchDetailsActivity

    // TODO: Update TextViews when new Matches added

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

package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
public class MatchesOverviewActivity extends Fragment implements UpdateableFragment {
    // User and related matches lists
    private User user;
    private List<Long> matchIDList;
    private List<Long> recentMatchIDList;
    // TODO: Number of matches shown defaulted to 25 for now; allow choice of number later?
    private int numOfMatchesShown = 25; // Number of recent matches to show on screen

    // Database
    private DBHelper db;
    private HTTPRequestService mRequestService;
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
     * Dummy method used to link instances of UpdateableFragment
     */
    @Override
    public void update() {
        if (!user.equals(mRequestService.getmCurrentUser())) {
            user = mRequestService.getmCurrentUser();
            generateView();
        }
    }

    // TODO: @derek: I didn't want to refactor your code too much. This is duplicated code so maybe you could cut down on it to reduce repetition - vincent
    private void generateView() {
        matchIDList = db.getPlayerMatchIDs(user.getSteamId32());
        recentMatchIDList = matchIDList.subList(Math.max(matchIDList.size() - numOfMatchesShown, 0),
                matchIDList.size());
        recentMatchList = new ArrayList<>();
        recentMatchStatsList = new ArrayList<>();
        for (Long matchID : recentMatchIDList) {
            recentMatchList.add(getMatch(matchID));
            recentMatchStatsList.add(db.getMatchPlayer(matchID, user.getSteamId32()));
        }

        matchListAdapter = new MatchListAdapter(getActivity(), R.layout.match_list_item, recentMatchStatsList);
        matchListView.setAdapter(matchListAdapter);
        matchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout selectedItem = (LinearLayout) view;
                Match selectedMatch = (Match) selectedItem.getTag(R.id.match_tag);

                Intent detailsIntent = new Intent(getActivity(), MatchDetailsActivity.class);
                detailsIntent.putExtra("SelectedMatch", selectedMatch);
                startActivity(detailsIntent);
            }
        });
        playerNameTextView.setText(user.getPersonaName());
        setOverallStatsWidgets();
        setAverageStatsWidgets();
        averagesLabelTextView.setText(getString(R.string.average_stats_label, numOfMatchesShown));
        recentMatchesTextView.setText(getString(R.string.recent_matches_label, numOfMatchesShown));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matches_overview, container, false);


        // Connect to database
        db = DBHelper.getInstance(getActivity());
        mRequestService = HTTPRequestService.getInstance();
        user = mRequestService.getmCurrentUser();

        // Connect to player profile widgets
        playerImageView = (ImageView) view.findViewById(R.id.playerImageView);
        playerNameTextView = (TextView) view.findViewById(R.id.playerNameTextView);
        // Connect to player stats widgets
        winsTextView = (TextView) view.findViewById(R.id.winsTextView);
        lossesTextView = (TextView) view.findViewById(R.id.lossesTextView);
        winRateTextView = (TextView) view.findViewById(R.id.winRateTextView);
        avgWinRateTextView = (TextView) view.findViewById(R.id.avgWinRateTextView);
        avgKillsTextView = (TextView) view.findViewById(R.id.avgKillsTextView);
        avgDeathsTextView = (TextView) view.findViewById(R.id.avgDeathsTextView);
        avgAssistsTextView = (TextView) view.findViewById(R.id.avgAssistsTextView);
        avgGPMTextView = (TextView) view.findViewById(R.id.avgGPMTextView);
        avgXPMTextView = (TextView) view.findViewById(R.id.avgXPMTextView);
        avgLastHitsTextView = (TextView) view.findViewById(R.id.avgLastHitsTextView);
        avgHeroDMGTextView = (TextView) view.findViewById(R.id.avgHeroDMGTextView);
        avgHeroHealTextView = (TextView) view.findViewById(R.id.avgHeroHealTextView);
        avgTowerDMGTextView = (TextView) view.findViewById(R.id.avgTowerDMGTextView);
        avgDurationTextView = (TextView) view.findViewById(R.id.avgDurationTextView);
        // Connect to label widgets
        averagesLabelTextView = (TextView) view.findViewById(R.id.averagesLabelTextView);
        recentMatchesTextView = (TextView) view.findViewById(R.id.recentMatchesTextView);

        // Connect to ListView
        matchIDList = db.getPlayerMatchIDs(user.getSteamId32());
        recentMatchIDList = matchIDList.subList(Math.max(matchIDList.size() - numOfMatchesShown, 0),
                matchIDList.size());
        recentMatchList = new ArrayList<>();
        recentMatchStatsList = new ArrayList<>();
        for (Long matchID : recentMatchIDList) {
            recentMatchList.add(getMatch(matchID));
            recentMatchStatsList.add(db.getMatchPlayer(matchID, user.getSteamId32()));
        }
        matchListAdapter = new MatchListAdapter(getActivity(), R.layout.match_list_item, recentMatchStatsList);
        matchListView = (ListView) view.findViewById(R.id.recentMatchesListView);
        matchListView.setAdapter(matchListAdapter);

        //Log.i("user count", String.valueOf(db.getAllUsers().size()));
        //Log.i("match count: ", String.valueOf(db.getAllMatches().size()));
        //Log.i("player count: ", String.valueOf(db.getAllMatchPlayers().size()));

        // List<Long> testList = db.getPlayerMatchIDs(114611);
        // for (Long i : testList)
        //     Log.i("Match ID: ", String.valueOf(i));
        // Log.i("Match ID count: ", String.valueOf(testList.size()));

        matchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout selectedItem = (LinearLayout) view;
                Match selectedMatch = (Match) selectedItem.getTag(R.id.match_tag);

                Intent detailsIntent = new Intent(getActivity(), MatchDetailsActivity.class);
                detailsIntent.putExtra("SelectedMatch", selectedMatch);
                startActivity(detailsIntent);
            }
        });

        // Link widgets to stat data
        // TODO: Make functions to set image view
        // Set default player profile image
        /*
        playerImageURI = getURIFromResource(this, R.drawable.steam_icon);
        playerImageView.setImageURI(playerImageURI);
        */
        if (HTTPRequestService.isNetworkAvailable(getActivity())) {
            HTTPRequestService.loadProfileImage(user.getAvatarUrl(), playerImageView);
        }
        playerNameTextView.setText(user.getPersonaName());
        setOverallStatsWidgets();
        setAverageStatsWidgets();
        averagesLabelTextView.setText(getString(R.string.average_stats_label, numOfMatchesShown));
        recentMatchesTextView.setText(getString(R.string.recent_matches_label, numOfMatchesShown));

        return view;
    }

    private void setOverallStatsWidgets() {
        // Get all user matches (not just recent)
        List<Match> matchList = new ArrayList<>();
        for (Long matchID : matchIDList) matchList.add(getMatch(matchID));

        // Find overall wins and losses
        int numOfWins = 0;
        int numOfLosses = 0;
        for (Match match : matchList) {
            int numOfMatchPlayers = match.getMatchPlayerList().size();
            boolean isUserFound = false;
            int i = 0;
            // Search for user in each match's list of players and check if they're part of winning team
            while (!isUserFound && i < numOfMatchPlayers) {
                MatchPlayer matchPlayer = match.getMatchPlayerList().get(i);
                if (matchPlayer.getAccountId() == user.getSteamId32()) {
                    isUserFound = true;
                    if ((match.isRadiantWin() && !matchPlayer.isDire()) ||
                            (!match.isRadiantWin() && matchPlayer.isDire()))
                        numOfWins++;
                    else numOfLosses++;
                } else i++;
            }
        }

        // Set overall wins, losses, and winrate
        winsTextView.setText(statToString(numOfWins));
        lossesTextView.setText(statToString(numOfLosses));
        if (numOfWins + numOfLosses > 0)
            winRateTextView.setText(percentToString((double) numOfWins / (numOfWins + numOfLosses), 2));
        else winRateTextView.setText(percentToString(0.0, 2));
    }

    private void setAverageStatsWidgets() {
        int totalWins = 0, totalLosses = 0, totalKills = 0, totalDeaths = 0, totalAssists = 0,
                totalGPM = 0, totalXPM = 0, totalLH = 0, totalHD = 0, totalHH = 0, totalTD = 0,
                totalDuration = 0;

        // Retrieve stat totals from all recent user matches
        for (Match match : recentMatchList) {
            totalDuration += match.getDuration();

            int numOfMatchPlayers = match.getMatchPlayerList().size();
            boolean isUserFound = false;
            int i = 0;
            // Search for user in each match's list of players
            while (!isUserFound && i < numOfMatchPlayers) {
                MatchPlayer matchPlayer = match.getMatchPlayerList().get(i);
                if (matchPlayer.getAccountId() == user.getSteamId32()) {
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
                } else i++;
            }
        }

        // Calculate average stats and set textviews
        if (totalWins + totalLosses > 0)
            avgWinRateTextView.setText(percentToString((double) totalWins / (totalWins + totalLosses), 2));
        else avgWinRateTextView.setText(percentToString(0.0, 2));
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
    private String percentToString(double fractionValue, int maxFractionDigits) {
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(maxFractionDigits);
        return percent.format(fractionValue);
    }

    // Convert value to String for TextViews (e.g. 13400 to 13.4k)
    private String statToString(int statValue) {
        String textViewString = null;
        // Converts values over 1000 to shorter strings (e.g. 13400 to 13.4k)
        if (statValue >= 1000L) {
            DecimalFormat oneDP = new DecimalFormat("#.#");
            textViewString = oneDP.format((double) statValue / 1000) + "k";
        } else textViewString = String.valueOf(statValue);

        return textViewString;
    }

    // Convert duration values (in seconds) to String for TextViews (e.g. 1230 to 20:30)
    @NonNull
    private String durationToString(int duration) {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }

    // Retrieves the match with the specified match ID from the database. Unlike the DBHelper version,
    // this method also sets the match's list of players to the list of all players who played in the match.
    private Match getMatch(long matchID) {
        Match match = db.getMatch(matchID);
        match.setMatchPlayerList(db.getMatchPlayers(matchID));
        return match;
    }

    /**
     * Launches <code>MatchDetailsActivity</code> showing information about the <code>Match</code>
     * that was clicked in the ListView.
     *
     * @param v The view that was called this method.
     */
//    public void viewMatchDetails(View v) {
//        if (v instanceof LinearLayout) {
//            LinearLayout selectedItem = (LinearLayout) v;
//            Match selectedMatch = (Match) selectedItem.getTag(R.id.match_tag);
//
//            Intent detailsIntent = new Intent(getActivity(), MatchDetailsActivity.class);
//            detailsIntent.putExtra("SelectedMatch", selectedMatch);
//            startActivity(detailsIntent);
//        }
//    }

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
    public static Uri getURIFromResource(Context context, int resID) throws Resources.NotFoundException {
        Resources res = context.getResources();
        // Build a String in the form:
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(resID)
                + "/" + res.getResourceTypeName(resID) + "/" + res.getResourceEntryName(resID);
        // Parse to Uri object
        return Uri.parse(uri);
    }

}

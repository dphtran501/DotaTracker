package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to provide custom adapter for the <code>Match</code> list.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 28, 2017
 */
public class MatchListAdapter extends ArrayAdapter<MatchPlayer>
{
    private Context mContext;
    private int mResourceID;
    private List<MatchPlayer> mMatchPlayerList = new ArrayList<>();

    private DBHelper db;

    /**
     * Instantiates a new <code>MatchListAdapter</code> given a context, resource ID, and a list of
     * <code>MatchPlayer</code>s with the same Steam account ID (all players represent same user).
     *
     * @param context       The context for which the adapter is being used (typically an activity).
     * @param resource      The resource ID (typically the layout file name)
     * @param matchPlayers  The list of <code>MatchPlayer</code>s with the same 32-bit Steam account ID,
     *                      which means all the <code>MatchPlayer</code>s in the list are the same
     *                      user playing in different matches.
     */
    public MatchListAdapter(@NonNull Context context, int resource, @NonNull List<MatchPlayer> matchPlayers)
    {
        super(context, resource, matchPlayers);
        mContext = context;
        mResourceID = resource;
        mMatchPlayerList = matchPlayers;

        db = DBHelper.getInstance(context);
    }

    /**
     * Gets the view associated with the layout.
     *
     * @param position    The position of the <code>MatchPlayer</code> object selected in the list.
     * @param convertView The converted view.
     * @param parent      The parent - ArrayAdapter.
     * @return The new view with all content set.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        // Inflates the list item layout for each item in the list
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Retrieve widgets of the list item
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = inflater.inflate(mResourceID, null);
            viewHolder = new ViewHolder();
            viewHolder.matchListItemLinearLayout = (LinearLayout) convertView.findViewById(R.id.matchListItemLinearLayout);
            viewHolder.matchListItemHeroImageView = (ImageView) convertView.findViewById(R.id.matchListItemHeroImageView);
            viewHolder.matchListItemHeroTextView = (TextView) convertView.findViewById(R.id.matchListHeroTextView);
            viewHolder.matchListItemTeamTextView = (TextView) convertView.findViewById(R.id.matchListItemTeamTextView);
            viewHolder.matchListItemResultTextView = (TextView) convertView.findViewById(R.id.matchListItemResultTextView);
            viewHolder.matchListItemDurationTextView = (TextView) convertView.findViewById(R.id.matchListItemDurationTextView);
            viewHolder.matchListItemLastPlayedTextView = (TextView) convertView.findViewById(R.id.matchListItemLastPlayedTextView);
            viewHolder.matchListItemKillsTextView = (TextView) convertView.findViewById(R.id.matchListItemKillsTextView);
            viewHolder.matchListItemDeathsTextView = (TextView) convertView.findViewById(R.id.matchListItemDeathsTextView);
            viewHolder.matchListItemAssistsTextView = (TextView) convertView.findViewById(R.id.matchListItemAssistsTextView);
            viewHolder.matchListItemKDAProgressBar = (ProgressBar) convertView.findViewById(R.id.matchListItemKDAProgressBar);
            convertView.setTag(R.id.viewholder_tag, viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag(R.id.viewholder_tag);

        // Retrieve match played by MatchPlayer
        MatchPlayer selectedPlayer = mMatchPlayerList.get(position);
        Match selectedMatch = getMatch(selectedPlayer.getMatchId());
        // Set widgets of list item based on selected item
        // TODO: Get hero image and name
        try
        {
            Hero selectedPlayerHero = Hero.getHeroFromID(mContext, selectedPlayer.getHeroId());
            AssetManager manager = mContext.getAssets();
            InputStream inputStream = manager.open(selectedPlayerHero.getFileName());
            Drawable heroImage = Drawable.createFromStream(inputStream, selectedPlayerHero.getHeroName());
            viewHolder.matchListItemHeroImageView.setImageDrawable(heroImage);
            viewHolder.matchListItemHeroTextView.setText(selectedPlayerHero.getHeroName());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        viewHolder.matchListItemTeamTextView.setText((selectedPlayer.isDire() ? "Dire" : "Radiant"));
        if ((selectedMatch.isRadiantWin() && !selectedPlayer.isDire())
                || (!selectedMatch.isRadiantWin() && selectedPlayer.isDire()))
        {
            viewHolder.matchListItemResultTextView.setTextColor(Color.GREEN);
            viewHolder.matchListItemResultTextView.setText(mContext.getString(R.string.wins_label));
        }
        else
        {
            viewHolder.matchListItemResultTextView.setTextColor(Color.RED);
            viewHolder.matchListItemResultTextView.setText(mContext.getString(R.string.loses_label));
        }
        viewHolder.matchListItemDurationTextView.setText(durationToString(selectedMatch.getDuration()));
        viewHolder.matchListItemLastPlayedTextView.setText(getTimeSinceLastPlay(selectedMatch.getStartTime()));
        viewHolder.matchListItemKillsTextView.setText(String.valueOf(selectedPlayer.getKills()));
        viewHolder.matchListItemDeathsTextView.setText(String.valueOf(selectedPlayer.getDeaths()));
        viewHolder.matchListItemAssistsTextView.setText(String.valueOf(selectedPlayer.getAssists()));
        viewHolder.matchListItemKDAProgressBar.setMax(selectedPlayer.getKills() + selectedPlayer.getDeaths()
                + selectedPlayer.getAssists());
        viewHolder.matchListItemKDAProgressBar.setProgress(selectedPlayer.getKills());
        viewHolder.matchListItemKDAProgressBar.setSecondaryProgress(selectedPlayer.getKills() + selectedPlayer.getDeaths());

        // Set tag of list item layout to selected match
        viewHolder.matchListItemLinearLayout.setTag(R.id.match_tag, selectedMatch);

        return convertView;
    }

    // Helper class to hold component views inside tag of Layout. Increases efficiency by
    // eliminating need to look up views repeatedly for each list item
    // See: http://android.amberfog.com/?p=296
    static class ViewHolder
    {
        LinearLayout matchListItemLinearLayout;
        ImageView matchListItemHeroImageView;
        TextView matchListItemHeroTextView;
        TextView matchListItemTeamTextView;
        TextView matchListItemResultTextView;
        TextView matchListItemDurationTextView;
        TextView matchListItemLastPlayedTextView;
        TextView matchListItemKillsTextView;
        TextView matchListItemDeathsTextView;
        TextView matchListItemAssistsTextView;
        ProgressBar matchListItemKDAProgressBar;
    }

    // Convert duration values (in seconds) to String for TextViews (e.g. 1230 to 20:30)
    @NonNull
    private String durationToString(int duration)
    {
        int minutes = duration / 60;
        int seconds = duration % 60;
        String minutesString = (minutes < 10) ? ("0" + minutes) : String.valueOf(minutes);
        String secondsString = (seconds < 10) ? ("0" + seconds) : String.valueOf(seconds);
        return minutesString + ":" + secondsString;
    }

    // Get length of time since match start as a String
    private String getTimeSinceLastPlay(long matchStartTime)
    {
        String lastPlay = null;

        long secondsSinceLastPlay = System.currentTimeMillis() / 1000L - matchStartTime;

        if (secondsSinceLastPlay / (12L * 30L * 24L * 60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (12L * 30L * 24L * 60L * 60L) + " y.a.";
        else if (secondsSinceLastPlay / (30L * 24L * 60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (30L * 24L * 60L * 60L) + " mo.a.";
        else if (secondsSinceLastPlay / (24L * 60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (24L * 60L * 60L) + " d.a.";
        else if (secondsSinceLastPlay / (60L * 60L) >= 1L)
            lastPlay = secondsSinceLastPlay / (60L * 60L) + " h.a.";
        else if (secondsSinceLastPlay / 60L >= 1L)
            lastPlay = secondsSinceLastPlay / 60L + " m.a.";
        else lastPlay = secondsSinceLastPlay + " s.a.";

        return lastPlay;
    }

    // Retrieves the match with the specified match ID from the database. Unlike the DBHelper version,
    // this method also sets the match's list of players to the list of all players who played in the match.
    private Match getMatch(long matchID)
    {
        Match match = db.getMatch(matchID);
        match.setMatchPlayerList(db.getMatchPlayers(matchID));
        return match;
    }
}

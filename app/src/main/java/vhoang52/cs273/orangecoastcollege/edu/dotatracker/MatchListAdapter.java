package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to provide custom adapter for the <code>Match</code> list.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 28, 2017
 */
public class MatchListAdapter extends ArrayAdapter<Match>
{
    private Context mContext;
    private int mResourceID;
    private List<Match> mMatchList = new ArrayList<>();

    /**
     * Instantiates a new <code>MatchListAdapter</code> given a context, resource ID, and a list of
     * <code>Match</code>es.
     *
     * @param context  The context for which the adapter is being used (typically an activity).
     * @param resource The resource ID (typically the layout file name)
     * @param matches  The list of <code>Match</code>es to display.
     */
    public MatchListAdapter(@NonNull Context context, int resource, @NonNull List<Match> matches)
    {
        super(context, resource, matches);
        mContext = context;
        mResourceID = resource;
        mMatchList = matches;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        // Inflates the list item layout for each item in the list
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceID, null);

        // Retrieve widgets of the list item
        LinearLayout matchListItemLinearLayout = (LinearLayout) view.findViewById(R.id.matchListItemLinearLayout);
        ImageView matchListItemHeroImageView = (ImageView) view.findViewById(R.id.matchListItemHeroImageView);
        TextView matchListItemTeamTextView = (TextView) view.findViewById(R.id.matchListItemTeamTextView);
        TextView matchListItemResultTextView = (TextView) view.findViewById(R.id.matchListItemResultTextView);
        TextView matchListItemDurationTextView = (TextView) view.findViewById(R.id.matchListItemDurationTextView);
        TextView matchListItemLastPlayedTextView = (TextView) view.findViewById(R.id.matchListItemLastPlayedTextView);
        TextView matchListItemKillsTextView = (TextView) view.findViewById(R.id.matchListItemKillsTextView);
        TextView matchListItemDeathsTextView = (TextView) view.findViewById(R.id.matchListItemDeathsTextView);
        TextView matchListItemAssistsTextView = (TextView) view.findViewById(R.id.matchListItemAssistsTextView);
        ProgressBar matchListItemKDAProgressBar = (ProgressBar) view.findViewById(R.id.matchListItemKDAProgressBar);

        // TODO: might need to use DBHelper here in order to get player stats (getMatchPlayer)
        // Set widgets of list item based on selected item
        Match selectedMatch = mMatchList.get(position);
        // TODO: Get hero image
        // TODO: Get Team
        // TODO: Get Result
        matchListItemDurationTextView.setText(selectedMatch.getDuration());
        // TODO: Get last played from start time
        // TODO: Get kills, deaths, and assists

        // Set tag of list item layout to selected match
        matchListItemLinearLayout.setTag(selectedMatch);

        return view;

    }
}

package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;

/**
 * This fragment displays the overall match statistics for Team Radiant and Team Dire, where the
 * match is specified in <code>MatchDetailsActivity</code>.
 *
 * @author Derek Tran
 * @version 1.0
 * @since December 1, 2017
 */

public class VersusMatchDetailsFragment extends Fragment
{
    /**
     * Initializes <code>VersusMatchDetailsFragment</code> and retrieves <code>Match</code> passed
     * from <code>MatchDetailsActivity</code>.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Retrieve match from MatchDetailsActivity
        Match match = getArguments().getParcelable("Match");
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_match_versus, container, false);
        return view;

        // TODO: Link to widgets and populate fields
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
}

package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private Match match;
    private int radiantTotalLevel = 0, direTotalLevel = 0;
    private int radiantTotalKills = 0, direTotalKills = 0;
    private int radiantTotalDeaths = 0, direTotalDeaths = 0;
    private int radiantTotalAssists = 0, direTotalAssists = 0;
    private int radiantTotalGPM = 0, direTotalGPM = 0;
    private int radiantTotalXPM = 0, direTotalXPM = 0;
    private int radiantTotalLH = 0, direTotalLH = 0;
    private int radiantTotalDN = 0, direTotalDN = 0;
    private int radiantTotalHD = 0, direTotalHD = 0;
    private int radiantTotalHH = 0, direTotalHH = 0;
    private int radiantTotalTD = 0, direTotalTD = 0;
    private int radiantTotalG = 0, direTotalG = 0;

    private TextView radiantLevelTextView, direLevelTextView;
    private TextView radiantKillsTextView, direKillsTextView;
    private TextView radiantDeathsTextView, direDeathsTextView;
    private TextView radiantAssistsTextView, direAssistsTextView;
    private TextView radiantGPMTextView, direGPMTextView;
    private TextView radiantXPMTextView, direXPMTextView;
    private TextView radiantLHTextView, direLHTextView;
    private TextView radiantDNTextView, direDNTextView;
    private TextView radiantHDTextView, direHDTextView;
    private TextView radiantHHTextView, direHHTextView;
    private TextView radiantTDTextView, direTDTextView;
    private TextView radiantGTextView, direGTextView;

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
        match = getArguments().getParcelable("Match");

        // Retrieve total stats for each team
        for (MatchPlayer mp : match.getMatchPlayerList())
        {
            if (!mp.isDire())
            {
                radiantTotalLevel += mp.getLevel();
                radiantTotalKills += mp.getKills();
                radiantTotalDeaths += mp.getDeaths();
                radiantTotalAssists += mp.getAssists();
                radiantTotalGPM += mp.getGPM();
                radiantTotalXPM += mp.getXPM();
                radiantTotalLH += mp.getLastHits();
                radiantTotalDN += mp.getDenies();
                radiantTotalHD += mp.getHeroDamage();
                radiantTotalHH += mp.getHeroHealing();
                radiantTotalTD += mp.getTowerDamage();
                radiantTotalG += mp.getGold();
            }
            else
            {
                direTotalLevel += mp.getLevel();
                direTotalKills += mp.getKills();
                direTotalDeaths += mp.getDeaths();
                direTotalAssists += mp.getAssists();
                direTotalGPM += mp.getGPM();
                direTotalXPM += mp.getXPM();
                direTotalLH += mp.getLastHits();
                direTotalDN += mp.getDenies();
                direTotalHD += mp.getHeroDamage();
                direTotalHH += mp.getHeroHealing();
                direTotalTD += mp.getTowerDamage();
                direTotalG += mp.getGold();
            }
        }

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

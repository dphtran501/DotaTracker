package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment displays the single-match statistics of each player in Team Radiant, where the
 * match is specified in <code>MatchDetailsActivity</code>.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 28, 2017
 */
public class RadiantMatchDetailsFragment extends Fragment
{

    private Match match;
    private List<MatchPlayer> radiantPlayers;

    ImageView player1ImageView, player2ImageView, player3ImageView, player4ImageView, player5ImageView;
    TextView player1UserNameTextView, player2UserNameTextView, player3UserNameTextView, player4UserNameTextView, player5UserNameTextView;
    TextView player1LevelTextView, player2LevelTextView, player3LevelTextView, player4LevelTextView, player5LevelTextView;
    TextView player1KillsTextView, player2KillsTextView, player3KillsTextView, player4KillsTextView, player5KillsTextView;
    TextView player1DeathsTextView, player2DeathsTextView, player3DeathsTextView, player4DeathsTextView, player5DeathsTextView;
    TextView player1AssistsTextView, player2AssistsTextView, player3AssistsTextView, player4AssistsTextView, player5AssistsTextView;
    TextView player1GPMTextView, player2GPMTextView, player3GPMTextView, player4GPMTextView, player5GPMTextView;
    TextView player1XPMTextView, player2XPMTextView, player3XPMTextView, player4XPMTextView, player5XPMTextView;
    TextView player1LHTextView, player2LHTextView, player3LHTextView, player4LHTextView, player5LHTextView;
    TextView player1DNTextView, player2DNTextView, player3DNTextView, player4DNTextView, player5DNTextView;
    TextView player1HDTextView, player2HDTextView, player3HDTextView, player4HDTextView, player5HDTextView;
    TextView player1HHTextView, player2HHTextView, player3HHTextView, player4HHTextView, player5HHTextView;
    TextView player1TDTextView, player2TDTextView, player3TDTextView, player4TDTextView, player5TDTextView;
    TextView player1GTextView, player2GTextView, player3GTextView, player4GTextView, player5GTextView;
    ProgressBar player1KDABar, player2KDABar, player3KDABar, player4KDABar, player5KDABar;

    /**
     * Initializes <code>RadiantMatchDetailsFragment</code> and retrieves <code>Match</code> passed
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

        // Retrieve Radiant Players
        radiantPlayers = new ArrayList<>();
        for (MatchPlayer mp : match.getMatchPlayerList())
            if (!mp.isDire()) radiantPlayers.add(mp);


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
        View view = inflater.inflate(R.layout.fragment_match_team, container, false);
        // TODO: Link to widgets and populate fields

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Hero Image
        player1ImageView = (ImageView) getView().findViewById(R.id.player1HeroImageView);
        player2ImageView = (ImageView) getView().findViewById(R.id.player2HeroImageView);
        player3ImageView = (ImageView) getView().findViewById(R.id.player3HeroImageView);
        player4ImageView = (ImageView) getView().findViewById(R.id.player4HeroImageView);
        player5ImageView = (ImageView) getView().findViewById(R.id.player5HeroImageView);
        // User name
        player1UserNameTextView = (TextView) getView().findViewById(R.id.player1UserNameTextView);
        player2UserNameTextView = (TextView) getView().findViewById(R.id.player2UserNameTextView);
        player3UserNameTextView = (TextView) getView().findViewById(R.id.player3UserNameTextView);
        player4UserNameTextView = (TextView) getView().findViewById(R.id.player4UserNameTextView);
        player5UserNameTextView = (TextView) getView().findViewById(R.id.player5UserNameTextView);
        // Level
        player1LevelTextView = (TextView) getView().findViewById(R.id.player1LevelTextView);
        player2LevelTextView = (TextView) getView().findViewById(R.id.player2LevelTextView);
        player3LevelTextView = (TextView) getView().findViewById(R.id.player3LevelTextView);
        player4LevelTextView = (TextView) getView().findViewById(R.id.player4LevelTextView);
        player5LevelTextView = (TextView) getView().findViewById(R.id.player5LevelTextView);
        // K
        player1KillsTextView = (TextView) getView().findViewById(R.id.player1KillsTextView);
        player2KillsTextView = (TextView) getView().findViewById(R.id.player2KillsTextView);
        player3KillsTextView = (TextView) getView().findViewById(R.id.player3KillsTextView);
        player4KillsTextView = (TextView) getView().findViewById(R.id.player4KillsTextView);
        player5KillsTextView = (TextView) getView().findViewById(R.id.player5KillsTextView);
        // D
        player1DeathsTextView = (TextView) getView().findViewById(R.id.player1DeathsTextView);
        player2DeathsTextView = (TextView) getView().findViewById(R.id.player2DeathsTextView);
        player3DeathsTextView = (TextView) getView().findViewById(R.id.player3DeathsTextView);
        player4DeathsTextView = (TextView) getView().findViewById(R.id.player4DeathsTextView);
        player5DeathsTextView = (TextView) getView().findViewById(R.id.player5DeathsTextView);
        // A
        player1AssistsTextView = (TextView) getView().findViewById(R.id.player1AssistsTextView);
        player2AssistsTextView = (TextView) getView().findViewById(R.id.player2AssistsTextView);
        player3AssistsTextView = (TextView) getView().findViewById(R.id.player3AssistsTextView);
        player4AssistsTextView = (TextView) getView().findViewById(R.id.player4AssistsTextView);
        player5AssistsTextView = (TextView) getView().findViewById(R.id.player5AssistsTextView);
        // KDABar
        player1KDABar = (ProgressBar) getView().findViewById(R.id.player1KDAProgressBar);
        player2KDABar = (ProgressBar) getView().findViewById(R.id.player2KDAProgressBar);
        player3KDABar = (ProgressBar) getView().findViewById(R.id.player3KDAProgressBar);
        player4KDABar = (ProgressBar) getView().findViewById(R.id.player4KDAProgressBar);
        player5KDABar = (ProgressBar) getView().findViewById(R.id.player5KDAProgressBar);
        // GPM
        player1GPMTextView = (TextView) getView().findViewById(R.id.player1GPMTextView);
        player2GPMTextView = (TextView) getView().findViewById(R.id.player2GPMTextView);
        player3GPMTextView = (TextView) getView().findViewById(R.id.player3GPMTextView);
        player4GPMTextView = (TextView) getView().findViewById(R.id.player4GPMTextView);
        player5GPMTextView = (TextView) getView().findViewById(R.id.player5GPMTextView);
        // XPM
        player1XPMTextView = (TextView) getView().findViewById(R.id.player1XPMTextView);
        player2XPMTextView = (TextView) getView().findViewById(R.id.player2XPMTextView);
        player3XPMTextView = (TextView) getView().findViewById(R.id.player3XPMTextView);
        player4XPMTextView = (TextView) getView().findViewById(R.id.player4XPMTextView);
        player5XPMTextView = (TextView) getView().findViewById(R.id.player5XPMTextView);
        // LH
        player1LHTextView = (TextView) getView().findViewById(R.id.player1LastHitsTextView);
        player2LHTextView = (TextView) getView().findViewById(R.id.player2LastHitsTextView);
        player3LHTextView = (TextView) getView().findViewById(R.id.player3LastHitsTextView);
        player4LHTextView = (TextView) getView().findViewById(R.id.player4LastHitsTextView);
        player5LHTextView = (TextView) getView().findViewById(R.id.player5LastHitsTextView);
        // DN
        player1DNTextView = (TextView) getView().findViewById(R.id.player1DeniesTextView);
        player2DNTextView = (TextView) getView().findViewById(R.id.player2DeniesTextView);
        player3DNTextView = (TextView) getView().findViewById(R.id.player3DeniesTextView);
        player4DNTextView = (TextView) getView().findViewById(R.id.player4DeniesTextView);
        player5DNTextView = (TextView) getView().findViewById(R.id.player5DeniesTextView);
        // HD
        player1HDTextView = (TextView) getView().findViewById(R.id.player1HeroDMGTextView);
        player2HDTextView = (TextView) getView().findViewById(R.id.player2HeroDMGTextView);
        player3HDTextView = (TextView) getView().findViewById(R.id.player3HeroDMGTextView);
        player4HDTextView = (TextView) getView().findViewById(R.id.player4HeroDMGTextView);
        player5HDTextView = (TextView) getView().findViewById(R.id.player5HeroDMGTextView);
        // HH
        player1HHTextView = (TextView) getView().findViewById(R.id.player1HeroHealTextView);
        player2HHTextView = (TextView) getView().findViewById(R.id.player2HeroHealTextView);
        player3HHTextView = (TextView) getView().findViewById(R.id.player3HeroHealTextView);
        player4HHTextView = (TextView) getView().findViewById(R.id.player4HeroHealTextView);
        player5HHTextView = (TextView) getView().findViewById(R.id.player5HeroHealTextView);
        // TD
        player1TDTextView = (TextView) getView().findViewById(R.id.player1TowerDMGTextView);
        player2TDTextView = (TextView) getView().findViewById(R.id.player2TowerDMGTextView);
        player3TDTextView = (TextView) getView().findViewById(R.id.player3TowerDMGTextView);
        player4TDTextView = (TextView) getView().findViewById(R.id.player4TowerDMGTextView);
        player5TDTextView = (TextView) getView().findViewById(R.id.player5TowerDMGTextView);
        // G
        player1GTextView = (TextView) getView().findViewById(R.id.player1GoldTextView);
        player2GTextView = (TextView) getView().findViewById(R.id.player2GoldTextView);
        player3GTextView = (TextView) getView().findViewById(R.id.player3GoldTextView);
        player4GTextView = (TextView) getView().findViewById(R.id.player4GoldTextView);
        player5GTextView = (TextView) getView().findViewById(R.id.player5GoldTextView);
    }

    private void setPlayer1(MatchPlayer player1){}

    private void setPlayer2(MatchPlayer player2){}

    private void setPlayer3(MatchPlayer player3){}

    private void setPlayer4(MatchPlayer player4){}

    private void setPlayer5(MatchPlayer player5){}
}

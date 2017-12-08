package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This fragment displays the single-match statistics of each player in Team Dire, where the
 * match is specified in <code>MatchDetailsActivity</code>.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 30, 2017
 */

public class DireMatchDetailsFragment extends Fragment {
    private Match match;
    private List<MatchPlayer> direPlayers;
    private List<Hero> direHeroes;

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
     * Initializes <code>DireMatchDetailsFragment</code> and retrieves <code>Match</code> passed
     * from <code>MatchDetailsActivity</code>.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve match from MatchDetailsActivity
        match = getArguments().getParcelable("Match");

        // Retrieve Dire Players and Heroes
        direPlayers = new ArrayList<>();
        direHeroes = new ArrayList<>();
        for (MatchPlayer mp : match.getMatchPlayerList())
        {
            if (mp.isDire())
            {
                direPlayers.add(mp);
                try
                {
                    direHeroes.add(Hero.getHeroFromID(getContext(), mp.getHeroId()));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_team, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        if (direPlayers.size() >= 1 ) {
            setPlayer1(direPlayers.get(0), direHeroes.get(0));
        }
        if (direPlayers.size() >= 2) {
            setPlayer2(direPlayers.get(1), direHeroes.get(1));
        }
        if (direPlayers.size() >= 3) {
            setPlayer3(direPlayers.get(2), direHeroes.get(2));
        }
        if (direPlayers.size() >= 4) {
            setPlayer4(direPlayers.get(3), direHeroes.get(3));
        }
        if (direPlayers.size() >= 5) {
            setPlayer5(direPlayers.get(4), direHeroes.get(4));
        }
    }

    private void setPlayer1(MatchPlayer player1, Hero hero1) {
        AssetManager manager = getContext().getAssets();
        InputStream inputStream = null;
        try
        {
            inputStream = manager.open(hero1.getFileName());
            Drawable heroImage = Drawable.createFromStream(inputStream, hero1.getHeroName());
            player1ImageView.setImageDrawable(heroImage);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        player1UserNameTextView.setText(String.valueOf(player1.getAccountId()));
        player1LevelTextView.setText(statToString(player1.getLevel()));
        player1KillsTextView.setText(statToString(player1.getKills()));
        player1DeathsTextView.setText(statToString(player1.getDeaths()));
        player1AssistsTextView.setText(statToString(player1.getAssists()));
        player1GPMTextView.setText(statToString(player1.getGPM()));
        player1XPMTextView.setText(statToString(player1.getXPM()));
        player1LHTextView.setText(statToString(player1.getLastHits()));
        player1DNTextView.setText(statToString(player1.getDenies()));
        player1HDTextView.setText(statToString(player1.getHeroDamage()));
        player1HHTextView.setText(statToString(player1.getHeroHealing()));
        player1TDTextView.setText(statToString(player1.getTowerDamage()));
        player1GTextView.setText(statToString(player1.getGold()));
        player1KDABar.setMax(player1.getKills() + player1.getDeaths() + player1.getAssists());
        player1KDABar.setProgress(player1.getKills());
        player1KDABar.setSecondaryProgress(player1.getKills() + player1.getDeaths());
    }

    private void setPlayer2(MatchPlayer player2, Hero hero2) {
        AssetManager manager = getContext().getAssets();
        InputStream inputStream = null;
        try
        {
            inputStream = manager.open(hero2.getFileName());
            Drawable heroImage = Drawable.createFromStream(inputStream, hero2.getHeroName());
            player2ImageView.setImageDrawable(heroImage);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        player2UserNameTextView.setText(String.valueOf(player2.getAccountId()));
        player2LevelTextView.setText(statToString(player2.getLevel()));
        player2KillsTextView.setText(statToString(player2.getKills()));
        player2DeathsTextView.setText(statToString(player2.getDeaths()));
        player2AssistsTextView.setText(statToString(player2.getAssists()));
        player2GPMTextView.setText(statToString(player2.getGPM()));
        player2XPMTextView.setText(statToString(player2.getXPM()));
        player2LHTextView.setText(statToString(player2.getLastHits()));
        player2DNTextView.setText(statToString(player2.getDenies()));
        player2HDTextView.setText(statToString(player2.getHeroDamage()));
        player2HHTextView.setText(statToString(player2.getHeroHealing()));
        player2TDTextView.setText(statToString(player2.getTowerDamage()));
        player2GTextView.setText(statToString(player2.getGold()));
        player2KDABar.setMax(player2.getKills() + player2.getDeaths() + player2.getAssists());
        player2KDABar.setProgress(player2.getKills());
        player2KDABar.setSecondaryProgress(player2.getKills() + player2.getDeaths());
    }

    private void setPlayer3(MatchPlayer player3, Hero hero3) {
        AssetManager manager = getContext().getAssets();
        InputStream inputStream = null;
        try
        {
            inputStream = manager.open(hero3.getFileName());
            Drawable heroImage = Drawable.createFromStream(inputStream, hero3.getHeroName());
            player3ImageView.setImageDrawable(heroImage);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        player3UserNameTextView.setText(String.valueOf(player3.getAccountId()));
        player3LevelTextView.setText(statToString(player3.getLevel()));
        player3KillsTextView.setText(statToString(player3.getKills()));
        player3DeathsTextView.setText(statToString(player3.getDeaths()));
        player3AssistsTextView.setText(statToString(player3.getAssists()));
        player3GPMTextView.setText(statToString(player3.getGPM()));
        player3XPMTextView.setText(statToString(player3.getXPM()));
        player3LHTextView.setText(statToString(player3.getLastHits()));
        player3DNTextView.setText(statToString(player3.getDenies()));
        player3HDTextView.setText(statToString(player3.getHeroDamage()));
        player3HHTextView.setText(statToString(player3.getHeroHealing()));
        player3TDTextView.setText(statToString(player3.getTowerDamage()));
        player3GTextView.setText(statToString(player3.getGold()));
        player3KDABar.setMax(player3.getKills() + player3.getDeaths() + player3.getAssists());
        player3KDABar.setProgress(player3.getKills());
        player3KDABar.setSecondaryProgress(player3.getKills() + player3.getDeaths());
    }

    private void setPlayer4(MatchPlayer player4, Hero hero4) {
        AssetManager manager = getContext().getAssets();
        InputStream inputStream = null;
        try
        {
            inputStream = manager.open(hero4.getFileName());
            Drawable heroImage = Drawable.createFromStream(inputStream, hero4.getHeroName());
            player4ImageView.setImageDrawable(heroImage);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        player4UserNameTextView.setText(String.valueOf(player4.getAccountId()));
        player4LevelTextView.setText(statToString(player4.getLevel()));
        player4KillsTextView.setText(statToString(player4.getKills()));
        player4DeathsTextView.setText(statToString(player4.getDeaths()));
        player4AssistsTextView.setText(statToString(player4.getAssists()));
        player4GPMTextView.setText(statToString(player4.getGPM()));
        player4XPMTextView.setText(statToString(player4.getXPM()));
        player4LHTextView.setText(statToString(player4.getLastHits()));
        player4DNTextView.setText(statToString(player4.getDenies()));
        player4HDTextView.setText(statToString(player4.getHeroDamage()));
        player4HHTextView.setText(statToString(player4.getHeroHealing()));
        player4TDTextView.setText(statToString(player4.getTowerDamage()));
        player4GTextView.setText(statToString(player4.getGold()));
        player4KDABar.setMax(player4.getKills() + player4.getDeaths() + player4.getAssists());
        player4KDABar.setProgress(player4.getKills());
        player4KDABar.setSecondaryProgress(player4.getKills() + player4.getDeaths());
    }

    private void setPlayer5(MatchPlayer player5, Hero hero5) {
        AssetManager manager = getContext().getAssets();
        InputStream inputStream = null;
        try
        {
            inputStream = manager.open(hero5.getFileName());
            Drawable heroImage = Drawable.createFromStream(inputStream, hero5.getHeroName());
            player5ImageView.setImageDrawable(heroImage);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        player5UserNameTextView.setText(String.valueOf(player5.getAccountId()));
        player5LevelTextView.setText(statToString(player5.getLevel()));
        player5KillsTextView.setText(statToString(player5.getKills()));
        player5DeathsTextView.setText(statToString(player5.getDeaths()));
        player5AssistsTextView.setText(statToString(player5.getAssists()));
        player5GPMTextView.setText(statToString(player5.getGPM()));
        player5XPMTextView.setText(statToString(player5.getXPM()));
        player5LHTextView.setText(statToString(player5.getLastHits()));
        player5DNTextView.setText(statToString(player5.getDenies()));
        player5HDTextView.setText(statToString(player5.getHeroDamage()));
        player5HHTextView.setText(statToString(player5.getHeroHealing()));
        player5TDTextView.setText(statToString(player5.getTowerDamage()));
        player5GTextView.setText(statToString(player5.getGold()));
        player5KDABar.setMax(player5.getKills() + player5.getDeaths() + player5.getAssists());
        player5KDABar.setProgress(player5.getKills());
        player5KDABar.setSecondaryProgress(player5.getKills() + player5.getDeaths());
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

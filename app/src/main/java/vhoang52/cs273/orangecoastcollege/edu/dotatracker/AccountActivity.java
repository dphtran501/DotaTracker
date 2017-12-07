package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountActivity extends Fragment implements UpdateableFragment  {
    private static final String TAG = "AccountActivityFragment";
    HTTPRequestService mService;

    private User mUser;
    private DBHelper mDBHelper;

    private CircleImageView profilePicture;
    private TextView userName;
    private RingProgressBar winRingProgressBar;
    private TextView winPercentageTextView;

    private ListView mostPlayedHeroesListView;
    private MostPlayedHeroesListAdapter listAdapter;
    private List<Hero> mMostPlayedHeroes = new ArrayList<>();

    private double mWins = 0;
    private double mGamesPlayed = 0;

    /**
     * Called whenever the adapter is notified.
     * Updates to the fragment views will occur here
     */
    @Override
    public void update() {
        mUser = mService.getmCurrentUser();
        userName.setText(mUser.getPersonaName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        TODO: populate with actual heroes
        View view = inflater.inflate(R.layout.activity_seve_account, container, false);

        mService = HTTPRequestService.getInstance();
        mUser = mService.getmCurrentUser();
        Log.e(TAG, "!!!!!!!!! mUser->" + mUser.toString());
        mDBHelper = DBHelper.getInstance(getContext());

        profilePicture = view.findViewById(R.id.profilePicture);
        userName = view.findViewById(R.id.userName);

        winRingProgressBar = view.findViewById(R.id.winRingProgressBar);
        winPercentageTextView = view.findViewById(R.id.winPercentageTextView);

        mostPlayedHeroesListView = view.findViewById(R.id.mostPlayedHeroesListView);

        userName.setText(mUser.getPersonaName());



//        Create a list of all the games the user has played in
        List<MatchPlayer> matches = mDBHelper.getPlayerMatchStats(mUser.getSteamId32());

//        Find game statistics
        HashMap<Integer, int[]> heroFrequency = new HashMap<>();// <Hero, {times played, wins}>
        for (MatchPlayer matchPlayer : matches) {
            int heroID = matchPlayer.getHeroId();
            Log.i(TAG, "onCreateView: " + heroID);
            Match match = mDBHelper.getMatch(matchPlayer.getMatchId());
            int[] value = {1, 0};
//            Check to see if hero has already been played
            if (heroFrequency.containsKey(heroID)) {
                int[] existingValue = heroFrequency.get(heroID);
                value = existingValue;
//                Increase times played by 1
                ++value[0];
            }
//                Check to see if player won game
            if (!match.isRadiantWin() == matchPlayer.isDire()) {
//                Increase times won by 1
                ++value[1];
                ++mWins;
            }
            ++mGamesPlayed;
            heroFrequency.put(heroID, value);
        }

        NumberFormat df = DecimalFormat.getPercentInstance();
        df.setMaximumFractionDigits(1);
        double winPercent = 100 * mWins / mGamesPlayed, lossPercent = 100 - winPercent;
        winRingProgressBar.setProgress((int) winPercent);
        winPercentageTextView.setText(df.format(winPercent / 100));
        try {
            for (Integer integer : heroFrequency.keySet()) {
                mMostPlayedHeroes.add(Hero.getHeroFromID(getContext(), integer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listAdapter = new MostPlayedHeroesListAdapter(view.getContext(), R.layout.hero_list_item, mMostPlayedHeroes);
        listAdapter.setHash(heroFrequency);
        mostPlayedHeroesListView.setAdapter(listAdapter);

        return view;
    }
}

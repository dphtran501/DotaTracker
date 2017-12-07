package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountActivity extends Fragment {
    private static final String TAG = "AccountActivityFragment";
    HTTPRequestService mService;

    private User mUser;
    private DBHelper mDBHelper;

    private CircleImageView profilePicture;
    private TextView userName;
    private RingProgressBar winRingProgressBar;
    private RingProgressBar lossRingProgressBar;
    private TextView winPercentageTextView;

    private ListView mostPlayedHeroesListView;
    private MostPlayedHeroesListAdapter listAdapter;
    private List<Hero> mMMostPlayedHeroes = new ArrayList<>();

    private double mWins = 0;
    private double mGamesPlayed = 0;

    private ViewGroup mContainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TODO: populate with actual heroes
        View view = inflater.inflate(R.layout.activity_seve_account, container, false);

        mService = HTTPRequestService.getInstance();
        mUser = mService.getmCurrentUser();
        mDBHelper = DBHelper.getInstance(getContext());

        profilePicture = view.findViewById(R.id.profilePicture);
        userName = view.findViewById(R.id.userName);

        winRingProgressBar = view.findViewById(R.id.winRingProgressBar);
        lossRingProgressBar = view.findViewById(R.id.lossRingProgressBar);
        winPercentageTextView = view.findViewById(R.id.winPercentageTextView);

        mostPlayedHeroesListView = view.findViewById(R.id.mostPlayedHeroesListView);

        userName.setText(mUser.getPersonaName());

        listAdapter = new MostPlayedHeroesListAdapter(view.getContext(), R.layout.hero_list_item, mMMostPlayedHeroes);
        mostPlayedHeroesListView.setAdapter(listAdapter);

//        Create a list of all the games the user has played in
        List<MatchPlayer> matches = mDBHelper.getPlayerMatchStats(mUser.getSteamId32());

//        Find game statistics
        HashMap<Integer, int[]> heroFrequency = new HashMap<>();// <Hero, {times played, wins}>
        for (MatchPlayer matchPlayer : matches) {
            int heroID = matchPlayer.getHeroId();
            Match match = mDBHelper.getMatch(matchPlayer.getMatchId());
            int[] value = {1, 0};
//            Check to see if hero has already been played
            if (heroFrequency.containsKey(heroID)) {
                int[] existingValue = heroFrequency.get(heroID);
                value = existingValue;
//                Increase times played by 1
                ++value[0];
                ++mGamesPlayed;
            }
//                Check to see if player won game
            if (!match.isRadiantWin() == matchPlayer.isDire()) {
//                Increase times won by 1
                ++value[1];
                ++mWins;
            }
            heroFrequency.put(heroID, value);
        }

        double winPercent = 100 * mWins / mGamesPlayed, lossPercent = 100 - winPercent;
        winRingProgressBar.setProgress((int) winPercent);
        lossRingProgressBar.setProgress((int) lossPercent);
        winPercentageTextView.setText(getString(R.string.win_percentage_data, winPercent));
        return view;
    }
}

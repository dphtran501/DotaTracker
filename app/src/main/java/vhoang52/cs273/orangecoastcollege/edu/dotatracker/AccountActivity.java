package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class AccountActivity extends Fragment implements UpdateableFragment {
    private static final String TAG = "AccountActivityFragment";
    HTTPRequestService mService;

    private User mUser;
    private DBHelper mDBHelper;

    View mView;

    //private CircleImageView profilePicture;
    private ImageView profilePicture;
    private TextView userName;
    private RingProgressBar winRingProgressBar;
    private TextView winPercentageTextView;

    private LinearLayout mostPlayedHeroesListView;
    private MostPlayedHeroesListAdapter listAdapter;
    private List<Hero> mMostPlayedHeroes;

    private double mWins = 0;
    private double mGamesPlayed = 0;

    /**
     * Called whenever the adapter is notified.
     * Updates to the fragment views will occur here
     */
    @Override
    public void update() {

        if (!mUser.equals(mService.getmCurrentUser())) {
            mUser = mService.getmCurrentUser();

            userName.setText(mUser.getPersonaName());
            setProfilePicture();

            // TODO: handle ui updates here
            findGameStats();

        }
    }

    private void setProfilePicture() {
        if (HTTPRequestService.isNetworkAvailable(getActivity())) {
            HTTPRequestService.loadProfileImage(mUser.getAvatarUrl(), profilePicture, new HTTPRequestService.ProfileImageCallback() {
                @Override
                public void onSuccess(Drawable drawable) {
                    profilePicture.setImageDrawable(null);
                    profilePicture.setImageDrawable(drawable);
                    profilePicture.refreshDrawableState();
                }
            });
        } else {
            try {
                InputStream stream = getActivity().getAssets().open("steam_icon.png");
                Drawable drawable = Drawable.createFromStream(stream, null);
                profilePicture.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_seve_account, container, false);

        mService = HTTPRequestService.getInstance();
        mUser = mService.getmCurrentUser();
        mDBHelper = DBHelper.getInstance(getContext());

        profilePicture = mView.findViewById(R.id.profilePicture);
        setProfilePicture();

        userName = mView.findViewById(R.id.userName);

        winRingProgressBar = mView.findViewById(R.id.winRingProgressBar);
        winPercentageTextView = mView.findViewById(R.id.winPercentageTextView);

        mostPlayedHeroesListView = mView.findViewById(R.id.mostPlayedHeroesListView);

        userName.setText(mUser.getPersonaName());

        findGameStats();




        return mView;
    }

    private void findGameStats() {
//        Create a list of all the games the user has played in
        List<MatchPlayer> matches = mDBHelper.getPlayerMatchStats(mUser.getSteamId32());
//        If the adapter already existed clear the elements
        if(listAdapter != null) listAdapter.clear();

        mMostPlayedHeroes = new ArrayList<>();

        listAdapter = new MostPlayedHeroesListAdapter(mView.getContext(), R.layout.hero_list_item, mMostPlayedHeroes);

//        Find game statistics
        HashMap<Integer, int[]> heroFrequency = new HashMap<>();// <Hero, {times played, wins}>
        for (MatchPlayer matchPlayer : matches) {
            int heroID = matchPlayer.getHeroId();
            //Log.i(TAG, "onCreateView: " + heroID);
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

//        Fill out progressbar and textview inside
        NumberFormat df = DecimalFormat.getPercentInstance();
        df.setMaximumFractionDigits(1);
        double winPercent = 100 * mWins / mGamesPlayed;
        winRingProgressBar.setProgress((int) winPercent);
        winPercentageTextView.setText(" " + df.format(winPercent / 100));

//        Populate mMostPlayedHeroes with Heroes from heroFrequency
        try {
            for (Integer integer : heroFrequency.keySet()) {
                mMostPlayedHeroes.add(Hero.getHeroFromID(getContext(), integer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        listAdapter.setHash(heroFrequency);

        listAdapter.notifyDataSetChanged();


        Map<Hero, Double> sorted = new HashMap<>();
        for (int index = 0; index < mMostPlayedHeroes.size(); index++) {
            Hero hero = mMostPlayedHeroes.get(index);
            double winPercentage = heroFrequency.get(hero.getId())[0] / (double) heroFrequency.get(hero.getId())[1];
            sorted.put(hero, winPercentage);
        }

        for (Map.Entry<Hero,Double> heroDoubleEntry : entriesSortedByValues(sorted)) {

            mostPlayedHeroesListView.addView(listAdapter.getView(mMostPlayedHeroes.indexOf(heroDoubleEntry.getKey()), mView, mostPlayedHeroesListView));
        }
    }

    static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1; // Special fix to preserve items with equal values
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}

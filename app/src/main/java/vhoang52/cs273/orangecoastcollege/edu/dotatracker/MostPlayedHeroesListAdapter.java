package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class MostPlayedHeroesListAdapter extends ArrayAdapter {

    private static final String TAG = "MostPlayedHeroesListAdr";
    private Context mContext;
    private int mResourceID;
    private List<Hero> mHeroList = new ArrayList<>();
    private HashMap<Hero, int[]> mHeroHashMap;

    public MostPlayedHeroesListAdapter(@NonNull Context context, int resource, @NonNull List<Hero> heroes) {
        super(context, resource, heroes);
        mContext = context;
        mResourceID = resource;
        mHeroList = heroes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceID, null);

        ImageView heroListImageView = view.findViewById(R.id.heroListImageView);
        TextView heroNameTextView = view.findViewById(R.id.heroNameTextView);
        TextView numGamesWonTextView = view.findViewById(R.id.numGamesWonTextView);
        TextView percentGamesWonTextView = view.findViewById(R.id.percentGamesWonTextView);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        Hero selectedHero = mHeroList.get(position);

        AssetManager manager = mContext.getAssets();
        try {
            InputStream inputStream = manager.open(selectedHero.getFileName());
            Drawable heroImage = Drawable.createFromStream(inputStream, selectedHero.getHeroName());
            heroListImageView.setImageDrawable(heroImage);
        } catch (IOException e) {
            Log.e("DotaTracker", e.getMessage());
            heroListImageView.setImageResource(R.drawable.paulding_hero);
        }


        heroNameTextView.setText(selectedHero.getHeroName());

        //TODO: Add statistic data
        int[] arr = mHeroHashMap.get(selectedHero);
        int gamesPlayed = 100;
        int gamesWon = 100;
        if(arr == null) Log.e(TAG, "getView: NULL ARRAY" + selectedHero);
        else {
           gamesPlayed = arr[0];
           gamesWon = arr[1];
        }

        NumberFormat df = DecimalFormat.getPercentInstance();
        df.setMaximumFractionDigits(1);

        progressBar.setMax(100);
        progressBar.setProgress(0);

        double winPercent = ((double) gamesWon) / gamesPlayed;

        numGamesWonTextView.setText(mContext.getString(R.string.hero_games, gamesPlayed,(gamesPlayed == 1)? "" : "s"));
        percentGamesWonTextView.setText(df.format(winPercent));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress((int)(winPercent * 100), true);
//            Log.i(TAG, "getView: " + (int)(winPercent * 100));
        }
        else
            progressBar.setProgress((int) (winPercent * 100));

        return view;
    }

    public void setHash(HashMap<Integer, int[]> hash) {
        mHeroHashMap = new HashMap<>();
        for (Integer integer : hash.keySet()) {
            try {
                mHeroHashMap.put(Hero.getHeroFromID(mContext, integer), hash.get(integer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

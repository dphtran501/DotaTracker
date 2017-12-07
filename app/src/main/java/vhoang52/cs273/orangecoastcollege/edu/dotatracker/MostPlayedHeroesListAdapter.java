package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private HashMap<Hero, int[]> mHeroHashMap = new HashMap<>();

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

        numGamesWonTextView.setText(mContext.getString(R.string.hero_games, gamesPlayed));
        percentGamesWonTextView.setText(df.format(gamesWon/gamesPlayed));

        return view;
    }

    public void setHash(HashMap<Integer, int[]> hash) {
        for (Integer integer : hash.keySet()) {
            try {
                mHeroHashMap.put(Hero.getHeroFromID(mContext, integer), hash.get(integer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

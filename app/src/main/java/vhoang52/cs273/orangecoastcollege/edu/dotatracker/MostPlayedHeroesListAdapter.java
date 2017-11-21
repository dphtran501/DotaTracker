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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MostPlayedHeroesListAdapter extends ArrayAdapter {

    private Context mContext;
    private int mResourceID;
    private List<Hero> mHeroList = new ArrayList<>();

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

//        AssetManager manager = mContext.getAssets();
//        try {
//            InputStream inputStream = manager.open(selectedHero.getFileName());
//            Drawable heroImage = Drawable.createFromStream(inputStream, selectedHero.getHeroName());
//            heroListImageView.setImageDrawable(heroImage);
//        } catch (IOException e) {
//            Log.e("DotaTracker", e.getMessage());
//            heroListImageView.setImageResource(R.drawable.paulding_hero);
//        }

        heroListImageView.setImageResource(
                mContext.getResources().getIdentifier(selectedHero.getFileName(),
                        "drawable", mContext.getPackageName()));
        heroNameTextView.setText(selectedHero.getHeroName());

        //TODO: Add statistic data
        numGamesWonTextView.setText(mContext.getString(R.string.hero_games));
        percentGamesWonTextView.setText(mContext.getString(R.string.hero_win_percentage));

        return view;
    }
}

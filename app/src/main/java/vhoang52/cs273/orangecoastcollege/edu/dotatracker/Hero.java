package vhoang52.cs273.orangecoastcollege.edu.dotatracker;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Hero {
    private String mLocalizedName;
    private String mTokenName;
    private int mId;
    private String mFileName;

    public Hero(String mLocalizedName, String mTokenName, int mId) {
        this.mLocalizedName = mLocalizedName;
        this.mTokenName = mTokenName;
        this.mId = mId;
        this.mFileName = "npc_dota_" + mTokenName + ".png";
    }

    public static List<Hero> getAllHeroes(Context context) throws IOException {
        InputStream is = context.getAssets().open("heroes.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        Gson gson = new Gson();
        List<Hero> heroList = gson.fromJson(new String(buffer),
                new TypeToken<List<Hero>>() {
                }.getType());

        return heroList;
    }

    public static Hero getHeroFromID(Context context, int id) throws IOException {
        for (Hero hero : getAllHeroes(context)) {
            if(hero.getId() == id) return hero;
        }
        return null;
    }


    public String getHeroName() {
        return mLocalizedName;
    }

    public void setHeroName(String heroName) {
        mLocalizedName = heroName;
    }

    public int getId() {
        return mId;
    }

    public void setId(short Id) {
        mId = Id;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName() {
        mFileName = "npc_dota_" + mTokenName + ".png";
    }

    @Override
    public String toString() {
        return "Hero{" +
                "mLocalizedName='" + mLocalizedName + '\'' +
                ", mTokenName='" + mTokenName + '\'' +
                ", mId=" + mId +
                ", mFileName='" + mFileName + '\'' +
                '}';
    }
}

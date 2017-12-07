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
    private int mID;
    private String mFileName;

    public Hero(String mLocalizedName, String mTokenName, int mID) {
        this.mLocalizedName = mLocalizedName;
        this.mTokenName = mTokenName;
        this.mID = mID;
        this.mFileName = "npc_dota_" + mTokenName + ".png";
    }

    public static List<Hero> getAllHeroes(Context context) throws IOException {
        InputStream is = context.getAssets().open("heroes.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        Gson gson = new Gson();
        return gson.fromJson(new String(buffer),
                new TypeToken<List<Hero>>() {
                }.getType());
    }

    public String getHeroName() {
        return mLocalizedName;
    }

    public void setHeroName(String heroName) {
        mLocalizedName = heroName;
    }

    public int getID() {
        return mID;
    }

    public void setID(short ID) {
        mID = ID;
    }

    public String getFileName() {
        return mFileName;
    }
}

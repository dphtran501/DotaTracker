package vhoang52.cs273.orangecoastcollege.edu.dotatracker;


import android.content.Context;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Hero implements Comparable {
    private String mLocalizedName;
    private String mTokenName;
    private int mId;


    public Hero(String mLocalizedName, String mTokenName, int mId) {
        this.mLocalizedName = mLocalizedName;
        this.mTokenName = mTokenName;
        this.mId = mId;
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
        return mTokenName + ".png";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hero hero = (Hero) o;

        if (mId != hero.mId) return false;
        if (!mLocalizedName.equals(hero.mLocalizedName)) return false;
        return mTokenName.equals(hero.mTokenName);
    }

    @Override
    public int hashCode() {
        int result = mLocalizedName.hashCode();
        result = 31 * result + mTokenName.hashCode();
        result = 31 * result + mId;
        return result;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "mLocalizedName='" + mLocalizedName + '\'' +
                ", mTokenName='" + mTokenName + '\'' +
                ", mId=" + mId +
                ", mFileName='" + getFileName() + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 1;
    }
}

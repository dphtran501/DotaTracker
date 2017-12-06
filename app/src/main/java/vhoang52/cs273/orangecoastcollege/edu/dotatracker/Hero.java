package vhoang52.cs273.orangecoastcollege.edu.dotatracker;


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

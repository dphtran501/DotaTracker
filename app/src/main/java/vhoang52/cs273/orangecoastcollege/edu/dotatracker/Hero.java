package vhoang52.cs273.orangecoastcollege.edu.dotatracker;


public class Hero {
    private String mHeroName;
    private short mID;
    private String mFileName;

    public Hero(String heroName, short ID, String fileName) {
        mHeroName = heroName;
        mID = ID;
        mFileName = "hero_" + ID;
    }

    public String getHeroName() {
        return mHeroName;
    }

    public void setHeroName(String heroName) {
        mHeroName = heroName;
    }

    public short getID() {
        return mID;
    }

    public void setID(short ID) {
        mID = ID;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }
}

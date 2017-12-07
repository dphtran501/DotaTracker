package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents a player from a DOTA match.
 *
 * @author Derek Tran
 * @version 1.0
 * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">
 * WebAPI/GetMatchDetails</a>
 * @since December 1, 2017
 */

public class MatchPlayer implements Parcelable
{
    private long mMatchId;
    private long mAccountId;
    private int mPlayerSlot;
    private int mHeroId;
    private int[] mItems;
    private int mKills;
    private int mDeaths;
    private int mAssists;
    private int mLeaverStatus;      // 0 - NONE - finished match, no abandon.
    // 1 - DISCONNECTED - player DC, no abandon.
    // 2 - DISCONNECTED_TOO_LONG - player DC > 5min, abandoned.
    // 3 - ABANDONED - player DC, clicked leave, abandoned.
    // 4 - AFK - player AFK, abandoned.
    // 5 - NEVER_CONNECTED - player never connected, no abandon.
    // 6 - NEVER_CONNECTED_TOO_LONG - player took too long to connect, no abandon.
    private int mGold;
    private int mLastHits;
    private int mDenies;
    private int mGPM; // gold per minute
    private int mXPM; // xp per minute
    private int mGoldSpent;
    private int mHeroDamage;
    private int mTowerDamage;
    private int mHeroHealing;
    private int mLevel;
    //private MatchPlayerUnit mMatchPlayerUnit;

    /**
     * Instantiates a <code>MatchPlayer</code> object.
     *
     * @param matchId     The ID of the match that the player played in.
     * @param accountId   The ID of the player's 32-bit Steam account.
     * @param playerSlot  An 8-bit unsigned integer where the first bit represents the player's team
     *                    (0 for Radiant and 1 for Dire), and the final three bits represent the
     *                    player's position that that team.
     * @param heroId      The ID of the hero that the player played in the match.
     * @param kills       The number of kills by the player in the match.
     * @param deaths      The number of deaths of the player in the match.
     * @param assists     The number of assists by the player in the match.
     * @param gold        The amount of gold the player had by the end of the match.
     * @param lastHits    The number of last hits by the player in the match.
     * @param denies      The number of denies by the player in the match.
     * @param GPM         The amount of gold the player gained per minute in the match.
     * @param XPM         The amount of experience the player gained per minute in the match.
     * @param heroDamage  The amount of damage the player dealt to heroes in the match.
     * @param towerDamage The amount of damage the player dealt to towers in the match.
     * @param heroHealing The amount of health the player healed on heroes in the match.
     * @param level       The level of the player by the end of the match.
     */
    public MatchPlayer(long matchId, long accountId, int playerSlot, int heroId, int kills, int deaths,
                       int assists, int gold, int lastHits, int denies, int GPM, int XPM, int heroDamage,
                       int towerDamage, int heroHealing, int level)
    {
        mMatchId = matchId;
        mAccountId = accountId;
        mPlayerSlot = playerSlot;
        mHeroId = heroId;
        mKills = kills;
        mDeaths = deaths;
        mAssists = assists;
        mGold = gold;
        mLastHits = lastHits;
        mDenies = denies;
        mGPM = GPM;
        mXPM = XPM;
        mHeroDamage = heroDamage;
        mTowerDamage = towerDamage;
        mHeroHealing = heroHealing;
        mLevel = level;
    }

    /**
     * Instantiates a <code>MatchPlayer/code> from a parcel.
     *
     * @param parcel The package with all information for the <code>MatchPlayer</code>.
     */
    private MatchPlayer(Parcel parcel)
    {
        // ORDER MATTERS!
        mMatchId = parcel.readLong();
        mAccountId = parcel.readLong();
        mPlayerSlot = parcel.readInt();
        mHeroId = parcel.readInt();
        mKills = parcel.readInt();
        mDeaths = parcel.readInt();
        mAssists = parcel.readInt();
        mGold = parcel.readInt();
        mLastHits = parcel.readInt();
        mDenies = parcel.readInt();
        mGPM = parcel.readInt();
        mXPM = parcel.readInt();
        mHeroDamage = parcel.readInt();
        mTowerDamage = parcel.readInt();
        mHeroHealing = parcel.readInt();
        mLevel = parcel.readInt();
    }

    // In order to read a Parcel, we need a CREATOR (STATIC FIELD)
    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates
     * instances of the <code>MatchPlayer</code> class from a Parcel.
     */
    public static final Parcelable.Creator<MatchPlayer> CREATOR = new Creator<MatchPlayer>()
    {
        /**
         * This method is used with Intents to create new <code>MatchPlayer</code> objects.
         * @param parcel The package with all information for the <code>MatchPlayer</code>.
         * @return The new <code>MatchPlayer</code> object.
         */
        @Override
        public MatchPlayer createFromParcel(Parcel parcel)
        {
            return new MatchPlayer(parcel);
        }

        /**
         * This method is used with JSON to create an array of <code>MatchPlayer</code> objects.
         * @param size The size of the JSON array (how many <code>MatchPlayer</code> objects).
         * @return New array of <code>MatchPlayer</code> objects.
         */
        @Override
        public MatchPlayer[] newArray(int size)
        {
            return new MatchPlayer[size];
        }
    };

    /**
     * Gets the ID of the match that the player played in.
     *
     * @return The ID of the match that the player played in.
     */
    public long getMatchId()
    {
        return mMatchId;
    }

    /**
     * Gets the ID of the player's Steam account.
     *
     * @return The ID of the player's Steam account.
     */
    public long getAccountId()
    {
        return mAccountId;
    }

    /**
     * Gets the 8-bit unsigned integer where the first bit represents the player's team (0 for Radiant
     * and 1 for Dire), and the final three bits represent the player's position that that team.
     *
     * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails#Player_Slot">
     *     WebAPI/GetMatchDetails#Player_Slot</a>
     * @return The 8-bit unsigned integer representing the player's player slot.
     */
    public int getPlayerSlot()
    {
        return mPlayerSlot;
    }

    /**
     * Gets the ID of the hero that the player played in the match.
     *
     * @return The ID of the hero that the player played in the match.
     */
    public int getHeroId()
    {
        return mHeroId;
    }

    /**
     * Gets the number of kills by the player in the match.
     *
     * @return The number of kills by the player in the match.
     */
    public int getKills()
    {
        return mKills;
    }

    /**
     * Gets the number of deaths of the player in the match.
     *
     * @return The number of deaths of the player in the match.
     */
    public int getDeaths()
    {
        return mDeaths;
    }

    /**
     * Gets the number of assists by the player in the match.
     *
     * @return The number of assists by the player in the match.
     */
    public int getAssists()
    {
        return mAssists;
    }

    /**
     * Gets the amount of gold the player had by the end of the match.
     *
     * @return The amount of gold the player had by the end of the match.
     */
    public int getGold()
    {
        return mGold;
    }

    /**
     * Gets the number of last hits by the player in the match.
     *
     * @return The number of last hits by the player in the match.
     */
    public int getLastHits()
    {
        return mLastHits;
    }

    /**
     * Gets the number of denies by the player in the match.
     *
     * @return The number of denies by the player in the match.
     */
    public int getDenies()
    {
        return mDenies;
    }

    /**
     * Gets the amount of gold gained by the player per minute in the match.
     *
     * @return The amount of gold gained by the player per minute in the match.
     */
    public int getGPM()
    {
        return mGPM;
    }

    /**
     * Gets the amount of experience gained by the player per minute in the match.
     *
     * @return The amount of experience gained by the player per minute in the match.
     */
    public int getXPM()
    {
        return mXPM;
    }

    /**
     * Gets the amount of damage the player dealt to heroes in match.
     *
     * @return The amount of damage the player dealt to heroes in the match.
     */
    public int getHeroDamage()
    {
        return mHeroDamage;
    }

    /**
     * Gets the amount of damage the player dealt to towers in the match.
     *
     * @return The amount of damage the player dealt to towers in the match.
     */
    public int getTowerDamage()
    {
        return mTowerDamage;
    }

    /**
     * Gets the amount of health the player healed on heroes in the match.
     *
     * @return The amount of health the player healed on heroes in the match.
     */
    public int getHeroHealing()
    {
        return mHeroHealing;
    }

    /**
     * Gets the level of the player by the end of the match.
     *
     * @return The level of the player by the end of the match.
     */
    public int getLevel()
    {
        return mLevel;
    }

    /**
     * Checks whether the player is on team Dire or team Radiant.
     *
     * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails#Player_Slot">
     *     WebAPI/GetMatchDetails#Player_Slot</a>
     * @return True if the player is on team Dire; false if on team Radiant.
     */
    public boolean isDire()
    {
        // Shifts first bit to the right-end of byte (shifts by 7), and uses bitwise AND to check if
        // it's set (1) or clear (0)
        // (e.g. 11101110 >> 7 = 00000001; 00000001 & 00000001 = 00000001)
        byte firstBit = (byte) ((mPlayerSlot >> 7) & 1);
        // If the first bit is set, then the player is on Dire; if clear, the player is on Radiant
        return firstBit == 1;
    }

    /**
     * Gets the player's position in their team for the match (between 0 and 4, inclusive).
     *
     * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails#Player_Slot">
     *     WebAPI/GetMatchDetails#Player_Slot</a>
     * @return This player's position in their team for the match (between 0 and 4, inclusive).
     */
    public int getPosition()
    {
        // Uses bitwise AND to retrieve last 3 bits of mPlayerSlot, which represents the position
        // (e.g. 10001011 & 00000111 = 00000011)
        return mPlayerSlot & 7;
    }

    /**
     * Returns 0 if it's a standard parcel, else if sending files need to return file descriptors.
     *
     * @return 0
     */
    @Override
    public int describeContents()
    {
        return 0;
    }

    /**
     * Writes all the member variables of the class to the parcel. We specify the data types.
     *
     * @param parcel The package with details about the <code>MatchPlayer</code>.
     * @param i      Any custom flags (with files)
     */
    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mMatchId);
        parcel.writeLong(mAccountId);
        parcel.writeInt(mPlayerSlot);
        parcel.writeInt(mHeroId);
        parcel.writeInt(mKills);
        parcel.writeInt(mDeaths);
        parcel.writeInt(mAssists);
        parcel.writeInt(mGold);
        parcel.writeInt(mLastHits);
        parcel.writeInt(mDenies);
        parcel.writeInt(mGPM);
        parcel.writeInt(mXPM);
        parcel.writeInt(mHeroDamage);
        parcel.writeInt(mTowerDamage);
        parcel.writeInt(mHeroHealing);
        parcel.writeInt(mLevel);
    }

    @Override
    public String toString() {
        return "MatchPlayer{" +
                "mMatchId=" + mMatchId +
                ", mAccountId=" + mAccountId +
                ", mPlayerSlot=" + mPlayerSlot +
                ", mHeroId=" + mHeroId +
                ", mKills=" + mKills +
                ", mDeaths=" + mDeaths +
                ", mAssists=" + mAssists +
                ", mGold=" + mGold +
                ", mLastHits=" + mLastHits +
                ", mDenies=" + mDenies +
                ", mGPM=" + mGPM +
                ", mXPM=" + mXPM +
                ", mHeroDamage=" + mHeroDamage +
                ", mTowerDamage=" + mTowerDamage +
                ", mHeroHealing=" + mHeroHealing +
                ", mLevel=" + mLevel +
                '}';
    }
}

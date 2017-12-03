package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

/**
 * This class represents a player from a DOTA match.
 *
 * @author Derek Tran
 * @version 1.0
 * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">
 * WebAPI/GetMatchDetails</a>
 * @since December 1, 2017
 */

public class MatchPlayer
{
    private long mMatchId;
    private long mAccountId;
    private int mHeroId;
    //private int[] mItems;
    private int mKills;
    private int mDeaths;
    private int mAssists;
    //private int mLeaverStatus;      // 0 - NONE - finished match, no abandon.
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
    //private int mGoldSpent;
    private int mHeroDamage;
    private int mTowerDamage;
    private int mHeroHealing;
    private int mLevel;
    //private MatchPlayerUnit mMatchPlayerUnit;

    // TODO: player slot (needed to determine which team each player is in)

    /**
     * Instantiates a <code>MatchPlayer</code> object.
     *
     * @param matchId     The ID of the match that the player played in.
     * @param accountId   The ID of the player's 32-bit Steam account.
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
    public MatchPlayer(long matchId, long accountId, int heroId, int kills, int deaths, int assists,
                       int gold, int lastHits, int denies, int GPM, int XPM, int heroDamage,
                       int towerDamage, int heroHealing, int level)
    {
        mMatchId = matchId;
        mAccountId = accountId;
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
}

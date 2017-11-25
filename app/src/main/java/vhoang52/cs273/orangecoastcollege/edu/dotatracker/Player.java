package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

/**
 * This class represents a player from a single match.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 23, 2017
 */

public class Player
{
    private int mAccountID;
    // TODO: Need to see what's a good data type for receiving 8-bit unsigned integers
    private byte mPlayerSlot;
    // TODO: Check with Seve about hero ID relation table
    private int mHeroID;
    // TODO: List of Items? Make Item Class?
    private int mKills;
    private int mDeaths;
    private int mAssists;
    // TODO: Leaver status?
    private int mGold;
    private int mLastHits;
    private int mDenies;
    private int mGPM;       // Gold per minute
    private int mXPM;       // Experience per minute
    // TODO: Gold spent?
    private int mHeroDamage;
    private int mTowerDamage;
    private int mHeroHealing;
    private int mLevel;
    // TODO: Ability upgrades?
    // TODO: Additional units?


    /**
     * Instantiates a <code>Player</code> object given the player's match statistics from a single
     * match.
     *
     * @param accountID   The account ID of the player.
     * @param playerSlot  An 8-bit unsigned integer where the first bit represents the player's team
     *                    and the final three bits represent the player's position in the team.
     * @param heroID      The ID of the hero played by the player in the match.
     * @param kills       The number of kills by the player in the match.
     * @param deaths      The number of deaths of the player in the match.
     * @param assists     The number of assists by the player in the match.
     * @param gold        The amount of gold the player had by the end of the match.
     * @param lastHits    The number of last hits by the player in the match.
     * @param denies      The number of denies by the player in the match.
     * @param GPM         The average amount of gold the player gained per minute in the match.
     * @param XPM         The average amount of experience the player gained per minute in the match.
     * @param heroDamage  The amount of damage the player dealt to heroes in the match.
     * @param towerDamage The amount of damage the player dealt to towers in the match.
     * @param heroHealing The amount of health the player healed on heroes in the match.
     * @param level       The level of the player at the end of the match.
     */
    public Player(int accountID, byte playerSlot, int heroID, int kills, int deaths, int assists,
                  int gold, int lastHits, int denies, int GPM, int XPM, int heroDamage,
                  int towerDamage, int heroHealing, int level)
    {
        mAccountID = accountID;
        mPlayerSlot = playerSlot;
        mHeroID = heroID;
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
     * Gets the account ID of the player.
     *
     * @return The account ID of the player.
     */
    public int getAccountID() { return mAccountID; }

    /**
     * Gets the 8-bit unsigned integer where the first bit represents the player's team and the
     * final three bits represent the player's position in the team.
     *
     * @return The 8-bit unsigned integer of the player.
     */
    public byte getPlayerSlot() { return mPlayerSlot; }

    /**
     * Gets the ID of the hero played by the player in the match.
     *
     * @return The ID of the hero played by the player in the match.
     */
    public int getHeroID() { return mHeroID; }

    /**
     * Gets the number of kills by the player in the match.
     *
     * @return The number of kills by the player in the match.
     */
    public int getKills() { return mKills; }

    /**
     * Gets the number of deaths of the player in the match.
     *
     * @return The number of deaths of the player in the match.
     */
    public int getDeaths() { return mDeaths; }

    /**
     * Gets the number of assists by the player in the match.
     *
     * @return The number of assists by the player in the match.
     */
    public int getAssists() { return mAssists; }

    /**
     * Gets the amount of gold the player had by the end of the match.
     *
     * @return The amount of gold the player had by the end of the match.
     */
    public int getGold() { return mGold; }

    /**
     * Gets the number of last hits by the player in the match.
     *
     * @return The number of last hits by the player in the match.
     */
    public int getLastHits() { return mLastHits; }

    /**
     * Gets the number of denies by the player in the match.
     *
     * @return The number of denies by the player in the match.
     */
    public int getDenies() { return mDenies; }

    /**
     * Gets the average amount of gold the player gained per minute in the match.
     *
     * @return The average amount of gold the player gained per minute in the match.
     */
    public int getGPM() { return mGPM; }

    /**
     * Gets the average amount of experience the player gained per minute in the match.
     *
     * @return The average amount of experience the player gained per minute in the match.
     */
    public int getXPM() { return mXPM; }

    /**
     * Gets the amount of damage the player dealt to heroes in the match.
     *
     * @return The amount of damage the player dealt to heroes in the match.
     */
    public int getHeroDamage() { return mHeroDamage; }

    /**
     * Gets the amount of damage the player dealt to towers in the match.
     *
     * @return The amount of damage the player dealt to towers in the match.
     */
    public int getTowerDamage() { return mTowerDamage; }

    /**
     * Gets the amount of health the player healed on heroes in the match.
     *
     * @return The amount of health the player healed on heroes in the match.
     */
    public int getHeroHealing() { return mHeroHealing; }

    /**
     * Gets the level of the player by the end of the match.
     *
     * @return The level of the player by the end of the match.
     */
    public int getLevel() { return mLevel; }

    /**
     * Checks whether the player is on team Dire or team Radiant.
     *
     * @return True if the player is on team Dire; false if on team Radiant.
     */
    public boolean isDire()
    {
        // TODO: Change if data type for mPlayerSlot changes
        // Shifts first bit to right-end of byte, and uses bitwise AND to check if it's set (1) or
        // clear (0)
        byte firstBit = (byte) ((mPlayerSlot >> 7) & 1);
        // If the first bit is set, then the player is on Dire; if clear, the player is on Radiant
        return firstBit == 1;
    }

    // TODO: Function to retrieve player position (based on mPlayerSlot)
}


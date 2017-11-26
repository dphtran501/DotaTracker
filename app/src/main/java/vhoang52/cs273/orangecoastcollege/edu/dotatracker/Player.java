package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

/**
 * This class represents a player from a single match.
 *
 * @author Derek Tran
 * @version 1.0
 * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">
 * https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails</a>
 * @since November 23, 2017
 */

public class Player
{
    private int mAccountID;
    // TODO: Need to see what's a good data type for receiving 8-bit unsigned integers
    // TODO: Could store as short since we know we're retrieving 8-bit unsigned ints
    private int mPlayerSlot;
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
    public Player(int accountID, int playerSlot, int heroID, int kills, int deaths, int assists,
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
     * Gets the account ID of this player.
     *
     * @return The account ID of this player.
     */
    public int getAccountID() { return mAccountID; }

    /**
     * Gets the 8-bit unsigned integer where the first bit represents this player's team and the
     * final three bits represent this player's position in the team.
     *
     * @return The 8-bit unsigned integer of this player.
     */
    public int getPlayerSlot() { return mPlayerSlot; }

    /**
     * Gets the ID of the hero played by this player in the match.
     *
     * @return The ID of the hero played by this player in the match.
     */
    public int getHeroID() { return mHeroID; }

    /**
     * Gets the number of kills by this player in the match.
     *
     * @return The number of kills by this player in the match.
     */
    public int getKills() { return mKills; }

    /**
     * Gets the number of deaths of this player in the match.
     *
     * @return The number of deaths of this player in the match.
     */
    public int getDeaths() { return mDeaths; }

    /**
     * Gets the number of assists by this player in the match.
     *
     * @return The number of assists by this player in the match.
     */
    public int getAssists() { return mAssists; }

    /**
     * Gets the amount of gold this player had by the end of the match.
     *
     * @return The amount of gold this player had by the end of the match.
     */
    public int getGold() { return mGold; }

    /**
     * Gets the number of last hits by this player in the match.
     *
     * @return The number of last hits by this player in the match.
     */
    public int getLastHits() { return mLastHits; }

    /**
     * Gets the number of denies by this player in the match.
     *
     * @return The number of denies by this player in the match.
     */
    public int getDenies() { return mDenies; }

    /**
     * Gets the average amount of gold this player gained per minute in the match.
     *
     * @return The average amount of gold this player gained per minute in the match.
     */
    public int getGPM() { return mGPM; }

    /**
     * Gets the average amount of experience this player gained per minute in the match.
     *
     * @return The average amount of experience this player gained per minute in the match.
     */
    public int getXPM() { return mXPM; }

    /**
     * Gets the amount of damage this player dealt to heroes in the match.
     *
     * @return The amount of damage this player dealt to heroes in the match.
     */
    public int getHeroDamage() { return mHeroDamage; }

    /**
     * Gets the amount of damage this player dealt to towers in the match.
     *
     * @return The amount of damage this player dealt to towers in the match.
     */
    public int getTowerDamage() { return mTowerDamage; }

    /**
     * Gets the amount of health this player healed on heroes in the match.
     *
     * @return The amount of health this player healed on heroes in the match.
     */
    public int getHeroHealing() { return mHeroHealing; }

    /**
     * Gets the level of this player by the end of the match.
     *
     * @return The level of this player by the end of the match.
     */
    public int getLevel() { return mLevel; }

    /**
     * Checks whether this player is on team Dire or team Radiant.
     *
     * @return True if this player is on team Dire; false if on team Radiant.
     */
    public boolean isDire()
    {
        // TODO: Change if data type for mPlayerSlot changes
        // Shifts first bit to right-end of byte (shifts by 7), and uses bitwise AND to check if
        // it's set (1) or clear (0)
        // (e.g. 11101110 >> 7 = 00000001; 00000001 & 00000001 = 00000001)
        byte firstBit = (byte) ((mPlayerSlot >> 7) & 1);
        // If the first bit is set, then the player is on Dire; if clear, the player is on Radiant
        return firstBit == 1;
    }

    /**
     * Gets this player's position in their team for the match (between 0 and 4, inclusive).
     * @return This player's position in their team for the match (between 0 and 4, inclusive).
     */
    public int getPosition()
    {
        // Uses bitwise AND to retrieve last 3 bits of mPlayerSlot, which represents the position
        // (e.g. 10001011 & 00000111 = 00000011)
        return mPlayerSlot & 7;
    }
}


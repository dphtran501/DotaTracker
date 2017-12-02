package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single DOTA match played by the user.
 *
 * @author Derek Tran
 * @version 1.0
 * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">
 * WebAPI/GetMatchDetails</a>
 * @since December 1, 2017
 */

public class Match
{
    private long mMatchID;
    private long mMatchSeqNum;
    private List<MatchPlayer> mMatchPlayerList;
    private boolean mRadiantWin;
    private int mDuration;
    //private int mFirstBloodTime;
    //private int mLobbyType;
    //private int mNumPlayers;
    private int mGameMode;      // 0 - None
    // 1 - All Pick
    // 2 - Captain's Mode
    // 3 - Random Draft
    // 4 - Single Draft
    // 5 - All Random
    // 6 - Intro
    // 7 - Diretide
    // 8 - Reverse Captain's Mode
    // 9 - The Greeviling
    // 10 - Tutorial
    // 11 - Mid Only
    // 12 - Least Played
    // 13 - New Player Pool
    // 14 - Compendium Matchmaking
    // 15 - Co-op vs Bots
    // 16 - Captains Draft
    // 18 - Ability Draft
    // 20 - All Random Deathmatch
    // 21 - 1v1 Mid Only
    // 22 - Ranked Matchmaking


    /**
     * Instantiates a <code>Match</code> object.
     *
     * @param matchID               The ID of the match.
     * @param matchSeqNum           The sequence number representing the order in which the match was recorded.
     * @param matchPlayerList The list of players who played in the match.
     * @param radiantWin            Truth value of who won the match; true if Radiant won, false if Dire won.
     * @param duration              The length of the match in seconds.
     * @param gameMode              An integer representing the game mode.
     *                              See <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">WebAPI/GetMatchDetails</a>
     *                              for the list of integers and what game mode each integer represents.
     */
    public Match(long matchID, long matchSeqNum, List<MatchPlayer> matchPlayerList, boolean radiantWin, int duration, int gameMode)
    {
        mMatchID = matchID;
        mMatchSeqNum = matchSeqNum;
        mMatchPlayerList = new ArrayList<>(matchPlayerList);    // defensive copy
        mRadiantWin = radiantWin;
        mDuration = duration;
        mGameMode = gameMode;
    }

    /**
     * Gets the ID of the match.
     *
     * @return The ID of the match.
     */
    public long getMatchID()
    {
        return mMatchID;
    }

    /**
     * Gets the sequence number of the match.
     *
     * @return The sequence number of the match.
     */
    public long getMatchSeqNum()
    {
        return mMatchSeqNum;
    }

    /**
     * Gets the list of players who played in the match.
     *
     * @return The list of players who played in the match.
     */
    public List<MatchPlayer> getMatchPlayerList()
    {
        return mMatchPlayerList;
    }

    /**
     * Sets the list of players who played in the match/
     *
     * @param matchPlayerList The list of players who played in the match.
     */
    public void setMatchPlayerList(List<MatchPlayer> matchPlayerList)
    {
        mMatchPlayerList = new ArrayList<>(matchPlayerList);    // defensive copy
    }

    /**
     * Checks whether Radiant won the match.
     *
     * @return True if Radiant won the match, false if Dire won the match.
     */
    public boolean isRadiantWin()
    {
        return mRadiantWin;
    }

    /**
     * Gets the length of the match in seconds.
     *
     * @return The length of the match in seconds.
     */
    public int getDuration()
    {
        return mDuration;
    }

    /**
     * Gets the game mode of the match.
     *
     * @return An integer representing the game mode.
     * See <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">WebAPI/GetMatchDetails</a>
     * for the list of integers and what game mode each integer represents.
     */
    public int getGameMode()
    {
        return mGameMode;
    }
}

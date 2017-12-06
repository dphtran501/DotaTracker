package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.os.Parcel;
import android.os.Parcelable;

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

public class Match implements Parcelable
{
    private long mMatchID;
    private long mMatchSeqNum;
    private List<MatchPlayer> mMatchPlayerList;
    private boolean mRadiantWin;
    private long mStartTime;
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

    // TODO: Radiant and Dire scores?


    /**
     * Instantiates a <code>Match</code> object.
     *
     * @param matchID               The ID of the match.
     * @param matchSeqNum           The sequence number representing the order in which the match was recorded.
     * @param matchPlayerList       The list of players who played in the match.
     * @param radiantWin            Truth value of who won the match; true if Radiant won, false if Dire won.
     * @param startTime             The Unix timestamp of when the match started.
     * @param duration              The length of the match in seconds.
     * @param gameMode              An integer representing the game mode.
     *                              See <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">WebAPI/GetMatchDetails</a>
     *                              for the list of integers and what game mode each integer represents.
     */
    public Match(long matchID, long matchSeqNum, List<MatchPlayer> matchPlayerList, boolean radiantWin,
                 long startTime, int duration, int gameMode)
    {
        mMatchID = matchID;
        mMatchSeqNum = matchSeqNum;
        mMatchPlayerList = new ArrayList<>(matchPlayerList);    // defensive copy
        mRadiantWin = radiantWin;
        mStartTime = startTime;
        mDuration = duration;
        mGameMode = gameMode;
    }

    /**
     * Instantiates a <code>Match</code> object with no players.
     *
     * @param matchID               The ID of the match.
     * @param matchSeqNum           The sequence number representing the order in which the match was recorded.
     * @param radiantWin            Truth value of who won the match; true if Radiant won, false if Dire won.
     * @param startTime             The Unix timestamp of when the match started.
     * @param duration              The length of the match in seconds.
     * @param gameMode              An integer representing the game mode.
     *                              See <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">WebAPI/GetMatchDetails</a>
     *                              for the list of integers and what game mode each integer represents.
     */
    public Match(long matchID, long matchSeqNum, boolean radiantWin, long startTime, int duration, int gameMode)
    {
        mMatchID = matchID;
        mMatchSeqNum = matchSeqNum;
        mMatchPlayerList = new ArrayList<>();
        mRadiantWin = radiantWin;
        mStartTime = startTime;
        mDuration = duration;
        mGameMode = gameMode;
    }

    /**
     * Instantiates a <code>Match</code> from a parcel.
     *
     * @param parcel The package with all information for the <code>Match</code>.
     */
    private Match(Parcel parcel)
    {
        // ORDER MATTERS!
        mMatchID = parcel.readLong();
        mMatchSeqNum = parcel.readLong();
        mMatchPlayerList = new ArrayList<>();
        parcel.readTypedList(mMatchPlayerList, MatchPlayer.CREATOR);
        mRadiantWin = parcel.readInt() == 1;
        mStartTime = parcel.readLong();
        mDuration = parcel.readInt();
        mGameMode = parcel.readInt();
    }

    // In order to read a Parcel, we need a CREATOR (STATIC FIELD)
    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates
     * instances of the <code>Game</code> class from a Parcel.
     */
    public static final Parcelable.Creator<Match> CREATOR = new Creator<Match>()
    {
        /**
         * This method is used with Intents to create new <code>Match</code> objects.
         * @param parcel The package with all information for the <code>Match</code>.
         * @return The new <code>Match</code> object.
         */
        @Override
        public Match createFromParcel(Parcel parcel)
        {
            return new Match(parcel);
        }

        /**
         * This method is used with JSON to create an array of <code>Match</code> objects.
         * @param size The size of the JSON array (how many <code>Match</code> objects).
         * @return New array of <code>Match</code> objects.
         */
        @Override
        public Match[] newArray(int size)
        {
            return new Match[size];
        }
    };

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
        return new ArrayList<>(mMatchPlayerList);               // defensive copy
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
     * Gets the Unix timestamp of when the match started.
     *
     * @return The Unix timestamp of when the match started.
     */
    public long getStartTime()
    {
        return mStartTime;
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

    /**
     * Gets the score of team Radiant for this match.
     *
     * @return The score of team Radiant for this match.
     */
    public int getRadiantScore()
    {
        int score = 0;
        for (MatchPlayer matchPlayer : mMatchPlayerList)
            if (!matchPlayer.isDire()) score += matchPlayer.getKills();

        return score;
    }

    /**
     * Gets the score of team Dire for this match.
     *
     * @return The score of team Dire for this match.
     */
    public int getDireScore()
    {
        int score = 0;
        for (MatchPlayer matchPlayer : mMatchPlayerList)
            if (matchPlayer.isDire()) score += matchPlayer.getKills();

        return score;
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
     * @param parcel The package with details about the <code>Game</code>.
     * @param i      Any custom flags (with files)
     */
    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mMatchID);
        parcel.writeLong(mMatchSeqNum);
        parcel.writeTypedList(mMatchPlayerList);
        parcel.writeInt(mRadiantWin ? 1 : 0);
        parcel.writeLong(mStartTime);
        parcel.writeInt(mDuration);
        parcel.writeInt(mGameMode);
    }
}

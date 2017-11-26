package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

/**
 * This class represents a single DOTA match played by the user.
 *
 * @author Derek Tran
 * @version 1.0
 * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails">
 * https://wiki.teamfortress.com/wiki/WebAPI/GetMatchDetails</a>
 * @since November 23, 2017
 */

public class Match
{
    // TODO: check if ID is integer or string
    private int mID;
    private int mSequenceNumber;
    private boolean mIsRadiantWin;
    private int mDuration;              // seconds
    private int mStartTime;             // Unix timestamp
    // TODO: Lobby type?
    // TODO: Game mode?
    private int mRadiantScore;
    private int mDireScore;

    /**
     * Instantiates a <code>Match</code> object given the match's statistics.
     *
     * @param ID             The ID of the match.
     * @param sequenceNumber The sequence number representing the order in which the match was
     *                       recorded in the match history.
     * @param isRadiantWin   Truth value of whether Radiant won the match.
     * @param duration       The length of the match in seconds.
     * @param startTime      Unix timestamp of when the match began.
     * @param radiantScore   Radiant's score.
     * @param direScore      Dire's score.
     */
    public Match(int ID, int sequenceNumber, boolean isRadiantWin, int duration, int startTime,
                 int radiantScore, int direScore)
    {
        mID = ID;
        mSequenceNumber = sequenceNumber;
        mIsRadiantWin = isRadiantWin;
        mDuration = duration;
        mStartTime = startTime;
        mRadiantScore = radiantScore;
        mDireScore = direScore;
    }

    /**
     * Gets the ID of this match.
     *
     * @return The ID of this match.
     */
    public int getID() { return mID; }

    /**
     * Gets the sequence number representing the order in which the match was recorded in the match
     * history.
     *
     * @return The sequence number representing the order in which the match was recorded in the
     * match history.
     */
    public int getSequenceNumber() { return mSequenceNumber; }

    /**
     * Checks whether Radiant won this match. True if Radiant won; false if Dire won.
     *
     * @return True if Radiant won; false if Dire won.
     */
    public boolean isRadiantWin() { return mIsRadiantWin; }

    /**
     * Gets the duration of this match in seconds.
     *
     * @return The duration of this match in seconds.
     */
    public int getDuration() { return mDuration; }

    /**
     * Gets the Unix timestamp of when this match began.
     *
     * @return The Unix timestamp of when this match began.
     */
    public int getStartTime() { return mStartTime; }

    /**
     * Gets Radiant's score.
     *
     * @return Radiant's score.
     */
    public int getRadiantScore() { return mRadiantScore; }

    /**
     * Gets Dire's score.
     *
     * @return Dire's score.
     */
    public int getDireScore() { return mDireScore; }
}

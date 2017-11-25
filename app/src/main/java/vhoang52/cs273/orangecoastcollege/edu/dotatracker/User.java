package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the user signed in to this application.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 23, 2017
 */

public class User
{
    // TODO: long or int?
    private long mID;
    private String mUserName;
    // TODO: password?
    private Uri mImageURI;
    private List<Match> mMatchList;

    /**
     * Instantiates a <code>User</code> object given the user's ID, user name, URI of the user's
     * profile picture, and a list of <code>Match</code>es the user has played.
     * @param ID The ID of the user.
     * @param userName The user name of the user.
     * @param imageURI The URI of the user's profile picture.
     * @param matchList The list of <code>Match</code>es the user has played.
     */
    public User(long ID, String userName, Uri imageURI, List<Match> matchList)
    {
        mID = ID;
        mUserName = userName;
        mImageURI = imageURI;
        mMatchList = new ArrayList<>(matchList);    // defensive copy
    }

    /**
     * Gets the ID of this user.
     * @return The ID of this user.
     */
    public long getID() { return mID; }

    /**
     * Gets this user's user name.
     * @return This user's user name.
     */
    public String getUserName() { return mUserName; }

    /**
     * Sets this user's user name.
     * @param userName This user's user name.
     */
    public void setUserName(String userName) { mUserName = userName; }

    /**
     * Gets the URI of this user's profile picture.
     * @return The URI of this user's profile picture.
     */
    public Uri getImageURI() { return mImageURI; }

    /**
     * Sets the URI of this user's profile picture.
     * @param imageURI The URI of this user's profile picture.
     */
    public void setImageURI(Uri imageURI) { mImageURI = imageURI; }

    /**
     * Gets the list of <code>Match</code>es this user has played.
     * @return The list of <code>Match</code>es this user has played.
     */
    public List<Match> getMatchList() { return new ArrayList<>(mMatchList); }   // defensive copy

    /**
     * Sets the list of <code>Match</code>es this user has played.
     * @param matchList The list of <code>Match</code>es this user has played.
     */
    public void setMatchList(List<Match> matchList) { mMatchList = new ArrayList<>(matchList); }

    // TODO: functions to retrieve game statistics


}

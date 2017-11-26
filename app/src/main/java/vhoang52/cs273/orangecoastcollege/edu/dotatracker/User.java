package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.net.Uri;

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
    // TODO: accountID?
    private long mID;
    private String mUserName;
    // TODO: password?
    private Uri mImageURI;
    // TODO: use relationship table instead of matchList?

    /**
     * Instantiates a <code>User</code> object given the user's ID, user name, and URI of the user's
     * profile picture.
     *
     * @param ID        The ID of the user.
     * @param userName  The user name of the user.
     * @param imageURI  The URI of the user's profile picture.
     */
    public User(long ID, String userName, Uri imageURI)
    {
        mID = ID;
        mUserName = userName;
        mImageURI = imageURI;
    }

    /**
     * Gets the ID of this user.
     *
     * @return The ID of this user.
     */
    public long getID() { return mID; }

    /**
     * Gets this user's user name.
     *
     * @return This user's user name.
     */
    public String getUserName() { return mUserName; }

    /**
     * Sets this user's user name.
     *
     * @param userName This user's user name.
     */
    public void setUserName(String userName) { mUserName = userName; }

    /**
     * Gets the URI of this user's profile picture.
     *
     * @return The URI of this user's profile picture.
     */
    public Uri getImageURI() { return mImageURI; }

    /**
     * Sets the URI of this user's profile picture.
     *
     * @param imageURI The URI of this user's profile picture.
     */
    public void setImageURI(Uri imageURI) { mImageURI = imageURI; }

    // TODO: functions to retrieve game statistics


}

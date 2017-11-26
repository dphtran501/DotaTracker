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
    private long mAccountID;
    private String mUserName;
    // TODO: password?
    private Uri mImageURI;

    /**
     * Instantiates a <code>User</code> object given the user's account ID, user name, and URI of
     * the user's profile picture.
     *
     * @param accountID The account ID of the user.
     * @param userName  The user name of the user.
     * @param imageURI  The URI of the user's profile picture.
     */
    public User(long accountID, String userName, Uri imageURI)
    {
        mAccountID = accountID;
        mUserName = userName;
        mImageURI = imageURI;
    }

    /**
     * Gets the account ID of this user.
     *
     * @return The ID account of this user.
     */
    public long getAccountID() { return mAccountID; }

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

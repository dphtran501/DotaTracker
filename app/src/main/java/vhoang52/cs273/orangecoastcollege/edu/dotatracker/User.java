package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

/**
 * This class represents the user signed in to this application.
 *
 * @author Derek Tran
 * @version 1.0
 * @see <a href="https://wiki.teamfortress.com/wiki/WebAPI/GetPlayerSummaries">
 * WebAPI/GetPlayerSummaries</a>
 * @since December 1, 2017
 */

public class User
{
    private long mSteamId32;
    private int mPrivacy; // 1 - private; 2 - Friends only; 3 - Friends of Friends; 4 - Users Only; 5 - Public
    private int mProfileState; // 0 - profile not configured; 1 - user has configured profile
    private String mPersonaName;
    private long mLastLogOff;
    private String mProfileUrl;
    private String mAvatarUrl;

    /**
     * Instantiates a <code>User</code> object.
     *
     * @param steamId32    The user's 32-bit Steam ID.
     * @param privacy      The access setting of the profile, where 1 means it's private, 2 means it's
     *                     friends only, 3 means it allows access to friends of friends, 4 means it's
     *                     users only, and 5 means it's public.
     * @param profileState The state of the user's profile, where 0 means it's not configured and
     *                     1 means it has been configured.
     * @param personaName  The user's display name.
     * @param lastLogOff   The Unix timestamp of when the user was last online.
     * @param profileUrl   The URL to the user's Steam Community profile.
     * @param avatarUrl    The URL to the user's avatar image.
     */
    public User(long steamId32, int privacy, int profileState, String personaName, long lastLogOff, String profileUrl, String avatarUrl)
    {
        mSteamId32 = steamId32;
        mPrivacy = privacy;
        mProfileState = profileState;
        mPersonaName = personaName;
        mLastLogOff = lastLogOff;
        mProfileUrl = profileUrl;
        mAvatarUrl = avatarUrl;
    }

    /**
     * The user's 32-bit Steam ID.
     *
     * @return The user's 32-bit Steam ID.
     */
    public long getSteamId32()
    {
        return mSteamId32;
    }

    /**
     * The user's 64-bit Steam ID.
     *
     * @return The user's 64-bit Steam ID.
     */
    public long getSteamId64()
    {
        return mSteamId32 + 76561197960265728L;
    }

    /**
     * Gets the access setting of the user's profile.
     *
     * @return 1 if the profile is private, 2 if it's friends only, 3 if it allows access to
     * friends of friends, 4 if it's users only, or 5 if it's public.
     */
    public int getPrivacy()
    {
        return mPrivacy;
    }

    /**
     * Sets the access setting of the user's profile.
     *
     * @param privacy Integer describing the profile's access settings. 1 if the profile is private,
     *                2 if it's friends only, 3 if it allows access to friends of friends, 4 if it's
     *                users only, or 5 if it's public.
     */
    public void setPrivacy(int privacy)
    {
        mPrivacy = privacy;
    }

    /**
     * Gets the state of the user's profile.
     *
     * @return 0 if it's not configured or 1 if it has been configured.
     */
    public int getProfileState()
    {
        return mProfileState;
    }

    /**
     * Sets the state of the user's profile.
     *
     * @param profileState Integer describing the user's profile state. 0 if it's not configured or
     *                     1 if it has been configured.
     */
    public void setProfileState(int profileState)
    {
        mProfileState = profileState;
    }

    /**
     * Gets the user's display name.
     *
     * @return The user's display name.
     */
    public String getPersonaName()
    {
        return mPersonaName;
    }

    /**
     * Sets the user's display name.
     *
     * @param personaName The user's display name.
     */
    public void setPersonaName(String personaName)
    {
        mPersonaName = personaName;
    }

    /**
     * Gets the Unix timestamp of when the user was last online.
     *
     * @return The Unix timestamp of when the user was last online.
     */
    public long getLastLogOff()
    {
        return mLastLogOff;
    }

    /**
     * Sets the Unix timestamp of when the user was last online.
     *
     * @param lastLogOff The Unix timestamp of when the user was last online.
     */
    public void setLastLogOff(long lastLogOff)
    {
        mLastLogOff = lastLogOff;
    }

    /**
     * Gets the URL to the user's Steam Community profile.
     *
     * @return The URL to the user's Steam Community profile.
     */
    public String getProfileUrl()
    {
        return mProfileUrl;
    }

    /**
     * Sets the URL to the user's Steam Community profile.
     *
     * @param profileUrl The URL to the user's Steam Community profile.
     */
    public void setProfileUrl(String profileUrl)
    {
        mProfileUrl = profileUrl;
    }

    /**
     * Gets the URL to the user's avatar image.
     *
     * @return The URL to the user's avatar image.
     */
    public String getAvatarUrl()
    {
        return mAvatarUrl;
    }

    /**
     * Sets the URL to the user's avatar image.
     *
     * @param avatarUrl The URL to the user's avatar image.
     */
    public void setAvatarUrl(String avatarUrl)
    {
        mAvatarUrl = avatarUrl;
    }

    /**
     * Checks if this <code>User</code> is equal to another <code>User</code>.
     *
     * @param o The <code>User</code> to compare to.
     * @return True if the argument <code>User</code> is the same as this <code>User</code>, else false.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (mSteamId32 != user.mSteamId32) return false;
        if (mPrivacy != user.mPrivacy) return false;
        if (mProfileState != user.mProfileState) return false;
        if (mLastLogOff != user.mLastLogOff) return false;
        if (mPersonaName != null ? !mPersonaName.equals(user.mPersonaName) : user.mPersonaName != null)
            return false;
        if (mProfileUrl != null ? !mProfileUrl.equals(user.mProfileUrl) : user.mProfileUrl != null)
            return false;
        return mAvatarUrl != null ? mAvatarUrl.equals(user.mAvatarUrl) : user.mAvatarUrl == null;
    }

    /**
     * Generates a hash code for this <code>User</code>.
     *
     * @return The hash code for this <code>User</code>.
     */
    @Override
    public int hashCode()
    {
        int result = (int) (mSteamId32 ^ (mSteamId32 >>> 32));
        result = 31 * result + mPrivacy;
        result = 31 * result + mProfileState;
        result = 31 * result + (mPersonaName != null ? mPersonaName.hashCode() : 0);
        result = 31 * result + (int) (mLastLogOff ^ (mLastLogOff >>> 32));
        result = 31 * result + (mProfileUrl != null ? mProfileUrl.hashCode() : 0);
        result = 31 * result + (mAvatarUrl != null ? mAvatarUrl.hashCode() : 0);
        return result;
    }

    /**
     * Generates a String representation of this <code>User</code>.
     *
     * @return String representation of this <code>User</code>.
     */
    @Override
    public String toString()
    {
        return "User{" + "mSteamId32=" + mSteamId32 + ", mPrivacy=" + mPrivacy
                + ", mProfileState=" + mProfileState + ", mPersonaName='" + mPersonaName + '\''
                + ", mLastLogOff=" + mLastLogOff + ", mProfileUrl='" + mProfileUrl + '\''
                + ", mAvatarUrl='" + mAvatarUrl + '\'' + '}';
    }
}

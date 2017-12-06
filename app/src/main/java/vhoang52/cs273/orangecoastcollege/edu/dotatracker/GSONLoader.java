package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import com.google.gson.Gson;

/**
 * A class to unpack JSON files and load their information into the application's data models.
 *
 * @author Derek Tran
 * @version 1.0
 * @since December 2, 2017
 */
public class GSONLoader
{
    // TODO: Implementation of GSONLoader class

    private Gson gson = new Gson();

    public void getUserFromJson(String json)
    {
        User user = gson.fromJson(json, User.class);
        // TODO: Store user in database
    }

    public void getMatchFromJson(String json)
    {
        Match match = gson.fromJson(json, Match.class);
        // TODO: Store match and associated match players in database
        // TODO: Retrieving list of MatchPlayers from JSON?
    }

}

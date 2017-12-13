package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class to manage the SQLite database used to store College data.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 25, 2017
 */

public class DBHelper extends SQLiteOpenHelper
{
    private static DBHelper sInstance = null;

    // Database name and version
    private static final String DATABASE_NAME = "DotaTracker";
    private static final int DATABASE_VERSION = 1;

    // User table
    private static final String USERS_TABLE = "Users";
    private static final String[] USERS_FIELD_NAMES = {"_id", "privacy", "profile_state", "persona_name",
            "last_log_off", "profile_url", "avatar_url"};
    private static final String[] USERS_FIELD_TYPES = {"INTEGER PRIMARY KEY", "INTEGER", "INTEGER",
            "TEXT", "INTEGER", "TEXT", "TEXT"};

    // Match table
    private static final String MATCHES_TABLE = "Matches";
    private static final String[] MATCHES_FIELD_NAMES = {"_id", "sequence_number", "radiant_win",
            "start_time", "duration", "game_mode"};
    private static final String[] MATCHES_FIELD_TYPES = {"INTEGER PRIMARY KEY", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER"};

    // MatchPlayer table (acts as relationship table between User and Match)
    private static final String MATCH_PLAYERS_TABLE = "MatchPlayers";
    private static final String[] MATCH_PLAYERS_FIELD_NAMES = {"match_id", "steam_id", "player_slot",
            "hero_id", "kills", "deaths", "assists", "gold", "last_hits", "denies", "gpm", "xpm",
            "hero_damage", "tower_damage", "hero_healing", "level"};
    private static final String[] PLAYERS_FIELD_TYPES = {"INTEGER", "INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER"};
    //private static final String[] MATCH_PLAYERS_FOREIGN_KEYS = {MATCH_PLAYERS_FIELD_NAMES[0], MATCH_PLAYERS_FIELD_NAMES[1]};
    //private static final String[] MATCH_PLAYERS_PARENT_TABLES = {MATCHES_TABLE, USERS_TABLE};
    //private static final String[] MATCH_PLAYERS_CANDIDATE_KEYS = {MATCHES_FIELD_NAMES[0], USERS_FIELD_NAMES[0]};
    //TODO: Delete on cascade when user is deleted?
    //private static final boolean[] MATCH_PLAYERS_CASCADE_DELETE = {true, true}; // match, user

    /**
     * Gets an instance of the <code>DBHelper</code>.
     * @param context The context of the activity retrieving the <code>DBHelper</code> instance.
     * @return An instance of the <code>DBHelper</code>.
     */
    public static synchronized DBHelper getInstance(Context context)
    {
        // Use application context to ensure you don't accidentally leak Activity's context
        // Application context is a singleton
        // See: https://android-developers.googleblog.com/2009/01/avoiding-memory-leaks.html
        if (sInstance == null) sInstance = new DBHelper(context.getApplicationContext());
        return sInstance;
    }

    // Make private to prevent direct instantiation. Use getInstance() instead.
    private DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database table for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(createTable(USERS_TABLE, USERS_FIELD_NAMES, USERS_FIELD_TYPES));
        db.execSQL(createTable(MATCHES_TABLE, MATCHES_FIELD_NAMES, MATCHES_FIELD_TYPES));
//        db.execSQL(createTable(MATCH_PLAYERS_TABLE, MATCH_PLAYERS_FIELD_NAMES, PLAYERS_FIELD_TYPES,
//                MATCH_PLAYERS_FOREIGN_KEYS, MATCH_PLAYERS_PARENT_TABLES, MATCH_PLAYERS_CANDIDATE_KEYS,
//                MATCH_PLAYERS_CASCADE_DELETE));
        db.execSQL(createTable(MATCH_PLAYERS_TABLE, MATCH_PLAYERS_FIELD_NAMES, PLAYERS_FIELD_TYPES, " UNIQUE (match_id, steam_id))"));
    }

    @NonNull
    private String createTable(String tableName, String[] fieldNames, String[] fieldTypes, String constraint)
    {
        StringBuilder createSQL = new StringBuilder("CREATE TABLE ");
        createSQL.append(tableName).append("(");
        for (int i = 0; i < fieldNames.length; i++)
            createSQL.append(fieldNames[i]).append(" ")
                    .append(fieldTypes[i]).append(",");

        createSQL.append(constraint);
        return createSQL.toString();
    }


    @NonNull
    private String createTable(String tableName, String[] fieldNames, String[] fieldTypes)
    {
        StringBuilder createSQL = new StringBuilder("CREATE TABLE ");
        createSQL.append(tableName).append("(");
        for (int i = 0; i < fieldNames.length; i++)
            createSQL.append(fieldNames[i]).append(" ")
                    .append(fieldTypes[i]).append((i < fieldNames.length - 1) ? "," : ")");

        return createSQL.toString();
    }

    @NonNull
    private String createTable(String tableName, String[] fieldNames, String[] fieldTypes,
                               String[] foreignKeys, String[] parentTables, String[] candidateKeys,
                               boolean[] hasCascadeDelete)
    {
        StringBuilder createSQL = new StringBuilder("CREATE TABLE ");
        createSQL.append(tableName).append("(");
        for (int i = 0; i < fieldNames.length; i++)
            createSQL.append(fieldNames[i]).append(" ").append(fieldTypes[i]).append(",");

        for (int i = 0; i < foreignKeys.length; i++)
            createSQL.append("FOREIGN KEY(").append(foreignKeys[i]).append(") REFERENCES ")
                    .append(parentTables[i]).append("(").append(candidateKeys[i]).append(")")
                    .append((hasCascadeDelete[i]) ? " ON DELETE CASCADE" : "")
                    .append((i < foreignKeys.length - 1) ? "," : ")");

        return createSQL.toString();
    }

    /**
     * Drops the existing database table and creates a new one when database is upgraded.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MATCHES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MATCH_PLAYERS_TABLE);
        onCreate(db);
    }

//    /**
//     * Configures database connection to enable foreign key support.
//     * @param db The database.
//     */
//    @Override
//    public void onConfigure(SQLiteDatabase db)
//    {
//        super.onConfigure(db);
//        db.setForeignKeyConstraintsEnabled(true);
//    }

    //************** USER TABLE OPERATIONS****************

    /**
     * Adds a new <code>User</code> to the database.
     *
     * @param user The new <code>User</code> to add to the database.
     */
    public void addUser(User user)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERS_FIELD_NAMES[0], user.getSteamId32());
        values.put(USERS_FIELD_NAMES[1], user.getPrivacy());
        values.put(USERS_FIELD_NAMES[2], user.getProfileState());
        values.put(USERS_FIELD_NAMES[3], user.getPersonaName());
        values.put(USERS_FIELD_NAMES[4], user.getLastLogOff());
        values.put(USERS_FIELD_NAMES[5], user.getProfileUrl());
        values.put(USERS_FIELD_NAMES[6], user.getAvatarUrl());

        db.insert(USERS_TABLE, null, values);
    }

    /**
     * Gets a <code>User</code> from the database with the specified 32-bit Steam ID.
     *
     * @param steamID32 The 32-bit Steam ID of the <code>User</code> to retrieve from the database.
     * @return The <code>User</code> with the specified 32-bit Steam ID in the database. Returns null
     * if no <code>User</code> record has the specified account ID.
     */
    public User getUser(long steamID32)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(USERS_TABLE, USERS_FIELD_NAMES,
                USERS_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(steamID32)},
                null, null, null, null);

        User user = null;
        if (cursor.moveToFirst())
            user = new User(cursor.getLong(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getLong(4), cursor.getString(5),
                    cursor.getString(6));

        cursor.close();

        return user;
    }

    /**
     * Gets a list of all <code>User</code>s in the database.
     *
     * @return A list of all <code>User</code>s in the database.
     */
    public ArrayList<User> getAllUsers()
    {
        ArrayList<User> usersList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(USERS_TABLE, USERS_FIELD_NAMES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                User user = new User(cursor.getLong(0), cursor.getInt(1), cursor.getInt(2),
                        cursor.getString(3), cursor.getLong(4), cursor.getString(5),
                        cursor.getString(6));
                usersList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return usersList;
    }

    // TODO: delete match records related to user too?
    /**
     * Deletes all <code>User</code>s in the database.
     */
    public void deleteAllUsers()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(USERS_TABLE, null, null);
    }

    /**
     * Checks if a <code>User</code> with the specified Steam ID exists in the database.
     *
     * @param steamID32 The 32-bit Steam account ID of the <code>User</code> to check for.
     * @return True if the <code>User</code> with the specified Steam ID exists in the database;
     * otherwise false.
     */
    public boolean isUserInDB(long steamID32)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(USERS_TABLE, new String[]{USERS_FIELD_NAMES[0]},
                USERS_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(steamID32)},
                null, null, null, null);

        boolean isUserFound = cursor.moveToFirst();
        cursor.close();
        return isUserFound;
    }


    //************** MATCH TABLE OPERATIONS ****************

    /**
     * Adds a new <code>Match</code> to the database. Does not add players in that match to the
     * database.
     *
     * @param match The new <code>Match</code> to add to the database.
     */
    public void addMatch(Match match)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MATCHES_FIELD_NAMES[0], match.getMatchID());
        values.put(MATCHES_FIELD_NAMES[1], match.getMatchSeqNum());
        values.put(MATCHES_FIELD_NAMES[2], match.isRadiantWin() ? 1 : 0);
        values.put(MATCHES_FIELD_NAMES[3], match.getStartTime());
        values.put(MATCHES_FIELD_NAMES[4], match.getDuration());
        values.put(MATCHES_FIELD_NAMES[5], match.getGameMode());

        db.insert(MATCHES_TABLE, null, values);

        // To add MatchPlayers who played in match into database, call addPlayer outside of this class

    }

    /**
     * Adds a new <code>Match</code> to the database. Also adds the players in that match to the
     * database.
     *
     * @param match The new <code>Match</code> to add to the database.
     */
    public void addMatchWithPlayers(Match match)
    {
        addMatch(match);
        for (MatchPlayer mp : match.getMatchPlayerList()) addPlayer(mp);
    }

    /**
     * Gets a <code>Match</code> from the database with the specified match ID.
     *
     * @param matchID The ID of the <code>Match</code> to retrieve from the database.
     * @return The <code>Match</code> with the specified match ID in the database. Returns null if no
     * <code>Match</code> record has the specified ID.
     */
    public Match getMatch(long matchID)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCHES_TABLE, MATCHES_FIELD_NAMES,
                MATCHES_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(matchID)},
                null, null, null, null);

        // To get MatchPlayers who played in match, call getMatchPlayers outside of this class

        Match match = null;
        if (cursor.moveToFirst())
            match = new Match(cursor.getLong(0), cursor.getLong(1), cursor.getInt(2) == 1,
                    cursor.getLong(3), cursor.getInt(4), cursor.getInt(5));

        cursor.close();

        return match;
    }

    public List<Match> getMatchList(long[] matchId) {

        return null;
    }

    /**
     * Gets a list of all <code>Match</code>es in the database.
     *
     * @return A list of all <code>Match</code>es in the database.
     */
    public ArrayList<Match> getAllMatches()
    {
        ArrayList<Match> matchesList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCHES_TABLE, MATCHES_FIELD_NAMES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                // To get MatchPlayers who played in match, call getMatchPlayers outside of this class

                Match match = new Match(cursor.getLong(0), cursor.getLong(1), cursor.getInt(2) == 1,
                        cursor.getLong(3), cursor.getInt(4), cursor.getInt(5));
                matchesList.add(match);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return matchesList;
    }

    // TODO: getPlayerMatches(long steamID32)
//    public List<Match> getPlayerMatches(long steamID32)
//    {
//        List<Match> playerMatches = new ArrayList<>();
//
//        List<Long> playerMatchIDs = getPlayerMatchIDs(steamID32);
//        for (Long matchID : playerMatchIDs) playerMatches.add()
//
//    }

    /**
     * Deletes all <code>Match</code>es in the database.
     */
    public void deleteAllMatches()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MATCHES_TABLE, null, null);
    }

    /**
     * Checks if a <code>Match</code> with the specified match ID exists in the database.
     *
     * @param matchID The ID of the <code>Match</code> to check for.
     * @return True if the <code>Match</code> with the specified match ID exists in the database;
     * otherwise false.
     */
    public boolean isMatchInDB(long matchID)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCHES_TABLE, new String[]{MATCHES_FIELD_NAMES[0]},
                MATCHES_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(matchID)},
                null, null, null, null);

        boolean isMatchFound = cursor.moveToFirst();
        cursor.close();
        return isMatchFound;
    }

    //************** MATCHPLAYER TABLE OPERATIONS ****************

    /**
     * Adds a new player to the database.
     *
     * @param player The new <code>MatchPlayer</code> to add to the database.
     */
    public void addPlayer(MatchPlayer player)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MATCH_PLAYERS_FIELD_NAMES[0], player.getMatchId());
        values.put(MATCH_PLAYERS_FIELD_NAMES[1], player.getAccountId());
        values.put(MATCH_PLAYERS_FIELD_NAMES[2], player.getPlayerSlot());
        values.put(MATCH_PLAYERS_FIELD_NAMES[3], player.getHeroId());
        values.put(MATCH_PLAYERS_FIELD_NAMES[4], player.getKills());
        values.put(MATCH_PLAYERS_FIELD_NAMES[5], player.getDeaths());
        values.put(MATCH_PLAYERS_FIELD_NAMES[6], player.getAssists());
        values.put(MATCH_PLAYERS_FIELD_NAMES[7], player.getGold());
        values.put(MATCH_PLAYERS_FIELD_NAMES[8], player.getLastHits());
        values.put(MATCH_PLAYERS_FIELD_NAMES[9], player.getDenies());
        values.put(MATCH_PLAYERS_FIELD_NAMES[10], player.getGPM());
        values.put(MATCH_PLAYERS_FIELD_NAMES[11], player.getXPM());
        values.put(MATCH_PLAYERS_FIELD_NAMES[12], player.getHeroDamage());
        values.put(MATCH_PLAYERS_FIELD_NAMES[13], player.getTowerDamage());
        values.put(MATCH_PLAYERS_FIELD_NAMES[14], player.getHeroHealing());
        values.put(MATCH_PLAYERS_FIELD_NAMES[15], player.getLevel());

        db.insert(MATCH_PLAYERS_TABLE, null, values);

    }

    /**
     * Gets a <code>MatchPlayer</code> of a specified match from the database.
     *
     * @param matchID   The ID of the match that the <code>MatchPlayer</code> to retrieve from the
     *                  database played in.
     * @param steamID32 The 32-bit Steam ID of the <code>MatchPlayer</code> to retrieve from the database.
     * @return The <code>MatchPlayer</code> in the database with the specified 32-bit Steam ID who played in
     * the match with the specified match ID. Returns null if no <code>MatchPlayer</code> record has the
     * specified 32-bit Steam ID and match ID.
     */
    public MatchPlayer getMatchPlayer(long matchID, long steamID32)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCH_PLAYERS_TABLE, MATCH_PLAYERS_FIELD_NAMES,
                MATCH_PLAYERS_FIELD_NAMES[0] + "=? AND " + MATCH_PLAYERS_FIELD_NAMES[1] + "=?",
                new String[]{String.valueOf(matchID), String.valueOf(steamID32)},
                null, null, null, null);

        MatchPlayer player = null;
        if (cursor.moveToFirst())
            player = new MatchPlayer(cursor.getLong(0), cursor.getLong(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
                    cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
                    cursor.getInt(14), cursor.getInt(15));

        cursor.close();

        return player;
    }

    /**
     * Gets a list of <code>MatchPlayer</code>s in the database that played in a specified match.
     *
     * @param matchID The ID of the match that the <code>MatchPlayer</code>s to retrieve from the database
     *                played in.
     * @return A list of <code>MatchPlayer</code>s in the database that played in the match with the
     * specified match ID.
     */
    public List<MatchPlayer> getMatchPlayers(long matchID)
    {
        ArrayList<MatchPlayer> matchPlayersList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCH_PLAYERS_TABLE, MATCH_PLAYERS_FIELD_NAMES,
                MATCH_PLAYERS_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(matchID)},
                null, null, null, null);

        if (cursor.moveToNext())
        {
            do
            {
                MatchPlayer player = new MatchPlayer(cursor.getLong(0), cursor.getLong(1),
                        cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9),
                        cursor.getInt(10), cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
                        cursor.getInt(14), cursor.getInt(15));
                matchPlayersList.add(player);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return matchPlayersList;
    }

    /**
     * Gets a <code>MatchPlayer</code>'s single-match statistics for every match the player has played in
     * from the database.
     *
     * @param steamID32 The 32-bit Steam ID of the <code>Player</code> to query.
     * @return A list of <code>MatchPlayer</code>s in the database with the specified 32-bit Steam ID.
     * Since Steam IDs are unique for each <code>MatchPlayer</code>, each <code>MatchPlayer</code>
     * in the list should represent the single-match statistics of a single player (user).
     */
    public List<MatchPlayer> getPlayerMatchStats(long steamID32)
    {
        ArrayList<MatchPlayer> playerMatchStatsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCH_PLAYERS_TABLE, MATCH_PLAYERS_FIELD_NAMES,
                MATCH_PLAYERS_FIELD_NAMES[1] + "=?", new String[]{String.valueOf(steamID32)},
                null, null, null, null);

        if (cursor.moveToNext())
            do
            {
                MatchPlayer player = new MatchPlayer(cursor.getLong(0), cursor.getLong(1),
                        cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9),
                        cursor.getInt(10), cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
                        cursor.getInt(14), cursor.getInt(15));
                playerMatchStatsList.add(player);
            } while (cursor.moveToNext());

        cursor.close();

        return playerMatchStatsList;
    }

    /**
     * Gets a list of match IDs for matches played by the player with the specified Steam account ID.
     *
     * @param steamID32 The 32-bit Steam account ID of the player to query.
     * @return A list of match IDs for matches played by the player with the specified Steam account ID.
     */
    public List<Long> getPlayerMatchIDs(long steamID32)
    {
        List<Long> userMatchIDList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCH_PLAYERS_TABLE, new String[]{MATCH_PLAYERS_FIELD_NAMES[0]},
                MATCH_PLAYERS_FIELD_NAMES[1] + "=?", new String[]{String.valueOf(steamID32)},
                null, null, null, null);

        if (cursor.moveToNext())
            do
            {
                userMatchIDList.add(cursor.getLong(0));
            } while (cursor.moveToNext());

        cursor.close();
        return userMatchIDList;
    }

    public ArrayList<MatchPlayer> getAllMatchPlayers()
    {
        ArrayList<MatchPlayer> matchPlayersList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCH_PLAYERS_TABLE, MATCH_PLAYERS_FIELD_NAMES, null, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {

                MatchPlayer player = new MatchPlayer(cursor.getLong(0), cursor.getLong(1),
                        cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9),
                        cursor.getInt(10), cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
                        cursor.getInt(14), cursor.getInt(15));
                matchPlayersList.add(player);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return matchPlayersList;
    }

    /**
     * Deletes all <code>Player</code>s in the database.
     */
    public void deleteAllPlayers()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MATCH_PLAYERS_TABLE, null, null);
    }

    /**
     * Checks if a <code>MatchPlayer</code> with the specified match ID and Steam ID exists in the
     * database.
     *
     * @param matchID   The ID of the match played by the <code>MatchPlayer</code> to check for.
     * @param steamID32 The 32-bit Steam account ID of the <code>MatchPlayer</code> to check for.
     * @return True if the <code>MatchPlayer</code> with the specified match ID and Steam ID exists
     * in the database; otherwise false.
     */
    public boolean isMatchPlayerInDB(long matchID, long steamID32)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCH_PLAYERS_TABLE, new String[]{MATCH_PLAYERS_FIELD_NAMES[0], MATCH_PLAYERS_FIELD_NAMES[1]},
                MATCH_PLAYERS_FIELD_NAMES[0] + "=? AND " + MATCH_PLAYERS_FIELD_NAMES[1] + "=?",
                new String[]{String.valueOf(matchID), String.valueOf(steamID32)},
                null, null, null, null);

        boolean isMatchPlayerFound = cursor.moveToFirst();
        cursor.close();
        return isMatchPlayerFound;
    }

}

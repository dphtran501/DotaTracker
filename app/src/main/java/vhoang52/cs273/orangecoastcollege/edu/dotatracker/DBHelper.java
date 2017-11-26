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
    private Context mContext;

    // Database name and version
    private static final String DATABASE_NAME = "DotaTracker";
    private static final int DATABASE_VERSION = 1;

    // TODO: need to check if account ID and match ID are ints or strings
    // TODO: either can be primary keys, but using string as primary key can cause performance issue

    // TODO: User table

    // Match table
    private static final String MATCHES_TABLE = "Matches";
    private static final String[] MATCHES_FIELD_NAMES = {"_id", "sequence_number", "is_radiant_win",
            "duration", "start_time", "radiant_score", "dire_score"};
    private static final String[] MATCHES_FIELD_TYPES = {"INTEGER PRIMARY KEY", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER"};

    // TODO: foreign key once user table is done
    // Player table (acts as relationship table between User and Match)
    private static final String PLAYERS_TABLE = "Players";
    private static final String[] PLAYERS_FIELD_NAMES = {"account_id", "match_id", "player_slot",
            "hero_id", "kills", "deaths", "assists", "gold", "last_hits", "denies", "gpm", "xpm",
            "hero_damage", "tower_damage", "hero_healing", "level"};
    private static final String[] PLAYERS_FIELD_TYPES = {"INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER", "INTEGER"};

    /**
     * Instantiates a new <code>DBHelper</code> object with the given context.
     *
     * @param context The activity used to open or create the database.
     */
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * Creates the database table for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(createTable(MATCHES_TABLE, MATCHES_FIELD_NAMES, MATCHES_FIELD_TYPES));
        db.execSQL(createTable(PLAYERS_TABLE, PLAYERS_FIELD_NAMES, PLAYERS_FIELD_TYPES));
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
        db.execSQL("DROP TABLE IF EXISTS " + MATCHES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYERS_TABLE);
        onCreate(db);
    }

    //************** MATCH TABLE OPERATIONS ****************

    /**
     * Adds a new <code>Match</code> to the database.
     *
     * @param match The new <code>Match</code> to add to the database.
     */
    public void addMatch(Match match)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MATCHES_FIELD_NAMES[0], match.getID());
        values.put(MATCHES_FIELD_NAMES[1], match.getSequenceNumber());
        values.put(MATCHES_FIELD_NAMES[2], match.isRadiantWin() ? 1 : 0);
        values.put(MATCHES_FIELD_NAMES[3], match.getDuration());
        values.put(MATCHES_FIELD_NAMES[4], match.getStartTime());
        values.put(MATCHES_FIELD_NAMES[5], match.getRadiantScore());
        values.put(MATCHES_FIELD_NAMES[6], match.getDireScore());

        db.insert(MATCHES_TABLE, null, values);

        db.close();
    }

    /**
     * Gets a <code>Match</code> from the database with the specified ID.
     *
     * @param id The ID of the <code>Match</code> to retrieve from the database.
     * @return The <code>Match</code> with the specified ID in the database. Returns null if no
     * <code>Match</code> record has the specified ID.
     */
    public Match getMatch(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCHES_TABLE, MATCHES_FIELD_NAMES,
                MATCHES_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);


        Match match = null;
        if (cursor.moveToFirst())
            match = new Match(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2) == 1, cursor.getInt(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getInt(6));

        cursor.close();
        db.close();

        return match;
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
        do
        {
            Match match = new Match(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2) == 1, cursor.getInt(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getInt(6));
            matchesList.add(match);
        } while (cursor.moveToNext());

        cursor.close();
        db.close();

        return matchesList;
    }

    /**
     * Deletes all <code>Match</code>es in the database.
     */
    public void deleteAllMatches()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MATCHES_TABLE, null, null);
        db.close();
    }

    //************** PLAYER TABLE OPERATIONS ****************

    /**
     * Adds a new player to the database.
     *
     * @param player The new <code>Player</code> to add to the database.
     */
    public void addPlayer(Player player)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PLAYERS_FIELD_NAMES[0], player.getAccountID());
        values.put(PLAYERS_FIELD_NAMES[1], player.getMatchID());
        values.put(PLAYERS_FIELD_NAMES[2], player.getPlayerSlot());
        values.put(PLAYERS_FIELD_NAMES[3], player.getHeroID());
        values.put(PLAYERS_FIELD_NAMES[4], player.getKills());
        values.put(PLAYERS_FIELD_NAMES[5], player.getDeaths());
        values.put(PLAYERS_FIELD_NAMES[6], player.getAssists());
        values.put(PLAYERS_FIELD_NAMES[7], player.getGold());
        values.put(PLAYERS_FIELD_NAMES[8], player.getLastHits());
        values.put(PLAYERS_FIELD_NAMES[9], player.getDenies());
        values.put(PLAYERS_FIELD_NAMES[10], player.getGPM());
        values.put(PLAYERS_FIELD_NAMES[11], player.getXPM());
        values.put(PLAYERS_FIELD_NAMES[12], player.getHeroDamage());
        values.put(PLAYERS_FIELD_NAMES[13], player.getTowerDamage());
        values.put(PLAYERS_FIELD_NAMES[14], player.getHeroHealing());
        values.put(PLAYERS_FIELD_NAMES[15], player.getLevel());

        db.insert(PLAYERS_TABLE, null, values);

        db.close();
    }

    /**
     * Gets a <code>Player</code> of a specified match from the database.
     *
     * @param accountID The account ID of the <code>Player</code> to retrieve from the database.
     * @param matchID   The ID of the match that the <code>Player</code> to retrieve from the database
     *                  played in.
     * @return The <code>Player</code> in the database with the specified account ID who played in
     * the match with the specified match ID. Returns null if no <code>Player</code> record has the
     * specified account ID and match ID.
     */
    public Player getMatchPlayer(int accountID, int matchID)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLAYERS_TABLE, PLAYERS_FIELD_NAMES,
                PLAYERS_FIELD_NAMES[0] + "=? AND " + PLAYERS_FIELD_NAMES[1] + "=?",
                new String[]{String.valueOf(accountID), String.valueOf(matchID)},
                null, null, null, null);

        Player player = null;
        if (cursor.moveToFirst())
            player = new Player(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
                    cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
                    cursor.getInt(14), cursor.getInt(15));

        cursor.close();
        db.close();

        return player;
    }

    /**
     * Gets a <code>Player</code>'s single-match statistics for every match the player has played in
     * from the database.
     *
     * @param accountID The account ID of the <code>Player</code> to query.
     * @return A list of <code>Player</code>s in the database with the specified account ID. Since
     * account IDs are unique for each <code>Player</code>, each <code>Player</code> in the list
     * should represent the single-match statistics of a single player (user).
     */
    public List<Player> getPlayerMatchStats(int accountID)
    {
        ArrayList<Player> playerMatchStatsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLAYERS_TABLE, PLAYERS_FIELD_NAMES,
                PLAYERS_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(accountID)},
                null, null, null, null);

        if (cursor.moveToNext())
            do
            {
                Player player = new Player(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6),
                        cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
                        cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
                        cursor.getInt(14), cursor.getInt(15));
                playerMatchStatsList.add(player);
            } while (cursor.moveToNext());

        cursor.close();
        db.close();

        return playerMatchStatsList;
    }

    /**
     * Gets a list of <code>Player</code>s in the database that played in a specified match.
     *
     * @param matchID The ID of the match that the <code>Player</code>s to retrieve from the database
     *                played in.
     * @return A list of <code>Player</code>s in the database that played in the match with the
     * specified ID.
     */
    public List<Player> getMatchPlayers(int matchID)
    {
        ArrayList<Player> matchPlayersList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLAYERS_TABLE, PLAYERS_FIELD_NAMES,
                PLAYERS_FIELD_NAMES[1] + "=?", new String[]{String.valueOf(matchID)},
                null, null, null, null);

        if (cursor.moveToNext())
            do
            {
                Player player = new Player(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                        cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6),
                        cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10),
                        cursor.getInt(11), cursor.getInt(12), cursor.getInt(13),
                        cursor.getInt(14), cursor.getInt(15));
                matchPlayersList.add(player);
            } while (cursor.moveToNext());

        cursor.close();
        db.close();

        return matchPlayersList;
    }

    // TODO: getter for all players
    // TODO: deleters for all players
    // TODO: deleter for match players and player match statisics? (might not because of foreign keys?)

    /*
    public ArrayList<Match> getAllMatches()
    {
        ArrayList<Match> matchesList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCHES_TABLE, MATCHES_FIELD_NAMES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst())
            do
            {
                Match match = new Match(cursor.getInt(0), cursor.getInt(1),
                        cursor.getInt(2) == 1, cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getInt(6));
                matchesList.add(match);
            } while (cursor.moveToNext());

        cursor.close();
        db.close();

        return matchesList;
    }

    public void deleteAllMatches()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MATCHES_TABLE, null, null);
        db.close();
    }
    */

}

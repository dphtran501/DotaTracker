package vhoang52.cs273.orangecoastcollege.edu.dotatracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;

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

    // Match table
    private static final String MATCHES_TABLE = "Matches";
    private static final String[] MATCHES_FIELD_NAMES = {"_id", "sequence_number", "is_radiant_win",
            "duration", "start_time", "radiant_score", "dire_score"};
    private static final String[] MATCHES_FIELD_TYPES = {"INTEGER PRIMARY KEY", "INTEGER", "INTEGER",
            "INTEGER", "INTEGER", "INTEGER", "INTEGER"};

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
        onCreate(db);
    }

    //************** MATCH TABLE OPERATIONS ****************

    /**
     * Adds a new match to the database.
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
     * @return The <code>Match</code> with the specified ID in the database.
     */
    public Match getMatch(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(MATCHES_TABLE, MATCHES_FIELD_NAMES,
                MATCHES_FIELD_NAMES[0] + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) cursor.moveToFirst();

        Match match = new Match(cursor.getInt(0), cursor.getInt(1),
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

        if (cursor.moveToFirst()) do
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

}

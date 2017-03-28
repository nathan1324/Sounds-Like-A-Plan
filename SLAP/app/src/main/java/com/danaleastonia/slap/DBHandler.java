package com.danaleastonia.slap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.danaleastonia.slap.DBContract.FeedEntry;
import com.danaleastonia.slap.DBContract.FriendEntry;
import com.danaleastonia.slap.DBContract.UserEntry;

import java.util.HashMap;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "usersInfo";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ( " +
                UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UserEntry.KEY_NAME + " TEXT," +
                UserEntry.KEY_USERNAME + " TEXT," +
                UserEntry.KEY_USR_EMAIL + " TEXT UNIQUE," +
                UserEntry.KEY_PASSWORD + " TEXT );";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_FEED_TABLE = "CREATE TABLE " + FeedEntry.TABLE_NAME + " ( " +
                FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FeedEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FeedEntry.COLUMN_TITLE + " TEXT NOT NULL );";
        db.execSQL(CREATE_FEED_TABLE);

        String CREATE_FRIEND_TABLE = "CREATE TABLE " + FriendEntry.TABLE_NAME + " ( " +
                FriendEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FriendEntry.COLUMN_NAME + " TEXT NOT NULL );";
        db.execSQL(CREATE_FRIEND_TABLE);
    }

    /**
     * Updates the the database on by dropping old table for a newer upgraded table.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FriendEntry.TABLE_NAME);

        // Creating tables again
        onCreate(db);
    }

    /**
     * Returns the position of the cursor in the feed entry.
     *
     * @return cursor position
     */
    public Cursor getFeedCursor() {
        String selectQuery = "SELECT * FROM " + FeedEntry.TABLE_NAME + " ORDER BY " + FeedEntry._ID + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    /**
     *  Returns the position of the cursor in the friends entry.
     *
     * @return cursor
     */
    public Cursor getFriendCursor() {
        String selectQuery = "SELECT * FROM " + FriendEntry.TABLE_NAME + " ORDER BY " + FriendEntry._ID + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    /**
     * Adds a new user and their information to the database.
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserEntry.KEY_NAME, user.getName());           // user's name
        values.put(UserEntry.KEY_USERNAME, user.getUserName());   // user's username
        values.put(UserEntry.KEY_USR_EMAIL, user.getEmail());     // user's email
        values.put(UserEntry.KEY_PASSWORD, user.getPassword());   // user's password

        // Inserting Row
        db.insert(UserEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Retrieves a user information by searching the database by user's email.
     *
     * @param email the email specified by the user
     * @return user information of specific user
     */
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserEntry.TABLE_NAME, new String[]{UserEntry._ID,
                        UserEntry.KEY_NAME,
                        UserEntry.KEY_USERNAME,
                        UserEntry.KEY_USR_EMAIL,
                        UserEntry.KEY_PASSWORD},
                UserEntry.KEY_USR_EMAIL + "=?",
                new String[]{String.valueOf(email)}, null, null, null, null);

        User user = null;
        if (cursor != null) {
            cursor.moveToFirst();
            user = new User(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }

        return user;
    }

    /**
     *
     * @return emailPasswordMap
     */
    public Map<String, String> getEmailPasswordMap() {
        Map<String, String> emailPasswordMap = new HashMap<>();
        String selectQuery = "SELECT * FROM " + UserEntry.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String email = cursor.getString(3);
                String password = cursor.getString(4);
                emailPasswordMap.put(email, password);
            } while (cursor.moveToNext());
        }
        return emailPasswordMap;
    }

    /**
     * Adds a friend to friend list by name.
     *
     * @param name name of
     * @return count
     */
    public long addFriend(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FriendEntry.COLUMN_NAME, name);
        long count = db.insert(FriendEntry.TABLE_NAME, null, values);
        db.close();
        return count;
    }

    /**
     * Adds a new status to the feed.
     *
     * @param title friend's status and message to add to feed
     * @param email
     * @return count location of feed
     */
    public long addFeedItem(String title, String email)
    {
        String name = getUserByEmail(email).getName();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME, name);
        values.put(FeedEntry.COLUMN_TITLE, title);
        long count = db.insert(FeedEntry.TABLE_NAME, null, values);
        db.close();
        return count;
    }
}
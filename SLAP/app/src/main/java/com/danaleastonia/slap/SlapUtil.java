package com.danaleastonia.slap;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;


public class SlapUtil {
    public static void assignRandomImage(ImageView imageView, int id) {
        //Random rand = new Random();
        int randomNum = id % 5;

        switch (randomNum) {
            case 1: {
                imageView.setImageResource(R.drawable.user1);
                break;
            }
            case 2: {
                imageView.setImageResource(R.drawable.user2);
                break;
            }
            case 3: {
                imageView.setImageResource(R.drawable.user3);
                break;
            }
            case 4: {
                imageView.setImageResource(R.drawable.user4);
                break;
            }
        }
    }

    public static void insertDummyFeedData(Context context) {
        DBHandler handler = new DBHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.FeedEntry.COLUMN_TITLE, "Busy");
        values.put(DBContract.FeedEntry.COLUMN_NAME, "Joe Reed");
        db.insert(DBContract.FeedEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FeedEntry.COLUMN_TITLE, "Free");
        values.put(DBContract.FeedEntry.COLUMN_NAME, "Nate Easton");
        db.insert(DBContract.FeedEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FeedEntry.COLUMN_TITLE, "Going to Zelicks");
        values.put(DBContract.FeedEntry.COLUMN_NAME, "Molly Jennings");
        db.insert(DBContract.FeedEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FeedEntry.COLUMN_TITLE, "Studying for physics");
        values.put(DBContract.FeedEntry.COLUMN_NAME, "Monica James");
        db.insert(DBContract.FeedEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FeedEntry.COLUMN_TITLE, "Cafe India");
        values.put(DBContract.FeedEntry.COLUMN_NAME, "Jessica James");
        db.insert(DBContract.FeedEntry.TABLE_NAME, null, values);

        db.close();
    }

    public static void insertDummyFriendsData(Context context) {
        DBHandler handler = new DBHandler(context);
        SQLiteDatabase db = handler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Joe Easton");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Sam Esmail");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Mike Hunt");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Nicole Weir");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Isaac Newton");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Daniel Andrada");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Emily Sikes");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Jack McGee");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        values.clear();
        values.put(DBContract.FriendEntry.COLUMN_NAME, "Some Dude");
        db.insert(DBContract.FriendEntry.TABLE_NAME, null, values);

        db.close();
    }
}

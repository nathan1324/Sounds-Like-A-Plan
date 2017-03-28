package com.danaleastonia.slap;

import android.provider.BaseColumns;


public class DBContract {

    public static final class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "feed_list";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_NAME = "name";
    }

    public static final class FriendEntry implements BaseColumns {
        public static final String TABLE_NAME = "friend_list";
        public static final String COLUMN_NAME = "name";
    }

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String KEY_NAME = "name";
        public static final String KEY_USERNAME = "userName";
        public static final String KEY_USR_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";

    }
}

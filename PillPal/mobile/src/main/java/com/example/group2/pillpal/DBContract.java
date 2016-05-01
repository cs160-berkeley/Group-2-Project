package com.example.group2.pillpal;

import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jessica. this is actually a DBhelper class! The
 * word 'contract' sounded cooler in the moment and now I
 * have ragrets.
 */
public class DBContract extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        private static final String TEXT_TYPE = " TEXT";
//        private static final String BLOB_TYPE = " BLOB ";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_OBJ + TEXT_TYPE + " )";

        private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor. Not anymore suckers.
        public DBContract (Context context, String dbName, int dbVersion) {
            super(context, dbName, null, dbVersion);
    }


        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            Log.w("TaskDBAdapter", "Changing from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        /* Inner class that defines the table contents */
        public static abstract class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "users";
            public static final String COLUMN_NAME_ID = "entryid";
            public static final String COLUMN_NAME_OBJ = "object";
        }
}




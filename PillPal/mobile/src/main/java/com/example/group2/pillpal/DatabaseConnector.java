package com.example.group2.pillpal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.sql.SQLException;

/**
 * Created by Jessica on 4/30/16.
 */
public class DatabaseConnector {

    // Declare Variables
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "pillPal";
    private static final String TABLE_NAME = "users";
    private static final String ID = "_id";
    /** Names of the two columns */
    private static final String ENTRY_ID = "entryid";
    private static final String SERIALIZED = "object";
    private SQLiteDatabase database;
    private DBContract dbOpenHelper;

    public DatabaseConnector(Context context) {
        dbOpenHelper = new DBContract(context, DB_NAME, DATABASE_VERSION);

    }

    // Open Database function
    public void open() throws SQLException {
        // Allow database to be in writable mode
        database = dbOpenHelper.getWritableDatabase();
    }

    // Close Database function
    public void close() {
        if (database != null)
            database.close();
    }

    // Create Database function
    public void InsertObject(String id, String strObject) {
        ContentValues newCon = new ContentValues();
        newCon.put(ENTRY_ID, id);
        newCon.put(SERIALIZED, strObject);

        try {
            open();
            database.insert(TABLE_NAME, null, newCon);
            close();
        } catch (SQLException e) {
            Log.d("Error", e.toString());
        }
    }

    // Update Database function
    public void UpdateObject(long id, String idUser, String object) {
        ContentValues editCon = new ContentValues();
        editCon.put(ENTRY_ID, idUser);
        editCon.put(SERIALIZED, object);

        try {
            open();
            database.update(TABLE_NAME, editCon, ID + "=" + id, null);
            close();
        }catch (SQLException e) {
            Log.d("Error", e.toString());
        }
    }

    // Delete Database function
    public void DeleteObject(long id) {
        try {
            open();
            database.delete(TABLE_NAME, ID + "=" + id, null);
            close();
        }catch (SQLException e ) {
            Log.d("Error", e.toString());
        }
    }

    // List all data function
    public Cursor ListAllObjects() {
        return database.query(TABLE_NAME, new String[] { ID, ENTRY_ID }, null,
                null, null, null, ENTRY_ID);
    }

    // Capture single data by ID
    public Cursor GetOneObject(long id) {
        return database.query(TABLE_NAME, null, ID + "=" + id, null, null,
                null, null);
    }

}
package com.example.group2.pillpal;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jessica on 4/26/16.
 * http://stackoverflow.com/questions/22089648/creating-login-page-with-sqlite
 */



public class DataBaseAdapter {
    static final String DATABASE_NAME = "pilldb";
    static final int DATABASE_VERSION = 2;

    static final String USERNAME_COLUMN = "UserName";
    static final String PASSWORD_COLUMN = "Password";
    static final String QUALIFICATION_COLUMN = "Qualification";
    static final String SPECIALIZATION_COLUMN = "Specialization";
    static final String COLUMN_ID = "id";
    static final String REGISTRATIONNUMBER_COLUMN = "RegistrationNumber";
    static final String CELLNUMBER_COLUMN = "CellNumber";
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD String,QUALIFICATION String,SPECIALIZATION text,REGISTRATIONNUMBER String,CELLNUMBER integer,MAIL String); ";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public DataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String userName, String password, String qualification, String specialization, String registrationNumber, String cellNumber, String mailId) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD", password);
        newValues.put("QUALIFICATION", qualification);
        newValues.put("SPECIALIZATION", specialization);
        newValues.put("REGISTRATIONNUMBER", registrationNumber);
        newValues.put("CONTACTNUMBER", cellNumber);
        newValues.put("EMAIL", mailId);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);

        System.out.println("USERNAME");
        System.out.println("PASSWORD");

        //   Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String UserName) {
        //String id=String.valueOf(ID);
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{UserName});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public String getSinlgeEntry(String userName) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public void updateEntry(String userName, String password, String qualification, String specialization, String registrationNumber, String cellNumber, String mailId) {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD", password);
        updatedValues.put("QUALIFICATION", qualification);
        updatedValues.put("SPECIALIZATION", specialization);
        updatedValues.put("REGISTRATIONNUMBER", registrationNumber);
        updatedValues.put("CONTACTNUMBER", cellNumber);
        updatedValues.put("EMAIL", mailId);

        String where = "USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{userName});
    }
}
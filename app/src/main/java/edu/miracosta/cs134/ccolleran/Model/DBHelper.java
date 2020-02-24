package edu.miracosta.cs134.ccolleran.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    // Task 1: Make constants for all database values
    // database name, version, table name, field names, primary key (psf shortcut)
    public static final String DATABASE_NAME = "ToDo2Day";
    public static final String TABLE_NAME = "Tasks";
    public static final int VERSION = 1;

    // naming convention: "field" in front for fields, "key" before "field" for primary key
    public static final String KEY_FIELD_ID = "_id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_IS_DONE = "is_done";

    public DBHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";

        Log.i(DATABASE_NAME, createSQL); // making sure string was created correctly
        db.execSQL(createSQL);
    }

    public void updateTask(Task task)
    {

        SQLiteDatabase db = getWritableDatabase();
        int isDoneStatus; // converting from boolean to int
        if (task.ismIsDone())
        {
            isDoneStatus = 1;
        }
        else
        {
            isDoneStatus = 0;
        }

        //String strSQL = "UPDATE myTable SET Column1 = someValue WHERE columnId = "+ someValue;
        String strSQL = "UPDATE " + TABLE_NAME
                + " SET " + FIELD_IS_DONE + " = " + isDoneStatus
                + " WHERE " + KEY_FIELD_ID  + " = " + task.getmId();

        db.execSQL(strSQL);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // 1) Drop the old table, recreate the new
        if (newVersion > oldVersion)
        {
            String dropSQL = "DROP TABLE IF EXISTS "
                    + TABLE_NAME;
            Log.i(DATABASE_NAME, dropSQL);
            db.execSQL(dropSQL);
            onCreate(db); // after dropping old db, now create new one
        }

    }

    /**
     * Adds a new task to the database
     *
     * @param task the new task to be added
     * @return the newly assigned ID (primary key)
     */
    public long addTask(Task task)
    {
        long id;
        // Decide whether we're reading or writing to/from the database

        // For ADDING tasks we are WRITING to the database

        SQLiteDatabase db = getWritableDatabase();

        // When we add tasks to the database, we use a data structure called
        // ContentValues (Key, value) pairs

        ContentValues values = new ContentValues();

        // Set up our key/values pairs
        values.put(FIELD_DESCRIPTION, task.getmDescription());
        values.put(FIELD_IS_DONE, task.ismIsDone() ? 1 : 0); // ternary statement so sql doesnt bomb out

        id = db.insert(TABLE_NAME, null, values);

        // After we're done, close the connection to the database
        db.close();
        return id;
    }

    public List<Task> getAllTasks()
    {

        List<Task> allTasks = new ArrayList<>();
        // Get the tasks from the database

        // For GETTING tasks we are READING to the database
        SQLiteDatabase db = getReadableDatabase();

        // Query the database to retrieve all records
        // Store them in a data structure known as a cursor
        Cursor cursor = db.query(TABLE_NAME, // What table to query from
                new String[]{KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE}, // Saying which fields we want
                null,
                null,
                null,
                null,
                null,
                null);

        // Loop through the cursor results, one at a time
        // 1) Instantiate a new Task object
        // 2) Add the new Task to the List

        if (cursor.moveToFirst()) // seeing if the cursor has anything in it
        {
            do
            {
                long id = cursor.getLong(0); // getters parameters are the indexes of the array which the cooresponding values are stored
                String description = cursor.getString(1);
                boolean isDone = cursor.getInt(2) == 1; // easy trick to get sql INTEGER to java boolean

                allTasks.add(new Task(id, description, isDone)); // adding task to the list
            }
            while(cursor.moveToNext()); // Go to next Task
        }

        // Close the cursor (if not done database will start to run slowly)
        cursor.close();

        // Close the connection (DO THIS BEFORE WRITING MORE CODE TO MAKE SURE CLOSES/ DON'T FORGET)
        db.close();

        return allTasks;
    }

    public void clearAllTasks() // method to delete all records within the table (table still exists)
    {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME, null, null);


        db.close(); // make sure to close database connection
    }
}

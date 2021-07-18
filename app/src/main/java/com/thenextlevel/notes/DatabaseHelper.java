package com.thenextlevel.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DatabaseName = "MyNotes";
    private static final int DatabaseVersion = 1;

    private static final String TableName = "my_notes";
    private static final String ColumnId = "id";
    private static final String ColumnTitle = "title";
    private static final String ColumnDescription = "description";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TableName +
                "(" + ColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ColumnTitle + " TEXT, " +
                ColumnDescription + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    void addNotes(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ColumnTitle, title);
        contentValues.put(ColumnDescription, description);

        long resultValue = db.insert(TableName, null, contentValues);

        if (resultValue == -1) {
            Toast.makeText(context, "Note not Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllNotes() {

        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;

        if (database != null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllNotes() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TableName;
        database.execSQL(query);
    }

    void updateNote(String title, String description, String id) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);

        long result = database.update(TableName, cv, "id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSingleItem(String id) {
        SQLiteDatabase database = this.getWritableDatabase();

        long result = database.delete(TableName, "id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Something went wrong while deleting Note", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
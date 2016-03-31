package yipengy.com.studentscore.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database open helper class
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS studentScore;";
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS studentScore (id INTEGER NOT NULL primary key,"
            + "quiz1 INTEGER NOT NULL, quiz2 INTEGER NOT NULL, quiz3 INTEGER NOT NULL, "
            + "quiz4 INTEGER NOT NULL, quiz5 INTEGER NOT NULL);";

    public DatabaseOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // create mortgage table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // leaves empty
    }
}
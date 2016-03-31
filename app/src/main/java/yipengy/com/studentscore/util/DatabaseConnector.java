package yipengy.com.studentscore.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import yipengy.com.studentscore.R;
import yipengy.com.studentscore.exception.RecordOutOfBoundException;
import yipengy.com.studentscore.model.Student;

/**
 * Database utilities.
 */
public class DatabaseConnector {
    private static final String TAG = "DatabaseConnector";
    // database name
    private static final String DATABASE_NAME = "StudentDb";
    // select all query
    private static final String SELECT_ALL = "SELECT * FROM studentScore;";

    private static final int MAX_RECORD = 40;

    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;
    private Context context;

    public DatabaseConnector(Context context) {
        this.context = context;
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);

        database = databaseOpenHelper.getWritableDatabase();
        database.execSQL(DatabaseOpenHelper.DROP_TABLE);
        database.execSQL(DatabaseOpenHelper.CREATE_TABLE);
    }

    // open the database
    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    // close the database
    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public void insertToDb(int fileResId) {
        BufferedReader br = null;
        try {
            InputStream inputStream = context.getResources().openRawResource(fileResId);

            InputStreamReader inputreader = new InputStreamReader(inputStream);
            br = new BufferedReader(inputreader);
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                // skip the header line
                if (lineNumber == 0) {
                    lineNumber++;
                    continue;
                }

                // throw an exception if have more than max number of input
                if (lineNumber > MAX_RECORD) {
                    throw new RecordOutOfBoundException(
                            "RecordOutOfBound: Only " + MAX_RECORD + "records are accepted. Ignoring the rest.");
                }

                // process the line.
                String[] tokens = line.split(" ");
                int SID = Integer.parseInt(tokens[0]);

                int[] scores = new int[tokens.length - 1];
                for (int i = 0; i < scores.length; i++) {
                    scores[i] = Integer.parseInt(tokens[i + 1]);
                }

                // insert into DB
                ContentValues newStudent = new ContentValues();
                newStudent.put("id", SID);
                newStudent.put("quiz1", scores[0]);
                newStudent.put("quiz2", scores[1]);
                newStudent.put("quiz3", scores[2]);
                newStudent.put("quiz4", scores[3]);
                newStudent.put("quiz5", scores[4]);
                open();
                if (database != null) {
                    Log.d(TAG, "Inserting " + newStudent.toString());
                    database.insert("studentScore", null, newStudent);
                }
                close();
                lineNumber++;
            }
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (RecordOutOfBoundException e) {
            Log.d(TAG, e.getMessage());
        } finally {
            // close the bufferedReader
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Student[] getAllStudents() {
        List<Student> students = new ArrayList<>();
        open();

        if (database != null) {
            Cursor cursor = database.rawQuery(SELECT_ALL, null);
            if (cursor.moveToFirst()) {
                do {
                    int totalColumn = cursor.getColumnCount();
                    if (totalColumn != 6) {
                        Log.d(TAG, "Error in database: " + totalColumn);
                        continue;
                    }

                    int id = cursor.getInt(0);
                    int[] scores = new int[5];

                    for (int i = 1; i < totalColumn; i++) {
                        scores[i - 1] = cursor.getInt(i);
                    }

                    students.add(new Student(id, scores));
                } while (cursor.moveToNext());
            }
        }
        return students.toArray(new Student[students.size()]);
    }
}

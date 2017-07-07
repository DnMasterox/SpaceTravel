package nshumakov.com.spacetravel.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by nshumakov on 06.07.2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DEBUG_TAG = DatabaseOpenHelper.class.getSimpleName();
    private static final boolean LOGV = false;

    public DatabaseOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Удаление всех таблиц из базы
     *
     * @param db - object of SQLiteDatabase
     */
    public void dropTables(SQLiteDatabase db) {

        if (LOGV) {
            Log.d(DEBUG_TAG, "onDropTables called");
        }
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Stats.TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        if (LOGV) {
            Log.v(DEBUG_TAG, "onCreate()");
        }
        db.execSQL("CREATE TABLE " + DatabaseContract.Stats.TABLE_NAME + " (" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " + DatabaseContract.Stats.ScoresColumns.NAME
                + " TEXT NOT NULL, " + DatabaseContract.Stats.ScoresColumns.SCORE + " INTEGER NOT NULL );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(DEBUG_TAG, "onUpgrade called");
    }
}
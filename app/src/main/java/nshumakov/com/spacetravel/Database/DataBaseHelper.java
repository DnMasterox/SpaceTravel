package nshumakov.com.spacetravel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nshumakov on 02.07.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScoresManager";
    private static final String TABLE_SCORES = "scores";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";
    private static final String KEY_CLICKSCORE = "clickScore";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORES_TABLE = "CREATE TABLE" + TABLE_SCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORE + " TEXT," + KEY_CLICKSCORE + " TEXT" + ")";
        db.execSQL(CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addStats(Stats stats) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("KEY_NAME", stats.getName());
        contentValues.put("KEY_SCORE", stats.getScore());
        contentValues.put("KEY_CLICK_SCORE", stats.getClickScore());
        db.insert(TABLE_SCORES, null, contentValues);
        db.close();
    }

    public Stats getScore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCORES, new String[]{KEY_ID, KEY_NAME, KEY_SCORE, KEY_CLICKSCORE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Stats stats = new Stats(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return stats;
    }

    public List<Stats> getAllStats() {
        List<Stats> statsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM" + TABLE_SCORES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Stats stats = new Stats();
                stats.setId(Integer.parseInt(cursor.getString(0)));
                stats.setName(cursor.getString(1));
                stats.setScore(cursor.getString(2));
                stats.setClickScore(cursor.getString(3));

                statsList.add(stats);
            }
            while (cursor.moveToNext());
        }
        return statsList;
    }

    public int updateStats(Stats stats) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, stats.getName());
        values.put(KEY_SCORE, stats.getScore());
        values.put(KEY_CLICKSCORE, stats.getClickScore());

        return db.update(TABLE_SCORES, values, KEY_ID + "=?",
                new String[]{String.valueOf(stats.getId())});
    }

    public void deleteStats(Stats stats) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_ID + "=?",
                new String[]{String.valueOf(stats.getId())});
        db.close();
    }

    public int getStatsCount() {
        String countQuery = "SELECT * FROM" + TABLE_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}

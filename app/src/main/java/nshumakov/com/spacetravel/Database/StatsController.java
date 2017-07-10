package nshumakov.com.spacetravel.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by nshumakov on 06.07.2017.
 */

public class StatsController {

    private static final boolean LOGV = false;
    private static int maxRowsInNames = -1;
    private static final String TAG = StatsController.class.getSimpleName();

    private StatsController() {

    }

    public static int getMaxRowsInNames() {

        return maxRowsInNames;
    }

    /**
     * Функция возвращает все данные из базы при запросе к ней
     *
     * @param context
     * @return
     */
    public static ArrayList<DatabaseContract.Stats> readScores(Context context) {

        ArrayList<DatabaseContract.Stats> list = null;
        try {
            DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(context);
            SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
            String[] columnsToTake = {BaseColumns._ID, DatabaseContract.Stats.ScoresColumns.NAME};
            Cursor cursor = sqliteDB.query(DatabaseContract.Stats.TABLE_NAME, columnsToTake, null, null, null, null,
                    DatabaseContract.Stats.DEFAULT_SORT);
            if (cursor.moveToFirst()) {
                list = new ArrayList<DatabaseContract.Stats>();
            }
            while (cursor.moveToNext()) {
                DatabaseContract.Stats oneRow = new DatabaseContract.Stats();
                oneRow.setId(cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)));
                oneRow.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Stats.ScoresColumns.NAME)));
                list.add(oneRow);
            }
            cursor.close();
            dbhelper.close();
        } catch (Exception e) {
            Log.e(TAG, "Failed to select Stats.", e);
        }
        return list;
    }

    public static void setMaxRowsInNames(int maxRowsInNames) {

        StatsController.maxRowsInNames = maxRowsInNames;
    }
    /**Изменение строки в списке*/
    public static void update(Context context, String comment, long l) {

        try {
            DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(context);
            SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
            String quer = null;
            int countRows = -1;
            Cursor cursor = sqliteDB.query(DatabaseContract.Stats.TABLE_NAME, new String[] { "count(*)" }, null, null, null,
                    null, DatabaseContract.Stats.DEFAULT_SORT);
            if (cursor.moveToFirst()) {
                countRows = cursor.getInt(0);
                if (LOGV) {
                    Log.v(TAG, "Count in Names table" + String.valueOf(countRows));
                }
            }
            cursor.close();
            quer = String.format("UPDATE " + DatabaseContract.Stats.TABLE_NAME + " SET " + DatabaseContract.Stats.ScoresColumns.SCORE
                    + " = '" + comment + "' WHERE " + BaseColumns._ID + " = " + l);
            Log.d("", "" + quer);
            sqliteDB.execSQL(quer);
            sqliteDB.close();
            dbhelper.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "Failed open database. ", e);
        } catch (SQLException e) {
            Log.e(TAG, "Failed to update Names. ", e);
        }
    }

    /**
     * Удаление строки из списка
     */
    public static void delete(Context context, long l) {

        DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(context);
        SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
        sqliteDB.delete(DatabaseContract.Stats.TABLE_NAME, BaseColumns._ID + " = " + l, null);
        sqliteDB.close();
        dbhelper.close();
    }

    /**
     * Эта функция создает запрос которые дальше записывает данные в нашу базу данных
     */
    public static void write(Context context, String name, int score) {

        try {
            //создали нашу базу и открыли для записи
            DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(context);
            SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
            String quer = null;
            int countRows = -1;
            //Открыли курсор для записи
            Cursor cursor = sqliteDB.query(DatabaseContract.Stats.TABLE_NAME, new String[]{"count(*)"}, null, null, null,
                    null, DatabaseContract.Stats.DEFAULT_SORT);
            if (cursor.moveToFirst()) {
                countRows = cursor.getInt(0);
                if (LOGV) {
                    Log.v(TAG, "Count in Stats table" + String.valueOf(countRows));
                }
            }
            cursor.close();
            if ((maxRowsInNames == -1) || (maxRowsInNames >= countRows)) {
                //дальше наш запрос в базу для записи полученных дынных из функции
                quer = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s);",
                        // таблица
                        DatabaseContract.Stats.TABLE_NAME,
                        // колонки
                        DatabaseContract.Stats.ScoresColumns.NAME, DatabaseContract.Stats.ScoresColumns.SCORE,
                        // поля
                        name, score);
            }
            //закрыли всю базу
            sqliteDB.execSQL(quer);
            sqliteDB.close();
            dbhelper.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "Failed open rimes database. ", e);
        } catch (SQLException e) {
            Log.e(TAG, "Failed to insert Names. ", e);
        }
    }
}
package nshumakov.com.spacetravel.Activities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.TextView;

import nshumakov.com.spacetravel.Database.DatabaseContract;
import nshumakov.com.spacetravel.Database.DatabaseOpenHelper;
import nshumakov.com.spacetravel.R;

/**
 * Created by nshumakov on 07.07.2017.
 */

public class DataActivity extends Activity {

    static final String TAG = DataActivity.class.getSimpleName();
    private Long mRowId;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        //получаем из инта нужный нам айдишник и открываем нужное поле
        long id = getIntent().getLongExtra("_id", -6);
        DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(getBaseContext());
        SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
        Cursor c = sqliteDB.query(DatabaseContract.Stats.TABLE_NAME, null, BaseColumns._ID + "=" + id, null, null, null,
                null);
        TextView lv = (TextView) findViewById(R.id.response);
        TextView tw = (TextView) findViewById(R.id.request);
        //выводим все в текствьюхи
        if (c.moveToFirst()) {
            tw.setText(c.getString(c.getColumnIndex(DatabaseContract.Stats.ScoresColumns.NAME)));
            lv.setText(c.getString(c.getColumnIndex(DatabaseContract.Stats.ScoresColumns.SCORE)));
        }
        dbhelper.close();
        sqliteDB.close();
        Log.v(TAG, "ID=" + id);
    }
}
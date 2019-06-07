package nshumakov.com.spacetravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import nshumakov.com.spacetravel.database.DatabaseContract;
import nshumakov.com.spacetravel.database.DatabaseOpenHelper;
import nshumakov.com.spacetravel.R;
import nshumakov.com.spacetravel.database.StatsController;

/**
 * Created by nshumakov on 07.07.2017.
 */

public class DataActivity extends Activity {

    private InterstitialAd mInterstitialAd;
    static final String TAG = DataActivity.class.getSimpleName();
    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5296887595223904~4971970093");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("53D0BAD3FD1E58BDA2191958FE8A90E2")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(" ca-app-pub-5296887595223904/1011957059");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("53D0BAD3FD1E58BDA2191958FE8A90E2")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build());


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, 1, 0, "Main menu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1: {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Intent intent = new Intent(this, StartActivity.class);
                    startActivity(intent);
                }

            }
            break;
        }
        return true;
    }
}
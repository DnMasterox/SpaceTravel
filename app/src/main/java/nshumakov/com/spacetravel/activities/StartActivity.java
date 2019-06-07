package nshumakov.com.spacetravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import nshumakov.com.spacetravel.googleSignIn.GoogleSignInActivity;
import nshumakov.com.spacetravel.R;
import nshumakov.com.spacetravel.services.MyService;

/**
 * Created by nshumakov on 03.06.2017.
 */

public class StartActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "StartActivity";

    private AdView mAdView;
    public static Intent music;
    private boolean bool = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5296887595223904~4971970093");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("53D0BAD3FD1E58BDA2191958FE8A90E2")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        ImageButton startButton = (ImageButton) findViewById(R.id.btnStart);
        startButton.setOnClickListener(this);

        ImageButton exitButton = (ImageButton) findViewById(R.id.btnExit);
        exitButton.setOnClickListener(this);

        ImageButton statsButton = (ImageButton) findViewById(R.id.btnStats);
        statsButton.setOnClickListener(this);

        ImageButton settingsButton = (ImageButton) findViewById(R.id.btnSettings);
        settingsButton.setOnClickListener(this);

        ImageButton google = (ImageButton) findViewById(R.id.googleAuth);
        google.setOnClickListener(this);

        ImageButton thanks = (ImageButton) findViewById(R.id.thanksTo);
        thanks.setOnClickListener(this);

        music = new Intent(this, MyService.class);
        startService(music);
    }

    /**
     * Обработка нажатия кнопок
     */
    public void onClick(View view) {
        switch (view.getId()) {
            //переход на сюрфейс
            case R.id.btnStart: {
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
            }
            break;
            //включение выключение музыки
            case R.id.btnSettings: {
                bool = !bool;
                if (bool == true) {
                    stopService(music);
                } else startService(music);

            }
            break;
            //доступ к доске лидеров из базы данных
            case R.id.btnStats: {
                Intent intent = new Intent();
                intent.setClass(this, ScoresActivity.class);
                startActivity(intent);
            }
            break;
            //выход
            case R.id.btnExit: {
                stopService(music);
                finishAffinity();
            }
            break;
            case R.id.googleAuth: {
                Intent intent = new Intent(this, GoogleSignInActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.thanksTo: {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://millionthvector.blogspot.com")));
            }
            break;

            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        mAdView.destroy();
        stopService(music);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mAdView.resume();
        super.onResume();
    }
}

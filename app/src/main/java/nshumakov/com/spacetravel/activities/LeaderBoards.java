package nshumakov.com.spacetravel.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import nshumakov.com.spacetravel.database.StatsController;
import nshumakov.com.spacetravel.R;

public class LeaderBoards extends Activity {

    private static final String TAG = "LeaderBoards";
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);
        Intent intent = getIntent();

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-5296887595223904/5865894147");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("53D0BAD3FD1E58BDA2191958FE8A90E2")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build());


        final TextView scores = (TextView) findViewById(R.id.scores);
        final String score = intent.getStringExtra("score");
        scores.setText("Your score is: " + score);
        final EditText name = (EditText) findViewById(R.id.name);
        ImageButton btn = (ImageButton) findViewById(R.id.button_ok);
        ImageButton btn1 = (ImageButton) findViewById(R.id.button_mm);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    StatsController.write(getBaseContext(), '"' + name.getText().toString() + '"', Integer.valueOf(score));
                    Intent intent = new Intent(getApplicationContext(), ScoresActivity.class);
                    startActivity(intent);
                    Log.e("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(intent);
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });
    }
}
package nshumakov.com.spacetravel.Activities;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import nshumakov.com.spacetravel.Database.DataBaseHelper;
import nshumakov.com.spacetravel.GamePlay.GameView;
import nshumakov.com.spacetravel.R;
import nshumakov.com.spacetravel.Services.MyService;

/**
 * Created by nshumakov on 03.06.2017.
 */

public class StartActivity extends Activity implements View.OnClickListener {
    public static Intent music;
    private boolean bool = false;
    public static DataBaseHelper dataBaseHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startButton = (ImageButton) findViewById(R.id.btnStart);
        startButton.setOnClickListener(this);

        ImageButton exitButton = (ImageButton) findViewById(R.id.btnExit);
        exitButton.setOnClickListener(this);

        ImageButton leaderboardButton = (ImageButton) findViewById(R.id.btnLeaderboard);
       /* leaderboardButton.setOnClickListener(this);*/

        ImageButton settingsButton = (ImageButton) findViewById(R.id.btnSettings);
        settingsButton.setOnClickListener(this);
        music = new Intent(this, MyService.class);
      /*  startService(music);*/
        dataBaseHelper = new DataBaseHelper(this);
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
            case R.id.btnSettings: {
                bool = !bool;
                if (bool == true) {
                    stopService(music);
                } else startService(music);

            }
           /* break;
            case R.id.btnLeaderboard: {

            }*/
            break;
            //выход
            case R.id.btnExit: {
                stopService(music);
                finishAffinity();
            }
            break;

            default:
                break;
        }
    }
    public void onStartAct(View view){
        Intent intent = new Intent(StartActivity.this, LeaderBoards.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        stopService(music);
        super.onDestroy();
    }
}

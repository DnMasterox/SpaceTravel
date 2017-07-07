package nshumakov.com.spacetravel.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import nshumakov.com.spacetravel.R;
import nshumakov.com.spacetravel.Services.MyService;

/**
 * Created by nshumakov on 03.06.2017.
 */

public class StartActivity extends Activity implements View.OnClickListener {
    public static Intent music;
    private boolean bool = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startButton = (ImageButton) findViewById(R.id.btnStart);
        startButton.setOnClickListener(this);

        ImageButton exitButton = (ImageButton) findViewById(R.id.btnExit);
        exitButton.setOnClickListener(this);

        ImageButton statsButton = (ImageButton) findViewById(R.id.btnStats);
        statsButton.setOnClickListener(this);

        ImageButton settingsButton = (ImageButton) findViewById(R.id.btnSettings);
        settingsButton.setOnClickListener(this);
        music = new Intent(this, MyService.class);
      /*  startService(music);*/
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
            break;
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
        stopService(music);
        super.onDestroy();
    }
}

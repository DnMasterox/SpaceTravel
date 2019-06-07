package nshumakov.com.spacetravel.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import nshumakov.com.spacetravel.gamePlay.GameView;
import nshumakov.com.spacetravel.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    public static int HEIGHT;
    public static int WIDTH;
    public static float xAccelerometer;
    public static float yAccelerometer;
    public static float zAccelerometer;
    private boolean run = false;
    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WIDTH = displayMetrics.widthPixels;
        HEIGHT = displayMetrics.heightPixels;
        super.onCreate(savedInstanceState);
        FrameLayout game = new FrameLayout(this);
        FrameLayout.LayoutParams gameLayoutParam = new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameView(this);
        LinearLayout gameWidgets = new LinearLayout(this);
        game.addView(gameView);
        game.addView(gameWidgets);
        setContentView(game, gameLayoutParam);
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        manager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                xAccelerometer = event.values[0];
                yAccelerometer = event.values[1];
                zAccelerometer = event.values[2];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resumeButton:


            case R.id.pauseButton: {
                run = !run;
                if (run) {
                    gameView.pause();
                } else gameView.resume();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                gameView.countLive = 0;
                gameView.countDeath = 0;
                break;
            case R.id.mainMenu:
                Intent intent2 = new Intent(this, StartActivity.class);
                startActivity(intent2);
                break;
            case R.id.exitApp:
                stopService(StartActivity.music);
                finishAffinity();
                break;
            case R.id.pauseButton: {
                run = !run;
                if (run) {
                    gameView.pause();
                } else
                    this.onPause();
                try {
                    Thread.sleep(1000);
                    this.onResume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            break;
        }
        return true;
    }

   /* @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onResume() {
        gameView.resume();
        if (gameView.player.getPlLives() <= 0) {
            gameView = new GameView(this);
        } else
            gameView.setVisibility(View.VISIBLE);

        super.onResume();
      /*  Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onPause() {
        stopService(StartActivity.music);
        gameView.pause();
        super.onPause();
        gameView.setVisibility(View.GONE);
       /* Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onDestroy() {
        stopService(StartActivity.music);
        super.onDestroy();
        finishAffinity();
       /* Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();*/
    }

    public void onStartAct(View view) {
    }
}

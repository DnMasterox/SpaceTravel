package nshumakov.com.spacetravel.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import nshumakov.com.spacetravel.GamePlay.GameView;
import nshumakov.com.spacetravel.Services.MyService;
import nshumakov.com.spacetravel.R;

public class MainActivity extends Activity {
    public static int HEIGHT;
    public static int WIDTH;
    public static float xAccelerometer;
    public static float yAccelerometer;
    public static float zAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WIDTH = displayMetrics.widthPixels;
        HEIGHT = displayMetrics.heightPixels;
        super.onCreate(savedInstanceState);
        FrameLayout game = new FrameLayout(this);
        FrameLayout.LayoutParams gameLayoutParam = new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GameView gameView = new GameView(this);
        LinearLayout gameWidgets = new LinearLayout(this);
        game.addView(gameView);
        game.addView(gameWidgets);
        setContentView(game, gameLayoutParam);




        SensorManager manager =  (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
        },sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }


  /*  @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resumeButton:


            case R.id.restartGameButton:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                GameView.countLive = 0;
                GameView.countDeath = 0;
        }
    }*/

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
                GameView.countLive = 0;
                GameView.countDeath = 0;
                break;
            case R.id.mainMenu:
                Intent intent2 = new Intent(this, StartActivity.class);
                startActivity(intent2);
                break;
            case R.id.exitApp:
                stopService(StartActivity.music);
                finishAffinity();
                /*Intent intent3 = new Intent(this, StartActivity.class);
                startActivity(intent3);
                finish();*/
                break;
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        GameView.countLive = 0;
        GameView.countDeath = 0;
        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        stopService(StartActivity.music);
        super.onDestroy();
        finishAffinity();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("Shit", "onDestroy()");
    }

}

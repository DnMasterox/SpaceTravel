package nshumakov.com.spacetravel;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
    public static int HEIGHT;
    public static int WIDTH;
    Intent music;

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
        music = new Intent(this, MyService.class);
        startService(music);
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
                stopService(music);
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
        stopService(music);
        super.onDestroy();
        finishAffinity();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("Shit", "onDestroy()");
    }

}

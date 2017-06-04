package nshumakov.com.spacetravel;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {
    public static int HEIGHT;
    public static int WIDTH;

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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resumeButton:



            case R.id.restartGameButton:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                GameView.countLive = 0;
                GameView.countDeath = 0;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("Shit", "onDestroy()");
    }

}

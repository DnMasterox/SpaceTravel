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
    private MediaPlayer backMusic = new MediaPlayer();
    public static int countDeath;
    public static int countLive = GameView.countLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        backMusic();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WIDTH = displayMetrics.widthPixels;
        HEIGHT = displayMetrics.heightPixels;
       /*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameview = new GameView(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(gameview);*/
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);*/

        FrameLayout game = new FrameLayout(this);
        FrameLayout.LayoutParams gameLayoutParam = new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       /* GameView gameView = new GameView(this, R.drawable.sky, R.drawable.enemy_a, R.drawable.enemy_b, R.drawable.enemy_c, R.drawable.enemy_d, R.drawable.enemy_e);*/
        GameView gameView = new GameView(this);
        LinearLayout gameWidgets = new LinearLayout(this);

        Button restartGameButton = new Button(this);
       /* Button resumeButton = new Button(this);*/
        /*TextView myText = new TextView(this);*/

        /*resumeButton.setWidth(WIDTH / 10);
        resumeButton.setId(R.id.resumeButton);
        resumeButton.setText("Resume");*/
        restartGameButton.setWidth(WIDTH / 10);
        restartGameButton.setId(R.id.restartGameButton);
        restartGameButton.setText("ReStart");
       /* myText.setText("zZz");

        gameWidgets.addView(myText);*/
        gameWidgets.addView(restartGameButton);
      /*  gameWidgets.addView(resumeButton);*/

        game.addView(gameView);
        game.addView(gameWidgets);

        setContentView(game, gameLayoutParam);
        restartGameButton.setOnClickListener(this);
      /*  resumeButton.setOnClickListener(this);*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resumeButton:
                Intent intent = new Intent(this, MainActivity.class);
                backMusic.stop();
                startActivity(intent);
                GameView.countLive = 0;
                GameView.countDeath = 0;
            case R.id.restartGameButton:
                Intent intent1 = new Intent(this, MainActivity.class);
                backMusic.stop();
                startActivity(intent1);
                GameView.countLive = 0;
                GameView.countDeath = 0;
        }
    }

    /*Музыка на заднем плане*/

    private void backMusic() {
        try {
            if (backMusic.isPlaying()) {
                backMusic.reset();
                backMusic.release();

            }
            AssetFileDescriptor descriptor;
            descriptor = getAssets().openFd("waves.mp3");
            backMusic.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            backMusic.prepare();
            backMusic.setLooping(true);
            backMusic.start();
        } catch (IllegalStateException illexc) {
            illexc.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        backMusic.stop();
        startActivity(intent);
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

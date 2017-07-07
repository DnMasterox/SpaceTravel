package nshumakov.com.spacetravel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

import nshumakov.com.spacetravel.GamePlay.GameView;
import nshumakov.com.spacetravel.Activities.MainActivity;
import nshumakov.com.spacetravel.R;

/**
 * Created by nshumakov on 04.04.2017.
 */

public class Enemy {
    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    private Random rnd = new Random();
    private boolean deathFlag = false;
    private boolean reverse = false;

    public boolean isDeathFlag() {
        return deathFlag;
    }

    public void setDeathFlag(boolean deathFlag) {
        this.deathFlag = deathFlag;
    }

    /**
     * Х и У коорданаты
     */
    public int x;
    public int y;

    /**
     * Скорость
     */
    public int speedX;
    public int speedY = 5;

    /**
     * Высота и ширина спрайта
     */
    public int width;
    public int height;

    public GameView gameView;
    public Bitmap bitmap;

    /**
     * Конструктор класса
     */
    public Enemy(GameView gameView, Bitmap bmp, int speedX) {
        this.gameView = gameView;
        this.bitmap = bmp;
        this.x = GameWidth;
        this.y = rnd.nextInt(GameHeight - 100);
        this.speedX = speedX;

        this.width = 30;
        this.height = 50;
    }

    public Enemy(GameView gameView, Bitmap bmp, int speedX, int x, int y) {
        this.gameView = gameView;
        this.bitmap = bmp;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    private void update() {
        int a = speedX;
        switch (a) {

            case 10: {
                speedY = 0;
                x -= speedX;
            }
            case 15: {//снаряды от врагов
                speedY = 0;
                x -= speedX;
            }
            default: {
                x -= speedX;
                if (!reverse) {
                    y += speedY;
                    if (y >= GameHeight - bitmap.getHeight()/2) {
                        reverse = true;
                    }
                } else {
                    y -= speedY;
                    if (y <= 0) {
                        reverse = false;
                    }
                }
            }
        }
        if (deathFlag) {
            speedY = 0;
            speedX = 30;
        } else if (x <= 5) {
            x += speedX;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void onExplode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = BitmapFactory.decodeResource(gameView.getContext().getResources(), R.drawable.explosion);
            }
        }).start();
    }
}

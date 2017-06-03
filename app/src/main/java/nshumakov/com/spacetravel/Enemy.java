package nshumakov.com.spacetravel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by nshumakov on 04.04.2017.
 */

public class Enemy {
    private int Height = MainActivity.HEIGHT;
    private int Width = MainActivity.WIDTH;
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
    public Bitmap bmp;

    /**
     * Конструктор класса
     */
    public Enemy(GameView gameView, Bitmap bmp, short speedX) {
        this.gameView = gameView;
        this.bmp = bmp;

        /*int spd = rnd.nextInt(10);
        if (spd == 0) spd = 1;*/
        this.x = Width;
        this.y = rnd.nextInt(Height - 100);
        this.speedX = speedX;

        this.width = 20;
        this.height = 20;
    }

    private void update() {
        int a = speedX;
        switch (a) {
            case 10:{
                x -= speedX;
            }
            default: {
                x -= speedX;
                if (!reverse) {
                    y += speedY;
                    if (y >= Height - bmp.getHeight()) {
                        reverse = true;
                    }
                } else {
                    y -= speedY;
                    if (y <= 0) {
                        reverse = false;
                    }
                }
                if (deathFlag) {
                    speedY = 0;
                    speedX = 30;
                } else if (x <= 5) {
                    x += speedX;
                }
            }
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    public void onExplode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bmp = BitmapFactory.decodeResource(gameView.getContext().getResources(), R.drawable.explosion);
            }
        }).start();

    }
}

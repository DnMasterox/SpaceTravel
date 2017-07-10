package nshumakov.com.spacetravel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import nshumakov.com.spacetravel.GamePlay.GameView;
import nshumakov.com.spacetravel.Activities.MainActivity;
import nshumakov.com.spacetravel.R;


/**
 * Created by nshumakov on 21.04.2017.
 */

public class Boss {
    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    private boolean deathFlag = false;
    private boolean reverseY = false;
    private boolean reverseX = false;
    public int countCrash;


    public void setReverse(boolean reverse) {
        reverse = !reverse;
        this.reverseY = reverse;
    }

    public boolean isDeathFlag() {
        return deathFlag;
    }

    /**
     * Х и У коорданаты
     */
    public int x;
    public int y;

    /**
     * Скорость
     */
    private int speedX = 10;
    private int speedY = 5;

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
    public Boss(GameView gameView, Bitmap bitmap, int countCrash) {
        this.gameView = gameView;
        this.bitmap = bitmap;
        this.countCrash = countCrash * 10;
        this.x = GameWidth;
        this.y = GameHeight / 2;
        this.speedX = countCrash * 2;

        this.width = 50;
        this.height = 50;
    }

    private void update() {
        if (!reverseX) {
            x -= speedX;
            if (x <= 5) {
                reverseX = true;
            }
        } else {
            x += speedX;
            if (x >= GameWidth - bitmap.getWidth()) {
                reverseX = false;
            }
        }
        if (!reverseY) {
            y += speedY;
            if (y >= GameHeight - bitmap.getHeight()) {
                reverseY = true;
            }
        } else {
            y -= speedY;
            if (y <= 0) {
                reverseY = false;
            }
        }
        if (countCrash <= 0) {
            reverseX = false;
            reverseY = false;
           /* bitmap.recycle();*/
            bitmap = BitmapFactory.decodeResource(gameView.getContext().getResources(), R.drawable.explosion_boss);
            deathFlag = true;
            speedY = 0;
            speedX = 30;
            if (x < 0 || y < 0) {
                bitmap.recycle();
            }
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}


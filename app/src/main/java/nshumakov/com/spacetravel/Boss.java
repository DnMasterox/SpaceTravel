package nshumakov.com.spacetravel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


/**
 * Created by nshumakov on 21.04.2017.
 */

public class Boss {
    private int Height = MainActivity.HEIGHT;
    private int Width = MainActivity.WIDTH;
    private boolean deathFlag = false;
    private boolean reverseY = false;
    private boolean reverseX = false;
    public int countChrash;


    public void setReverse(boolean reverse) {
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
    public Bitmap bmp;

    /**
     * Конструктор класса
     */
    public Boss(GameView gameView, Bitmap bmp, short countChrash) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.countChrash = countChrash;
        this.x = Width;
        this.y = Height / 2;
        this.speedX = speedX;

        this.width = 20;
        this.height = 20;
    }

    private void update() {
        if (!reverseX) {
            x -= speedX;
            if (x <= 100) {
                reverseX = true;
            }
        } else {
            x += speedX;
            if (x >= Width - bmp.getWidth()) {
                reverseX = false;
            }
        }
        if (!reverseY) {
            y += speedY;
            if (y >= Height - bmp.getHeight()) {
                reverseY = true;
            }
        } else {
            y -= speedY;
            if (y <= 0) {
                reverseY = false;
            }
        }
        if (countChrash <= 0) {
            reverseX = false;
            reverseY = false;
            bmp = BitmapFactory.decodeResource(gameView.getContext().getResources(), R.drawable.explosion_boss);
            deathFlag = true;
            speedY = 0;
            speedX = 40;
        } else if (x <= Width / 10) {
            x += speedX;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }
}


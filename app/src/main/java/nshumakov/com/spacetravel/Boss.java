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
    private boolean reverse = false;
    public int countChrash;

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
        if (countChrash <= 0) {
            bmp = BitmapFactory.decodeResource(gameView.getContext().getResources(), R.drawable.explosion_boss);
            deathFlag=true;
            speedY = 0;
            speedX = 30;
        } else if (x <= Width / 10) {
            x += speedX;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }
}


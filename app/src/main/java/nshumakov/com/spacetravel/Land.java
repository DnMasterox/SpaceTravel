package nshumakov.com.spacetravel;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by nshumakov on 15.04.2017.
 */

public class Land {
    private int Height = MainActivity.HEIGHT;
    private int Width = MainActivity.WIDTH;
    /**
     * Х и У коорданаты
     */
    private int mBGFarMoveX = 0;
    private int mBGNearMoveX = 0;
    private int x;
    private int speed;

    private GameView gameView;

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    private Bitmap bmp;

    /**
     * Конструктор класса
     */
    public Land(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.x = 0;
        this.speed = 1;

    }

    public void update() {
        x -= speed;
    }

    public void onDraw(Canvas canvas) {

        mBGFarMoveX = mBGFarMoveX - speed;
        mBGNearMoveX = mBGNearMoveX - speed * 4;
        int newFarX = bmp.getWidth() - (-mBGFarMoveX);
        if (newFarX <= 0) {
            mBGFarMoveX = 0;
            canvas.drawBitmap(bmp, mBGFarMoveX, 0, null);
        } else {
            canvas.drawBitmap(bmp, mBGFarMoveX, 0, null);
            canvas.drawBitmap(bmp, newFarX, 0, null);
            update();
        }
    }
}

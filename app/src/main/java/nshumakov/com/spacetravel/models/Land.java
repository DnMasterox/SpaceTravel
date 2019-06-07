package nshumakov.com.spacetravel.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import nshumakov.com.spacetravel.gamePlay.GameView;

/**
 * Created by nshumakov on 15.04.2017.
 */

public class Land extends BaseModel  {
    /**
     * Х и У коорданаты
     */
    private int mBGFarMoveX = 0;
    private int mBGNearMoveX = 0;
    private int x;
    private int speed;

    public Bitmap bitmap;

    /**
     * Конструктор класса
     */
    public Land(GameView gameView, Bitmap bitmap, int speed) {
        GameView gameView1 = gameView;
        this.bitmap = bitmap;
        this.x = 0;
        this.speed = speed;

    }

    public void update() {
        x -= speed;
    }

    public void draw(Canvas canvas) {
        mBGFarMoveX = mBGFarMoveX - speed;
        mBGNearMoveX = mBGNearMoveX - speed * 4;
        int newFarX = bitmap.getWidth() - (-mBGFarMoveX);
        if (newFarX <= 0) {
            mBGFarMoveX = 0;
            canvas.drawBitmap(bitmap, mBGFarMoveX, 0, null);
        } else {
            canvas.drawBitmap(bitmap, mBGFarMoveX, 0, null);
            canvas.drawBitmap(bitmap, newFarX, 0, null);
            update();
        }
    }
}

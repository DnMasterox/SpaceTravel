package nshumakov.com.spacetravel.Models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import nshumakov.com.spacetravel.GamePlay.GameView;
import nshumakov.com.spacetravel.Activities.MainActivity;

/**
 * Created by nshumakov on 15.04.2017.
 */

public class Land {
    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    /**
     * Х и У коорданаты
     */
    private int mBGFarMoveX = 0;
    private int mBGNearMoveX = 0;
    private int x;
    private int speed;

    private GameView gameView;

    public void setBmp(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap bitmap;

    /**
     * Конструктор класса
     */
    public Land(GameView gameView, Bitmap bitmap) {
        this.gameView = gameView;
        this.bitmap = bitmap;
        this.x = 0;
        this.speed = 1;

    }

    public void update() {
        x -= speed;
    }

    public void onDraw(Canvas canvas) {

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

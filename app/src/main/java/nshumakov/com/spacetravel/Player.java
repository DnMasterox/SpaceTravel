package nshumakov.com.spacetravel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by nshumakov on 04.04.2017.
 */
public class Player {

    private int Height = MainActivity.HEIGHT;
    private int Width = MainActivity.WIDTH;
    boolean pl = false;
    /**
     * Объект главного класса
     */
    private GameView gameView;

    //спрайт

    public Bitmap getBmp() {
        return bmp;
    }

    private Bitmap bmp = null;
    private Bitmap bmpA = null;
    private Bitmap bmpB = null;

    //х и у координаты рисунка
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //конструктор
    public Player(GameView gameView) {
        this.gameView = gameView;
        this.x = 5;                        //отступ по х нет
        this.y = Height / 2 - Height / 10; //делаем по центру
        bmpA = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pl_sprite_cr_cr);
        bmpB = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pl_sprite_cr);
    }

    //рисуем наш спрайт
    public void onDraw(Canvas canvas) {
        Animated();
        canvas.drawBitmap(bmp, x, y, null);
    }

    private void Animated() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pl = !pl;
                if (pl) {
                    bmp = bmpA;
                } else {
                    bmp = bmpB;
                }

            }
        }).start();
    }
}
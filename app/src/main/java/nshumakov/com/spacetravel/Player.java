package nshumakov.com.spacetravel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by nshumakov on 04.04.2017.
 */
public class Player {

    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    boolean pl = false;
    private int plLives = 10;
    private int width = 20;
    private int height = 20;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    int[] imgsArray = new int[]{R.drawable.lifepoints_a, R.drawable.lifepoints_b, R.drawable.lifepoints_c,
            R.drawable.lifepoints_d, R.drawable.lifepoints_e, R.drawable.lifepoints_f, R.drawable.lifepoints_g,
            R.drawable.lifepoints_h, R.drawable.lifepoints_i, R.drawable.lifepoints_j};

    public int getPlLives() {
        return plLives;
    }

    public void setPlLives(int plLives) {
        this.plLives = plLives;
    }

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
        this.y = GameHeight / 2 - GameHeight / 10; //делаем по центру
        bmpA = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pl_sprite_cr_cr);
        bmpB = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.pl_sprite_cr);

    }

    //рисуем наш спрайт
    public void onDraw(Canvas canvas) {
        Animated();
        canvas.drawBitmap(bmp, x, y, null);
        canvas.drawBitmap(Lives(plLives), 5, GameHeight - Lives(plLives).getHeight(), null);
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

    private Bitmap Lives(int a) {
        int b = 0;
        switch (a) {
            case 1:
                b = imgsArray[9];
                break;
            case 2:
                b = imgsArray[8];
                break;
            case 3:
                b = imgsArray[7];
                break;
            case 4:
                b = imgsArray[6];
                break;
            case 5:
                b = imgsArray[5];
                break;
            case 6:
                b = imgsArray[4];
                break;
            case 7:
                b = imgsArray[3];
                break;
            case 8:
                b = imgsArray[2];
                break;
            case 9:
                b = imgsArray[1];
                break;
            case 10:
                b = imgsArray[0];
                break;
        }
        return BitmapFactory.decodeResource(gameView.getResources(), b);
    }
}
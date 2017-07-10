package nshumakov.com.spacetravel.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import nshumakov.com.spacetravel.GamePlay.GameView;
import nshumakov.com.spacetravel.Activities.MainActivity;
import nshumakov.com.spacetravel.R;

/**
 * Created by nshumakov on 04.04.2017.
 */
public class Player {

    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    private int plLives = 10;
    private static final int BMP_ROWS = 2;
    private static final int BMP_COLUMNS = 8;
    private int currentFrame = 0;
    private int width;
    private int height;

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
    private Bitmap bmp;

    //х и у координаты рисунка
    public static int x;
    public static int y;

    //конструктор
    public Player(GameView gameView) {
        this.gameView = gameView;
        this.x = 5;                        //отступ по х нет
        this.y = GameHeight / 2 - GameHeight / 10; //делаем по центру
        this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.player);
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;

    }

    //рисуем наш спрайт
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
     /*   canvas.drawBitmap(Lives(plLives), 5, GameHeight - Lives(plLives).getHeight(), null);*/
        canvas.drawBitmap(Lives(plLives), 5, GameHeight - GameHeight / 9, null);


    }

    public void update() {
        currentFrame = ++currentFrame % BMP_COLUMNS;
        y = y + 10 * Integer.valueOf((int) MainActivity.yAccelerometer);
        if (y <= 0) {
            y = 0;
        } else if (y >= GameHeight - Lives(plLives).getHeight() - bmp.getHeight() / 2) {
            y = GameHeight - Lives(plLives).getHeight() - bmp.getHeight() / 2;
        }

    }

    private Bitmap Lives(int a) {
        int bitmap = 0;
        switch (a) {
            case 1:
                bitmap = imgsArray[9];
                break;
            case 2:
                bitmap = imgsArray[8];
                break;
            case 3:
                bitmap = imgsArray[7];
                break;
            case 4:
                bitmap = imgsArray[6];
                break;
            case 5:
                bitmap = imgsArray[5];
                break;
            case 6:
                bitmap = imgsArray[4];
                break;
            case 7:
                bitmap = imgsArray[3];
                break;
            case 8:
                bitmap = imgsArray[2];
                break;
            case 9:
                bitmap = imgsArray[1];
                break;
            case 10:
                bitmap = imgsArray[0];
                break;
        }
        return BitmapFactory.decodeResource(gameView.getResources(), bitmap);
    }
}
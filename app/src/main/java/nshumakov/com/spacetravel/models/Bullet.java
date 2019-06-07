package nshumakov.com.spacetravel.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import nshumakov.com.spacetravel.gamePlay.GameView;
import nshumakov.com.spacetravel.activities.MainActivity;

/**
 * Created by nshumakov on 04.04.2017.
 */

public class Bullet extends BaseModel  {
    private int GameHeight = MainActivity.HEIGHT;
    /**
     * Картинка
     */
    public Bitmap bitmap;

    /**
     * Позиция
     */
    public int x;
    public int y;

    /**
     * Скорость по Х=15
     */
    public static int mSpeed = 30;

    public double angle;

    /**
     * Ширина
     */
    public int width;

    /**
     * Ввыоста
     */
    public int height;

    public GameView gameView;

    /**
     * Конструктор
     */
    public Bullet(GameView gameView, Bitmap bitmap) {
        this.gameView = gameView;
        this.bitmap = bitmap;
        this.x = Player.x;//позиция по Х
        this.y = Player.y + GameHeight / 10;//позиция по Y
        this.width = bitmap.getWidth(); //ширина снаряда
        this.height = bitmap.getHeight() * 2;  //высота снаряда
        //угол полета пули в зависипости от координаты касания к экрану
        angle = Math.atan((double) (y - gameView.shotY) / (x - gameView.shotX));
    }

    public Bullet(GameView gameView, Bitmap bitmap, int height) {
        this.gameView = gameView;
        this.bitmap = bitmap;
        this.x = 0;//позиция по Х
        this.y = 0;//позиция по Y
        this.width = 1; //ширина снаряда
        this.height = height;  //высота снаряда
    }

    /**
     * Перемещение объекта, его направление
     */
    public void update() {
        x += mSpeed * Math.cos(angle);         //движение по Х со скоростью mSpeed и углу заданном координатой angle
        y += mSpeed * Math.sin(angle);// движение по У -//-
        if (mSpeed <= 0) {
            mSpeed = 30;
        }
    }

    /**
     * Рисуем наши спрайты
     */
    public void draw(Canvas canvas) {
        update();                              //говорим что эту функцию нам нужно вызывать для работы класса
        canvas.drawBitmap(bitmap, x, y, null);
    }
}

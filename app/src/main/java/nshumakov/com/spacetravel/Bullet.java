package nshumakov.com.spacetravel;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by nshumakov on 04.04.2017.
 */

public class Bullet {
    private int Height = MainActivity.HEIGHT;
    private int Width = MainActivity.WIDTH;
    /**
     * Картинка
     */
    private Bitmap bitmap;

    /**
     * Позиция
     */
    public int x;
    public int y;

    /**
     * Скорость по Х=15
     */
    private int mSpeed = 25;

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
        this.x = 50;//позиция по Х
        this.y = (Height / 2 - Height / 10) + 50;//позиция по Y
        this.width = 100; //ширина снаряда
        this.height = 100;  //высота снаряда
        //угол полета пули в зависипости от координаты касания к экрану
        angle = Math.atan((double) (y - gameView.shotY) / (x - gameView.shotX));
    }

    /**
     * Перемещение объекта, его направление
     */
    private void update() {
        x += mSpeed * Math.cos(angle);         //движение по Х со скоростью mSpeed и углу заданном координатой angle
        y += mSpeed * Math.sin(angle);         // движение по У -//-
    }

    /**
     * Рисуем наши спрайты
     */
    public void onDraw(Canvas canvas) {
        update();                              //говорим что эту функцию нам нужно вызывать для работы класса
        canvas.drawBitmap(bitmap, x, y, null);
    }
}

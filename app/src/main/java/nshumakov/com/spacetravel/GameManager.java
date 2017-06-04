package nshumakov.com.spacetravel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


import java.util.Iterator;


/**
 * Created by nshumakov on 03.06.2017.
 */

public class GameManager extends Thread {

    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    private Paint text = new Paint();

    /**
     * Наша скорость в мс = 10
     */
    static final long FPS = 30;

    /**
     * Объект класса
     */
    private GameView view;
    /**
     * gameOver
     */
    Bitmap gameOver;
    /**
     * Переменная задавания состояния потока рисования
     */
    private boolean running = false;

    /**
     * Конструктор класса
     */
    public GameManager(GameView view) {
        this.view = view;
    }

    /**
     * Задание состояния потока
     */
    public void setRunning(boolean run) {
        running = run;
    }

    /**
     * Действия, выполняемые в потоке
     */

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        text.setTextSize(view.getHeight() / 30);
        text.setColor(Color.BLACK);//цвет отображаемого текста
        text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));//тип текста
        gameOver = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.gameover);
        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                // подготовка Canvas-а
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    if (view.player.getPlLives() > 0) {
                        //первый рисуется фон
                        view.landing.onDraw(canvas);
                        //вторым пули
                        Iterator<Bullet> j = view.ball.iterator();
                        while (j.hasNext()) {
                            Bullet b = j.next();
                            if (b.x >= 1000 || b.x <= 1000) {
                                b.onDraw(canvas);
                            } else {
                                j.remove();
                            }
                        }
                        //третий игрок
                        view.player.onDraw(canvas);
                        view.testCollision();
                        Iterator<Enemy> i = view.enemy.iterator();
                        while (i.hasNext()) {
                            Enemy e = i.next();
                            if (e.x <= 1000 || e.x >= 1000) {//была ошибка
                                e.onDraw(canvas);
                            } else {
                                i.remove();
                            }
                        }
                    } else {
                        view.landing.onDraw(canvas);
                      /*  canvas.drawColor(Color.BLACK);*/
                        canvas.drawBitmap(gameOver, GameWidth / 2 - gameOver.getWidth() / 2, GameHeight / 2 - gameOver.getHeight() / 2, null);
                        canvas.drawText("Your score is: " + String.valueOf(view.countDeath), GameWidth / 2 - gameOver.getWidth() / 2, GameHeight / 2 + gameOver.getHeight(), text);//Счётчик убийств
                    }
                    view.onDraw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(1);
            } catch (Exception e) {
            }
        }
    }
}

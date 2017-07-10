package nshumakov.com.spacetravel.GamePlay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


import java.util.Iterator;

import nshumakov.com.spacetravel.Activities.LeaderBoards;
import nshumakov.com.spacetravel.Activities.MainActivity;
import nshumakov.com.spacetravel.Models.Bullet;
import nshumakov.com.spacetravel.Models.Enemy;


/**
 * Created by nshumakov on 03.06.2017.
 */

public class GameManager extends Thread {

    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    private Paint text = new Paint();
    private Paint textDeathcount = new Paint();
    public boolean gameOver = false;
    public Context context;

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
    Bitmap gameOverBmp;
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

    public boolean isRunning() {
        return running;
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
        textDeathcount.setTextSize(view.getHeight() / 40);
        textDeathcount.setColor(Color.BLACK);//цвет отображаемого текста
        textDeathcount.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));//тип текста

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
                            if (b.x >= 0 && b.x <= GameWidth && b.y >= 0 && b.y <= GameWidth) {
                                b.onDraw(canvas);
                            } else {
                                j.remove();
                                b.bitmap.recycle();
                            }
                        }
                        //третим рисуем игрока
                        view.player.onDraw(canvas);
                        view.testCollision();
                        Iterator<Enemy> i = view.enemy.iterator();
                        while (i.hasNext()) {
                            Enemy e = i.next();
                            if (e.x >= 0 && e.y >= 0 && e.y <= GameWidth) {//была ошибка
                                e.onDraw(canvas);
                            } else {
                                i.remove();
                                e.bitmap.recycle();
                            }
                        }
                        canvas.drawText(String.valueOf(String.valueOf(view.countDeath)), 5, 20, textDeathcount);
                        view.onDraw(canvas);
                    } else {
                       /* text.setTextSize(view.getHeight() / 30);
                        text.setColor(Color.YELLOW);//цвет отображаемого текста
                        text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));//тип текста
                        gameOverBmp = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.gameover);
                        canvas.drawBitmap(gameOverBmp, GameWidth / 2 - gameOverBmp.getWidth() / 2, GameHeight / 2 - gameOverBmp.getHeight() / 2, null);
                        canvas.drawText("Your score is: " + String.valueOf(view.countDeath)
                                , GameWidth / 2 - gameOverBmp.getWidth() / 2, GameHeight / 2 + gameOverBmp.getHeight(), text);//Счётчик убийств*/
                        running = false;
                       /* view.onDraw(canvas);*/
                        Intent intent = new Intent(context, LeaderBoards.class);
                        int a = view.countDeath;
                        view.countDeath = 0;
                        intent.putExtra("score", String.valueOf(a));
                        context.startActivity(intent);
                    }

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

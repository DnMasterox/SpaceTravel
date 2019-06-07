package nshumakov.com.spacetravel.gamePlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;


import java.util.Iterator;

import nshumakov.com.spacetravel.activities.MainActivity;
import nshumakov.com.spacetravel.models.Bullet;
import nshumakov.com.spacetravel.models.Enemy;


/**
 * Created by nshumakov on 03.06.2017.
 */

public class GameManager extends Thread {

    private int GameWidth = MainActivity.WIDTH;
    private Paint textDeathcount = new Paint();
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

    public boolean isRunning() {
        return running;
    }

    /**
     * Действия, выполняемые в потоке
     */

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
       /* textDeathcount.setTextSize(view.getHeight() / 40);
        textDeathcount.setColor(Color.BLACK);//цвет отображаемого текста
        textDeathcount.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));//тип текста*/

        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                // подготовка Canvas-а
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    //рисуем работаем пока жизни игрока больше 0
                    if (view.player.getPlLives() > 0) {
                        //первый рисуется фон
                        view.landing.draw(canvas);
                        //вторым пули
                        Iterator<Bullet> j = view.ball.iterator();
                        while (j.hasNext()) {
                            Bullet b = j.next();
                            if (b.x >= 0 && b.x <= GameWidth && b.y >= 0 && b.y <= GameWidth) {
                                b.draw(canvas);
                            } else {
                                j.remove();
                                b.bitmap.recycle();
                            }
                        }
                        //третьим рисуем игрока
                        view.player.draw(canvas);
                        view.testCollision();
                        Iterator<Enemy> i = view.enemy.iterator();
                        while (i.hasNext()) {
                            Enemy e = i.next();
                            if (e.x >= 0 && e.y >= 0 && e.y <= GameWidth) {
                                e.draw(canvas);
                            } else {
                                i.remove();
                                e.bitmap.recycle();
                            }
                        }
                       /* canvas.drawText(String.valueOf(String.valueOf(view.countDeath)), 5, 20, textDeathcount);*/
                        view.doDraw(canvas);
                    } else {
                        //если жизни меньше ноля - тормозим цикл
                        running = false;
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

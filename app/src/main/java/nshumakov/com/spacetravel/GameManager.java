package nshumakov.com.spacetravel;

import android.graphics.Canvas;

/**
 * Created by nshumakov on 03.06.2017.
 */

public class GameManager extends Thread {
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

    /**
     * Действия, выполняемые в потоке
     */

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                // подготовка Canvas-а
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    // собственно рисование
                    view.onDraw(canvas);
                    view.testCollision();

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

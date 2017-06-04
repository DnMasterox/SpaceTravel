package nshumakov.com.spacetravel;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by nshumakov on 04.04.2017.
 */

public class GameView extends SurfaceView implements Runnable {
    int[] imgsArray = new int[]{R.drawable.enemy_a, R.drawable.enemy_b, R.drawable.enemy_c, R.drawable.enemy_d, R.drawable.enemy_e};
    private boolean BossonDraw = false;
    private int Height = MainActivity.HEIGHT;
    private int Width = MainActivity.WIDTH;

    /**
     * Задание волн
     */
    private int firstWave = 5;
    private int secondWave = 10;
    private int thirdWave = 20;
    private int fourthWave = 30;
    private int fifthWave = 40;
    /**
     * Конец задания волн
     */
    public static int countDeath = 0;
    public static int countLive = 0;
    private Random random;

    public List<Bullet> ball = new ArrayList<Bullet>();
    public Player player;
    public List<Enemy> enemy = new ArrayList<Enemy>();
    private Thread thread = new Thread(this);
    public Land landing;
    private Boss boss;

    private MediaPlayer sound = new MediaPlayer();


    private Bitmap enemies;
    private Bitmap enemies1;
    private Bitmap enemies2;
    private Bitmap enemies3;
    private Bitmap enemies4;
    private Button button;
   /* private Bitmap land;*/


    /**
     * Объект класса GameLoopThread
     */

    private GameManager gameLoopThread;
    public int shotX;
    public int shotY;
    /**
     * Переменная запускающая поток рисования
     */
    private boolean running = false;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500);
                setEnemies(countLive);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public GameView(Context context) {
        super(context);
        landing = new Land(this, BitmapFactory.decodeResource(getResources(), R.drawable.sky_redsunset));
        player = new Player(this);
        boss = new Boss(this, BitmapFactory.decodeResource(getResources(), R.drawable.mega_alien_f), (short) 20);
        gameLoopThread = new GameManager(this);
        /**Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback() {
            /** Создание области рисования */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                gameLoopThread.setRunning(true);
                if (gameLoopThread.getState() == Thread.State.NEW) {

                    gameLoopThread.start();
                }
                if (thread.getState() == Thread.State.NEW) {
                    thread.start();
                } else {
                    try {
                        GameView.this.notify();
                    } catch (Exception exx) {
                        exx.printStackTrace();
                    }
                }
            }

            /** Изменение области рисования */
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            /*** Уничтожение области рисования */
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        // ожидание завершение потока
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     * Функция рисующая все спрайты и фон
     */
    protected void onDraw(Canvas canvas) {
        if (BossonDraw) {
            boss.onDraw(canvas);
            bossTest();
        }
    }

    public Bullet createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Bullet(this, bmp);
    }

    public boolean onTouchEvent(MotionEvent e) {

        shotX = (int) e.getX();
        shotY = (int) e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN)
            ball.add(createSprite(R.drawable.bullet));
        sound();
        return true;
    }

    /*Проверка на столкновения*/
    public synchronized void testCollision() {
        Iterator<Enemy> i = enemy.iterator();

        while (i.hasNext()) {
            Enemy enemies = i.next();
            Iterator<Bullet> b = ball.iterator();
            if (!enemies.isDeathFlag() && (Math.abs(player.getX() - enemies.x) <= (player.getWidth() / 2 + enemies.width))
                    && (Math.abs(player.getY() - enemies.y) <= (player.getHeight() / 2 + enemies.height))) {
                enemies.setDeathFlag(true);
                enemies.onExplode();
                /*countDeath++;*/
                i.remove();
                player.setPlLives(player.getPlLives() - 1);
            }
            while (b.hasNext()) {
                Bullet balls = b.next();
              /*  if ((Math.abs(balls.x - enemies.x) <= (balls.width + enemies.width) / 2f)
                        && (Math.abs(balls.y - enemies.y) <= (balls.height + enemies.height) / 2f))*/
                if (!enemies.isDeathFlag()) {
                    if ((Math.abs(balls.x - enemies.x) <= (balls.width /*/ 2*/ + enemies.width))
                            && (Math.abs(balls.y - enemies.y) <= (balls.height / 2 + enemies.height)) && (balls.y < Width-enemies.y)) {
                        enemies.setDeathFlag(true);
                        enemies.onExplode();
                        b.remove();
                        countDeath++;
                        Log.d("-->>", String.valueOf(countDeath));

                    }
                }
            }

        }
    }

    private void bossTest() {
        Iterator<Bullet> b = ball.iterator();
        while (b.hasNext()) {
            Bullet balls = b.next();
            if ((Math.abs(balls.x - boss.x) <= (balls.width / 2 + boss.width))
                    && (Math.abs(balls.y - boss.y) <= (balls.height / 2 + boss.height))) {
                boss.setReverse(true);
                boss.countChrash--;
                b.remove();
                if (boss.countChrash <= 0 && boss.x <= 0) {
                    BossonDraw = false;
                }

                /*if ((Math.abs(boss.x - player.getX()) <= (boss.width / 2 + player.getBmp().getWidth()))
                        && (Math.abs(boss.y - player.getY()) <= (boss.height / 2 + player.getBmp().getHeight()))) {
                                }*/
            }
        }
    }

    /*Звук на выстрел*/
    private void sound() {
        try {
            sound.stop();
            sound.reset();
            AssetFileDescriptor descriptor;
            descriptor = getContext().getAssets().openFd("blast.mp3");
            sound.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            sound.prepare();
            sound.start();
        } catch (IllegalStateException illexc) {
            illexc.printStackTrace();
        } catch (IOException illexc) {
            illexc.printStackTrace();
        }
    }


    private int setLvlBackground(int a) {
        int b = 0;
        if (a >= 0 && a <= 20) {
            b = R.drawable.sky;
        } else if (a > 20) {
            b = R.drawable.earthmap;
        }
        return b;
    }

    private synchronized void setEnemies(int cLive) {
        countLive++;
        Random rnd = new Random();
        int index;

        if (cLive >= 0 && cLive <= firstWave) {
            index = rnd.nextInt(imgsArray.length - 4);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 2));
        }
        if (cLive > firstWave && cLive <= secondWave) {
            index = rnd.nextInt(imgsArray.length - 3);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 4));
        }
        if (cLive > secondWave && cLive <= thirdWave) {
            index = rnd.nextInt(imgsArray.length - 2);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 6));
        }
        if (cLive > thirdWave && cLive <= fourthWave) {
            index = rnd.nextInt(imgsArray.length - 1);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 8));
        }
        if (cLive > fourthWave && cLive <= fifthWave) {
            index = rnd.nextInt(imgsArray.length);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 10));
        }
        if (cLive > fifthWave) {
            BossonDraw = true;
        }
    }
}

package nshumakov.com.spacetravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

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
    private Paint text = new Paint();

    public static int countDeath = 0;
    public static int countLive = 0;
    private Random random;

    private List<Bullet> ball = new ArrayList<Bullet>();
    private Player player;
    private List<Enemy> enemy = new ArrayList<Enemy>();
    private Thread thread = new Thread(this);
    private Land landing;
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
  /*  private GameThread mThread;*/
    private GameManager gameLoopThread ;
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
                Thread.sleep(1000);
                setEnemies(countLive);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * -------------Start of GameThread--------------------------------------------------
     */
  /*  public class GameThread extends Thread {
        *//**
         * Объект класса
         *//*
        private GameView view;


        *//**
         * Конструктор класса
         *//*
        public GameThread(GameView view) {
            this.view = view;
        }

        *//**
         * Задание состояния потока
         *//*
        public void setRunning(boolean run) {
            running = run;
        }

        *//**
         * Действия, выполняемые в потоке
         *//*
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    // подготовка Canvas-а
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHolder()) {
                        // собственно рисование
                        view.onDraw(canvas);
                        testCollision();

                    }
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }*/

    /**
     * -------------End of GameThread--------------------------------------------------\\
     */

    public GameView(Context context) {
        super(context);
        landing = new Land(this, BitmapFactory.decodeResource(getResources(), R.drawable.sky_redsunset));
        player = new Player(this);
        boss = new Boss(this, BitmapFactory.decodeResource(getResources(), R.drawable.mega_alien_f), (short) 20);
        gameLoopThread  = new GameManager(this);
        /**Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback() {

            /** Создание области рисования */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread .setRunning(true);
                if (gameLoopThread .getState() == Thread.State.NEW) {

                    gameLoopThread .start();
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
                gameLoopThread .setRunning(false);
                while (retry) {
                    try {
                        // ожидание завершение потока
                        gameLoopThread .join();
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
        landing.onDraw(canvas);
        Iterator<Bullet> j = ball.iterator();
        while (j.hasNext()) {
            Bullet b = j.next();
            if (b.x >= 1000 || b.x <= 1000) {
                b.onDraw(canvas);
            } else {
                j.remove();
            }
        }
        player.onDraw(canvas);

        Iterator<Enemy> i = enemy.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.x <= 1000 || e.x >= 1000) {//была ошибка
                e.onDraw(canvas);
            } else {
                i.remove();
            }
        }
       /* text.setTextSize(Height / 40);
        text.setColor(Color.YELLOW);//цвет отображаемого текста
        text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));//тип текста
        canvas.drawText("Врагов повержено:" + String.valueOf(countDeath), 10, Height - Height / 40, text);//Счётчик убийств
        canvas.drawText("Врагов рождено:" + String.valueOf(enemy.size()), 10, Height - (2 * Height / 40), text);//счётчик созданных врагов
        canvas.drawText("Выжило:" + String.valueOf(countLive), 10, Height - (3 * Height / 40), text);//счётчик созданных врагов*/


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
        sound();
        shotX = (int) e.getX();
        shotY = (int) e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN)
            ball.add(createSprite(R.drawable.bullet));
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
                countDeath++;
                i.remove();
                player.setPlLives(player.getPlLives() - 1);
            }
            while (b.hasNext()) {
                Bullet balls = b.next();
              /*  if ((Math.abs(balls.x - enemies.x) <= (balls.width + enemies.width) / 2f)
                        && (Math.abs(balls.y - enemies.y) <= (balls.height + enemies.height) / 2f))*/
                if (!enemies.isDeathFlag()) {
                    if ((Math.abs(balls.x - enemies.x) <= (balls.width / 2 + enemies.width))
                            && (Math.abs(balls.y - enemies.y) <= (balls.height / 2 + enemies.height))) {
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
        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();

    }


    /*Метод для мегакилла*/
    private boolean megaKill(int a) {
        if (a >= 100) {
            if (a % 100 == 0 || a % 100 == 1 /*|| a % 100 == 2*/) {
                return true;
            }
        }
        return false;
    }

    private void finishFunction() {
        Activity activity = (Activity) getContext();
        activity.finish();
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

        if (cLive >= 0 && cLive <= 5) {
            index = rnd.nextInt(imgsArray.length - 4);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 2));
        }
        if (cLive > 5 && cLive <= 10) {
            index = rnd.nextInt(imgsArray.length - 3);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 4));


        }
        if (cLive > 10 && cLive <= 20) {
            index = rnd.nextInt(imgsArray.length - 2);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 6));

        }
        if (cLive > 20 && cLive <= 30) {
            index = rnd.nextInt(imgsArray.length - 1);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 8));
        }
        if (cLive > 30 && cLive <= 40) {
            index = rnd.nextInt(imgsArray.length);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), imgsArray[index]), (short) 10));
        }
        if (cLive > 40) {
            BossonDraw = true;
        }

       /* if (a >= 0 && a <= 5) {
          *//*  if (a == 5) {
                landing = new Land(this, BitmapFactory.decodeResource(getResources(), R.drawable.earthmap));
            }*//*
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), R.drawable.enemy_a), (short) 1));
        } else if (a >= 10 && a < 15) {
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), R.drawable.enemy_b), (short) 2));
        } else if (a >= 20 && a < 250) {
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), R.drawable.enemy_c), (short) 3));
        } else if (a >= 30 && a < 35) {
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), R.drawable.enemy_d), (short) 4));
        } else if (a >= 40 && a < 50) {
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), R.drawable.enemy_e), (short) 5));
        }*/
    }
}

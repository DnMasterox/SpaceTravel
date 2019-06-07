package nshumakov.com.spacetravel.gamePlay;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nshumakov.com.spacetravel.activities.LeaderBoards;
import nshumakov.com.spacetravel.activities.MainActivity;
import nshumakov.com.spacetravel.models.Boss;
import nshumakov.com.spacetravel.models.Bullet;
import nshumakov.com.spacetravel.models.Enemy;
import nshumakov.com.spacetravel.models.Land;
import nshumakov.com.spacetravel.models.Player;
import nshumakov.com.spacetravel.R;


/**
 * Created by nshumakov on 04.04.2017.
 */

public class GameView extends SurfaceView implements Runnable {

    private static final String TAG = "GameView";

    SurfaceHolder holder;
    volatile boolean ok = false;
    private Intent intent = null;
    /**
     * Объявление и инициализация массивов с картинками моделей
     */
    private int[] tempImgsArray = new int[]{R.drawable.a_enemy_a, R.drawable.a_enemy_b, R.drawable.a_enemy_c, R.drawable.a_enemy_d, R.drawable.a_enemy_e};
    private int[] imgsArrayLvl_b = new int[]{R.drawable.b_enemy_a, R.drawable.b_enemy_b, R.drawable.b_enemy_c, R.drawable.b_enemy_d, R.drawable.b_enemy_e};
    private int[] imgsArrayLvl_c = new int[]{R.drawable.c_enemy_a, R.drawable.c_enemy_b, R.drawable.c_enemy_c, R.drawable.c_enemy_d, R.drawable.c_enemy_e};
    private int[] imgsArrayLvl_d = new int[]{R.drawable.d_enemy_a, R.drawable.d_enemy_b, R.drawable.d_enemy_c, R.drawable.d_enemy_d, R.drawable.d_enemy_e};
    private int[] imgsArrayLvl_e = new int[]{R.drawable.e_enemy_a, R.drawable.e_enemy_b, R.drawable.e_enemy_c, R.drawable.e_enemy_d, R.drawable.e_enemy_e};
    private int[] imgsArrayLvl_f = new int[]{R.drawable.f_enemy_a, R.drawable.f_enemy_b, R.drawable.f_enemy_c, R.drawable.f_enemy_d, R.drawable.f_enemy_e};
    private int[] imgsArrayLvl_g = new int[]{R.drawable.g_enemy_a, R.drawable.g_enemy_b, R.drawable.g_enemy_c, R.drawable.g_enemy_d, R.drawable.g_enemy_e};
    private int[] imgsArrayLvl_h = new int[]{R.drawable.h_enemy_a, R.drawable.h_enemy_b, R.drawable.h_enemy_c, R.drawable.h_enemy_d, R.drawable.h_enemy_e};
    private int[] imgsArrayLvl_i = new int[]{R.drawable.i_enemy_a, R.drawable.i_enemy_b, R.drawable.i_enemy_c, R.drawable.i_enemy_d, R.drawable.i_enemy_e};
    private int[] imgsArrayLvl_j = new int[]{R.drawable.j_enemy_a, R.drawable.j_enemy_b, R.drawable.j_enemy_c, R.drawable.j_enemy_d, R.drawable.j_enemy_e};
    private int[] imgsArrayLvl_k = new int[]{R.drawable.k_enemy_a, R.drawable.k_enemy_b, R.drawable.k_enemy_c, R.drawable.k_enemy_d, R.drawable.k_enemy_e};
    /**
     * Объявление и инициализация массива с фонами уровней
     */
    private int[] landscapes = new int[]{R.drawable.landscape_a, R.drawable.landscape_b, R.drawable.landscape_c, R.drawable.landscape_d,
            R.drawable.landscape_e, R.drawable.landscape_f, R.drawable.landscape_g, R.drawable.landscape_h, R.drawable.landscape_i, R.drawable.landscape_j,
            R.drawable.landscape_k, R.drawable.landscape_l, R.drawable.landscape_m, R.drawable.landscape_n, R.drawable.landscape_o, R.drawable.landscape_p,
            R.drawable.landscape_q, R.drawable.landscape_r, R.drawable.landscape_s, R.drawable.landscape_t};
    /**
     * Объявление и инициализация массива боссов
     */
    private int[] bosses = new int[]{R.drawable.a_boss_a, R.drawable.a_boss_b, R.drawable.a_boss_c, R.drawable.a_boss_d,
            R.drawable.a_boss_e, R.drawable.a_boss_f, R.drawable.a_boss_g, R.drawable.a_boss_h, R.drawable.a_boss_i, R.drawable.a_boss_j, R.drawable.a_boss_k};

    private List<int[]> LvlPackArray = new ArrayList<>();   //Лист из массивов моделей
    private boolean BossonDraw = false; //переменная отображения босса
    private int Height = MainActivity.HEIGHT;   //высота экрана данного устройства
    private int Width = MainActivity.WIDTH; //ширина экрана данного устройства

    /**
     * Задание волн
     */
    private int firstWave = 5;
    private int secondWave = firstWave * 2;
    private int thirdWave = secondWave * 2;
    private int fourthWave = thirdWave * 2;
    private int fifthWave = fourthWave * 2;
    /**
     * Счетчики
     */
    public static int countDeath = 0;
    public static int countLive = 0;
    private Random rnd = new Random();
    /**
     * Объявление моделей
     */
    public List<Bullet> ball = new LinkedList<>();
    public Player player;
    public List<Enemy> enemy = new LinkedList<>();
    public Land landing;
    private Boss boss;
    /**
     * Поток для паузы
     */
    private Thread thread = new Thread(this);
    /**
     * Звуки столкновений
     */
    private MediaPlayer sound = new MediaPlayer();

    /**
     * Объект класса GameLoopThread
     */

    public GameManager gameLoopThread = null;
    public int shotX;
    public int shotY;
    public int levelNumber = 1;


    @Override
    public void run() {
        while (ok) {
            if (!holder.getSurface().isValid()) {
                continue;
            }
            try {
                Thread.sleep(400);
                setLevel(countLive);
                //как только у игрока кончаются жизни вызываем окно сохранения результатов в базу данных
                if (player.getPlLives() <= 0) {
                    int a = countDeath;
                    if (intent == null) {
                        intent = new Intent(getContext(), LeaderBoards.class);
                        intent.putExtra("score", String.valueOf(a));

                    } else {
                        intent.putExtra("new_score", String.valueOf(a));
                    }
                    countDeath = 0;
                    getContext().startActivity(intent);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public GameView(Context context) {
        super(context);
        holder = getHolder();
        landing = new Land(this, BitmapFactory.decodeResource(getResources(), R.drawable.landscape_q), levelNumber);
        boss = new Boss(this, setRandomBitmap(bosses), levelNumber);
        gameLoopThread = new GameManager(this);
        gameLoopThread.context = getContext();
        LvlPackArray.add(imgsArrayLvl_b);
        LvlPackArray.add(imgsArrayLvl_c);
        LvlPackArray.add(imgsArrayLvl_d);
        LvlPackArray.add(imgsArrayLvl_e);
        LvlPackArray.add(imgsArrayLvl_f);
        LvlPackArray.add(imgsArrayLvl_g);
        LvlPackArray.add(imgsArrayLvl_h);
        LvlPackArray.add(imgsArrayLvl_i);
        LvlPackArray.add(imgsArrayLvl_j);
        LvlPackArray.add(imgsArrayLvl_k);
        player = new Player(this);
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
    protected void doDraw(Canvas canvas) {
        if (BossonDraw) {
            boss.draw(canvas);
            bossTest();
        }
    }

    /**
     * Функция пускает спрайт пули в место куда было сделано нажатие
     */
    public boolean onTouchEvent(MotionEvent e) {
        shotX = (int) e.getX();
        shotY = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                ball.add(new Bullet(this, BitmapFactory.decodeResource(getResources(), R.drawable.bullet)));
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (player.getPlLives() < 3) {
                    ball.add(new Bullet(this, BitmapFactory.decodeResource(getResources(), R.drawable.rocket_wall)));
                    return true;
                }
            }
            default:
                return false;
        }
    }

    /**
     * Проверка на столкновения
     */
    public synchronized void testCollision() {
        Iterator<Enemy> i = enemy.iterator();

        while (i.hasNext()) {
            Enemy enemies = i.next();
            Iterator<Bullet> b = ball.iterator();
            if (!enemies.isDeathFlag() && (Math.abs(player.x - enemies.x) <= (player.getWidth() / 2 + enemies.width))
                    && (Math.abs(player.y - enemies.y) <= (player.getHeight() / 2 + enemies.height))) {
                enemies.setDeathFlag(true);
                enemies.onExplode();
                i.remove();
                enemies.bitmap.recycle();
                player.setPlLives(player.getPlLives() - 1);
                sound();
            }
            while (b.hasNext()) {
                Bullet balls = b.next();
                if (!enemies.isDeathFlag()) {
                    if ((Math.abs(balls.x - enemies.x) <= (balls.width /*/ 2*/ + enemies.width))
                            && (Math.abs(balls.y - enemies.y) <= (balls.height / 2 + enemies.height)) && (balls.y < Width - enemies.y)) {
                        enemies.setDeathFlag(true);
                        enemies.onExplode();
                        b.remove();
                        enemies.bitmap.recycle();
                        countDeath++;
                        sound();
                    }
                }
            }

        }
    }

    /**
     * Проверка на столкновения с Боссом
     */
    private void bossTest() {
        Iterator<Bullet> b = ball.iterator();
        if (!boss.isDeathFlag() && (Math.abs(player.x - boss.x) <= (player.getWidth() / 2 + boss.width))
                && (Math.abs(player.y - boss.y) <= (player.getHeight() / 2 + boss.height))) {
            boss.setReverse(true);
            boss.countCrash--;
            player.setPlLives(player.getPlLives() - 1);
            sound();
        }
        while (b.hasNext()) {
            Bullet balls = b.next();
            if (!boss.isDeathFlag() && (Math.abs(balls.x - boss.x) <= (balls.width / 2 + boss.width))
                    && (Math.abs(balls.y - boss.y) <= (balls.height / 2 + boss.height))) {
                boss.setReverse(true);
                boss.countCrash--;
                b.remove();
                balls.bitmap.recycle();
                countDeath++;
                sound();
            }
        }
    }

    /**
     * Звук на столкновение
     */
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

    /**
     * Метод загрузки моделей уровня
     */
    private synchronized void setLevel(int cLive) {
        countLive++;
        int index;

        if (cLive >= 0 && cLive <= firstWave) {
            index = rnd.nextInt(tempImgsArray.length - 4);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), tempImgsArray[index]), levelNumber));
        }
        if (cLive > firstWave && cLive <= secondWave) {
            index = rnd.nextInt(tempImgsArray.length - 3);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), tempImgsArray[index]), levelNumber + 1));
        }
        if (cLive > secondWave && cLive <= thirdWave) {
            index = rnd.nextInt(tempImgsArray.length - 2);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), tempImgsArray[index]), levelNumber + 2));
        }
        if (cLive > thirdWave && cLive <= fourthWave) {
            index = rnd.nextInt(tempImgsArray.length - 1);
            enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), tempImgsArray[index]), levelNumber + 3));
        }
        if (cLive > fourthWave && cLive <= fifthWave) {
            index = rnd.nextInt(tempImgsArray.length);
            Enemy finaly = new Enemy(this, BitmapFactory.decodeResource(getResources(), tempImgsArray[index]), levelNumber + 4);
            enemy.add(finaly);
            if (finaly.x <= Width - Width / 4) {
                enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), R.drawable.a_rocket_f), finaly.x, finaly.y));
            }
        }
        if (cLive > fifthWave) {

            if (boss.countCrash > 0) {
                BossonDraw = true;
                enemy.add(new Enemy(this, BitmapFactory.decodeResource(getResources(), R.drawable.rocket), boss.x, boss.y));
            } else if (boss.countCrash <= 0 && boss.x <= 0) {
                if (player.getPlLives() < 10 && player.getPlLives() > 0) {
                    player.setPlLives(player.getPlLives() + 1);
                }
                BossonDraw = false;
                levelNumber++;
                Bullet.mSpeed--;
                countLive = 0;
                lvlRecycle();
                //чистим ресурсы
                landing = new Land(this, setRandomBitmap(landscapes), levelNumber);
                if (levelNumber < 10) {
                    boss = new Boss(this, setRandomBitmap(bosses), levelNumber);
                } else
                    boss = new Boss(this, BitmapFactory.decodeResource(getResources(), R.drawable.skullship), levelNumber);
            }
        }
    }

    /**
     * Метод для создания Bitmap из файла ресурсов
     */
    public Bitmap setRandomBitmap(int[] imgsArray) {
        return BitmapFactory.decodeResource(getResources(), imgsArray[rnd.nextInt(imgsArray.length - 1)]);
    }

    /**
     * Метод чистки ресурсов пройденного уровня
     */
    public synchronized void lvlRecycle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tempImgsArray = LvlPackArray.get(rnd.nextInt(LvlPackArray.size() - 1));
                ball = null;
                ball = new LinkedList<>();
                enemy = null;
                enemy = new LinkedList<>();
            }
        }).start();
    }

    /**
     * Метод принудительной остановки перерисовки
     */
    public void pause() {
        gameLoopThread.setRunning(false);
        ok = false;
        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    /**
     * Метод возобновления перерисовки
     */
    public void resume() {
        gameLoopThread = new GameManager(this);
        gameLoopThread.setRunning(true);
        ok = true;
        thread = new Thread(this);
        thread.start();
    }
}

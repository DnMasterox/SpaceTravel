package nshumakov.com.spacetravel.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

import nshumakov.com.spacetravel.gamePlay.GameView;
import nshumakov.com.spacetravel.activities.MainActivity;
import nshumakov.com.spacetravel.R;

/**
 * Created by nshumakov on 04.04.2017.
 */

public class Enemy extends BaseModel {
    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    private Random rnd = new Random();
    private boolean deathFlag = false;
    private boolean reverse = false;

    public boolean isDeathFlag() {
        return deathFlag;
    }

    public void setDeathFlag(boolean deathFlag) {
        this.deathFlag = deathFlag;
    }

    /**
     * Х и У коорданаты
     */
    public int x;
    public int y;

    /**
     * Скорость
     */
    public int speedX;
    public int speedY = 5;

    /**
     * Высота и ширина спрайта
     */
    public int width;
    public int height;

    public GameView gameView;
    public Bitmap bitmap;

    /**
     * Конструктор класса
     */
    public Enemy(GameView gameView, Bitmap bmp, int speedX) {
        this.gameView = gameView;
        this.bitmap = bmp;
        this.x = GameWidth;
        this.y = rnd.nextInt(GameHeight - GameHeight / 10);
        this.speedX = speedX;

        this.width = GameHeight / 10;
        this.height = GameHeight / 10;
    }

    public Enemy(GameView gameView, Bitmap bmp, int x, int y) {
        this.gameView = gameView;
        this.bitmap = bmp;
        this.x = x;
        this.y = y;
        this.speedX = 10;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public void update() {
        int a = speedX % 10;
        switch (a) {
            case 1: {
                speedY = 0;
                x -= speedX;
            }
            case 2: {
                x -= speedX;
                if (!reverse) {
                    y += 2;
                    reverse = true;
                } else if (reverse) {
                    y -= 2;
                    reverse = false;
                }
            }
            case 3: {
                x -= speedX;
                if (!reverse) {
                    if (y >= GameHeight - GameHeight / 5) {
                        reverse = true;
                        x--;
                    } else y++;
                } else if (reverse) {
                    y--;
                    if (y <= 20) {
                        reverse = false;
                    }
                }
            }
            case 4: {
                x -= speedX;
                if (!reverse) {
                    if (y >= GameHeight - GameHeight / 5) {
                        reverse = true;
                        x--;
                    } else y++;
                } else if (reverse) {
                    y--;
                    if (y <= 20) {
                        reverse = false;
                    }
                }
            }
            case 5: {
                speedY = 0;
                x -= speedX;
            }
            case 0: {
                speedY = 0;
                x -= speedX;
            }
            default: {
                x -= speedX;
                if (!reverse) {
                    y += speedY;
                    if (y >= GameHeight - GameHeight / 5) {
                        reverse = true;
                    }
                } else if (reverse) {
                    y -= speedY;
                    if (y <= 20) {
                        reverse = false;
                    }
                }
            }
        }
        if (deathFlag) {
            speedY = 0;
            speedX = 20;
        }
    }

    public void onDraw(Canvas canvas) {
        if (x > 0 && y > 0) {
            update();
            canvas.drawBitmap(bitmap, x, y, null);
        } else bitmap.recycle();
    }

    public void onExplode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = BitmapFactory.decodeResource(gameView.getContext().getResources(), R.drawable.explosion);
            }
        }).start();
    }
}

package nshumakov.com.spacetravel.Models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import nshumakov.com.spacetravel.Activities.MainActivity;
import nshumakov.com.spacetravel.GamePlay.GameView;

/**
 * Created by nshumakov on 23.06.2017.
 */

public abstract class BaseModel {
    private int GameHeight = MainActivity.HEIGHT;
    private int GameWidth = MainActivity.WIDTH;
    private GameView gameView;
    public int x;
    public int y;
    private Bitmap bmp;

    public interface onUpdate {
        public void update();
    }

    public void onDraw(Canvas canvas) {
    }
}

package nshumakov.com.spacetravel.models;

import android.graphics.Bitmap;
import nshumakov.com.spacetravel.gamePlay.GameView;

/**
 * Created by nshumakov on 20.07.2017.
 */

public abstract class BaseModel {
    private int GameHeight;
    private int GameWidth;
    public Bitmap bitmap;
    public GameView gameView;
    public int width;
    public int height;
    private int speedX;
    private int speedY;

    public BaseModel() {
    }

    public abstract void update();
}

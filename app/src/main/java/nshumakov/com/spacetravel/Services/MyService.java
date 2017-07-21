package nshumakov.com.spacetravel.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import nshumakov.com.spacetravel.R;

/**
 * Created by nshumakov on 04.06.2017.
 */

public class MyService extends Service {
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.waves);
        player.setLooping(true); // зацикливаем
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_NOT_STICKY;
    }
}

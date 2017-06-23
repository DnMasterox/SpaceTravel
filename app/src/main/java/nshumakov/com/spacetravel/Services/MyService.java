package nshumakov.com.spacetravel.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import nshumakov.com.spacetravel.R;

/**
 * Created by nshumakov on 04.06.2017.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "My Service Created", Toast.LENGTH_SHORT).show();

        player = MediaPlayer.create(this, R.raw.waves);
        player.setLooping(true); // зацикливаем
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        player.stop();

    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
        player.start();
    }
    public void onPause(){
        player.pause();
    }
}

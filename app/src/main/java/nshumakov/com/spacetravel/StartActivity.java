package nshumakov.com.spacetravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by nshumakov on 03.06.2017.
 */

public class StartActivity extends Activity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button) findViewById(R.id.button1);
        startButton.setOnClickListener(this);

        Button exitButton = (Button) findViewById(R.id.button2);
        exitButton.setOnClickListener(this);
    }

    /**
     * Обработка нажатия кнопок
     */
    public void onClick(View view) {
        switch (view.getId()) {
            //переход на сюрфейс
            case R.id.button1: {
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                GameView.countLive = 0;
                GameView.countDeath = 0;
            }
            break;

            //выход
            case R.id.button2: {
                finishAffinity();
            }
            break;

            default:
                break;
        }
    }
}

package nshumakov.com.spacetravel.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import nshumakov.com.spacetravel.Database.ManController;
import nshumakov.com.spacetravel.R;

public class LeaderBoards extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);
        Intent intent = getIntent();
        final TextView scores = (TextView) findViewById(R.id.scores);
        final String score = intent.getStringExtra("score");
        scores.setText("Your score is: " + score);
        final EditText name = (EditText) findViewById(R.id.name);
        ImageButton btn = (ImageButton) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ManController.write(getBaseContext(), '"' + name.getText().toString() + '"', Integer.valueOf(score));
                Intent intent2 = new Intent(getApplicationContext(), ScoresActivity.class);
                startActivity(intent2);
            }
        });
    }
}

package nshumakov.com.spacetravel.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import nshumakov.com.spacetravel.Database.Stats;
import nshumakov.com.spacetravel.R;

public class LeaderBoards extends AppCompatActivity {
    String text = "";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);
        textView = (TextView) findViewById(R.id.leaderboard);
        List<Stats> statsList = StartActivity.dataBaseHelper.getAllStats();
        for (Stats stats : statsList) {
            String leaderboard = "ID: " + stats.getId() + ", NAME: " + stats.getName() + ", SCORES: " + stats.getScore() + ", CLICK SCORES: " + stats.getClickScore() + "\n";
            text = text + leaderboard;
        }
        textView.setText(text);
    }
}

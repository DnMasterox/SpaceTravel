package nshumakov.com.spacetravel.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import nshumakov.com.spacetravel.database.DatabaseContract;
import nshumakov.com.spacetravel.database.DatabaseOpenHelper;
import nshumakov.com.spacetravel.database.StatsController;
import nshumakov.com.spacetravel.R;

public class ScoresActivity extends Activity {
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(getBaseContext());
        SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
        final String[] from = {DatabaseContract.Stats.ScoresColumns.NAME, DatabaseContract.Stats.ScoresColumns.SCORE};
        final Cursor c = sqliteDB.query(DatabaseContract.Stats.TABLE_NAME, null, null, null, null, null,
                DatabaseContract.Stats.DEFAULT_SORT);
        final int i = c.getCount();
        final int[] to = new int[]{R.id.text1, R.id.text2};
        final ListAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.list,
                c, new String[]{DatabaseContract.Stats.ScoresColumns.NAME, DatabaseContract.Stats.ScoresColumns.SCORE}, to,0);
        final ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Intent intent = new Intent(ScoresActivity.this, DataActivity.class);
                intent.putExtra("_id", id);
                startActivity(intent);
                finish();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

                final CharSequence[] items = {"Delete", "Rename"};
                AlertDialog.Builder builder3 = new AlertDialog.Builder(ScoresActivity.this);
                builder3.setTitle("Set action").setItems(items,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                switch (item) {
                                    case 0: {
                                        DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(getBaseContext());
                                        SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
                                        StatsController.delete(getBaseContext(), adapter.getItemId(pos));
                                        dbhelper.close();
                                        sqliteDB.close();
                                    }
                                    break;
                                    case 1: {
                                        // подключаем наш кастомный диалог лайаут
                                        LayoutInflater li = LayoutInflater.from(context);
                                        View promptsView = li.inflate(R.layout.promt, null);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                context);
                                        // делаем его диалогом
                                        alertDialogBuilder.setView(promptsView);
                                        final EditText userInput = (EditText) promptsView
                                                .findViewById(R.id.editTextDialogUserInput);
                                        // вешаем на него событие
                                        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        DatabaseOpenHelper dbhelper = new DatabaseOpenHelper(getBaseContext());
                                                        SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
                                                        StatsController.update(getBaseContext(), userInput
                                                                .getText().toString(), adapter.getItemId(pos));
                                                        dbhelper.close();
                                                        sqliteDB.close();
                                                    }
                                                }).setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        dialog.cancel();
                                                    }
                                                });
                                        // создаем диалог
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        // показываем его
                                        alertDialog.show();
                                    }
                                    break;
                                }
                            }
                        });
                builder3.show();
                return true;
            }
        });
        dbhelper.close();
        sqliteDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, 1, 0, "Main menu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1: {
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
            }
            break;
        }
        return true;
    }
}

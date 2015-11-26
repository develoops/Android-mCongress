package mcongress.mobicongress.com.myapplication;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");

        query.findInBackground().onSuccessTask(new Continuation<List<ParseObject>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<ParseObject>> task) throws Exception {
                List<ParseObject> results = task.getResult();
                List<Task<Void>> saveTasks = new ArrayList<>();
                for (final ParseObject post : results) {
                    post.put("published", true);
                    saveTasks.add(post.saveInBackground());
                }
                return Task.whenAll(saveTasks);

            }
        }).onSuccess(new Continuation<Void, Void>() {
            @Override
            public Void then(final Task<Void> task) {
                Log.i("EXITO","All posts published");
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
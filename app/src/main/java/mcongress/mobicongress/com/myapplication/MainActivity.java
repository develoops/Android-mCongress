package mcongress.mobicongress.com.myapplication;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import dataFetchers.CloudDataFetcher;
import fragments.MeetingsFragment;
import parseQueryGenerators.FirstDownloadGenerator;
import syncro.SyncSaver;
import bolts.Continuation;
import bolts.Task;

public class MainActivity extends AppCompatActivity {

    public StarterApplication app;
    public static Integer loopIndex;
    public static Integer contador =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.app = (StarterApplication) getApplicationContext();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        if(app.isFirstTime()){

            FirstDownloadGenerator first = new FirstDownloadGenerator();
            first.generateInitialChainofQuerys().continueWith(new Continuation<ArrayList<ParseQuery<ParseObject>>, Object>() {
                @Override
                public Object then(Task<ArrayList<ParseQuery<ParseObject>>> taskQueryChain) throws Exception {

                    CloudDataFetcher sync = new CloudDataFetcher();
                    sync.fetchFirstDataFromParse(taskQueryChain.getResult()).continueWith(new Continuation<ArrayList<ParseObject>, Object>() {
                        @Override
                        public Object then(Task<ArrayList<ParseObject>> fetchTask) throws Exception {
                            SyncSaver pin = new SyncSaver();
                            pin.pinFirstDownload(fetchTask.getResult()).onSuccessTask(new Continuation<Void, Task<Void>>() {
                                @Override
                                public Task<Void> then(Task<Void> task) throws Exception {

                                    if(task.isCompleted()){
                                        app.setFirstTime();
                                    }
                                    return task;
                                }
                            });
                            return fetchTask;
                        }
                    });
                    return taskQueryChain;
                }
            });


        }

        encapsuleSkip();

        /*
        MeetingsFragment loadDataFragment = MeetingsFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, loadDataFragment, "Load");
        ft.commitAllowingStateLoss();

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

    public static void encapsuleSkip(){

        final ArrayList<ParseObject> results = new ArrayList<>();

        final ArrayList<ParseQuery<ParseObject>>  mutu = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Entidad1");
        query.countInBackground().continueWith(new Continuation<Integer, Object>() {
            @Override
            public Object then(Task<Integer> task) throws Exception {

                loopIndex =  task.getResult()/1000;
                return task.continueWith(new Continuation<Integer, Object>() {
                    @Override
                    public Object then(Task<Integer> task2) throws Exception {

                        for (int i=1;i<loopIndex;i++){
                            ParseQuery<ParseObject> loopQuery = ParseQuery.getQuery("Entidad1");
                            loopQuery.orderByAscending("objectId");
                            loopQuery.setLimit(1000);
                            loopQuery.setSkip(contador);
                            contador = 1000*i;
                            mutu.add(loopQuery);
                        }

                        for (int i=1;i<loopIndex;i++){
                            ParseQuery<ParseObject> loopQuery = ParseQuery.getQuery("Entidad2");
                            loopQuery.orderByAscending("objectId");
                            loopQuery.setLimit(1000);
                            loopQuery.setSkip(contador);
                            contador = 1000*i;
                            mutu.add(loopQuery);
                        }

                        for (int i=1;i<loopIndex;i++){
                            ParseQuery<ParseObject> loopQuery = ParseQuery.getQuery("Entidad3");
                            loopQuery.orderByAscending("objectId");
                            loopQuery.setLimit(1000);
                            loopQuery.setSkip(contador);
                            contador = 1000*i;
                            mutu.add(loopQuery);
                        }

                        for(ParseQuery <ParseObject> q:mutu){
                            q.findInBackground().continueWith(new Continuation<List<ParseObject>, Object>() {
                                @Override
                                public Object then(Task<List<ParseObject>> findTask) throws Exception {
                                    for(ParseObject object:findTask.getResult()){
                                        ParseObject parseO = object;
                                        results.add(parseO);
                                    }
                                    ;return findTask.continueWith(new Continuation<List<ParseObject>, Object>() {
                                        @Override
                                        public Object then(Task<List<ParseObject>> saveTask) throws Exception {
                                            return ParseObject.pinAllInBackground(saveTask.getResult());
                                        }
                                    });
                                }
                            });
                        }

                        return task2;
                    }
                });
            }
        });
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
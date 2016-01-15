package dataFetchers;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import parseQueryGenerators.ParseQueryChainGenerator;
import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;


/**
 * Created by Alvaro on 11/30/15.
 */
public class CloudDataFetcher {

    public static String parseClass;
    public static ArrayList<String> parseClassRelationsName = new ArrayList<>();



    public static Task<ArrayList<ParseObject>> estructureQueryTasks(){

        ParseQueryChainGenerator parseQueryChaining = new ParseQueryChainGenerator(parseClass);
        parseQueryChaining.relations = parseClassRelationsName;
        return parseQueryChaining.generateChainParseQuerys().onSuccessTask(new Continuation<ArrayList<ParseQuery<ParseObject>>, Task<ArrayList<ParseObject>>>() {
            @Override
            public Task<ArrayList<ParseObject>> then(Task<ArrayList<ParseQuery<ParseObject>>> task) throws Exception {
                return createAndExecuteQueryTasks(task.getResult()).continueWith(new Continuation<ArrayList<ParseObject>, ArrayList<ParseObject>>() {
                    @Override
                    public ArrayList<ParseObject> then(Task<ArrayList<ParseObject>> task) throws Exception {
                        return task.getResult();
                    }
                });
            }



        });

    }

    public static Task<ArrayList<ParseObject>> createAndExecuteQueryTasks(ArrayList<ParseQuery<ParseObject>> arrayList){

        final TaskCompletionSource<ArrayList<ParseObject>> successful = new TaskCompletionSource<>();
        ArrayList<Task<List<ParseObject>>> mutu = new  ArrayList<>();



        for(ParseQuery<ParseObject> query:arrayList){

            mutu.add(query.findInBackground());

        }


        Task.whenAllResult(mutu).continueWith(new Continuation<List<List<ParseObject>>, Object>() {
            @Override
            public Object then(Task<List<List<ParseObject>>> task) throws Exception {


                return task;

            }
        });




        return successful.getTask();


    }

    public static Task<ArrayList<ParseObject>> fetchFirstDataFromParse(ArrayList<ParseQuery<ParseObject>> arrayList){

        final TaskCompletionSource<ArrayList<ParseObject>> successful = new TaskCompletionSource<>();


        ArrayList<Task<List<ParseObject>>> mutu = new ArrayList<>();

        for(ParseQuery<ParseObject> query:arrayList){


            mutu.add(query.findInBackground());
        }

        


        Task.whenAllResult(mutu).continueWith(new Continuation<List<List<ParseObject>>, Object>() {
            @Override
            public Object then(Task<List<List<ParseObject>>> task) throws Exception {
                return null;
            }
        });
        /*

        Task.whenAllResult(mutu).continueWith(new Continuation<List<List<ParseObject>>, Object>() {
            @Override
            public Object then(Task<List<List<ParseObject>>> task) throws Exception {
                //successful.setResult(task.getResult());
                successful.setResult();

                return task;
            }
        });

*/
        return successful.getTask();


    }
}

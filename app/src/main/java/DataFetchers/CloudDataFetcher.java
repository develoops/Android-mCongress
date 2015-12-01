package DataFetchers;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ParseQueryGenerators.ParseQueryChainGenerator;
import Syncro.Pinning;
import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;


/**
 * Created by Alvaro on 11/30/15.
 */
public class CloudDataFetcher {

    public static String parseClass;
    public static ArrayList<String> parseClassRelationsName = new ArrayList<>();

    public static void syncDataFromParse(){

        Pinning pin = new Pinning();


    }

    public static Task<Object> estructureQueryTasks(){



        ParseQueryChainGenerator parseQueryChaining = new ParseQueryChainGenerator(parseClass);
        parseQueryChaining.relations = parseClassRelationsName;
        return parseQueryChaining.generateChainParseQuerys().onSuccessTask(new Continuation<ArrayList<ParseQuery<ParseObject>>, Task<Object>>() {
            @Override
            public Task<Object> then(Task<ArrayList<ParseQuery<ParseObject>>> task) throws Exception {
                return createAndExecuteQueryTasks(task.getResult()).continueWith(new Continuation<Void, Object>() {
                    @Override
                    public Object then(Task<Void> task) throws Exception {
                        return task;
                    }
                });
            }
        });

    }

    public static Task<Void> createAndExecuteQueryTasks(ArrayList<ParseQuery<ParseObject>> arrayList){

        final TaskCompletionSource<Void> successful = new TaskCompletionSource<>();
        ArrayList<Task<List<ParseObject>>> mutu = new ArrayList<>();

        for(ParseQuery<ParseObject> query:arrayList){
            mutu.add(query.findInBackground());
        }
        Task.whenAll(mutu).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(Task<Void> task) throws Exception {
                successful.setResult(task.getResult());

                return task;
            }
        });

        return successful.getTask();


    }


    public static Task<Void> fetchFirstDataFromParse(ArrayList<ParseQuery<ParseObject>> arrayList){

        final TaskCompletionSource<Void> successful = new TaskCompletionSource<>();
        ArrayList<Task<List<ParseObject>>> mutu = new ArrayList<>();

        for(ParseQuery<ParseObject> query:arrayList){
            mutu.add(query.findInBackground());
        }
        Task.whenAll(mutu).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(Task<Void> task) throws Exception {
                successful.setResult(task.getResult());

                return task;
            }
        });

        return successful.getTask();


    }
}

package syncro;

import com.parse.ParseObject;

import java.util.ArrayList;

import dataFetchers.CloudDataFetcher;
import bolts.Continuation;
import bolts.Task;

/**
 * Created by Alvaro on 11/27/15.
 */
public class SyncSaver {

    public static String parseClassName;
    public static ArrayList<String> parseClassRelationsName = new ArrayList<>();

    public static void syncDataFromParse(){

        final CloudDataFetcher fetcher = new CloudDataFetcher();
        fetcher.parseClass = parseClassName;
        fetcher.parseClassRelationsName = parseClassRelationsName;
        fetcher.estructureQueryTasks().onSuccessTask(new Continuation<ArrayList<ParseObject>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<ArrayList<ParseObject>> task) throws Exception {
                return pini(task.getResult());
            }
        });


    }

    public static Task<Void> pini(ArrayList<ParseObject> arrayList) {

        final ArrayList<ParseObject> unPinObjects = new ArrayList<>();
        final ArrayList<ParseObject> pinObjects = new ArrayList<>();

        for (ParseObject object : arrayList) {
            if (object.get("booleano").equals("false")) {
                unPinObjects.add(object);
            } else {
                pinObjects.add(object);
            }
        }

        return ParseObject.unpinAllInBackground(unPinObjects).onSuccessTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(Task<Void> task) throws Exception {
                return ParseObject.unpinAllInBackground(unPinObjects).onSuccessTask(new Continuation<Void, Task<Void>>() {
                    @Override
                    public Task<Void> then(Task<Void> task) throws Exception {
                        return ParseObject.pinAllInBackground(pinObjects);
                    }
                });
            }
        });
    }


    public static Task<Void> pinFirstDownload(ArrayList<ParseObject> arrayList){

        return ParseObject.pinAllInBackground(arrayList);

    }

}

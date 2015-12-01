package Syncro;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * Created by Alvaro on 11/27/15.
 */
public class Pinning {

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

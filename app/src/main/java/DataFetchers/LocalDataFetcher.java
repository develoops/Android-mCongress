package DataFetchers;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ParseQueryGenerators.QueryBuilder;
import bolts.Continuation;
import bolts.Task;

/**
 * Created by Alvaro on 11/30/15.
 */
public class LocalDataFetcher {

    public static String parseClass;
    public static ArrayList<String> parseClassRelationsName = new ArrayList<>();

    public static ParseQuery<ParseObject> generateLocalQuery(){
        QueryBuilder query = new QueryBuilder(parseClass);
        query.includeKeys = parseClassRelationsName;


        return query.generateLocalParseQuery();

    }

    public static Task<List<ParseObject>> fetchDataFromLocal(){


        return generateLocalQuery().findInBackground().continueWith(new Continuation<List<ParseObject>, List<ParseObject>>() {
            @Override
            public List<ParseObject> then(Task<List<ParseObject>> task) throws Exception {
                return task.getResult();
            }
        });

    }

    public static void fetchUnSynchDataFromLocal(){



    }


}

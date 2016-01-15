package parseQueryGenerators;

import com.parse.ParseConfig;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by Alvaro on 11/26/15.
 */
public class FirstDownloadGenerator {

    public static Task<ArrayList<ParseQuery<ParseObject>>> generateInitialChainofQuerys(){

            return ParseConfig.getInBackground().continueWith(new Continuation<ParseConfig, ArrayList<ParseQuery<ParseObject>>>() {
                @Override
                public ArrayList<ParseQuery<ParseObject>> then(Task<ParseConfig> task) throws Exception {
                    List<String> classNames = task.getResult().getList("classNames");
                    ArrayList<ParseQuery<ParseObject>> mutuQuerys = new ArrayList<>();

                    for(String clase:classNames){
                        QueryBuilder queryBuilder = new QueryBuilder(clase);
                        mutuQuerys.add(queryBuilder.generateSimpleParseQuery());
                    }

                    return mutuQuerys;
                }
            });
    }
}

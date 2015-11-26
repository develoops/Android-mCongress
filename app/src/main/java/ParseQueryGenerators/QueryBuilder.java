package ParseQueryGenerators;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Created by Alvaro on 11/23/15.
 */
public class QueryBuilder {

    public static String parseClass;
    public static ArrayList<String> objectIds = new ArrayList<>();
    public static ArrayList<String> includeKeys = new ArrayList<>();

    public QueryBuilder(String className) {
        this.parseClass = className;
    }

    public static ParseQuery <ParseObject> generateSimpleParseQuery(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClass);
        query.setLimit(1000);
        return query;

    }

    public static ParseQuery <ParseObject> generateReferenceRelationQuery(ArrayList<String> relationKeyNames){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClass);
        query.setLimit(1000);
        query.selectKeys(relationKeyNames);
        return query;

    }

    public static ParseQuery <ParseObject> generateParseSubQuery(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClass);
        query.setLimit(1000);
        query.whereContainedIn("objectId",objectIds);
        return query;

    }

    public static ParseQuery <ParseObject> generateLocalParseQuery(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(parseClass);
        query.fromLocalDatastore();
        query.setLimit(1000);
        if(includeKeys.size()!=0){
            for(String relation:includeKeys){
                query.include(relation);
            }
        }
        return query;

    }

}

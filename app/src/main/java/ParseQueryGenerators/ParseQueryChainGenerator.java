package parseQueryGenerators;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by Alvaro on 11/23/15.
 */
public class ParseQueryChainGenerator {

    public static String parseClass;
    public static ArrayList<String> relations = new ArrayList<>();
    public static ArrayList<String> selectedKeys = new ArrayList<>();
    public static ArrayList<ParseQuery<ParseObject>> chainParseQuerys = new ArrayList<>();


    public ParseQueryChainGenerator(String className) {
        this.parseClass = className;
    }

    public static ParseQuery<ParseObject> rootParseQuery(){
        QueryBuilder parseQueryBuilder = new QueryBuilder(parseClass);
        ParseQuery<ParseObject> rootQuery = parseQueryBuilder.generateSimpleParseQuery();
        return rootQuery;

    }

    public static ParseQuery<ParseObject> generateRootParseQuery(){
        QueryBuilder parseQueryBuilder = new QueryBuilder(parseClass);
        ParseQuery<ParseObject> rootQuery = parseQueryBuilder.generateReferenceRelationQuery(relations);
        return rootQuery;

    }

    public static void addQuerytoChain(ParseQuery<ParseObject> query){
        chainParseQuerys.add(query);
    }


    public static Task<ArrayList<Map>> requestsWithRelationsReferences(){
        return generateRootParseQuery().findInBackground().continueWith(new Continuation<List<ParseObject>, ArrayList<Map>>() {
            @Override
            public ArrayList<Map> then(Task<List<ParseObject>> task) throws Exception {

                ArrayList<Map> dictionary = new ArrayList<>();
                List<ParseObject> parseObjects = task.getResult();
                for (ParseObject parseObject:parseObjects){
                    ParseObject parseObject1 = parseObject;
                    for(int i=0;i<relations.size();i++){

                        ParseObject objectForQuery = (ParseObject) parseObject1.get(relations.get(i));
                        if(objectForQuery.getClass()!=null){
                            Map <String,String> map =  new HashMap<>();
                            map.put(objectForQuery.getClassName(),"name");
                            map.put(objectForQuery.getObjectId(),"objectId");
                            dictionary.add(map);
                        }
                    }
                }
                return dictionary;
            }
        });
    }

    public static Task<ArrayList<ParseQuery<ParseObject>>> generateChainParseQuerys(){
        addQuerytoChain(rootParseQuery());
        return requestsWithRelationsReferences().continueWith(new Continuation<ArrayList<Map>, ArrayList<ParseQuery<ParseObject>>>() {
            @Override
            public ArrayList<ParseQuery<ParseObject>> then(Task<ArrayList<Map>> task) throws Exception {

                ArrayList<Map> results = task.getResult();
                ArrayList<String> names = new ArrayList<>();
                for(Map object:results){

                    names.add((String) object.get("name"));
                }

                Set<String> uniqueNames = new HashSet<>();
                uniqueNames.addAll(names);
                names.clear();
                names.addAll(uniqueNames);

                for (Object object:names){
                    ArrayList<String> objectsIds = new ArrayList<>();
                    String name = (String) object;
                    for(Map result:results){
                        if(result.get("name".equals(name))!=null){
                            objectsIds.add((String) result.get("objectId"));
                        }
                    }
                    QueryBuilder queryBuilder = new QueryBuilder(name);
                    queryBuilder.objectIds = objectsIds;
                    chainParseQuerys.add(queryBuilder.generateParseSubQuery());

                }
                return chainParseQuerys;
            }
        });
    }
}

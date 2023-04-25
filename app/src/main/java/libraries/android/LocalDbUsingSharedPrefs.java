package libraries.android;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//represents an object whose data is loaded from shared prefences
public abstract class LocalDbUsingSharedPrefs<T> implements LocalDatabaseUI<T> {
    private static Context context;

    public static void setContext(Context context) {
        LocalDbUsingSharedPrefs.context = context;
    }

    public static Context getContext() {
        return context;
    }

    //when you no longer need the data or need to reset
    @Override
    public void clear(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(getTableName(), Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();
    }

    //delete things matching your criteria or preferences or budget
    @Override
    public void delete(Where<T> where){
        SharedPreferences sharedpreferences = context.getSharedPreferences(getTableName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        Map<String,?> items = sharedpreferences.getAll();

        for(String key : items.keySet()){
            T object = deserialize(sharedpreferences.getString(key,""));
            if(where.isTrue(object)){
                editor.remove(key);
            }
        }
        editor.commit();

    }


    //return all menu items
    @Override
    public List<T> selectAll() {

        List<T> records = new ArrayList<>();
        SharedPreferences sharedpreferences = context.getSharedPreferences(getTableName(), Context.MODE_PRIVATE);
        Map<String,?> rows = sharedpreferences.getAll();
        for(String key: rows.keySet()){
            records.add(getSingleObject(sharedpreferences, key));

        }
        return records;
    }

    @Override
    public Integer count() {
        return this.selectAll().size();
    }

    @Override
    public Integer countWhere(Where<T> where) {
        return this.select(where).size();
    }


    @Override
    public List<T> selectAll(Interceptor<T> interceptor) {

        List<T> records = new ArrayList<>();
        SharedPreferences sharedpreferences = context.getSharedPreferences(getTableName(), Context.MODE_PRIVATE);
        Map<String,?> rows = sharedpreferences.getAll();
        for(String key: rows.keySet()){
            T item = getSingleObject(sharedpreferences, key);
            if(interceptor != null){
                interceptor.intercept(item);
            }
            records.add(item);

        }
        return records;
    }

    //applies a select to the database to decide which items to return
    @Override
    public List<T> select(Where<T> where){
        List<T> inputList = selectAll();
        List<T> resultList = new ArrayList<>();
        for(T item: inputList){
            if(where.isTrue(item)){
                resultList.add(item);
            }
        }
        return resultList;
    }

    @Override
    public T selectFirst(Where<T> where){
        List<T> inputList = selectAll();
        for(T item: inputList){
            if(where.isTrue(item)){
                return item;
            }
        }
        return null;
    }

    @Override
    public void forEach(ForEach<T> forEach){
        List<T> inputList = selectAll();
        for(T item: inputList){
            forEach.run(item);
        }
    }

    private T getSingleObject(SharedPreferences sharedpreferences, String key) {
        String serializedObject = sharedpreferences.getString(key, "");
        T object = deserialize(serializedObject);
        return object;
    }

    protected abstract String getTableName();

    protected abstract T deserialize(String serializedObject);

    @Override
    public T save(String key, T value) {
        try {

            SharedPreferences sharedpreferences = context.getSharedPreferences(getTableName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            String serializedDetails = serialize(value);
            editor.putString(key, serializedDetails);
            editor.commit();
            //System.out.println("object saved!!");
        } catch (Exception ex) {
            System.out.println("failed to save object: reason: " + ex.getMessage());
            //try again later
        }
        return getByKey(key);
    }



    protected abstract String serialize(T value);

    @Override
    public T getByKey(String key) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(getTableName(), Context.MODE_PRIVATE);
        return getSingleObject(sharedpreferences,key);
    }

    public T getByKeyOrDefault(String key, T defaultValue) {
        T result = getByKey(key);
        return result == null ? defaultValue : result;
    }

    @Override
    public boolean hasKey(String key){
        return getByKey(key) != null;
    }


    @Override
    public void deleteByKey(String key){
        try {

            SharedPreferences sharedpreferences = context.getSharedPreferences(getTableName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove(key);
            editor.commit();

        } catch (Exception ex) {
            System.out.println("failed to delete object: reason: " + ex.getMessage());

        }
    }

    @Override
    public T getByKeyOrDie(String key, String message) {
        T object = getByKey(key);
        if(null == object){
            throw new RuntimeException(message);
        }
        return object;
    }

    //============= for data analysis =====================
    @Override
    public Map<String, Integer> countByKey(CountByKey<T> countByKey){
        List<T> lines = selectAll();
        Map<String, Integer> results = new HashMap<>();
        for(T line: lines){
            String key = countByKey.getKey(line);
            if(!results.containsKey(key)){
                results.put(key,0);
            }
            results.put(key, results.get(key) + 1);
        }
        return results;
    }


    /** returns the records sorted according to some order */
    @Override
    public List<T> sort(Comparator<T> comparator){
        List<T> results = selectAll();
        Collections.sort(results, comparator);
        return results;
    }
    /* returns the first record when the values are sorted according to some order */
    @Override
    public T first(Comparator<T> comparator){
        List<T> results = sort(comparator);
        if(results == null || results.size() < 1){
            return null;
        }
        else{
            return results.get(0);
        }
    }
}

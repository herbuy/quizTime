package libraries.android;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface LocalDatabaseUI<T> {
    //when you no longer need the data or need to reset
    void clear();

    //delete things matching your criteria or preferences or budget
    void delete(Where<T> where);

    //return all menu items
    List<T> selectAll();

    Integer count();

    Integer countWhere(Where<T> where);

    List<T> selectAll(Interceptor<T> interceptor);

    //applies a select to the database to decide which items to return
    List<T> select(Where<T> where);

    T selectFirst(Where<T> where);

    void forEach(ForEach<T> forEach);

    T save(String key, T value);

    T getByKey(String key);

    boolean hasKey(String key);

    void deleteByKey(String key);

    T getByKeyOrDie(String key, String message);

    //============= for data analysis =====================
    Map<String, Integer> countByKey(CountByKey<T> countByKey);

    List<T> sort(Comparator<T> comparator);

    /* returns the first record when the values are sorted according to some order */
    T first(Comparator<T> comparator);

    public interface Interceptor<T2>{
        void intercept(T2 item);
    }

    public interface Where<T2>{
        boolean isTrue(T2 object);
    }

    public interface ForEach<T2>{
        void run(T2 object);
    }

    public interface CountByKey<T2>{
        String getKey(T2 line);
    }

    public interface SortingFactor2<T2>{
        int compare(T2 lhs, T2 rhs);
    }
}

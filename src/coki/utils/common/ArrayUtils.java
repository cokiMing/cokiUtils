package coki.utils.common;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author wuyiming
 * Created by wuyiming on 2017/12/29.
 */
public class ArrayUtils {
    /**
     * 用于提取对象数组中的字段数组
     * @param collection
     * @param fieldName
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T,E> List<E> takeFieldsToList(Collection<T> collection, String fieldName) {
        List<E> result = new ArrayList<>(collection.size());
        putElement(result,collection,fieldName);
        return result;
    }

    public static <T,E> Set<E> takeFieldsToSet(Collection<T> collection, String fieldName) {
        Set<E> result = new HashSet<>(collection.size());
        putElement(result,collection,fieldName);
        return result;
    }

    private static <T,E> void putElement(
            Collection<E> collection,
            Collection<T> queryCollection,
            String fieldName) {
        try {
            for (T t : queryCollection) {
                Field field = t.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object o = field.get(t);
                collection.add((E) o);
            }
        } catch (Exception e) {

        }
    }
}

package coki.utils.common;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author wuyiming
 * Created by wuyiming on 2017/12/29.
 */
public class CollectionUtils {

    /**
     * 用于提取对象数组中的字段数组
     * @param collection 提取对象
     * @param fieldName  字段名称
     * @param <T>        成员类型
     * @param <E>        字段类型
     * @return           list结果集
     */
    public static <T,E> List<E> takeFieldsToList(Collection<T> collection, String fieldName) {
        List<E> result = new ArrayList<>(collection.size());
        putElement(result,collection,fieldName);
        return result;
    }

    /**
     * 用于提取对象数组中的字段数组
     * @param collection 提取对象
     * @param fieldName  字段名称
     * @param <T>        成员类型
     * @param <E>        字段类型
     * @return           set结果集
     */
    public static <T,E> Set<E> takeFieldsToSet(Collection<T> collection, String fieldName) {
        Set<E> result = new HashSet<>(collection.size());
        putElement(result,collection,fieldName);
        return result;
    }

    /**
     * 将集合中的成员按照某个字段归类并生成相应的map
     * @param collection 归类对象
     * @param fieldName  字段名称
     * @param <K>        字段类别
     * @param <T>        成员类型
     * @return           map结果集
     */
    @SuppressWarnings("unchecked")
    public static <K,T> Map<K,List<T>> classifyByFieldToMap(Collection<T> collection, String fieldName) {
        Map<K,List<T>> map = new HashMap<>(collection.size());
        try {
            for (T t : collection) {
                Field field = t.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                K key = (K)field.get(t);
                List<T> tList = map.get(key);
                if (tList == null) {
                    tList = new ArrayList<>(16);
                    map.put(key,tList);
                }
                tList.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException("invalid field or fieldType: " + fieldName);
        }

        return map;
    }

    /**
     * 将集合中的成员按照某个字段映射并生成相应的map
     * @param collection 归类对象
     * @param fieldName  字段名称
     * @param <K>        字段类别
     * @param <T>        成员类型
     * @return           map结果集
     */
    @SuppressWarnings("unchecked")
    public static <K,T> Map<K,T> takeFieldsToToMap(Collection<T> collection, String fieldName) {
        Map<K,T> map = new HashMap<>(collection.size());
        try {
            for (T t : collection) {
                Field field = t.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                K key = (K)field.get(t);
                map.put(key,t);
            }
        } catch (Exception e) {
            throw new RuntimeException("invalid field or fieldType: " + fieldName);
        }

        return map;
    }

    @SuppressWarnings("unchecked")
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
            throw new RuntimeException("invalid field or fieldType: " + fieldName);
        }
    }
}

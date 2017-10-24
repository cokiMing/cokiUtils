package coki.utils.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 适用于拥有标准的get、set方法的bean
 * 对于该对象来说，只能使用keep或exclude方法两者之一,
 * 否则会抛出异常
 * Created by wuyiming on 2017/10/24.
 */
public class FieldFilter {

    private Set<String> excludeFields;

    private Set<String> keepFields;

    private final Object object;

    public FieldFilter(Object object) {
        this.object = object;
    }

    /**
     * 声明不需要保留的字段
     * @param fields 不需要保留的字段名称
     * @return 需要保留地
     */
    public void exclude(String... fields) {
        keepCheck();
        if (excludeFields == null) {
            excludeFields = new HashSet<>();
        }
        Collections.addAll(excludeFields,fields);
    }

    /**
     * 声明必须要保留地字段
     * @param fields 需要保留的字段名称
     * @return
     */
    public void keep(String... fields) {
        excludeCheck();
        if (keepFields == null) {
            keepFields = new HashSet<>();
        }
        keepFields = new HashSet<>();
        Collections.addAll(keepFields,fields);
    }

    /**
     * 根据字段筛选后的结果创建一个JSON对象
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public JSON buildResult() throws InvocationTargetException, IllegalAccessException {
        Method[] declaredMethods = object.getClass().getDeclaredMethods();
        JSONObject result = new JSONObject();
        for (Method method : declaredMethods) {
            String name = method.getName();
            if (name.startsWith("get")) {
                String temp = name.substring(3);
                String fieldName = isUpperCase(temp)?temp : temp.replace(temp.charAt(0),(char)(temp.charAt(0) + 'z' - 'Z'));
                if (excludeFields != null && !excludeFields.contains(fieldName)) {
                    putResult(result,method,fieldName);
                }
                if (keepFields != null && keepFields.contains(fieldName)) {
                    putResult(result,method,fieldName);
                }
            }
        }

        return result;
    }

    private boolean isUpperCase(String field) {
        boolean isUpperCase = true;
        for (char c : field.toCharArray()) {
            if ( c >= 'a' && c <= 'z') {
                isUpperCase = false;
                break;
            }
        }

        return isUpperCase;
    }

    private void keepCheck() {
        if (keepFields != null) {
            throw new RuntimeException("can't use both 'keep' and 'exclude' at the same time !");
        }
    }

    private void excludeCheck() {
        if (excludeFields != null) {
            throw new RuntimeException("can't use both 'keep' and 'exclude' at the same time !");
        }
    }

    private void putResult(JSONObject jsonObject,Method method, String fieldName)
            throws InvocationTargetException, IllegalAccessException {
        Object res = method.invoke(object);
        if (res != null) {
            jsonObject.put(fieldName,res);
        }
    }
}

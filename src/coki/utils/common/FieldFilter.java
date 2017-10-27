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

    private Set<String> fieldSet;

    private final Object object;

    private boolean retain = true;

    private FieldFilter(Object object) {
        this.fieldSet = new HashSet<>();
        this.object = object;
    }

    public static FieldFilter createFilter(Object object) {
        return new FieldFilter(object);
    }

    /**
     * 声明需要处理的的字段(默认保留)
     * @param fields 字段名称
     * @return 需要保留地
     */
    public FieldFilter select(String... fields) {
        Collections.addAll(fieldSet,fields);
        return this;
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
                if (fieldSet.contains(fieldName) == retain ) {
                    putResult(result,method,fieldName);
                }
            }
        }

        return result;
    }

    /**
     * 反转，将保留字段设置为剔除字段
     * 或将剔除字段设置为保留字段。
     * @return
     */
    public FieldFilter reverse() {
        retain = !retain;
        return this;
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

    private void putResult(JSONObject jsonObject,Method method, String fieldName)
            throws InvocationTargetException, IllegalAccessException {
        Object res = method.invoke(object);
        if (res != null) {
            jsonObject.put(fieldName,res);
        }
    }

}

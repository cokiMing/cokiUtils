package coki.utils.common;

import coki.utils.pagination.Page;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyiming on 2017/9/15.
 */
public class BuilderCodeGenerator {

    private BuilderCodeGenerator() {
        throw new AssertionError("not allowed to create a non-instantiability object");
    }

    private final static String COMMON_SUFFIX_RIGHT_PAR = "}";
    private final static String COMMON_SUFFIX_SEMICOLON = ";";
    private final static String BUILDER_PREFIX = "public static class Builder {";
    private final static String PRIVATE = "private ";
    private final static String PUBLIC = "public ";
    private final static String NAME_PREFIX = "_";

    public static <T> void createBuilderCode(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> newFields = new ArrayList<>();
        List<String> newSetters = new ArrayList<>();
        List<String> buildFields = new ArrayList<>();

        for (Field field: fields) {
            /**
             * private String id;
             */
            newFields.add(PRIVATE
                    + field.getType().getSimpleName() + " "
                    + NAME_PREFIX +field.getName() + COMMON_SUFFIX_SEMICOLON);
            /**
             * public Builder id(String id) {
             *   _id = id;
             *   return this;
             * }
             */
            newSetters.add(PUBLIC + " Builder "
                    + field.getName() + "("
                    + field.getType().getSimpleName() + " "
                    + field.getName() + ") { "
                    + NAME_PREFIX +field.getName() + "=" + field.getName() + COMMON_SUFFIX_SEMICOLON
                    + " return this" + COMMON_SUFFIX_SEMICOLON + COMMON_SUFFIX_RIGHT_PAR);

            /**
             * id = builder._id;
             */
            buildFields.add(field.getName() + "= builder." + NAME_PREFIX + field.getName() + COMMON_SUFFIX_SEMICOLON) ;
        }

        String constructor = PUBLIC + " Builder() {" + COMMON_SUFFIX_RIGHT_PAR;
        String buildObject = PUBLIC + clazz.getSimpleName() + "(Builder builder) {";
        /**
         * public PresetPath build() {
         * return new PresetPath(this);
         * }
         */
        String build = PUBLIC + clazz.getSimpleName() + " build() {"
                + "return new " + clazz.getSimpleName() + "(this);" + COMMON_SUFFIX_RIGHT_PAR;

        List<String> result = new ArrayList<>();
        result.add(BUILDER_PREFIX);
        result.addAll(newFields);
        result.add(constructor);
        result.addAll(newSetters);
        result.add(build);
        result.add(COMMON_SUFFIX_RIGHT_PAR);
        result.add(buildObject);
        result.addAll(buildFields);
        result.add(COMMON_SUFFIX_RIGHT_PAR);

        for (String line : result) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        createBuilderCode(Page.class);
    }
}

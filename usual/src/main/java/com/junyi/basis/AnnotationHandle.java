package com.junyi.basis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * User: JY
 * Date: 2020/7/18 0018
 * Description: 对注解的处理
 */
public class AnnotationHandle {
    /**
     * 注解定义
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Range {
        int min() default 0;
        int max() default 255;
    }

    /**
     * 使用注解
     */
    public class Person {
        @Range(min=1, max=20)
        public String name;

        @Range(max=10)
        public String city;
    }

    /**
     * 注解处理
     * @param person
     * @throws IllegalArgumentException
     * @throws ReflectiveOperationException
     */
    void check(Person person) throws IllegalArgumentException, ReflectiveOperationException {
        // 遍历所有Field:
        for (Field field : person.getClass().getFields()) {
            if (field.isAnnotationPresent(Range.class)){
                // 获取Field定义的@Range:
                Range range = field.getAnnotation(Range.class);
                // 如果@Range存在:
                if (range != null) {
                    // 获取Field的值:
                    Object value = field.get(person);
                    // 如果值是String:
                    if (value instanceof String) {
                        String s = (String) value;
                        // 判断值是否满足@Range的min/max:
                        if (s.length() < range.min() || s.length() > range.max()) {
                            throw new IllegalArgumentException("Invalid field: " + field.getName());
                        }
                    }
                }
            }
        }
    }
}

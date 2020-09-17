package com.junyi;

import com.junyi.ioc.java.beans.Person;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * Java Beans实现了传统IoC容器
 */
public class Main {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        // 查看所有的PropertyDescriptor
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(System.out::println);

        // PropertyEditor
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    //PropertyDescriptor allow to add PropertyEditor
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    String name = propertyDescriptor.getName();
                    if ("age".equals(name)) {
                        propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
//                        propertyDescriptor.createPropertyEditor();
                    }
                });
    }
    public static class StringToIntegerPropertyEditor extends PropertyEditorSupport {
        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }

    }
}

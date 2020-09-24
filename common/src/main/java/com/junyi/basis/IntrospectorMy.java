package com.junyi.basis;

import com.junyi.entity.Book;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * User: JY
 * Date: 2020/7/18 0018
 * Description:
 */
public class IntrospectorMy {
    public static void main(String[] args) throws Exception {

        BeanInfo info = Introspector.getBeanInfo(Book.class);
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            System.out.println(pd.getName());
            System.out.println("  " + pd.getReadMethod());
            System.out.println("  " + pd.getWriteMethod());
        }

    }


}

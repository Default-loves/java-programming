package com.junyi;

import javax.swing.text.html.HTMLDocument;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: JY
 * Date: 2019/1/22 0022
 * Description:
 */
public class HashMapMy {

    public static void main(String[] args) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < 10; i++){
            hashMap.put(i, 'a'+i);
        }
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.print(entry.getKey());
            System.out.print(entry.getValue());
            System.out.println();
        }
        for (Integer key : hashMap.keySet())
            System.out.println("Key= " + key);
        for (Integer value : hashMap.values())
            System.out.println("Value=" + value);
    }
}

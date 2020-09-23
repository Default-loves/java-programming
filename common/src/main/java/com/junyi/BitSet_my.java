package com.junyi;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/*
Use BitSet of BitMap
Like : find the duplicate number in 10 billion numbers
 */
public class BitSet_my {
    public static Set<Integer> test(int[] arr){
        Set<Integer> output = new HashSet<>();
        BitSet bitSet = new BitSet();
        for( int value : arr ){
            if (bitSet.get(value))
                output.add(value);
            else
                bitSet.set(value);
        }
        return output;
    }
    public static void main(String[] argv){
        int[] arr = {1,2,3,4,5,6,7,8,3,4};
        Set<Integer> result = test(arr);
        System.out.println(result);
    }
}

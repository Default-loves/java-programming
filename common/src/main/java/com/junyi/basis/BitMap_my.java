package com.junyi.basis;
/*
    UnWork because of the range of byte is -128~127
 */
public class BitMap_my {
    private int capacity;
    private byte[] arr;
    public BitMap_my(int capacity){
        this.capacity = capacity;
        arr = new byte[(capacity/8 + 1)];
    }
    public void add(int value){
        int index = value >> 3;
        int pos = value % 8;
        arr[index] |= (1 << pos);
    }
    public void delete(int value){
        int index = value >> 3;
        int pos = value % 8;
        arr[index] &= ~(1 << pos);
    }
    public boolean search(int value){
        int index = value >> 3;
        int pos = value % 8;
        return (arr[index] & (1 << pos)) == 1;
    }
    public static void main(String[] argv){
        BitMap_my bitMapMy = new BitMap_my(16);
//        int[] arr = {1,5,7,8,2,12,15,10};
        int[] arr = {1,5,7};
        for (int value : arr)
            bitMapMy.add(value);
        System.out.println(bitMapMy.search(11));
        System.out.println(bitMapMy.search(10));
        System.out.println(bitMapMy.search(1));
        bitMapMy.delete(5);
        System.out.println(bitMapMy.search(5));

    }
}

package com.junyi;

/**
 * User: Administrator
 * Date: 2018/12/22 0022
 * Time: 20:55
 * Description:
 */
public class StackMy {
    private int[] stack;
    private int count;
    private int n;
    public StackMy(int size){
        this.stack = new int[size];
        this.count = 0;
        this.n = size;
    }
    public boolean push(int item){
        if (count == n) return false;
        stack[count++] = item;
        return true;
    }
    public int pop(){
        if (count == 0) return -1;
        int temp = stack[count-1];
        count--;
        return temp;
    }

    public static void main(String[] args) {
        StackMy stack = new StackMy(10);

    }
}

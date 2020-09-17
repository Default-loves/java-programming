package com.junyi.basis;

/**
 * User: JY
 * Date: 2018/12/31 0031
 * Description:
 */
public class SomethingStrange {
    public void doublePlus(){
        int i = 3;
        int m = i++ + i;
        System.out.println("m: " + m);
        i = 3;
        m = i + i++;
        System.out.println("m: " + m);
    }
    public void aboutPrint(){
        System.out.println("mobile" + 2 + 3);
        System.out.println(2 + 3 + "mobile");
    }

    public static void main(String[] args) {
        SomethingStrange ss = new SomethingStrange();
//        ss.doublePlus();
        ss.aboutPrint();
    }
}

package com.junyi;

import java.math.BigInteger;

class aAa {
    public static String decimalToBinary(int decimalSource){
        BigInteger bi = new BigInteger(String.valueOf(decimalSource));
        return bi.toString(2);
        // bi.toString(8) octal
        // bi.toString(16) hexadecimal
    }
    public static int binaryToDecimal(String binary){
        BigInteger bi = new BigInteger(binary, 2);
        return Integer.parseInt(bi.toString());
    }

    public static void main(String[] argv){
        System.out.println(decimalToBinary(123));
        System.out.println(binaryToDecimal("10101011"));
    }
}

class String2Some{
    public void String2Int(){
        String str = "123";
        int num1 = Integer.parseInt(str);
        int num2 = new Integer(str);
        int num3 = Integer.valueOf(str);
    }
    public void String2Long(){
        String str = "123";
        long num1 = Long.parseLong(str);
        long num2 = new Long(str);
        long num3 = Long.valueOf(str);
    }
    public void String2Byte(){
        String str = "123";
        byte[] num = new byte[200];
        num = str.getBytes();
    }
}
class Long2Some{
    public void Long2String(){
        long l = 123456789l;
        String str1 = Long.toString(l);
        String str2 = String.valueOf(l);
        String str3 = "" + l;
    }
    public void Long2Int(){
        long l = 123456789l;
        int num1 = (int) l;
        int num2 = Integer.parseInt(String.valueOf(l));
        int num3 = new Long(l).intValue();
    }
}

public class TypeConvert {
    public static void main(String[] args) {

    }
}

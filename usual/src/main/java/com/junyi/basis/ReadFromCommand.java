package com.junyi.basis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * User: JY
 * Date: 2020/2/29 0029
 * Description: 从终端读取数据
 */
public class ReadFromCommand {
    public static void main(String[] args) throws IOException {
        //方法一：
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input:");
        String s = scanner.nextLine();
        System.out.println(s);

        //方法二：
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String a = br.readLine();
        String b = br.readLine();
        System.out.println(a);
        System.out.println(b);
    }
}

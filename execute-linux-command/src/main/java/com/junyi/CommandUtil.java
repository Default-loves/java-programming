package com.junyi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @time: 2021/7/23 15:37
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class CommandUtil {

    public static  List<String> runCommand(String cmd, ResultProcessor processor) {
        List<String> resultList = new ArrayList<>();
        String[] cmds = {"/bin/sh", "-c", cmd};
        Process pro = null;
        System.out.println("【Run command: 】" + cmd);
        try {
            pro = Runtime.getRuntime().exec(cmds);
            pro.waitFor();
            InputStream in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = read.readLine()) != null) {
                processor.process(resultList, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}

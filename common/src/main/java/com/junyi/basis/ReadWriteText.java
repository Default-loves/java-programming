package com.junyi.basis;

import java.io.*;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */
public class ReadWriteText {
    public void readTextFile(String path){
        File file = new File(path);
        if (file.isFile() && file.exists()){
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(read);
                String line = "";
                while ((line = br.readLine()) != null)
                    System.out.println(line);
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void writeFile(String path){
        File file = new File(path);
        BufferedWriter bw = null;
        try {
            if (file.isFile() && !file.exists())
                file.createNewFile();
            bw = new BufferedWriter(new FileWriter(path, true));
            bw.write("hello");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null){
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        ReadWriteText rw = new ReadWriteText();
        String path = System.getProperty("user.dir") + "/resource/relationship.txt";
//        rw.writeFile(path);
        rw.readTextFile(path);
    }
}

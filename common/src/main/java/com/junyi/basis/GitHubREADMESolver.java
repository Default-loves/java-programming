package com.junyi.basis;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description: 解决自动处理 leetcode 项目 Markdown 目录的自动生成
 */
public class GitHubREADMESolver {

    public void readTextFile(String path) throws IOException {
        File file = new File(path);
        String outPath = "F:\\GithubMy\\my\\leetcode\\README.md";
        File out = new File(outPath);
        BufferedWriter bw = null;
        if (out.isFile() && !out.exists()) {
            out.createNewFile();
        }
        bw = new BufferedWriter(new FileWriter(outPath, false));
        String begin = "# MyLeetCode\n" +
                    "LeetCode的题目解答，使用的语言是Java\n";

        bw.write(begin);
        List<String> ignore = Arrays.asList(".git", "Algorithm");
        for (File one: file.listFiles()) {
            if (ignore.contains(one.getName())) {
                continue;
            }
            if (one.isDirectory()) {
                String a = "./" + one.getName().replaceAll("\\s", "&#32;");
                bw.write("### " + one.getName() + "\n");
                File[] files = one.listFiles();
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        Pattern pattern = Pattern.compile("[^0-9]");
                        Integer a;
                        Integer b;
                        try {
                            a = Integer.parseInt(pattern.matcher(o1.getName()).replaceAll(""));
                            b = Integer.parseInt(pattern.matcher(o2.getName()).replaceAll(""));
                        } catch (Exception e) {
                            return 0;
                        }
                        return a - b;
                    }
                });
                for (File two: files) {
                    String pre = String.format("- [%s]", two.getName());
                    String b = "(" + a + "/" + two.getName().replaceAll("\\s", "&#32;") + ")";
                    System.out.println(pre + b);
                    bw.write(pre + b + "\n");
                }
            }
        }
        if (bw != null){
            try {
                bw.flush();
                bw.close();
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
        GitHubREADMESolver rw = new GitHubREADMESolver();
        String path = "F:\\GithubMy\\my\\leetcode";
        try {
            rw.readTextFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        rw.writeFile(path);
    }
}

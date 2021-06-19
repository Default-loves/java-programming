package com.junyi.basis;


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
        String outPath = path + "\\README.md";
        File out = new File(outPath);
        if (out.isFile() && !out.exists()) {
            out.createNewFile();
        }
        String begin = "# MyLeetCode\n" +
                       "LeetCode的题目解答，使用的语言是Java\n";

        BufferedWriter bw = new BufferedWriter(new FileWriter(outPath, false));
        bw.write(begin);
        List<String> ignore = Arrays.asList(".git", "Algorithm");
        for (File outer: new File(path).listFiles()) {
            if (ignore.contains(outer.getName())) {
                continue;
            }
            if (outer.isDirectory()) {
                String a = "./" + outer.getName().replaceAll("\\s", "&#32;");
                bw.write("### " + outer.getName() + "\n");
                File[] innerFiles = outer.listFiles();
                Arrays.sort(innerFiles, (o1, o2) -> {
                    Pattern pattern = Pattern.compile("[^0-9]");
                    Integer a1;
                    Integer b;
                    try {
                        a1 = Integer.parseInt(pattern.matcher(o1.getName()).replaceAll(""));
                        b = Integer.parseInt(pattern.matcher(o2.getName()).replaceAll(""));
                    } catch (Exception e) {
                        return 0;
                    }
                    return a1 - b;
                });
                for (File inner: innerFiles) {
                    String pre = String.format("- [%s]", inner.getName());
                    String link = "(" + a + "/" + inner.getName().replaceAll("\\s", "&#32;") + ")";
                    String line = pre + link + "\n";
                    System.out.print(line);
                    bw.write(line);
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


    public static void main(String[] args) {
        GitHubREADMESolver rw = new GitHubREADMESolver();
        String path = "F:\\GithubMy\\my\\leetcode";
        try {
            rw.readTextFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.junyi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @time: 2021/6/8 14:32
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class ExcelUtil {

    public static SXSSFWorkbook exportExcel(String[] title, String sheetName , List<List<String>> list) {
        SXSSFWorkbook wb = new SXSSFWorkbook();
        int count = 1;
        CountDownLatch downLatch = new CountDownLatch(count);
        Executor executor = newFixedThreadPool(count);
        SXSSFSheet sheet = wb.createSheet(sheetName);
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        executor.execute(new PageTask(downLatch, sheet, title, style, list));
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return wb;
    }
}

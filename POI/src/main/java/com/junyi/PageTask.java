package com.junyi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @time: 2021/6/8 14:32
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class PageTask implements Runnable {
    private CountDownLatch           countDownLatch;
    private Sheet sheet;
    private String[]                 title;
    private CellStyle                style;
    private List<List<String>> list;
    public PageTask(CountDownLatch countDownLatch, Sheet sheet, String[] title, CellStyle style, List<List<String>> list) {
        this.countDownLatch = countDownLatch;
        this.sheet = sheet;
        this.title = title;
        this.style = style;
        this.list = list;
    }
    @Override
    public void run() {
        try {
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("XXX表");
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            cell.setCellStyle(style);
            row.setHeightInPoints(35.0f);
            CellRangeAddress head = new CellRangeAddress(0, 0, 0, title.length-1);
            sheet.addMergedRegion(head);

            row = sheet.createRow(1);
            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }
            for (int i = 0; i < list.size(); i++) {
                List<String> list1 = this.list.get(i);
                row = sheet.createRow(i + 2);
                for (int j = 0; j < title.length; j++) {
                    row.createCell(j).setCellValue(list1.get(j));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }
}

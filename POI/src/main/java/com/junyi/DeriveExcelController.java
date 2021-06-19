package com.junyi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @time: 2021/6/8 14:34
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
public class DeriveExcelController {


    public static final String[] TITLE      = new String[]{"第1列", "第2列", "第3列", "第4列", "第5列", "第6列", "第7列", "第8列", "第9列", "第10列"};
    public static final String   SHEET_NAME = "sheet1";

    @RequestMapping(value = "/exportThread")
    @ResponseBody
    public void exportSXSSFWorkbookByPageThread(HttpServletResponse response) throws Exception {
        //excel文件名
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = dateFormat.format(new Date());
        String fileName = date + ".xlsx";
        //sheet页中的行数,行数数据；
//        Integer num = 1000000;
        Integer num = 10;
        List<List<String>> list = buildContent(num);
        System.out.println("list：{"+list.size()+"}");
        long start = System.currentTimeMillis();
        SXSSFWorkbook wb = ExcelUtil.exportExcel(TITLE, SHEET_NAME, list);
        long millis = System.currentTimeMillis() - start;
        long second = millis / 1000;
        System.out.println("SXSSF Page Thread 导出" + num + "条数据，花费：" + second + "s/ " + millis + "ms");
        writeAndClose(response, fileName, wb);
        wb.dispose();
    }

    /**
     * 构建内容
     *
     * @param num
     * @return
     */
    private List<List<String>> buildContent(int num) {

        List<List<String>> resultList = new ArrayList<>();
        //小于当前的总行数
        for (int i=1; i<=num;i++){   //i的值会在1~4之间变化
            List<String> linkedList = new ArrayList<>();
            //合同编号
            linkedList.add("1");
            linkedList.add("粤A12345");
            linkedList.add("3");
            linkedList.add("4");
            linkedList.add("5");
            linkedList.add("6");
            linkedList.add("7");
            linkedList.add("8");
            linkedList.add("9");
            linkedList.add("10");
            resultList.add(linkedList);
        }
        return resultList;
    }

    private void writeAndClose(HttpServletResponse response, String fileName, Workbook wb) {
        try {
            setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

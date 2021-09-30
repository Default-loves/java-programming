package com.junyi;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.thread.ThreadUtil;
import com.junyi.entity.*;
import com.junyi.entity.tmp.CouponVM;
import com.junyi.entity.tmp.ParkCentralChargeVM;
import com.junyi.entity.tmp.VwEntryExitVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */

@Slf4j
public class aaa {

    public static void main(String[] args) throws ParseException {
        Pattern pattern = Pattern.compile("(\\w+\\.txt)\\((.*)\\)");
        String source = "1.txt(abc)";
        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
            log.info(matcher.group(0));
            log.info(matcher.group(1));
            log.info(matcher.group(2));
        }

    }
    @Test
    public void testYkt() throws IOException {
        ArrayList<VwEntryExitVo> list = new ArrayList<>();

        CouponVM c1 = new CouponVM();
        c1.setDiscountWay("abc");
        c1.setEquipmentName("door");
        CouponVM c2 = new CouponVM();
        c2.setDiscountWay("efg");
        c2.setEquipmentName("door");
        ParkCentralChargeVM charge = new ParkCentralChargeVM();
        charge.setCouponVMList(new ArrayList<>());
        charge.getCouponVMList().add(c1);
        charge.getCouponVMList().add(c2);
        charge.setAccountCharge(10d);
        charge.setPayCharge(3d);
        VwEntryExitVo item = new VwEntryExitVo();
        item.setParkCentralChargeVMList(new ArrayList<>());
        item.getParkCentralChargeVMList().add(charge);
        item.setCarNo("粤A12345");

        list.add(item);


        Workbook workbook = ExcelExportUtil.exportExcel( new ExportParams("导出测试", null, "测试"),
                VwEntryExitVo.class, list);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/123.xls");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void test() {
        String s = System.getProperty("user.dir") + "\\DRPark_SDK";
        System.out.println(s);
    }

    @Test
    public void testExportExcel() throws Exception {

        List<CourseEntity> courseEntityList = new ArrayList<>();
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId("1");
        courseEntity.setName("测试课程");
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setName("张老师");
        teacherEntity.setSex(1);
        courseEntity.setMathTeacher(teacherEntity);

        List<StudentEntity> studentEntities = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName("学生" + i);
            studentEntity.setSex(i);
            studentEntity.setBirthday(new Date());
            studentEntities.add(studentEntity);
        }
        courseEntity.setStudents(studentEntities);
        courseEntityList.add(courseEntity);
        Date start = new Date();
        Workbook workbook = ExcelExportUtil.exportExcel( new ExportParams("导出测试", null, "测试"),
                CourseEntity.class, courseEntityList);
        System.out.println(new Date().getTime() - start.getTime());
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/教师课程学生导出测试.xls");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void testCache() throws InterruptedException {
        TimedCache<String, String> cache = CacheUtil.newTimedCache(1_000);

        cache.put("key1", "value1");
        cache.put("key2", "value2", 2_000);

        cache.schedulePrune(500);
        for (int i = 0; i < 5; i++) {
            ThreadUtil.sleep(500);
            System.out.println("key1: " + cache.get("key1", false));
            System.out.println("key2: " + cache.get("key2", false));
        }
    }

    @Test
    public void testTimeCache() {
        //创建缓存，默认4毫秒过期
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(4);
//实例化创建
//TimedCache<String, String> timedCache = new TimedCache<String, String>(4);

        timedCache.put("key1", "value1", 1);//1毫秒过期
        timedCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 5);
        timedCache.put("key3", "value3");//默认过期(4毫秒)

//启动定时任务，每5毫秒秒检查一次过期
        timedCache.schedulePrune(5);

//等待5毫秒
        ThreadUtil.sleep(5);

//5毫秒后由于value2设置了5毫秒过期，因此只有value2被保留下来
        String value1 = timedCache.get("key1");//null
        String value2 = timedCache.get("key2");//value2

//5毫秒后，由于设置了默认过期，key3只被保留4毫秒，因此为null
        String value3 = timedCache.get("key3");//null
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);

//取消定时清理
        timedCache.cancelPruneSchedule();

    }

}

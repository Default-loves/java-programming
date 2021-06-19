package com.junyi;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
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

/**
 * User: JY
 * Date: 2019/1/23 0023
 * Description:
 */

@Slf4j
public class aaa {

    public static void main(String[] args) throws ParseException {

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

}

package com.junyi.basis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @time: 2020/10/26 17:54
 * @version: 1.0
 * @author: junyi Xu
 * @description: java 8 time 包下的 API
 * @see java.time.LocalDate
 * @see java.time.LocalDateTime
 *
 */
@Slf4j
public class TimeMy {
    /**设置格式化模板**/
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");

    /**设置日期时区常量**/
    public static final ZoneId CHINA_ZONE_ID = ZoneId.systemDefault();


    @Test
    public void create() {
        LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间
        LocalDate d = dt.toLocalDate(); // 转换到当前日期
        LocalTime t = dt.toLocalTime(); // 转换到当前时间

        // 指定日期和时间:
        LocalDate d2 = LocalDate.of(2019, 11, 30); // 2019-11-30, 注意11=11月
        LocalTime t2 = LocalTime.of(15, 16, 17); // 15:16:17
        LocalDateTime dt2 = LocalDateTime.of(2019, 11, 30, 15, 16, 17);
        LocalDateTime dt22 = LocalDateTime.of(d2, t2);

        LocalDateTime dt3 = LocalDateTime.parse("2019-11-19T15:16:17");
        LocalDate d3 = LocalDate.parse("2019-11-19");
        LocalTime t3 = LocalTime.parse("15:16:17");
    }

    /**Date格式化为DateTime**/
    @Test
    public void dateToDateTime(){
        Date date = new Date();
        LocalDateTime dateTime = date.toInstant().atZone(CHINA_ZONE_ID).toLocalDateTime();
        System.out.println(dateTime);
    }

    /** LocalDate/LocalDateTime转Date **/
    @Test
    public void toDate(){
        // LocalDate
        LocalDate localDate = LocalDate.now();
        Date d1 = Date.from(localDate.atStartOfDay(CHINA_ZONE_ID).toInstant());
        System.out.println(d1);

        // LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.now();
        Date d2 = Date.from(localDateTime.atZone(CHINA_ZONE_ID).toInstant());
        System.out.println(d2);
    }

    /** 日期格式化 **/
    @Test
    public void formatDate(){
        System.out.println(LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    /** 日期加减 **/
    @Test
    public void plusDay(){
        LocalDateTime dateTime = LocalDateTime.now(CHINA_ZONE_ID);
        //天
        dateTime=dateTime.plusDays(1);
        //时
        dateTime=dateTime.plusHours(-1);
        //分钟
        dateTime=dateTime.plusMinutes(30);
        System.out.println(dateTime.format(DATE_TIME_FORMATTER));
    }

    /** 日期变更 **/
    @Test
    public void changeDay() {
        LocalDateTime dt = LocalDateTime.of(2020, 12, 25, 11, 11, 59);
        System.out.println(dt);
        // 日期变为31日:
        LocalDateTime dt2 = dt.withDayOfMonth(31);
        System.out.println(dt2); // 2020-12-31T11:11:59
        // 月份变为3:
        LocalDateTime dt3 = dt2.withMonth(3);
        System.out.println(dt3); // 2020-3-31T11:11:59
    }

    /** 日期时间间隔
     *  Duration 和 Period 的表示方法符合ISO 8601的格式，它以P...T...的形式表示，P...T之间表示日期间隔，T后面表示时间间隔。如果是PT...的格式表示仅有时间间隔。
     * **/
    @Test
    public void betweenDay() {
        // LocalDateTime
        LocalDateTime start = LocalDateTime.of(2019,07,01,12,12,22);
        LocalDateTime end = LocalDateTime.of(2020,07,27,12,12,22);
        Long withSecond =  end.atZone(CHINA_ZONE_ID).toEpochSecond() - start.atZone(CHINA_ZONE_ID).toEpochSecond();
        System.out.println(withSecond/60/60/24);    // 392

        // LocalDate
        LocalDate startDate2 = LocalDate.of(2019,07,01);
        LocalDate endDate2 = LocalDate.of(2019,07,03);
        Long withSecond2 =  endDate2.toEpochDay() - startDate2.toEpochDay();
        System.out.println(withSecond2);        // 2

        start = LocalDateTime.of(2020,12,25,11,11,11);
        end = LocalDateTime.of(2020,12,29,12,12,12);
        Duration d = Duration.between(start, end);
        System.out.println(d); // PT97H1M1S，表示相差了97小时1分钟1秒

        Period p = LocalDate.of(2019, 11, 19).until(LocalDate.of(2020, 1, 9));
        System.out.println(p); // P1M21D，表示相差了1月21天


        Duration d1 = Duration.ofHours(10); // 10 hours
        Duration d2 = Duration.parse("P1DT2H3M"); // 1 day, 2 hours, 3 minutes
    }

    /** 第一天and最后一天 **/
    @Test
    public void theLastDay(){
        // 当月第一天
        LocalDateTime dateTime = LocalDateTime.of(2019,07,03,12,12,22);
        dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(dateTime);
        // 当月最后一天
        dateTime = dateTime.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(dateTime);
        // 当月第一个周一
        dateTime = dateTime.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(dateTime);


        //当月的第几天
        dateTime = LocalDateTime.now();
        int dayOfMonth = dateTime.getDayOfMonth();
        System.out.println(dayOfMonth);
        // 当前周的第几天
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        System.out.println(dayOfWeek);
    }

    @Test
    public void other() {
        // 获取当前时间的时间戳，单位为毫秒
        long current = System.currentTimeMillis();
        log.info(current + "");
    }
}

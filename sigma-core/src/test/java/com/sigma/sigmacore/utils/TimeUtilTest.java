package com.sigma.sigmacore.utils;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/11-17:55
 * desc:
 **/
public class TimeUtilTest {

    /**
     * 时区类
     */
    @Test
    public void testzonedDateTime() {
        System.out.println(
                "当前时区时间" + ZonedDateTime.now() +
                        "\n构造时区时间" + ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Asia/Shanghai")) +
                        "\n所有可用的时区id" + ZoneId.getAvailableZoneIds()


        );
    }

    /**
     * 计算时间差
     */
    @Test
    public void testDTF() {
        LocalTime lt1 = LocalTime.of(23, 59, 59);
        LocalTime lt2 = LocalTime.of(0, 0, 0);
        Duration du = Duration.between(lt2, lt1);
        System.out.println(du.toHours());//相差的小时数 可能是负数
        System.out.println(du.getSeconds());
    }

    /**
     * 格式化时间
     */
    @Test
    public void testDTF2() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);
        System.out.println(strDate);
    }

    /**
     * 枚举的月份和星期类
     */
    @Test
    public void testMonthAndWeek() {
        System.out.println("月份：" + Arrays.toString(Month.values()) +
                "\n星期：" + Arrays.toString(DayOfWeek.values()) +
                "\n月份int转name:" + Month.of(3)
        );
    }

    /**
     * 年类
     */
    @Test
    public void testYear() {
        Year y = Year.now();
        System.out.println("天数：" + y.length() + ";是否闰年:" + y.isLeap() + ";年份值:" + y.getValue());
    }

    /**
     * 日期类
     */
    @Test
    public void testLocalDate() {
        LocalDate ld = LocalDate.now();//等价于LocalDate.now(Clock.systemDefaultZone())
        System.out.println("日期：" + ld + "；年：" + ld.getYear() + "；月：" + ld.getMonthValue() + "；月中天：" + ld.getDayOfMonth() + "\n" +
                "年中天:" + ld.getDayOfYear() + ";周中天:" + ld.getDayOfWeek() + ";是否闰年:" + ld.isLeapYear() + ";月份天数:" + ld.lengthOfMonth() + "\n" +
                ";年天数:" + ld.lengthOfYear()
        );
        LocalDate a = LocalDate.of(2012, 7, 2);
        LocalDate b = LocalDate.of(2012, 7, 2);
        System.out.println("a在b之后吗？" + a.isAfter(b) + "\n" + "a在b之前吗？" + a.isBefore(b) + "\n" + "a和b同一天吗？" + a.isEqual(b));
    }

    /**
     * 时间类
     */
    @Test
    public void testLocalTime() {
        LocalTime lt = LocalTime.now();
        System.out.println("当前时间到毫秒" + lt + "；时间" + lt.getHour() + "：" + lt.getMinute() + "：" + lt.getSecond() + "." + lt.getNano());
    }

    /**
     * 日期和时间类
     */
    @Test
    public void testLocalDateTime() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("当前日期时间" + ldt + "；年：" + ldt.getYear() + "；月：" + ldt.getMonthValue() + "；日：" + ldt.getDayOfMonth() + "；时间" + ldt.getHour() + "：" + ldt.getMinute() + "：" + ldt.getSecond() + "." + ldt.getNano());
    }

    /**
     * 秒类
     */
    @Test
    public void testInstant() {
        Instant is = Instant.now();
        System.out.println("1970-01-01到现在的秒" + is.getEpochSecond() + "==毫秒==" + is.toEpochMilli());
    }

}

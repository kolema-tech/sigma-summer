package com.sigma.sigmacore.utils;

import lombok.experimental.var;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.sigma.sigmacore.utils.Java8DateTimeUtil.FM.yyyy_MM_dd;
import static com.sigma.sigmacore.utils.Java8DateTimeUtil.FM.yyyy_MM_dd_HH_mm_ss;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/2-16:54
 * desc:
 **/
public class Java8DateTimeUtil {

    /**
     * 將標準時區去除
     *
     * @param zonedDateTimeString 2018-12-12T01:01:01.000-08:00
     * @param targetFormat        yyyy-MM-dd HH:mm:ss
     * @return "2018-12-12 01:01:01"
     */
    public static String reduceZoneAndChangeFormat(String zonedDateTimeString, String targetFormat) {
        var zDateTime = ZonedDateTime.parse(zonedDateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return zDateTime.format(DateTimeFormatter.ofPattern(targetFormat));
    }

    /**
     * 转换器
     */
    public static class Convertor {
        /**
         * String轉換為LocalDateTime
         *
         * @param dateTimeString
         * @return LocalDateTime
         */
        public static LocalDateTime fromDateTimeString(String dateTimeString) {
            return fromDateTimeString(dateTimeString, FM.yyyy_MM_dd_HH_mm_ss);
        }

        /**
         * String轉換為LocalDateTime
         *
         * @param dateTimeString
         * @param format
         * @return LocalDateTime
         */
        public static LocalDateTime fromDateTimeString(String dateTimeString, String format) {
            var df = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(dateTimeString, df);
        }

        /**
         * LocalDate轉爲String
         *
         * @param localDateTime
         * @return
         */
        public static String toLocalDateTimeString(LocalDateTime localDateTime) {
            return toLocalDateTimeString(localDateTime, yyyy_MM_dd_HH_mm_ss);
        }

        /**
         * LocalDate轉爲String
         *
         * @param localDateTime
         * @param format
         * @return
         */
        public static String toLocalDateTimeString(LocalDateTime localDateTime, String format) {
            var fmt = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(fmt);
        }

        /**
         * String轉換為LocalDateTime
         *
         * @param dateTimeString
         * @return LocalDateTime
         */
        public static LocalDate fromDateString(String dateTimeString) {
            return fromDateString(dateTimeString, FM.yyyy_MM_dd);
        }

        /**
         * String轉換為LocalDateTime
         *
         * @param dateTimeString
         * @param format
         * @return LocalDateTime
         */
        public static LocalDate fromDateString(String dateTimeString, String format) {
            var df = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateTimeString, df);
        }


        /**
         * LocalDate轉爲String
         *
         * @param localDate
         * @return
         */
        public static String toLocalDateString(LocalDate localDate) {
            return toLocalDateString(localDate, yyyy_MM_dd);
        }

        /**
         * LocalDate轉爲String
         *
         * @param localDate
         * @param format
         * @return
         */
        public static String toLocalDateString(LocalDate localDate, String format) {
            var fmt = DateTimeFormatter.ofPattern(format);
            return localDate.format(fmt);
        }

        /**
         * date to LocalDate
         *
         * @param date
         * @return
         */
        public static LocalDate date2LocalDate(Date date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        /**
         * localDate 2 date
         *
         * @param localDate
         * @return
         */
        public static Date localDate2Date(LocalDate localDate) {

            var zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            return Date.from(zonedDateTime.toInstant());
        }
    }

    /**
     * 格式
     */
    public static class FM {
        /**
         * 默認日期格式
         */
        public static final String yyyy_MM_dd = "yyyy-MM-dd";

        /**
         * 默認日期時間格式
         */
        public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    }
}

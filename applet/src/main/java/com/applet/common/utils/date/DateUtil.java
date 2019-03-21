package com.applet.common.utils.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateUtil {

    public static final String TIME_4 = "HH:mm";
    public static final DateTimeFormatter TIME_FORMATTER_4 = DateTimeFormatter.ofPattern(TIME_4);
    public static final String DATE_8 = "yyyyMMdd";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_8 = DateTimeFormatter.ofPattern(DATE_8);
    public static final String DATE_10 = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_10 = DateTimeFormatter.ofPattern(DATE_10);
    public static final String DATETIME_19 = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_19 = DateTimeFormatter.ofPattern(DATETIME_19);
    public static final String DATETIME_17 = "yyyyMMdd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_17 = DateTimeFormatter.ofPattern(DATETIME_17);
    public static final String DATETIME_14 = "yyyyMMddHHmmss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_14 = DateTimeFormatter.ofPattern(DATETIME_14);

    public static String getCurrentDate() {
        return DATE_TIME_FORMATTER_10.format(LocalDate.now());
    }

    public static String getCurrentDateShort() {
        return DATE_TIME_FORMATTER_8.format(LocalDate.now());
    }

    public static String getCurrentDateTime() {
        return DATE_TIME_FORMATTER_17.format(LocalDateTime.now());
    }

    public static String getCurrentDateTime14() {
        return DATE_TIME_FORMATTER_14.format(LocalDateTime.now());
    }

    public static String getCurrentDateTime17() {
        return DATE_TIME_FORMATTER_17.format(LocalDateTime.now());
    }

    public static String getCurrentDateTime19() {
        return DATE_TIME_FORMATTER_19.format(LocalDateTime.now());
    }

    public static String getCurrentDateTime(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.now());
    }

    public static String getCurrentDateTime(DateTimeFormatter formatter) {
        return formatter.format(LocalDateTime.now());
    }

    public static String getDateTimeStr(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return formatter.format(dateTime);
    }

    public static String getDateStr(LocalDate dateTime, DateTimeFormatter formatter) {
        return formatter.format(dateTime);
    }

    public static String getTimestampStr(Long timestamp, DateTimeFormatter formatter) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return formatter.format(localDateTime);
    }

    public static LocalDateTime parseDateTime(String dateTime, DateTimeFormatter formatter) {
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static LocalDate parseDate(String date, DateTimeFormatter formatter) {
        return LocalDate.parse(date, formatter);
    }

    public static LocalTime parseTime(String date, DateTimeFormatter formatter) {
        return LocalTime.parse(date, formatter);
    }

    public static long betweenDays(String startDateStr, String endDateStr) {
        LocalDate startDate = parseDate(startDateStr, DateUtil.DATE_TIME_FORMATTER_10);
        LocalDate endDate = parseDate(endDateStr, DateUtil.DATE_TIME_FORMATTER_10);
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    public static long betweenDays(LocalDate startDate, LocalDate endDate) {
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    /**
     * WARN:此方法是根据时分秒的差值来计算相差天数 如果计算2017-05-26 23:59:00 到 2017-05-27 00:01:00 的相差天数并不会是一天 而是0
     * 要计算天数差值 请将LocalDateTime.toLocalDate() 后调用上面的重载方法 betweenDays(LocalDate,LocalDate)
     */
    public static long betweenDays(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).toDays();
    }

    /**
     * date = startDate + days
     */
    public static String getAddDays(String startDate, Long days) {
        LocalDate localDate = DateUtil.parseDate(startDate, DateUtil.DATE_TIME_FORMATTER_10);
        return localDate.plusDays(days).toString();
    }

    public static LocalDate getAddDays(LocalDate startDate, Long days) {
        return startDate.plusDays(days);
    }

    /**
     * date = startDate - days
     */
    public static String getMinusDays(String startDate, Long days) {
        LocalDate localDate = DateUtil.parseDate(startDate, DateUtil.DATE_TIME_FORMATTER_10);
        return localDate.minusDays(days).toString();
    }

    public static LocalDate getMinusDays(LocalDate startDate, Long days) {
        return startDate.minusDays(days);
    }
    /**
     * date = startDate - days
     */
    public static String getMinusWeeks(String startDate, Long weeks) {
        LocalDate localDate = DateUtil.parseDate(startDate, DateUtil.DATE_TIME_FORMATTER_10);
        return localDate.minusWeeks(weeks).toString();
    }

    public static LocalDate getMinusWeeks(LocalDate startDate, Long weeks) {
        return startDate.minusWeeks(weeks);
    }
    /**
     * date = startDate - months
     */
    public static String getMinusMonths(String startDate, Long months) {
        LocalDate localDate = DateUtil.parseDate(startDate, DateUtil.DATE_TIME_FORMATTER_10);
        return localDate.minusMonths(months).toString();
    }

    public static LocalDate getMinusMonths(LocalDate startDate, Long months) {
        return startDate.minusMonths(months);
    }
    /**
     * date = startDate - months
     */
    public static String getMinusYears(String startDate, Long years) {
        LocalDate localDate = DateUtil.parseDate(startDate, DateUtil.DATE_TIME_FORMATTER_10);
        return localDate.minusYears(years).toString();
    }

    public static LocalDate getMinusYears(LocalDate startDate, Long years) {
        return startDate.minusYears(years);
    }


    /**
     * date 转 localDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * date 转 localDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * date 转 string
     */
    public static String getDate10Str(Date date) {
        return getDateStr(dateToLocalDate(date), DateUtil.DATE_TIME_FORMATTER_10);
    }

    /**
     * datetime 转 string
     */
    public static String getDateTime19Str(Date date) {
        return getDateTimeStr(dateToLocalDateTime(date), DateUtil.DATE_TIME_FORMATTER_19);
    }
    /**
     * datetime 转 string
     */
    public static String getDate8Str(Date date) {
        return getDateTimeStr(dateToLocalDateTime(date), DateUtil.DATE_TIME_FORMATTER_8);
    }

    /**
     *  string 转 datetime
     */
    public static LocalDateTime getStr19Date(String date) {
        return parseDateTime(date,DateUtil.DATE_TIME_FORMATTER_19);
    }

    /**
     *  时间戳 转 string
     */
    public static String getTimestamp4Str(Long timestamp) {
        return getTimestampStr(timestamp,DateUtil.TIME_FORMATTER_4);
    }

    /**
     * 某种格式的时间字符串转换成另一种格式的时间字符串.
     *
     * @param DateTimeStr     时间字符串
     * @param sourceFormatter 源时间字符串格式
     * @param targetFormatter 目标时间字符串格式
     */
    public static String dateTimeStrConvert(String DateTimeStr, DateTimeFormatter sourceFormatter, DateTimeFormatter targetFormatter) {
        return getDateTimeStr(parseDateTime(DateTimeStr, sourceFormatter), targetFormatter);
    }

    public static LocalDate getFirstDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    public static int getCurrentDateOfYear() {
        return LocalDate.now().getYear();
    }

    public static int getCurrentDateOfMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * HH:mm 时间格式日期比较
     * @param source 目标日期
     * @param start 开始区间
     * @param end 介绍区间
     * @return
     */
    public static boolean time4CompareTo(String source, String start,String end){
        return compareTo(source,start,end,DateUtil.TIME_FORMATTER_4);
    }

    public static boolean compareTo(String source, String start,String end,DateTimeFormatter formatter){
        boolean boo = false;
        LocalTime localTime = parseTime(source, formatter);
        LocalTime startTime = parseTime(start, formatter);
        LocalTime endTime = parseTime(end, formatter);
        if(localTime.isAfter(startTime) && localTime.isBefore(endTime)){
            boo = true;
        }
        return boo;
    }
}

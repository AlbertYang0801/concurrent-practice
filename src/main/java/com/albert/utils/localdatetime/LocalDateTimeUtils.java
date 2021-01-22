package com.albert.utils.localdatetime;

import com.google.common.collect.Lists;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Date;
import java.util.List;

public class LocalDateTimeUtils {

    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_HOUR_TIME = "yyyy-MM-dd HH";

    //获取当前时间的毫秒值
    public static long getNowMillis() {
        return getMilliByTime(LocalDateTime.now());
    }

    //获取当前时间的秒值
    public static long getNowSeconds() {
        return getSecondsByTime(LocalDateTime.now());
    }

    //获取当日凌晨
    public static LocalDateTime getWeeHour() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
    }

    //获取当日凌晨 秒
    public static long getWeeHourSecend() {
        return getSecondsByTime(getWeeHour());
    }

    public static long getWeeHourSecend(int day) {
        return getSecondsByTime(getWeeHour().minusDays(day));
    }

    //获取当日凌晨 毫秒
    public static long getWeeHourMillis() {
        return getMilliByTime(getWeeHour());
    }

    //获取几天前的凌晨
    public static LocalDateTime getWeeHourAgo(int day) {
        return getWeeHour().minusDays(day);
    }

    //获取几天前的结束时间
    public static LocalDateTime getEndAgo(int day) {
        return getDayEnd(LocalDateTime.now()).minusDays(day);
    }

    //获取以当前时间为止，几小时前的整点 秒值
    public static long getOClockAgo(int number) {
        LocalDateTime hoursAgo = minu(LocalDateTime.now(), number, ChronoUnit.HOURS);
        LocalDateTime oClockAgo = hoursAgo.withMinute(0).withSecond(0).withNano(0);
        return getSecondsByTime(oClockAgo);
    }

    //获取时间集合。整点小时
    @Deprecated
    public static List<String> getTimeList(int beginNum) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        for (int i = beginNum; i >= 0; i--) {
            String hour = formatTime(now.minusHours(i), "HH");
            list.add(hour + ":00");
        }
        return list;
    }

    public static List<String> getTimeList(int number, ChronoUnit field, String format) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime numberAgo = minu(now, number, field);
        int total = number;
        for (int i = 0; i < total; i++) {
            list.add(formatTime(plus(numberAgo, i, field), format));
        }

        return list;
    }

    public static List<String> getTimeList(int number, ChronoUnit field, String format, boolean containNow) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime numberAgo = minu(now, number, field);
        int total = number;
        if (containNow) {
            total = number + 1;
        }
        for (int i = 0; i < total; i++) {
            list.add(formatTime(plus(numberAgo, i, field), format));
        }

        return list;
    }

    public static List<String> getTimeList(LocalDateTime beginTime, ChronoUnit field, String format) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        long diffTwoTime = betweenTwoTime(beginTime, now, field);
        long total = diffTwoTime;
        for (long i = 0; i < total; i++) {
            list.add(formatTime(plus(beginTime, i, field), format));
        }

        return list;
    }

    public static List<String> getTimeList(LocalDateTime beginTime, ChronoUnit field, String format, boolean containNow) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        long diffTwoTime = betweenTwoTime(beginTime, now, field);
        long total = diffTwoTime;
        if (containNow) {
            total = diffTwoTime + 1;
        }
        for (long i = 0; i < total; i++) {
            list.add(formatTime(plus(beginTime, i, field), format));
        }
        return list;
    }


    public static List<String> getTimeList(ChronoField field, int interval, ChronoUnit fieldUnit, int number, String format) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        int timeNow = now.get(field);
        int timeToWant = timeNow / interval * interval;
        LocalDateTime timeBegin = now.with(field, timeToWant).minus(number, fieldUnit);
        int total = number / interval;
        for (int i = 0; i < total; i++) {
            list.add(formatTime(plus(timeBegin, i * interval, fieldUnit), format));
        }

        return list;
    }

    /**
     * 获取两个时间之间的时间列表，根据指定格式格式化
     *
     * @param beginTime
     * @param endTime
     * @param field
     * @param format
     * @return
     */
    public static List<String> getTimeList(LocalDateTime beginTime, LocalDateTime endTime, ChronoUnit field, String format) {
        List<String> list = Lists.newArrayList();
        long diffTwoTime = betweenTwoTime(beginTime, endTime, field);
        long total = diffTwoTime;
        for (long i = 0; i <= total; i++) {
            list.add(formatTime(plus(beginTime, i, field), format));
        }

        return list;
    }

    /**
     * 获取两个时间之间的时间戳集合
     *
     * @param startTime
     * @param endTime
     * @param chronoUnit
     * @return
     */
    public static List<Long> getTimestampBetweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit chronoUnit) {
        List<Long> timeList = Lists.newArrayList();
        long hours = LocalDateTimeUtils.betweenTwoTime(startTime, endTime, chronoUnit);
        for (int i = 0; i <= hours; i++) {
            LocalDateTime plus = LocalDateTimeUtils.plus(startTime, i, chronoUnit);
            timeList.add(getMilliByTime(plus));
        }
        return timeList;
    }

    /**
     * 获取两个时间之间的时间列表，根据指定格式格式化，可以指定时间粒度
     *
     * @param beginTime
     * @param endTime
     * @param field
     * @param timeGranularity 时间粒度
     * @param format
     * @return
     */
    public static List<String> getTimeListByTimeGranularity(LocalDateTime beginTime, LocalDateTime endTime, ChronoUnit field, int timeGranularity, String format) {
        List<String> list = Lists.newArrayList();
        long diffTwoTime = betweenTwoTime(beginTime, endTime, field);
        long total = diffTwoTime;
        for (long i = 0; i <= total; i=i+timeGranularity) {
            list.add(formatTime(plus(beginTime, i, field), format));
        }
        return list;
    }

    @Deprecated
    public static List<String> getMinuteList(int countOfHour, int interval, String format) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        int minuteNow = now.getMinute();
        int minuteToWant = minuteNow / interval * interval;
        LocalDateTime timeBegin = now.withMinute(minuteToWant).minusHours(countOfHour);
        int total = countOfHour * 60 / interval;
        for (int i = 1; i <= total; i++) {
            String time = formatTime(timeBegin.plusMinutes(i * interval), format);
            list.add(time);
        }
        return list;
    }

    public static List<String> getMinuteList(int count, int interval, String format, ChronoUnit field) {
        List<String> list = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        int minuteNow = now.getMinute();
        int minuteToWant = minuteNow / interval * interval;
        LocalDateTime timeBegin = minu(now.withMinute(minuteToWant), count, ChronoUnit.MINUTES);
        int total = count / interval;
        for (int i = 1; i <= total; i++) {
            String time = formatTime(timeBegin.plusMinutes(i * interval), format);
            list.add(time);
        }
        return list;
    }

    //获取一天的开始时间，2017,7,22 00:00
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    //获取一天的结束时间，2017,7,22 23:59:59.999999999
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

    //获取当月第一天。 参数可以是LocalDate、LocalDateTime、LocalTime等。
    //传不同的参数会得到不同的结果。
    public static Temporal getFirstDayOfMonth(Temporal date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static Temporal getCopyDateToWant(Temporal date, TemporalAdjuster adjuster) {
        return date.with(adjuster);
    }

    //获取当月第一天的凌晨
    public static Long getFirstDayOfCurrentMonth() {
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalTime weeHour = LocalTime.of(0, 0, 0);
        LocalDateTime firstDayOfCurrentMonth = LocalDateTime.of(firstDay, weeHour);
        return getMilliByTime(firstDayOfCurrentMonth);
    }


    //获取当年第一天的凌晨
//    public static LocalDateTime getDayStartThisYear() {
//		LocalDateTime firstDay = (LocalDateTime) com.cloudwise.project.yiling.utils.LocalDateTimeUtils.getCopyDateToWant(LocalDateTime.now(), TemporalAdjusters.firstDayOfYear());
//		LocalDateTime dayStart = com.cloudwise.project.yiling.utils.LocalDateTimeUtils.getDayStart(firstDay);
//		return dayStart;
//	}

    //获取两个日期的差  field参数为ChronoUnit.*
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    //日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    //日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    //获取指定时间的指定格式
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDate(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(YEAR_MONTH_DAY));
    }

    public static String formatDateTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(DATE_TIME));
    }

    //获取当前时间的指定格式
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    //解析指定格式的时间
    public static LocalDateTime parseTime(String time, String pattern) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    //解析指定格式的日期（LocalDate）
    public static LocalDate parseLocalDate(String time, String pattern) {
        return LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    //获取指定日期的毫秒
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //获取指定日期的秒
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    //Date转换为LocalDateTime
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    //LocalDateTime转换为Date
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    //判断给定时间是否不在当前这个小时内
    public static boolean judgePassedTimeLine(int hourAgo) {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() != hourAgo) {
            return true;
        }
        return false;
    }

    //将毫秒时间戳转换为LocalDateTime
    public static LocalDateTime parseTimestamp(Long timestamp) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return localDateTime;
    }

    //解析秒时间戳
    public static LocalDateTime parseSecondTimestamp(Long timestatmp) {
        return Instant.ofEpochSecond(timestatmp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    //将毫秒时间戳解析为指定格式的时间字符串
    public static String parseTimestamp(Long timestamp, String pattern) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return LocalDateTimeUtils.formatTime(localDateTime, pattern);
    }

    //将秒时间戳解析为指定格式的时间字符串
    public static String parseSecondTimestamp(Long timestamp, String pattern) {
        LocalDateTime localDateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return LocalDateTimeUtils.formatTime(localDateTime, pattern);
    }



    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
//		LocalDateTime startTime = LocalDateTime.of(2019, 12, 5, 23, 1, 0);
//		LocalDateTime endTime = LocalDateTime.of(2019, 12, 6, 0, 0, 0);
//		//时间相差 0 小时
//		System.out.println("时间相差 "+betweenTwoTime(startTime, endTime, ChronoUnit.HOURS)+" 小时");
//		TemporalAdjuster temporal = TemporalAdjusters.firstDayOfMonth();
//		LocalDate now2 = LocalDate.now();
//		LocalDate firstDayOfMonth = now2.with(temporal);
//		System.out.println(getMinuteList(30, 2, "HH:mm", ChronoUnit.MINUTES));
//		LocalDateTime firstday = now.with(TemporalAdjusters.firstDayOfMonth());
//		System.out.println(firstday);
//		LocalDate now2 = LocalDate.now();
//		System.out.println(now2);
//		System.out.println(now2.with(TemporalAdjusters.firstDayOfMonth()));
//		System.out.println(getFirstDayOfMonth(now2));
//		System.out.println(getFirstDayOfMonth(now));
//		System.out.println(getTimeList(12, ChronoUnit.MONTHS, "yyyy-MM"));
//		System.out.println(getTimeList(12, ChronoUnit.MONTHS, "yyyy-MM", true));
//		LocalDateTime minusHours = now.minusHours(2);
//		System.out.println(betweenTwoTime(minusHours, now, ChronoUnit.HOURS));
//		System.out.println(getTimeList(minusHours, ChronoUnit.HOURS, "HH"));
//		System.out.println(getTimeList(minusHours, ChronoUnit.HOURS, "HH", true));
//		System.out.println(now.get(ChronoField.MONTH_OF_YEAR));
//		System.out.println(now.get(ChronoField.MINUTE_OF_HOUR));
        System.out.println(getTimeList(ChronoField.MINUTE_OF_HOUR, 2, ChronoUnit.MINUTES, 30, "HH:mm"));
    }

}

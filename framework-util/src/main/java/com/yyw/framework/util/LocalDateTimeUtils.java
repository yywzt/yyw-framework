package com.yyw.framework.util;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 对{@link LocalDateTimeUtil} 进行增强
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/15 10:55
 */
public class LocalDateTimeUtils extends LocalDateTimeUtil {

    /**
     * 比较两个日期是否为同一周
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一周
     * @since 5.8.5
     */
    public static boolean isSameWeek(final LocalDateTime date1, final LocalDateTime date2) {
        return date1 != null && date2 != null && isSameWeek(date1.toLocalDate(), date2.toLocalDate());
    }

    /**
     * 比较两个日期是否为同一周
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一周
     * @since 5.8.5
     */
    public static boolean isSameWeek(final LocalDate date1, final LocalDate date2) {
        return isSameYear(date1, date2)
                && weekOfYear(date1) == weekOfYear(date2);
    }

    /**
     * 比较两个日期是否为同一月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一月
     * @since 5.8.5
     */
    public static boolean isSameMonth(final LocalDateTime date1, final LocalDateTime date2) {
        return date1 != null && date2 != null && isSameMonth(date1.toLocalDate(), date2.toLocalDate());
    }

    /**
     * 比较两个日期是否为同一月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一月
     * @since 5.8.5
     */
    public static boolean isSameMonth(final LocalDate date1, final LocalDate date2) {
        return isSameYear(date1, date2)
                && date1.getMonthValue() == date2.getMonthValue();
    }

    /**
     * 比较两个日期是否为同一年
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一年
     * @since 5.8.5
     */
    public static boolean isSameYear(final LocalDateTime date1, final LocalDateTime date2) {
        return date1 != null && date2 != null && isSameYear(date1.toLocalDate(), date2.toLocalDate());
    }

    /**
     * 比较两个日期是否为同一年
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一年
     * @since 5.8.5
     */
    public static boolean isSameYear(final LocalDate date1, final LocalDate date2) {
        return date1 != null && date2 != null && date1.getYear() == date2.getYear();
    }
}

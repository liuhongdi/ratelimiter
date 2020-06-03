//package com.zklock.demo.util;
package com.ratelimiter.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    //得到规范的时间
  public static String getTimeNow() {
        DateTimeFormatter df_year = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime date = LocalDateTime.now();
        String datestr = date.format(df_year);

        return datestr;
    }

    //得到无格式的时间
    public static String getTimeNowStr() {
        DateTimeFormatter df_year = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime date = LocalDateTime.now();
        String datestr = date.format(df_year);

        return datestr;
    }

    //得到微秒格式的时间
    public static String getMicroTimeNow() {
        DateTimeFormatter df_year = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime date = LocalDateTime.now();
        String datestr = date.format(df_year);

        return datestr;
    }
}

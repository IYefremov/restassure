package com.cyberiansoft.test.globalutils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GlobalUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getVNextInspectionCreationTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(3).minusMinutes(1);
        return  localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getVNextInspectionDate() {
        LocalDate localDate = LocalDate.now();
        return  localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:08:00";
    }

    public static String getDeviceTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return  localDateTime.format(DateTimeFormatter.ofPattern("MM//dd/yyyy HH:mm:ss"));
    }

    public static String getInspectionSymbol() {
        return  "E-";
    }

    public static String getWorkOrderSymbol() {
        return  "O-";
    }
}

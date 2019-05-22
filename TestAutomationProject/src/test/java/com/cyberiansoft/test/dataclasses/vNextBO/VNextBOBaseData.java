package com.cyberiansoft.test.dataclasses.vNextBO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class VNextBOBaseData {

    public String getCurrentDate(boolean... isLocalized) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (isLocalized[0]) {
            return LocalDate.now(ZoneId.of("US/Pacific")).format(formatter);
        } else {
            return LocalDate.now().format(formatter);
        }
    }
}
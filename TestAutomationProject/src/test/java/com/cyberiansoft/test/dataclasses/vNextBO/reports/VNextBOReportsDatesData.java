package com.cyberiansoft.test.dataclasses.vNextBO.reports;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.enums.DateUtils;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class VNextBOReportsDatesData {

    final private DateTimeFormatter format = DateTimeFormatter.ofPattern(DateUtils.FULL_DATE_FORMAT.getFormat(), Locale.US);

    public String getCurrentDateMinusDays(int days) {
        return BackOfficeUtils.getCurrentDateLocalized()
                .minusDays(days)
                .format(format);
    }

    public String getCurrentDate() {
        return BackOfficeUtils.getCurrentDate(true);
    }
}

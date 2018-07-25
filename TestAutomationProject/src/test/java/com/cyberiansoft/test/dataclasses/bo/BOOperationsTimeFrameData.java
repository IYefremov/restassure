package com.cyberiansoft.test.dataclasses.bo;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZoneId;

public class BOOperationsTimeFrameData {

//    private final LocalDate currentdate = LocalDate.now().plusDays(1); todo delete, if works fine
//    //	private final LocalDate weekStart = BackOfficeUtils.getWeekStartDate().minusDays(1);
//    private final LocalDate weekStart = LocalDate.now().minusDays(8);
//    private final LocalDate lastweekstart = BackOfficeUtils.getLastWeekStartDate().minusDays(1);
//    private final LocalDate lastweekend = BackOfficeUtils.getLastWeekEndDate().plusDays(2);
//    //	private final LocalDate lastweekend = BackOfficeUtils.getLastWeekEndDate(lastweekstart).plusDays(2);
//    private final LocalDate startmonth = BackOfficeUtils.getMonthStartDate().minusDays(1);
//    private final LocalDate startlastmonth = BackOfficeUtils.getLastMonthStartDate().minusDays(1);
//    private final LocalDate endlastmonth = BackOfficeUtils.getLastMonthEndDate().plusDays(2);
//    private final LocalDate startyear = BackOfficeUtils.getYearStartDate().minusDays(1);
//    private final LocalDate startlastyear = BackOfficeUtils.getLastYearStartDate().minusDays(1);
//    private final LocalDate endlastyear = BackOfficeUtils.getLastYearEndDate().plusDays(1);

    @JsonProperty("statusAll")
    private String statusAll;

    public String getStatusAll() {
        return statusAll;
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.of("US/Pacific")).plusDays(1);
    }

    public LocalDate getWeekStart() {
        return LocalDate.now(ZoneId.of("US/Pacific")).minusDays(8);
    }

    public LocalDate getLastWeekStart() {
        return BackOfficeUtils.getLastWeekStartDate().minusDays(1);
    }

    public LocalDate getLastWeekEnd() {
        return BackOfficeUtils.getLastWeekEndDate().plusDays(2);
    }

    public LocalDate getStartMonth() {
        return BackOfficeUtils.getMonthStartDate().minusDays(1);
    }

    public LocalDate getStartLastMonth() {
        return BackOfficeUtils.getLastMonthStartDate().minusDays(1);
    }

    public LocalDate getEndLastMonth() {
        return BackOfficeUtils.getLastMonthEndDate().plusDays(2);
    }

    public LocalDate getStartYear() {
        return BackOfficeUtils.getYearStartDate().minusDays(1);
    }

    public LocalDate getStartLastYear() {
        return BackOfficeUtils.getLastYearStartDate().minusDays(1);
    }

    public LocalDate getEndLastYear() {
        return BackOfficeUtils.getLastYearEndDate().plusDays(1);
    }
}
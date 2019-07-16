package com.cyberiansoft.test.dataclasses.bo;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZoneId;

public class BOOperationsTimeFrameData {

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
        return CustomDateProvider.getLastWeekStartDate().minusDays(1);
    }

    public LocalDate getLastWeekEnd() {
        return CustomDateProvider.getLastWeekEndDate().plusDays(2);
    }

    public LocalDate getStartMonth() {
        return CustomDateProvider.getMonthStartDate().minusDays(1);
    }

    public LocalDate getStartLastMonth() {
        return CustomDateProvider.getLastMonthStartDate().minusDays(1);
    }

    public LocalDate getEndLastMonth() {
        return CustomDateProvider.getLastMonthEndDate().plusDays(2);
    }

    public LocalDate getStartYear() {
        return CustomDateProvider.getYearStartDate().minusDays(1);
    }

    public LocalDate getStartLastYear() {
        return CustomDateProvider.getLastYearStartDate().minusDays(1);
    }

    public LocalDate getEndLastYear() {
        return CustomDateProvider.getLastYearEndDate().plusDays(1);
    }
}
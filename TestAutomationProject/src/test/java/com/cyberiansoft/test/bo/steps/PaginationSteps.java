package com.cyberiansoft.test.bo.steps;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.WebPageWithPagination;
import com.cyberiansoft.test.driverutils.DriverBuilder;

public class PaginationSteps {

    public static void setPageSize(String pageSize) {
        final WebPageWithPagination pagination = new WebPageWithPagination(DriverBuilder.getInstance().getDriver());
        Utils.sendKeysWithEnter(pagination.getPageSizeField(), pageSize);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void setMaxPageSize() {
        final WebPageWithPagination pagination = new WebPageWithPagination(DriverBuilder.getInstance().getDriver());
        setPageSize(String.valueOf(pagination.MAX_TABLE_ROW_COUNT_VALUE));
    }
}

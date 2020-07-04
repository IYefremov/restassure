package com.cyberiansoft.test.vnext.screens.monitoring;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class TimeReportScreen extends MonitorScreen {
    @FindBy(xpath = "//*[@data-page='time-reports']")
    private WebElement rootElement;
    @FindBy(xpath = "//div[@class='period-block']//div[@class='info'][1]//div[@class='info-date']")
    private WebElement startDateLable;
    @FindBy(xpath = "//div[@class='period-block']//div[@class='info'][2]//div[@class='info-date']")
    private WebElement endDateLable;
    @FindBy(xpath = "//*[@class='list-empty-block-sub-header']")
    private WebElement emptyBlock;
}

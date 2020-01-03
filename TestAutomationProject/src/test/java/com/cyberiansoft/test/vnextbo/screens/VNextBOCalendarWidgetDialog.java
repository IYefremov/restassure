package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOCalendarWidgetDialog extends VNextBOBaseWebPage {

    @FindBy(id = "advSearch_fromDate_dateview")
    private WebElement fromDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearch_toDate_dateview']")
    private WebElement toDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearch_fromDate_dateview']//a[@class='k-link k-nav-prev']")
    private WebElement fromDateCalendarPreviousMonthArrow;

    @FindBy(xpath = "//div[@id='advSearch_toDate_dateview']//a[@class='k-link k-nav-prev']")
    private WebElement toDateCalendarPreviousMonthArrow;

    public VNextBOCalendarWidgetDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement fromDateCalendarSpecificDateButton(String specificDate) {

        return driver.findElement(By.xpath("//div[@id='advSearch_fromDate_dateview']//td/a[@data-value='" + specificDate + "']"));
    }

    public WebElement toDateCalendarSpecificDateButton(String specificDate) {

        return driver.findElement(By.xpath("//div[@id='advSearch_toDate_dateview']//td/a[@data-value='" + specificDate + "']"));
    }

    public List<WebElement> getDate(String date) {
        return driver.findElements(By.xpath("//a[@data-value='" + date + "']"));
    }
}
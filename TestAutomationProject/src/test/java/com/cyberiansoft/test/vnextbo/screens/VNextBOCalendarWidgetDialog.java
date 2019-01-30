package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class VNextBOCalendarWidgetDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='advSearch_fromDate_dateview']")
    private WebElement fromDateCalendarWidget;

    @FindBy(xpath = "//div[@id='advSearch_toDate_dateview']")
    private WebElement toDateCalendarWidget;

    @FindBy(id = "advSearch_fromDate_dateview")
    private WebElement fromDateCalendarButton;

    @FindBy(xpath = "advSearch_toDate_dateview")
    private WebElement toDateCalendarButton;

    public VNextBOCalendarWidgetDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private boolean isCalendarOpened(WebElement calendarType) {
        try {
            return waitShort.until(ExpectedConditions.attributeContains(calendarType, "style", "display: block"));
        } catch (Exception ignored) {
            return false;
        }
    }

    public VNextBORepairOrdersAdvancedSearchDialog selectFromDate(String date, VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog) {
        if (isCalendarOpened(fromDateCalendarWidget)) {
            WebElement toDate = driver.findElements(By.xpath("//a[@data-value='" + date + "']")).get(0);
            wait.until(ExpectedConditions.elementToBeClickable(toDate)).click();
            try {
                waitShort.until(ExpectedConditions.attributeContains(toDate, "aria-selected", "true"));
            } catch (Exception ignored) {}
        } else {
            System.out.println("In else From Date");
            advancedSearchDialog.clickFromDateButton();
            selectFromDate(date, advancedSearchDialog);
        }
        return PageFactory.initElements(driver, VNextBORepairOrdersAdvancedSearchDialog.class);
    }

    public VNextBORepairOrdersAdvancedSearchDialog selectToDate(String date, VNextBORepairOrdersAdvancedSearchDialog advancedSearchDialog) {
        if (isCalendarOpened(toDateCalendarWidget)) {
            WebElement toDate = driver.findElements(By.xpath("//a[@data-value='" + date + "']")).get(1);
                wait.until(ExpectedConditions.elementToBeClickable(toDate)).click();
            try {
                waitShort.until(ExpectedConditions.attributeContains(toDate, "aria-selected", "true"));
            } catch (Exception ignored) {}
        } else {
            System.out.println("In else To Date");
            advancedSearchDialog.clickToDateButton();
            selectToDate(date, advancedSearchDialog);
        }
        return PageFactory.initElements(driver, VNextBORepairOrdersAdvancedSearchDialog.class);
    }

    public String getMonthReplace(int monthValue, int prevMonthValue, LocalDateTime before) {
        DateTimeFormatter dataValueFormat = DateTimeFormatter.ofPattern("yyyy/M/d");
        return before
                .format(dataValueFormat)
                .replace("/" + String.valueOf(monthValue) + "/", "/" + String.valueOf(prevMonthValue) + "/");
    }
}
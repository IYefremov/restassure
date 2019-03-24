package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class VNextBOAuditLogDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='repair-order-audit-log-modal' and @style]")
    private WebElement auditLogDialog;

    @FindBy(xpath = "//div[@id='repair-order-audit-log-modal' and @style]//ul[@id='logsTabs']/li/a")
    private List<WebElement> auditLogsTabsList;

    @FindBy(xpath = "//div[@id='repair-order-audit-log-modal' and @style]//ul[@id='logsTabs']/li")
    private WebElement auditLogsTabs;

    @FindBy(xpath = "//div[@id='repairOrder_departmentsLog' and contains(@class, 'active')]//tbody[contains(@data-bind, 'departments')]/tr[last()]/td[1]/div[2]")
    private WebElement departmentsLastRecord;

    @FindBy(xpath = "//div[@id='repairOrder_servicesLog' and contains(@class, 'active')]//tbody[contains(@data-bind, 'services')]//td[last()-1]")
    private WebElement servicesActivityTimeFirstRecord;

    public VNextBOAuditLogDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isAuditLogDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(auditLogDialog));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAuditLogsTabsNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(auditLogsTabsList));
        return auditLogsTabsList.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public VNextBOAuditLogDialog clickAuditLogsTab(String tab) {
        wait.until(ExpectedConditions.visibilityOfAllElements(auditLogsTabsList));
        final WebElement element = driver.findElement(By
                .xpath("//div[@id='repair-order-audit-log-modal' and @style]//ul[@id='logsTabs']/li/a[text()='" + tab + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        waitForLoading();
        wait.until(ExpectedConditions.attributeToBe(element.findElement(By.xpath("./..")), "class", "active"));
        return this;
    }

    public String getDepartmentsLastRecord() {
        return wait.until(ExpectedConditions.visibilityOf(departmentsLastRecord)).getText();
    }

    public String getServicesActivityTimeFirstRecord() {
        return wait.until(ExpectedConditions.visibilityOf(servicesActivityTimeFirstRecord)).getText();
    }

    public String getActualLocalDateTime() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        return localDateTime.format(formatter);
    }

    public String getActualLocalDateTimePlusMinute() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.plusMinutes(1);
        return localDateTimeVariant.format(formatter);
    }

    public String getActualLocalDateTimeMinusMinute() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.minusMinutes(1);
        return localDateTimeVariant.format(formatter);
    }

    public String getActualLocalDateTimeMinusTwoMinutes() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.minusMinutes(2);
        return localDateTimeVariant.format(formatter);
    }
}

package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
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

    @FindBy(xpath = "//div[@id='repairOrder_phasesDepartmentsLog' and contains(@class, 'active')]//tbody[@data-template='phases-activity-log-template']/tr[last()]//span[@data-bind='text: timeFinished']")
    private WebElement departmentsAndPhasesLastRecord;

    @FindBy(xpath = "//div[@id='repairOrder_servicesLog' and contains(@class, 'active')]//tbody[contains(@data-bind, 'services')]//td[last()-1]")
    private WebElement servicesActivityTimeFirstRecord;

    @FindBy(xpath = "//div[@id='repair-order-audit-log-modal' and @style]//ul[@id='logsTabs']/li/a[text()='ALL']")
    private WebElement auditLogAllTab;

    @FindBy(xpath = "//div[@id='repair-order-audit-log-modal' and @style]//ul[@id='logsTabs']/li/a[contains(text(), 'Phases')]")
    private WebElement auditLogPhasesAndDepartmentsTab;

    @FindBy(xpath = "//div[@id='repair-order-audit-log-modal' and @style]//ul[@id='logsTabs']/li/a[text()='Services']")
    private WebElement auditLogServicesTab;

    @FindBy(xpath = "//div[@id='repair-order-audit-log-modal' and @style]//ul[@id='logsTabs']/li/a[text()='Parts']")
    private WebElement auditLogPartsTab;

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

    private VNextBOAuditLogDialog clickAuditLogsTab(WebElement tab) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(auditLogsTabsList);
        Utils.clickElement(tab);
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForAttributeToBe(tab.findElement(By.xpath("./..")), "class", "active");
        return this;
    }

    public VNextBOAuditLogDialog clickAuditLogsAllTab() {
        clickAuditLogsTab(auditLogAllTab);
        return this;
    }

    public VNextBOAuditLogDialog clickAuditLogsPhasesAndDepartmentsTab() {
        clickAuditLogsTab(auditLogPhasesAndDepartmentsTab);
        return this;
    }

    public VNextBOAuditLogDialog clickAuditLogsServicesTab() {
        clickAuditLogsTab(auditLogServicesTab);
        return this;
    }

    public VNextBOAuditLogDialog clickAuditLogsPartsTab() {
        clickAuditLogsTab(auditLogPartsTab);
        return this;
    }

    public String getDepartmentsAndPhasesLastRecord() {
        return wait.until(ExpectedConditions.visibilityOf(departmentsAndPhasesLastRecord)).getText();
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

    public String getActualLocalDateTimePlusTwoMinutes() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm a");
        final LocalDateTime localDateTime = localDate.minusHours(2); //todo remove minusHours(2) method, if fixed
        final LocalDateTime localDateTimeVariant = localDateTime.plusMinutes(2);
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

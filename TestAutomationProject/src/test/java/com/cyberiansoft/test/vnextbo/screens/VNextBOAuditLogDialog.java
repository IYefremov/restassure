package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
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

    public VNextBOAuditLogDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
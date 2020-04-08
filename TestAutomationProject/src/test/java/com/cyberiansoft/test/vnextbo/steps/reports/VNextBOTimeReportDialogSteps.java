package com.cyberiansoft.test.vnextbo.steps.reports;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.reports.VNextBOReportsData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.reports.VNextBOTimeReportDialog;
import com.cyberiansoft.test.vnextbo.validations.reports.VNextBOTimeReportDialogValidations;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;

public class VNextBOTimeReportDialogSteps {

    public static void waitForTimeReportDialogToBeOpened() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOTimeReportDialog().getDialogHeader(), 2);
        VNextBOTimeReportDialogValidations.verifyTimeReportDialogIsDisplayed();
    }

    public static void waitForTimeReportDialogToBeClosed() {
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOTimeReportDialog().getDialogHeader(), 1);
        VNextBOTimeReportDialogValidations.verifyTimeReportDialogIsClosed();
    }

    public static void setTimeZone(String timeZone) {
        final WebElement timeZoneElement = new VNextBOTimeReportDialog().getTimeZone();
        WaitUtilsWebDriver.elementShouldBeClickable(timeZoneElement, true, 2);
        new Select(timeZoneElement).selectByVisibleText(timeZone);
    }

    public static String getTimeZone() {
        final WebElement timeZoneElement = new VNextBOTimeReportDialog().getTimeZone();
        return Utils.getText(new Select(timeZoneElement).getFirstSelectedOption());
    }

    public static void setTeam(String team) {
        final WebElement teamElement = new VNextBOTimeReportDialog().getTeam();
        WaitUtilsWebDriver.elementShouldBeClickable(teamElement, true, 2);
        new Select(teamElement).selectByVisibleText(team);
    }

    public static void setEmployee(String employee) {
        final WebElement employeeElement = new VNextBOTimeReportDialog().getEmployee();
        WaitUtilsWebDriver.elementShouldBeClickable(employeeElement, true, 2);
        new Select(new VNextBOTimeReportDialog().getEmployee()).selectByVisibleText(employee);
    }

    public static void setFrom(String date) {
        setDateValues(new VNextBOTimeReportDialog().getFromInputField(), date);
    }

    public static void setTo(String date) {
        setDateValues(new VNextBOTimeReportDialog().getToInputField(), date);
    }

    private static void setDateValues(WebElement inputField, String date) {
        final Actions actions = new Actions(DriverBuilder.getInstance().getDriver());
        actions.sendKeys(inputField, Keys.END).build().perform();
        while (!Utils.getInputFieldValue(inputField).isEmpty()) {
            actions.sendKeys(inputField, Keys.DELETE).build().perform();
        }
        Utils.sendKeys(inputField, date, Keys.ENTER);
    }

    public static void setEmail(String email) {
        Utils.clearAndType(new VNextBOTimeReportDialog().getEmailInputField(), email);
    }

    public static void setPhone(String phone) {
        Utils.clearAndType(new VNextBOTimeReportDialog().getPhoneInputField(), phone);
    }

    public static void setData(VNextBOReportsData data, String from, String to) {
        setTimeZone(data.getTimeZone());
        setTeam(data.getTeam());
        setEmployee(data.getEmployee());
        setFrom(from);
        setTo(to);
        setEmail(data.getEmail());
    }

    public static String getEmailValue() {
        return Utils.getInputFieldValue(new VNextBOTimeReportDialog().getEmailInputField(), 3);
    }

    public static void clickEmailCheckbox() {
        Utils.clickElement(new VNextBOTimeReportDialog().getEmailCheckbox());
    }

    public static void clickPhoneCheckbox() {
        Utils.clickElement(new VNextBOTimeReportDialog().getPhoneCheckbox());
    }

    public static void waitForFromFieldValidationError() {
        WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>) driver ->
                !Utils.getText(new VNextBOTimeReportDialog().getFromDateValidationError()).isEmpty());
    }

    public static void generateReport() {
        Utils.clickElement(new VNextBOTimeReportDialog().getGenerateReportButton());
        Utils.acceptAlertIfPresent();
    }

    public static void cancelGeneratingReport() {
        Utils.clickElement(new VNextBOTimeReportDialog().getCancelButton());
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOTimeReportDialog().getDialogHeader(), 1);
    }

    public static void closeTimeReportDialog() {
        Utils.clickElement(new VNextBOTimeReportDialog().getCloseButton());
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOTimeReportDialog().getDialogHeader(), 1);
    }

    public static String getPDFFileTextByEmail(String emailSubject) {
        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(VNextBOConfigInfo.getInstance().getVNextBONadaMail());
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder().withSubject(emailSubject);
        String mailMessage = null;
        try {
            mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
            nada.deleteAllMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PDFReader.getPDFText(mailMessage);
    }
}

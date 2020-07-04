package com.cyberiansoft.test.vnextbo.testcases.reports;

import com.cyberiansoft.test.dataclasses.vNextBO.alerts.VNextBOAlertMessages;
import com.cyberiansoft.test.dataclasses.vNextBO.reports.VNextBOReportsData;
import com.cyberiansoft.test.dataclasses.vNextBO.reports.VNextBOReportsDatesData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.reports.VNextBOReportsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.reports.VNextBOTimeReportDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.reports.VNextBOReportsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.reports.VNextBOTimeReportDialogValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOReportsTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getReportsTD();
    }

    @BeforeMethod
    public void goToPage() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectReportsMenu();
        VNextBOReportsPageValidations.verifyReportsColumnsAreDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanGenerateTimeReportAndReceiveByEmail(String rowID, String description, JSONObject testData) {
        VNextBOReportsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOReportsData.class);
        final VNextBOReportsDatesData dates = new VNextBOReportsDatesData();

        VNextBOReportsPageSteps.clickGenerateReportButton(data.getReport());
        VNextBOTimeReportDialogSteps.waitForTimeReportDialogToBeOpened();
        VNextBOTimeReportDialogValidations.verifyEmailIsDisplayed(data.getEmail());
        VNextBOTimeReportDialogSteps.setData(data, dates.getCurrentDateMinusDays(5), dates.getCurrentDateMinusDays(10));
        VNextBOTimeReportDialogSteps.generateReport();
        VNextBOTimeReportDialogSteps.waitForFromFieldValidationError();
        VNextBOTimeReportDialogValidations.verifyFromFieldValidationErrorIsDisplayed(VNextBOAlertMessages.FROM_DATE_SHOULD_BE_LESS);
        VNextBOTimeReportDialogSteps.setTo(dates.getCurrentDate());
        VNextBOTimeReportDialogSteps.generateReport();
        VNextBOTimeReportDialogSteps.waitForTimeReportDialogToBeClosed();
        final String pdfText = VNextBOTimeReportDialogSteps.getPDFFileTextByEmail("TimeReport");
        VNextBOReportsPageValidations.verifyFileContainsText(pdfText, "Time Report");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanGenerateTimeReportAndReceiveByPhone(String rowID, String description, JSONObject testData) {
        VNextBOReportsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOReportsData.class);
        final VNextBOReportsDatesData dates = new VNextBOReportsDatesData();

        VNextBOReportsPageSteps.clickGenerateReportButton(data.getReport());
        VNextBOTimeReportDialogSteps.waitForTimeReportDialogToBeOpened();
        //TODO finish automating after bug fix http://receive-sms-online.info/
        //TODO https://cyb.tpondemand.com/entity/118989-r360-bo-the-delivery-method-radio
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelTimeReportByClickingCancelButtonOrRejectingWithXIcon(String rowID, String description, JSONObject testData) {
        VNextBOReportsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOReportsData.class);

        VNextBOReportsPageValidations.verifyReportsAreDisplayed();
        VNextBOReportsPageSteps.clickGenerateReportButton(data.getReport());
        VNextBOTimeReportDialogSteps.waitForTimeReportDialogToBeOpened();
        VNextBOTimeReportDialogSteps.cancelGeneratingReport();
        VNextBOTimeReportDialogValidations.verifyTimeReportDialogIsClosed();
        VNextBOReportsPageSteps.clickGenerateReportButton(data.getReport());
        VNextBOTimeReportDialogSteps.waitForTimeReportDialogToBeOpened();
        VNextBOTimeReportDialogSteps.closeTimeReportDialog();
        VNextBOTimeReportDialogValidations.verifyTimeReportDialogIsClosed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheEmailAndPhoneFieldsAreValidated(String rowID, String description, JSONObject testData) {
        VNextBOReportsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOReportsData.class);

        VNextBOReportsPageSteps.clickGenerateReportButton(data.getReport());
        VNextBOTimeReportDialogSteps.waitForTimeReportDialogToBeOpened();
        VNextBOTimeReportDialogSteps.clickEmailCheckbox();
        Arrays.asList(data.getInvalidEmails()).forEach(email -> {
            VNextBOTimeReportDialogSteps.setEmail(email);
            VNextBOTimeReportDialogSteps.generateReport();
            VNextBOTimeReportDialogValidations.verifyEmailErrorMessageIsDisplayed();
        });
        VNextBOTimeReportDialogSteps.clickPhoneCheckbox();
        Arrays.asList(data.getInvalidPhones()).forEach(phone -> {
            VNextBOTimeReportDialogSteps.setPhone(phone);
            VNextBOTimeReportDialogSteps.generateReport();
            VNextBOTimeReportDialogValidations.verifyPhoneErrorMessageIsDisplayed();
        });
        VNextBOTimeReportDialogSteps.closeTimeReportDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySystemSupportsTeamTimeZones(String rowID, String description, JSONObject testData) {
        VNextBOReportsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOReportsData.class);

        VNextBOReportsPageSteps.clickGenerateReportButton(data.getReport());
        VNextBOTimeReportDialogSteps.waitForTimeReportDialogToBeOpened();
        VNextBOTimeReportDialogValidations.verifyTimeZone(data.getTimeZone());
        //todo bug https://cyb.tpondemand.com/entity/109774-r360-bo-need-to-add-time
    }
}

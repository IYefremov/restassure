package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.CustomersInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.EmailValidations;
import com.cyberiansoft.test.vnext.validations.InformationDialogValidations;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VNextEmailInspectionsTestCases extends BaseTestClass {

    private final RetailCustomer testCustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    private final RetailCustomer testCustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");
    List<RetailCustomer> retailCustomerList = new ArrayList<RetailCustomer>() {{add(testCustomer1); add(testCustomer2);}};

    @BeforeClass(description = "Email Inspections Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getEmailInspectionsTestCasesDataPath();

        HomeScreenSteps.openCustomers();
        CustomersScreenSteps.switchToRetailMode();
        retailCustomerList.stream().forEach(retailCustomer -> {
            if (!CustomersInteractions.isCustomerExists(retailCustomer))
                CustomersScreenSteps.createNewRetailCustomer(retailCustomer);
        });
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSendEmailInspectionsWithDifferentCustomers(String rowID,
                                                                                 String description, JSONObject testData) throws Exception {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<String> inspectionsList = new ArrayList<>();
        for (RetailCustomer retailCustomer : retailCustomerList) {
            HomeScreenSteps.openCreateMyInspection();
            InspectionSteps.createInspection(retailCustomer, InspectionTypes.O_KRAMAR, inspectionData);
            inspectionsList.add(InspectionSteps.saveInspection());
            ScreenNavigationSteps.pressBackButton();
        }
        HomeScreenSteps.openInspections();
        inspectionsList.stream().forEach(InspectionSteps::selectInspection);
        InspectionSteps.clickEmailButton();
        SelectCustomerScreenSteps.selectCustomer(retailCustomerList.get(0));
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.sendEmail(nadaEMailService.getEmailId());
        ScreenNavigationSteps.pressBackButton();

        final String inspectionReportFileName = inspectionsList.get(0) + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionsList.get(0), inspectionReportFileName);
        Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionsList.get(0) +
                " in mail box " + nadaEMailService.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nadaEMailService.deleteMessageWithSubject(inspectionsList.get(0));

        File pdfDoc = new File(inspectionReportFileName);
        String pdfText = PDFReader.getPDFText(pdfDoc);
        Assert.assertTrue(pdfText.contains(retailCustomerList.get(0).getFullName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSendEmailInspectionsWithTheSameCustomer(String rowID,
                                                                     String description, JSONObject testData) throws Exception {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsList = testCaseData.getInspectionsData();
        for (InspectionData inspectionData : inspectionsList) {
            HomeScreenSteps.openCreateMyInspection();
            InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
            inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
            ScreenNavigationSteps.pressBackButton();
        }
        HomeScreenSteps.openInspections();
        inspectionsList.stream().map(InspectionData::getInspectionNumber).forEach(InspectionSteps::selectInspection);
        InspectionSteps.clickEmailButton();
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.sendEmail(nadaEMailService.getEmailId());
        ScreenNavigationSteps.pressBackButton();

        for (InspectionData inspectionData : inspectionsList) {
            final String inspectionReportFileName = inspectionData.getInspectionNumber() + ".pdf";
            NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                    .withSubjectAndAttachmentFileName(inspectionData.getInspectionNumber(), inspectionReportFileName);
            Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionData.getInspectionNumber() +
                    " in mail box " + nadaEMailService.getEmailId() + ". At time " +
                    LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
            nadaEMailService.deleteMessageWithSubject(inspectionData.getInspectionNumber());

            File pdfDoc = new File(inspectionReportFileName);
            String pdfText = PDFReader.getPDFText(pdfDoc);
            Assert.assertTrue(pdfText.contains(testcustomer.getFullName()));
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantSendEmailWithoutFilledTOField(String rowID,
                                                                     String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionID = InspectionSteps.saveInspection();

        InspectionSteps.selectInspection(inspectionID);
        InspectionSteps.clickEmailButton();
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.sendMails("", nadaEMailService.getEmailId(), nadaEMailService.getEmailId());
        InformationDialogValidations.clickOKAndVerifyMessage("The TO box is empty. Please specify at least one recipient.");
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantSendEmailsToIncorrectMailboxes(String rowID,
                                                      String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String incorrectMail = "test2cyberiansoft.com";
        String warningMessage = "The email address %1$s is not valid. Please enter a valid email address.";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionID = InspectionSteps.saveInspection();

        InspectionSteps.selectInspection(inspectionID);
        InspectionSteps.clickEmailButton();
        EmailSteps.setToMailAddressField(incorrectMail);
        EmailSteps.clickSendEmails();
        InformationDialogValidations.clickOKAndVerifyMessage(String.format(warningMessage, incorrectMail));

        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.setToMailAddressField(nadaEMailService.getEmailId());
        EmailSteps.setToCCMailAddressField(incorrectMail);
        EmailSteps.clickSendEmails();
        InformationDialogValidations.clickOKAndVerifyMessage(String.format(warningMessage, incorrectMail));
        EmailSteps.setToCCMailAddressField(nadaEMailService.getEmailId());
        EmailSteps.setToBCCMailAddressField(incorrectMail);
        EmailSteps.clickSendEmails();
        InformationDialogValidations.clickOKAndVerifyMessage(String.format(warningMessage, incorrectMail));

        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddMoreThan1CCMailbox(String rowID,
                                                       String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String ccMail = "test2@cyberiansoft.com";
        final String ccMail2 = "test44@cyberiansoft.com";
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionID = InspectionSteps.saveInspection();

        InspectionSteps.selectInspection(inspectionID);
        InspectionSteps.clickEmailButton();
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.setToCCMailAddressField(ccMail);
        EmailSteps.setSecondToCCMailAddressField(ccMail2);
        EmailSteps.sendEmail(nadaEMailService.getEmailId());
        InspectionsValidations.verifyInspectionHasMailIcon(inspectionID, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanClearTheFields(String rowID,
                                      String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionID = InspectionSteps.saveInspection();

        InspectionSteps.selectInspection(inspectionID);
        InspectionSteps.clickEmailButton();
        NadaEMailService nadaEMailService = new NadaEMailService();
        EmailSteps.setToMailAddressField(nadaEMailService.getEmailId());
        EmailSteps.setToCCMailAddressField(nadaEMailService.getEmailId());
        EmailSteps.setToBCCMailAddressField(nadaEMailService.getEmailId());
        EmailSteps.clickRemoveEmailAddressButton();
        EmailSteps.clickRemoveCCEmailAddressButton();
        EmailSteps.clickRemoveBCCEmailAddressButton();
        EmailValidations.validateToEmailFieldValue("");
        EmailValidations.validateCCEmailFieldValue("");
        EmailValidations.validateBCCEmailFieldValue("");
        ScreenNavigationSteps.pressBackButton();

        ScreenNavigationSteps.pressBackButton();
    }
}

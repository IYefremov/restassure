package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOTimeReportingDialog;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOTimeReportingDialogSteps {

    public static void selectTechnician(String technician) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getSelectTechnicianDropDownField());
        Utils.clickWithJS(timeReportingDialog.dropDownOption(technician));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void selectPhase(String phase) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getPhaseDropDownField());
        Utils.clickWithJS(timeReportingDialog.dropDownOption(phase));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void clickStartedOnlyCheckbox() {

        Utils.clickElement(new VNextBOTimeReportingDialog().getStartedOnlyCheckBox());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static List<String> getDisplayedServicesList() {

        return new VNextBOTimeReportingDialog().getServicesList().
                stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public static void closeDialog() {

        Utils.clickElement(new VNextBOTimeReportingDialog().getCloseXIconButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void addNewRecordForService(String service) {

        Utils.clickElement(new VNextBOTimeReportingDialog().addButtonByServiceName(service));
    }

    public static int getSavedRecordsNumber() {

        return new VNextBOTimeReportingDialog().getSavedTimeRecordsList().size();
    }

    public static void cancelAddingNewRecord() {

        Utils.clickElement(new VNextBOTimeReportingDialog().getNotSavedRecordCancelIcon());
    }

    public static void saveNewRecord() {

        Utils.clickElement(new VNextBOTimeReportingDialog().getNotSavedRecordSaveIcon());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void setStartDateTimeForNewRecord(String statDate) {

        VNextBOTimeReportingDialog reportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(reportingDialog.getNotSavedRecordStartDateInputField());
        Utils.clearAndType(reportingDialog.getNotSavedRecordStartDateInputField(), statDate);
    }

    public static void setStopDateTimeForNewRecord(String stopDate) {

        VNextBOTimeReportingDialog reportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(reportingDialog.getNotSavedRecordStopDateInputField());
        Utils.clearAndType(reportingDialog.getNotSavedRecordStopDateInputField(), stopDate);
    }

    public static void setTechnicianForNewRecord(String technician) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getNotSavedRecordTechnicianDropDown());
        Utils.clickWithJS(timeReportingDialog.dropDownOption(technician));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void deleteRecordByNumberAndCancelWithCancelButton(int recordNumber) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getDeleteIconsList().get(recordNumber));
        VNextBOModalDialogSteps.clickCancelButton();
    }

    public static void deleteRecordByNumberAndCancelWithXIcon(int recordNumber) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getDeleteIconsList().get(recordNumber));
        VNextBOModalDialogSteps.clickCloseButton();
    }

    public static void deleteRecordByNumber(int recordNumber) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getDeleteIconsList().get(recordNumber));
        VNextBOModalDialogSteps.clickOkButton();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void changeStartDateByTimeRecordNumber(int recordNumber, String stopDate) {

        VNextBOTimeReportingDialog reportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(reportingDialog.getSavedRecordsStartDatesList().get(recordNumber));
        Utils.clearAndType(reportingDialog.getSavedRecordsStartDatesList().get(recordNumber), stopDate);
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickElement(reportingDialog.getSavedRecordsStopDatesList().get(recordNumber));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void changeStopDateByTimeRecordNumber(int recordNumber, String stopDate) {

        VNextBOTimeReportingDialog reportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(reportingDialog.getSavedRecordsStopDatesList().get(recordNumber));
        Utils.clearAndType(reportingDialog.getSavedRecordsStopDatesList().get(recordNumber), stopDate);
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickElement(reportingDialog.getSavedRecordsStartDatesList().get(recordNumber));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void changeTechnicianByTimeRecordNumber(int recordNumber, String technician) {

        VNextBOTimeReportingDialog reportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(reportingDialog.getSavedRecordsTechniciansList().get(recordNumber));
        Utils.clickWithJS(reportingDialog.dropDownOption(technician));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}

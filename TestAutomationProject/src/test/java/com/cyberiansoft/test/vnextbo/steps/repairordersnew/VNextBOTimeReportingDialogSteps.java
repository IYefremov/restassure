package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOTimeReportingDialog;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        return new VNextBOTimeReportingDialog().getSavedTimeRecords().size();
    }

    public static void cancelAddingNewRecord() {

        Utils.clickElement(new VNextBOTimeReportingDialog().getNotSavedRecordCancelIcon());
    }

    public static void saveNewRecord() {

        Utils.clickElement(new VNextBOTimeReportingDialog().getNotSavedRecordSaveIcon());
    }

    public static void setStartDateTime() {

        VNextBOTimeReportingDialog reportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(reportingDialog.getNotSavedRecordStartDate());
        Utils.clearAndType(reportingDialog.getNotSavedRecordStartDate(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a")));
    }

    public static void setStopDateTime() {

        VNextBOTimeReportingDialog reportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(reportingDialog.getNotSavedRecordStopDate());
        Utils.clearAndType(reportingDialog.getNotSavedRecordStopDate(), LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a")));
    }

    public static void setTechnicianForNewRecord(String technician) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getNotSavedRecordTechnicianDropDown());
        Utils.clickWithJS(timeReportingDialog.dropDownOption(technician));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void deleteRecordByNumberAndCancelWithCancelButton(int recordNumber) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getDeleteIcons().get(recordNumber));
        VNextBOModalDialogSteps.clickCancelButton();
    }

    public static void deleteRecordByNumberAndCancelWithXIcon(int recordNumber) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getDeleteIcons().get(recordNumber));
        VNextBOModalDialogSteps.clickCloseButton();
    }

    public static void deleteRecordByNumber(int recordNumber) {

        VNextBOTimeReportingDialog timeReportingDialog = new VNextBOTimeReportingDialog();
        Utils.clickElement(timeReportingDialog.getDeleteIcons().get(recordNumber));
        VNextBOModalDialogSteps.clickOkButton();
    }
}

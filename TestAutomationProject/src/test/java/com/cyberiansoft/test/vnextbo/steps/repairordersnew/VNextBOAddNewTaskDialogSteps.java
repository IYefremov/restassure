package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOAddNewTaskDialog;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOAddNewTaskDialogSteps {

    private static void setPhase(String phase) {

        VNextBOAddNewTaskDialog newTaskDialog = new VNextBOAddNewTaskDialog();
        Utils.clickElement(newTaskDialog.getPhaseDropDownField());
        Utils.clickWithJS(newTaskDialog.dropDownOption(phase));
        //WaitUtilsWebDriver.waitABit(2000);
    }

    private static void setTask(String task) {

        VNextBOAddNewTaskDialog newTaskDialog = new VNextBOAddNewTaskDialog();
        Utils.clickElement(newTaskDialog.getTaskDropDownField());
        Utils.clickWithJS(newTaskDialog.dropDownOption(task));
    }

    private static void setTeam(String team) {

        VNextBOAddNewTaskDialog newTaskDialog = new VNextBOAddNewTaskDialog();
        Utils.clickElement(newTaskDialog.getTeamDropDownField());
        Utils.clickWithJS(newTaskDialog.dropDownOption(team));
    }

    private static void setTechnician(String team, String technician) {

        VNextBOAddNewTaskDialog newTaskDialog = new VNextBOAddNewTaskDialog();
        Utils.clearAndType(newTaskDialog.getTechnicianDropDownField(), team + " : " + technician);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(newTaskDialog.dropDownOption(team + " : " + technician)));
        Utils.clickWithJS(newTaskDialog.dropDownOption(team + " : " + technician));
    }

    private static void setNote(String note) {

        Utils.clearAndType(new VNextBOAddNewTaskDialog().getNoteTextarea(), note);
    }

    public static void addNewTaskWithRequiredFields(VNextBOMonitorData taskData) {

        setTask(taskData.getTask());
        setTechnician(taskData.getTeam(), taskData.getTechnician());
        setNote(taskData.getNotesMessage());
        Utils.clickElement(new VNextBOAddNewTaskDialog().getSubmitButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void addNewTaskWithPredefinedTechnician(VNextBOMonitorData taskData) {

        setPhase(taskData.getPhase());
        setTask(taskData.getTask());
        setNote(taskData.getNotesMessage());
        Utils.clickElement(new VNextBOAddNewTaskDialog().getSubmitButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void addNewTaskWithAllFields(VNextBOMonitorData taskData, boolean saveTask) {

        setPhase(taskData.getPhase());
        setTask(taskData.getTask());
        setTeam(taskData.getTeam());
        setTechnician(taskData.getTeam(), taskData.getTechnician());
        setNote(taskData.getNotesMessage());
        if (saveTask) Utils.clickElement(new VNextBOAddNewTaskDialog().getSubmitButton());
        else Utils.clickElement(new VNextBOAddNewTaskDialog().getCancelButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}

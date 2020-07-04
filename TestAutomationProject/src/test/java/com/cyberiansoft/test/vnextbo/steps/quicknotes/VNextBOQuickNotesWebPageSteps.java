package com.cyberiansoft.test.vnextbo.steps.quicknotes;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.quicknotes.VNextBONewNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.quicknotes.VNextBOQuickNotesWebPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VNextBOQuickNotesWebPageSteps {

    public static void clickAddNoteButton() {

        Utils.clickElement(new VNextBOQuickNotesWebPage().getAddQuickNotesButton());
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.elementToBeClickable(new VNextBONewNotesDialog().getAddButton()));
    }

    public static void clickEditNoteButtonForNoteByDescription(String noteDescription) {

        Utils.clickElement(new VNextBOQuickNotesWebPage().editNoteButtonByDescription(noteDescription));
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.visibilityOf(new VNextBONewNotesDialog().getCloseButton()));
    }

    public static void clickDeleteNoteButtonForNoteByDescription(String noteDescription) {

        Utils.clickElement(new VNextBOQuickNotesWebPage().deleteNoteButtonByDescription(noteDescription));
    }

    public static int getNotesAmount() {

        return new VNextBOQuickNotesWebPage().getQuickNotesList().size();
    }

    public static void addNewNote(String noteDescription) {

        clickAddNoteButton();
        VNextBONewNotesDialogSteps.addNote(noteDescription);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void addNewNoteWithoutSave(String noteDescription) {

        clickAddNoteButton();
        VNextBONewNotesDialogSteps.addNoteWithoutSave(noteDescription);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void updateNote(String noteDescription, String newNoteDescription) {

        clickEditNoteButtonForNoteByDescription(noteDescription);
        VNextBONewNotesDialogSteps.updateNote(newNoteDescription);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void deleteNote(String noteDescription) {

        clickDeleteNoteButtonForNoteByDescription(noteDescription);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void moveNoteFromTheLastToTheFirstPositionInTheList() {

        List<WebElement> notesList = new VNextBOQuickNotesWebPage().getQuickNotesList();
        WebElement lastNote = notesList.get(notesList.size() - 1);
        WebElement firstNote = notesList.get(0);

        String xto=Integer.toString(firstNote.getLocation().x);
        String yto=Integer.toString(firstNote.getLocation().y);
        ((JavascriptExecutor)DriverBuilder.getInstance().getDriver()).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
                        "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
                lastNote,xto,yto);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}

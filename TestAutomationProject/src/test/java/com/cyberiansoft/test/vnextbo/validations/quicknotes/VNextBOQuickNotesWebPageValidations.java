package com.cyberiansoft.test.vnextbo.validations.quicknotes;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.quicknotes.VNextBOQuickNotesWebPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class VNextBOQuickNotesWebPageValidations {

    public static void verifyLastNoteDescription(String expectedNoteDescription, boolean equals) {

        List<WebElement> notesList = new ArrayList<>(new VNextBOQuickNotesWebPage().getQuickNotesList());
        if (equals) Assert.assertEquals(Utils.getText(notesList.get(notesList.size() - 1)),
                expectedNoteDescription, "Last note description has contained incorrect value");
        else Assert.assertFalse(Utils.getText(notesList.get(notesList.size() - 1)).equals(expectedNoteDescription),
                "Last note description has contained incorrect value");
    }

    public static void verifyNotesAmountIsCorrect(int expectedNotesAmount) {

        List<WebElement> notesList = new ArrayList<>(new VNextBOQuickNotesWebPage().getQuickNotesList());
        Assert.assertEquals(notesList.size(), expectedNotesAmount, "Notes amount hasn't been correct");
    }

    public static void verifyNoteIsPresentedInTheList(String noteDescription) {

        boolean isPresented = false;
        for (WebElement note : new VNextBOQuickNotesWebPage().getQuickNotesList()) {
            if (Utils.getText(note).equals(noteDescription)) isPresented = true;
        }
        Assert.assertTrue(isPresented, "Note has not been presented in the notes list");
    }

    public static void verifyNoteIsNotPresentedInTheList(String noteDescription) {

        boolean isPresented = false;
        for (WebElement note : new VNextBOQuickNotesWebPage().getQuickNotesList()) {
            if (Utils.getText(note).equals(noteDescription)) isPresented = true;
        }
        Assert.assertFalse(isPresented, "Note has been presented in the notes list");
    }
}

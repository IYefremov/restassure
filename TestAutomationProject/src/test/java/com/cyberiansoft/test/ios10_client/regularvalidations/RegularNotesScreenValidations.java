package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import org.testng.Assert;

public class RegularNotesScreenValidations {

    public static void verifyTextNotesPresent(String textNotes) {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        Assert.assertTrue(notesScreen.isNotesPresent(textNotes));
    }

    public static void verifyNumberOfImagesNotes(int expectedNumberOfImages) {
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        Assert.assertEquals(notesScreen.getNumberOfAdddePhotos(), expectedNumberOfImages);
    }
}

package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.enums.PartInfoScreenField;
import com.cyberiansoft.test.vnext.screens.PartInfoScreen;
import org.openqa.selenium.WebElement;

public class PartInfoScreenInteractions {
    public static WebElement getPartInfoScreenField(PartInfoScreenField fieldToFind) {
        PartInfoScreen partInfoScreen = new PartInfoScreen();
        return partInfoScreen.getPartInfoFields().stream()
                .filter(field -> field.getAttribute("data-field").equals(fieldToFind.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(" Part info field not found " + fieldToFind.getValue()));
    }
}

package com.cyberiansoft.test.vnextbo.interactions.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class VNextBOInspectionsPageInteractions {

    public static String getFirstInspectionStatus() {
        return Utils.getText(new VNextBOInspectionsWebPage().getInspectionStatusLabels().get(0));
    }

    public static void openSavedAdvancedSearchFilter(String filterName) {
        WaitUtilsWebDriver.waitABit(2000);
        final WebElement savedSearchListForm = new VNextBOInspectionsWebPage().getSavedSearchListForm();
        Utils.moveToElement(savedSearchListForm.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i")));
        Utils.clickElement(savedSearchListForm.findElement(By.xpath(".//div/span[text()='" + filterName + "']/../i")));
    }
}

package com.cyberiansoft.test.vnextbo.steps.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.services.VNextBOServicesAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;

public class VNextBOServicesAdvancedSearchSteps extends VNextBOBaseWebPageSteps {

    private static void setNameField(String serviceName) {

        Utils.clearAndType(new VNextBOServicesAdvancedSearchDialog().getNameField(), serviceName);
    }

    private static void setServiceType(String type) {

        VNextBOServicesAdvancedSearchDialog advancedSearchDialog = new VNextBOServicesAdvancedSearchDialog();
        Utils.clickElement(advancedSearchDialog.getTypeField());
        Utils.selectOptionInDropDownWithJs(advancedSearchDialog.getTypeFieldDropDown(),
                advancedSearchDialog.dropDownFieldOption(type));
    }

    private static void selectArchivedCheckBox() {

        Utils.clickElement(new VNextBOServicesAdvancedSearchDialog().getArchiveCheckBox());
    }

    private static void clickSearchButton() {

        Utils.clickElement(new VNextBOServicesAdvancedSearchDialog().getSearchButton());
    }

    public static void searchArchivedServiceByName(String serviceName) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        setNameField(serviceName);
        selectArchivedCheckBox();
        clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }
}
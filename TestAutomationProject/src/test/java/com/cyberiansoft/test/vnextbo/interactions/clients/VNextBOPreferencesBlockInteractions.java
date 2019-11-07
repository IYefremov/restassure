package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOPreferencesBlock;
import org.openqa.selenium.support.PageFactory;

public class VNextBOPreferencesBlockInteractions {

    private VNextBOPreferencesBlock preferencesBlock;

    public VNextBOPreferencesBlockInteractions() {
        preferencesBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOPreferencesBlock.class);
    }

    public void clickUseSingleWoTypeCheckbox() {
        Utils.clickElement(preferencesBlock.getUseSingleWoTypeCheckbox());
    }

    public void clickVehicleHistoryEnforcedCheckbox() {
        Utils.clickElement(preferencesBlock.getVehicleHistoryEnforcedCheckbox());
    }

    public void setDefaultArea(String option) {
        Utils.clickElement(preferencesBlock.getDefaultAreaArrow());
        Utils.selectOptionInDropDown(preferencesBlock.getDefaultAreaDropDown(),
                preferencesBlock.getDefaultAreaListBoxOptions(), option);
    }
}
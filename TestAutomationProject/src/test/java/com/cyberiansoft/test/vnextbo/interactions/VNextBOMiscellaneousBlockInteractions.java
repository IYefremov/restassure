package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOMiscellaneousBlock;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.support.PageFactory;

public class VNextBOMiscellaneousBlockInteractions {

    private VNextBOMiscellaneousBlock miscellaneousBlock;

    public VNextBOMiscellaneousBlockInteractions() {
        miscellaneousBlock = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOMiscellaneousBlock.class);
    }

    public void setNotes(String notes) {
        Utils.clearAndType(miscellaneousBlock.getNotesField(), notes);
    }

    public void verifyMiscellaneousBlockIsExpanded() {
        try {
            WaitUtilsWebDriver.waitForVisibility(miscellaneousBlock.getNotesField(), 4);
        } catch (Exception ignored) {
            new VNextBOClientsDetailsViewInteractions().clickMiscellaneousTab();
        }
    }
}
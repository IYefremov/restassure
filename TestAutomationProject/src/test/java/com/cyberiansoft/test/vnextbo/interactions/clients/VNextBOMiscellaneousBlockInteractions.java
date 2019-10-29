package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOMiscellaneousBlock;
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
}
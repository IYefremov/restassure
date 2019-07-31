package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.support.PageFactory;

public class VNextBOInspectionsSteps {

    private VNextBOInspectionsWebPage inspectionsPage;

    public VNextBOInspectionsSteps() {
        inspectionsPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsWebPage.class);
    }

    public VNextBOInspectionsApprovalWebPage clickTheApproveInspectionButton() {
        Utils.clickElement(inspectionsPage.getApproveInspectionIcon());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }
}
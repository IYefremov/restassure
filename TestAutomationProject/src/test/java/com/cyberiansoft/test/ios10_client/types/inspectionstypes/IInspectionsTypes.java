package com.cyberiansoft.test.ios10_client.types.inspectionstypes;

import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;

public interface IInspectionsTypes {

        public String getInspectionTypeName();
        public <T extends IBaseWizardScreen>T getFirstVizardScreen();
}

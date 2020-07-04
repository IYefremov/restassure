package com.cyberiansoft.test.vnext.steps.questionform;

import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.ImageScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class ImageScreenSteps {

    public static void removeImage() {

        WaitUtils.click(new ImageScreen().getRemoveButton());
        GeneralSteps.confirmDialog();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

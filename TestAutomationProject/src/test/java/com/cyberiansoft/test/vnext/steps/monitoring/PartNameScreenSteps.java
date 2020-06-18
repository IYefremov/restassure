package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.PartNameScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class PartNameScreenSteps {

    public static void selectPartName(String partName) {

        WaitUtils.collectionSizeIsGreaterThan(new PartNameScreen().getNamesRecordsList(), 0);
        WaitUtils.click(new PartNameScreen().getNamesRecordsList().
                stream()
                .filter(part -> part.getRecordText().contains(partName))
                .findFirst().get().getRootElement());
    }
}

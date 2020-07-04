package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.PartPositionScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class PartPositionScreenSteps {

    public static void selectPartPosition(String partPosition) {

        WaitUtils.collectionSizeIsGreaterThan(new PartPositionScreen().getPositionsRecordsList(), 0);
        WaitUtils.click(new PartPositionScreen().getPositionsRecordsList().
                stream()
                .filter(position -> position.getRecordText().contains(partPosition))
                .findFirst().get().getRootElement());
    }
}

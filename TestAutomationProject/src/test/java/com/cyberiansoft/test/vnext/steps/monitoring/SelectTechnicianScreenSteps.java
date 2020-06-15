package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.screens.monitoring.SelectTechnicianScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class SelectTechnicianScreenSteps {

    public static void selectTechnician(String technicianName) {

        WaitUtils.collectionSizeIsGreaterThan(new SelectTechnicianScreen().getTechniciansRecordsList(), 0);
        WaitUtils.click(new SelectTechnicianScreen().getTechniciansRecordsList().
                stream()
                .filter(technician -> technician.getRecordText().contains(technicianName))
                .findFirst().get().getRootElement());
    }
}

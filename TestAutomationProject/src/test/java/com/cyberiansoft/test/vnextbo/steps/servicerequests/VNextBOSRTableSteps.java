package com.cyberiansoft.test.vnextbo.steps.servicerequests;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.VNextBOSRTable;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOSRTableSteps {

    public static String getEmptyTableNotification() {
        return Utils.getText(new VNextBOSRTable().getEmptyTable());
    }

    public static List<String> getUniqueCustomerNameFields() {
        return Utils.getText(new VNextBOSRTable().getCustomersList()).stream().distinct().collect(Collectors.toList());
    }
}

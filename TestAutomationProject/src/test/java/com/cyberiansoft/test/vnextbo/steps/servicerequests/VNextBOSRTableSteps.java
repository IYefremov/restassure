package com.cyberiansoft.test.vnextbo.steps.servicerequests;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.VNextBOSRTable;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRDetailsPageSteps;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOSRTableSteps {

    public static String getEmptyTableNotification() {
        return Utils.getText(new VNextBOSRTable().getEmptyTable());
    }

    public static List<String> getUniqueCustomerNameFields() {
        return Utils.getText(new VNextBOSRTable().getCustomersList()).stream().distinct().collect(Collectors.toList());
    }

    public static List<String> getUniqueStockNumbersFields() {
        return Utils.getText(new VNextBOSRTable().getStockNumbersList()).stream().distinct().collect(Collectors.toList());
    }

    public static void openSRDetailsPage(int order) {
        Utils.clickElement(new VNextBOSRTable().getSrNumbersList().get(order));
        VNextBOSRDetailsPageSteps.waitForSRDetailsPageToBeOpened();
    }

    public static List<String> getSRNumValues() {
        return Utils.getText(new VNextBOSRTable().getSrNumbersList());
    }
}

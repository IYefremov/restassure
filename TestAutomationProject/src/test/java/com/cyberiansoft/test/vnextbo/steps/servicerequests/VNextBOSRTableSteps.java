package com.cyberiansoft.test.vnextbo.steps.servicerequests;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRSearchData;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.VNextBOSRTable;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOReactSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRDetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs.VNextBOSRAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.VNextBOSRTableValidations;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOSRTableSteps {

    public static String getEmptyTableNotification() {
        return Utils.getText(new VNextBOSRTable().getEmptyTable());
    }

    public static List<String> getUniqueCustomerNameFields() {
        return Utils.getText(new VNextBOSRTable().getCustomersList()).stream().distinct().collect(Collectors.toList());
    }

    public static List<String> getUniqueSrTypesFields() {
        return Utils.getText(new VNextBOSRTable().getSrTypesList()).stream().distinct().collect(Collectors.toList());
    }

    public static List<String> getUniqueCreatedDatesFields() {
        return Utils.getText(new VNextBOSRTable().getCreatedDatesList())
                .stream()
                .distinct()
                .map(date -> date.replace("Created: ", ""))
                .collect(Collectors.toList());
    }

    public static List<String> getUniqueAssignedNameFields() {
        return Utils.getText(new VNextBOSRTable().getAssignedList()).stream().distinct().collect(Collectors.toList());
    }

    public static List<String> getUniqueStatusesFields() {
        return Utils.getText(new VNextBOSRTable().getStatusesList()).stream().distinct().collect(Collectors.toList());
    }

    public static List<String> getUniqueStatusBadgeLettersFields() {
        return Utils.getText(new VNextBOSRTable().getStatusBadgeLettersList()).stream().distinct().collect(Collectors.toList());
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

    public static int getSRListSize() {
        return new VNextBOSRTable().getSrNumbersList().size();
    }

    public static void waitForServiceRequestsListToBeUpdated(int previousSRListSize) {
        try {
            WaitUtilsWebDriver.getWebDriverWait(7).until(
                    (ExpectedCondition<Boolean>) driver -> VNextBOSRTableSteps.getSRListSize() > previousSRListSize);
        } catch (Exception ignored) {}
    }

    public static void searchBySrNum(String srNum, VNextBOSRSearchData searchData) {
        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(searchData);
        VNextBOSRAdvancedSearchDialogSteps.setSrNum(srNum);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOSRTableSteps.getSRNumValues()
                .forEach(sr -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(sr, srNum));
        VNextBOReactSearchPanelSteps.clearSearchFilter();
    }
}

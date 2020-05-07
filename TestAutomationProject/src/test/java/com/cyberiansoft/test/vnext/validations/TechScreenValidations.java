package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.ErrorDialogInteractions;
import com.cyberiansoft.test.vnext.interactions.TechnicianScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class TechScreenValidations {

    public static void validateTechnicianSelected(Employee employee) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertTrue(VehicleInfoScreenInteractions.getDataFieldValue(VehicleDataField.VEHICLE_TECH).contains(employee.getEmployeeName()));
            return true;
        });
    }

    public static void validateTechniciansSelected(List<Employee> employeeList) {
        employeeList.forEach(TechScreenValidations::validateTechnicianSelected);
    }

    public static void verifyTechnicianIsSelected(String techName) {
        Assert.assertTrue(TechnicianScreenInteractions.getTechnicianElement(techName).isElementChecked(),
                "Technician hasn't been checked");
    }

    public static void validateTechnicianPercentage(String techName, String techPercentage) {
        Assert.assertEquals(TechnicianScreenInteractions.getTechnicianElement(techName).getPercentageAmount(), techPercentage);
    }

    public static void validateTechniciansPercentage(Map<String, String> techPercentageList) {
        techPercentageList.forEach(TechScreenValidations::validateTechnicianPercentage);
    }

    public static void validateTechnicianValue(String techName, String techPercentage) {
        Assert.assertEquals(TechnicianScreenInteractions.getTechnicianElement(techName).getValueAmount(), techPercentage);
    }

    public static void validateTechniciansValues(Map<String, String> techPercentageList) {
        techPercentageList.forEach(TechScreenValidations::validateTechnicianValue);
    }

    public static void validatePercentageErrorMessageIsDisplayed() {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ErrorDialogInteractions.getErrorDialogText(),
                    "Total amount is not equal 100%.");
            return true;
        });
    }

    public static void verifyTechnicianIsSelectedWithCorrectPercentage(String techName, String techPercentage) {

        TopScreenPanelSteps.searchData(techName);
        TechScreenValidations.verifyTechnicianIsSelected(techName);
        TechScreenValidations.validateTechnicianPercentage(techName, techPercentage);
        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
    }
}

package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.ErrorDialogInteractions;
import com.cyberiansoft.test.vnext.interactions.TechnicianScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class TechScreenValidations {

    public static void validateTechnicianSelected(Employee employee) {
        WaitUtils.getGeneralWebdriverWait().until(driver -> {
            Assert.assertTrue(VehicleInfoScreenInteractions.getDataFieldValue(VehicleDataField.VEHICLE_TECH).contains(employee.getEmployeeName()));
            return true;
        });
    }

    public static void validateTechniciansSelected(List<Employee> employeeList) {
        employeeList.forEach(TechScreenValidations::validateTechnicianSelected);
    }

    public static void validateTechnicianPercentage(String techName, String techPercentage) {
        Assert.assertEquals(TechnicianScreenInteractions.getTechnicianElement(techName).getPercentageAmount(), techPercentage);
    }

    public static void validateTechniciansPercentage(Map<String, String> techPercentageList) {
        techPercentageList.forEach(TechScreenValidations::validateTechnicianPercentage);
    }

    public static void validatePercentageErrorMessageIsDisplayed() {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(ErrorDialogInteractions.getErrorDialogText(),
                    "Total amount is not equal 100%.");
            return true;
        });
    }
}

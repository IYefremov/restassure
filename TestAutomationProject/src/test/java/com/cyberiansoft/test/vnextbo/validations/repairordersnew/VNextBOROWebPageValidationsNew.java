package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROWebPageNew;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOROWebPageValidationsNew {

    public static void verifyCustomersAreCorrectInTheTable(String expectedCustomer) {

        for (WebElement customer: new VNextBOROWebPageNew().getOrdersCustomersList()) {
            Assert.assertEquals(Utils.getText(customer), expectedCustomer, "Customer hasn't been correct");
        }
    }

    public static void verifyEmployeesAreCorrectInTheTable(String expectedEmployee) {

        for (WebElement technician: new VNextBOROWebPageNew().getOrdersTechniciansList()) {
            Assert.assertTrue(Utils.getText(technician).contains(expectedEmployee), "Employee hasn't been correct");
        }
    }

    public static void verifyPhasesAreCorrectInTheTable(String expectedPhase) {

        for (WebElement phase: new VNextBOROWebPageNew().getOrdersPhasesList()) {
            Assert.assertEquals(Utils.getText(phase), expectedPhase, "Phase hasn't been correct");
        }
    }

    public static void verifyDepartmentsAreCorrectInTheTable(String expectedDepartment) {

        for (WebElement department: new VNextBOROWebPageNew().getOrdersDepartmentsList()) {
            Assert.assertEquals(Utils.getText(department), expectedDepartment, "Department hasn't been correct");
        }
    }

    public static void verifyWoTypesAreCorrectInTheTable(String expectedWoType) {

        for (WebElement woType: new VNextBOROWebPageNew().getWoTypesList()) {
            Assert.assertEquals(Utils.getText(woType), expectedWoType, "WO type hasn't been correct");
        }
    }

    public static void verifyWoNumbersAreCorrectInTheTable(String expectedWoNumber) {

        for (WebElement woNumber: new VNextBOROWebPageNew().getWoNumbersList()) {
            Assert.assertEquals(Utils.getText(woNumber), expectedWoNumber, "WO number hasn't been correct");
        }
    }

    public static void verifyRoNumbersAreCorrectInTheTable(String expectedRoNumber) {

        for (WebElement roNumber: new VNextBOROWebPageNew().getRoNumbersList()) {
            Assert.assertEquals(Utils.getInputFieldValue(roNumber), expectedRoNumber, "RO number hasn't been correct");
        }
    }

    public static void verifyStockNumbersAreCorrectInTheTable(String expectedStockNumber) {

        for (WebElement stockNumber: new VNextBOROWebPageNew().getStockNumbersList()) {
            Assert.assertEquals(Utils.getInputFieldValue(stockNumber), expectedStockNumber, "Stock number hasn't been correct");
        }
    }

    public static void verifyVinNumbersAreCorrectInTheTable(String expectedVinNumber) {

        for (WebElement vinNumber: new VNextBOROWebPageNew().getVinNumbersList()) {
            Assert.assertEquals(Utils.getText(vinNumber), expectedVinNumber, "VIN number hasn't been correct");
        }
    }
}

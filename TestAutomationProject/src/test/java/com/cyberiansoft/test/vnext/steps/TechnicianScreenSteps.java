package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.vnext.interactions.TechnicianScreenInteractions;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;

import java.util.List;
import java.util.Map;

public class TechnicianScreenSteps {
    public static void selectEvenlyPercentageSpilt() {
        TechnicianScreenInteractions.selectEvenlyOption();
    }

    public static void selectTechniciansPercentage(Map<String, String> techniciansPercentageList) {
        TechnicianScreenInteractions.selectCustomOption();
        techniciansPercentageList.forEach(TechnicianScreenInteractions::setTechnicianPercentage);
        TechnicianScreenInteractions.acceptScreen();
    }

    public static void selectTechnicians(List<Employee> employeeList) {
        TechnicianScreenInteractions.selectEvenlyOption();
        employeeList.forEach((employee) -> TechnicianScreenInteractions.selectTechnician(employee.getEmployeeName()));
    }

    public static void closeTechnicianMenu() {
        TechnicianScreenInteractions.acceptScreen();
    }

    public static void selectDefault() {
        TechnicianScreenInteractions.selectDefault();
    }

    public static void searchAndSelectTechnician(String techName) {

        TopScreenPanelSteps.searchData(techName);
        TechnicianScreenInteractions.selectTechnician(techName);
        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
    }
}

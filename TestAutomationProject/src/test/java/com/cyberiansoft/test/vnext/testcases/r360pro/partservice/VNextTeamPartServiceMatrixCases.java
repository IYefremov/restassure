package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.MatrixServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.MatrixServiceDetailsValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamPartServiceMatrixCases extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";

    @BeforeClass(description = "Team Monitoring Matrix Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPartServiceMatrixCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanCreateInspectionWithPartsMatrixService(String rowID,
                                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<MatrixServiceData> matrixServiceData = workOrderData.getMatrixServiceDataList();
        MatrixServiceData basicPartsServiceMatrixService = matrixServiceData.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartsServiceMatrixService.getServiceName());
        MatrixServiceSteps.selectMatrixService(basicPartsServiceMatrixService);
        basicPartsServiceMatrixService.getPartServiceDataList().forEach(MatrixServiceSteps::selectPartServiceInsideMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
        basicPartsServiceMatrixService.getPartServiceDataList().forEach(serviceData -> MatrixServiceDetailsValidations.validateServiceSelected(serviceData.getServiceName()));
        MatrixServiceDetailsValidations.validateMatrixServiceDetails(basicPartsServiceMatrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}

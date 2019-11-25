package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOPartsManagementTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementTD();
        com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }


    @BeforeMethod
    public void BackOfficeLogin() {

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenOperationsPartsManagementWithFullSetOfElements(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeePartsDetailsOfDifferentROs(String rowID, String description, JSONObject testData) {

    }

    //todo often fails, bug!!! Sometimes forbidden status option is displayed for Past Due Parts search
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithPastDuePartsOption(String rowID, String description, JSONObject testData) {

    }

    //todo often fails, bug!!! Sometimes forbidden status option is displayed for In Progress search
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithInProgressOption(String rowID, String description, JSONObject testData) {

    }

    //todo often fails, bug!!! Sometimes forbidden status option is displayed for Completed search
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectOrdersWithCompletedOption(String rowID, String description, JSONObject testData) {

    }
}
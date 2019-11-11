package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.testng.annotations.BeforeClass;

public class VNextBOMonitorReportProblemTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorReportProblemTD();
    }
}

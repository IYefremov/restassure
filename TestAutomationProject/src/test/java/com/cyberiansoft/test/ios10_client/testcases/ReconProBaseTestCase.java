package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.types.envtypes.IOSReconproEnvironmentType;
import org.testng.annotations.BeforeClass;

public class ReconProBaseTestCase extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        envType = IOSReconproEnvironmentType.getEnvironmentType(ReconProIOSStageInfo.getInstance().getEnvironmentType());
        if (envType.equals(IOSReconproEnvironmentType.DEVELOPMENT))
            deviceofficeurl = ReconProIOSStageInfo.getInstance().getBackOfficeDevelopmentURL();
        else if (envType.equals(IOSReconproEnvironmentType.INTEGRATION))
            deviceofficeurl = ReconProIOSStageInfo.getInstance().getBackOfficeStagingURL();
        else if (envType.equals(IOSReconproEnvironmentType.UAT))
            deviceofficeurl = ReconProIOSStageInfo.getInstance().getBackOfficeUATURL();

    }
}

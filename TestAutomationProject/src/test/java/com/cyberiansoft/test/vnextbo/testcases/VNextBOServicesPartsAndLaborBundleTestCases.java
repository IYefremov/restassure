package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOServicesPartsAndLaborBundleData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNexBOServicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextBOServicesPartsAndLaborBundleTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOServicesPartsAndLaborBundleData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

	private String userName;
	private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;
    private VNexBOLeftMenuPanel leftMenu;

	@BeforeMethod
	public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        WebDriverUtils.webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
		userName = VNextBOConfigInfo.getInstance().getVNextBOMail();
		userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage
                .userLogin(userName, userPassword)
                .executeJsForAddOnSettings(); //todo use the method getJsForAddOnSettings() from VNextBOServicesPartsAndLaborBundleData.java after fix
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }
	
	@AfterMethod
	public void BackOfficeLogout() {
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		if (headerpanel.logOutLinkExists())
			headerpanel.userLogout();
	}
	
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddLaborPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNexBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName())
                .clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())//todo fix, bug #72232
                .setServicePriceValue(data.getServicePrice())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddLaborPriceServiceWithUseLaborTimes(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNexBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName())
                .clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServicePriceValue(data.getServicePrice())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                //todo not stable because of the bug
                .checkUseLaborTimesCheckbox()
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotAddLaborServiceWithClickedRejectIcon(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNexBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName())
                .clickAddNewServiceButton()
                //todo finish

                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
    }
}
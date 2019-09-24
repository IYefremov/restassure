package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOServicesPartsAndLaborBundleData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

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

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
		userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
		userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = new VNextBOLoginScreenWebPage(webdriver);
        loginPage.userLogin(userName, userPassword);
        VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel(webdriver);
        headerPanel.executeJsForAddOnSettings(); //todo use the method getJsForAddOnSettings() from VNextBOServicesPartsAndLaborBundleData.java after fix
        leftMenu = new VNexBOLeftMenuPanel(webdriver);
    }
	
	@AfterMethod
	public void BackOfficeLogout() {
		VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel(webdriver);
		if (headerPanel.logOutLinkExists())
			headerPanel.userLogout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
	}
	
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddLaborPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
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
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddLaborPriceServiceWithUseLaborTimes(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .checkUseLaborTimesCheckbox()
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCannotAddLaborServiceWithClickedRejectIcon(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .checkUseLaborTimesCheckbox()
                .clickRejectNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddPartPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .clickSaveNewServiceButton();
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddPartPriceServiceWithCategorySubcategoryAndPartName(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);
        VNextBOAddNewServiceDialog addNewServiceDialog = new VNextBOAddNewServiceDialog(webdriver);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .selectPartCategory(data.getServicePartCategory())
                .selectSubcategory(data.getServicePartSubcategory())
                .selectPartName(data.getServicePartName())
                .clickNewServicePopupAddButton();

        Assert.assertTrue(addNewServiceDialog.getServicePartsInfoList().containsAll(data.getPartOptionsList()));

        addNewServiceDialog.clickSaveNewServiceButton();
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteLaborPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServicePriceValue(data.getServicePrice())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanRestoreDeletedLaborPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServicePriceValue(data.getServicePrice())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));

        servicesPage.deleteServiceByServiceName(data.getServiceName());

        servicesPage.performAdvancedSearchService(data.getServiceName(), data.getServiceType(), true);
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.unarchiveServiceByServiceName(data.getServiceName());
        Assert.assertFalse(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeletePartPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .clickSaveNewServiceButton();
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanRestoreDeletedPartPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .clickSaveNewServiceButton();
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));

        servicesPage.deleteServiceByServiceName(data.getServiceName());

        servicesPage.performAdvancedSearchService(data.getServiceName(), data.getServiceType(), true);
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.unarchiveServiceByServiceName(data.getServiceName());
        Assert.assertFalse(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditPartPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .clickSaveNewServiceButton();
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));

        servicesPage.deleteServiceByServiceName(data.getServiceName());

        servicesPage.performAdvancedSearchService(data.getServiceName(), data.getServiceType(), true);
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.unarchiveServiceByServiceName(data.getServiceName());
        Assert.assertFalse(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditLaborPriceService(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .clickSaveNewServiceButton();
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForMoneyServiceOrder(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .clickSaveNewServiceButton();
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForPercentageServiceOrder(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServicePriceValue(data.getServicePrice())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForLaborServiceOrder(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServicePriceValue(data.getServicePrice())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeSequenceNumberForPartServiceOrder(String rowID, String description, JSONObject testData) {
        VNextBOServicesPartsAndLaborBundleData data = JSonDataParser.getTestDataFromJson(testData, VNextBOServicesPartsAndLaborBundleData.class);

        VNextBOServicesWebPage servicesPage = leftMenu
                .selectServicesMenu()
                .searchServiceByServiceName(data.getServiceName())
                .deleteServiceIfPresent(data.getServiceName());
        int numOfServicesOnPage = servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName());

        servicesPage.clickAddNewServiceButton()
                .setServiceName(data.getServiceName())
                .setServiceDescription(data.getServiceDescription())
                .selectServicePriceType(data.getServicePriceType())
                .setServicePriceValue(data.getServicePrice())
                .setServiceLaborRate(data.getServiceLaborRate())
                .setServiceDefaultLaborTime(data.getServiceDefaultLaborTime())
                .clickSaveNewServiceButton()
                .searchServiceByServiceName(data.getServiceName());
        Assert.assertTrue(servicesPage.isServicePresentOnCurrentPageByServiceName(data.getServiceName()));
        Assert.assertEquals(numOfServicesOnPage + 1, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
        servicesPage.deleteServiceIfPresent(data.getServiceName());
        Assert.assertEquals(numOfServicesOnPage, servicesPage.getNumOfServicesOnCurrentPageByServiceName(data.getServiceName()));
    }
}
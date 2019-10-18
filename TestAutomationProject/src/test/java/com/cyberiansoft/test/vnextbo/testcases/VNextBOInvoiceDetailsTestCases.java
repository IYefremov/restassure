package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInvoiceDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInvoiceDetailsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOInvoiceDetailsData.json";

	private String userName;
	private String userPassword;
	private VNextBOLoginScreenWebPage loginPage;
	private VNextBOROWebPage repairOrdersPage;

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@BeforeMethod
	public void BackOfficeLogin() {
		browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
		try {
			DriverBuilder.getInstance().setDriver(browserType);
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		webdriver = DriverBuilder.getInstance().getDriver();

		webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
		userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
		userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

		loginPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOLoginScreenWebPage.class);
		loginPage.userLogin(userName, userPassword);
	}

	@AfterMethod
	public void BackOfficeLogout() {
        new VNextBOHeaderPanelSteps().logout();

		if (DriverBuilder.getInstance().getDriver() != null)
			DriverBuilder.getInstance().quitDriver();
	}

	//TODO test disabled, TC 45496 should be updated or test should be completely re-implemented
	@Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSetupConfigurationToRunInvoiceListSuite(String rowID, String description, JSONObject testData) {

		VNextBOInvoiceDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInvoiceDetailsData.class);

		VNexBOUsersWebPage usersWebPage = PageFactory.initElements(webdriver, VNexBOUsersWebPage.class);
		VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
		leftMenuInteractions.selectUsersMenu();
		final String usermail = data.getUserMailPrefix() + RandomUtils.nextInt(100000, 1000000) + data.getUserMailPostbox();
		System.out.println(usermail);
		VNexBOAddNewUserDialog adduserdialog = usersWebPage.clickAddUserButton();
		adduserdialog.createNewUser(data.getTechFirstName(), data.getTechLastName(), usermail, data.getTechUserPhone(), false);
		Assert.assertTrue(usersWebPage.findUserInTableByUserEmail(usermail));
        new VNextBOHeaderPanelSteps().logout();

		webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getBOoldURL());
		BackOfficeLoginWebPage oldloginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		oldloginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(),
				VNextBOConfigInfo.getInstance().getVNextBOPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = new ClientsWebPage(webdriver);
		companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		final String fullcustomer = data.getFirstName() + " " + data.getLastName();
		clientspage.searchClientByName(fullcustomer);

		if (!clientspage.isClientPresentInTable(fullcustomer)) {
			NewClientDialogWebPage newclientpage = new NewClientDialogWebPage(webdriver);
			clientspage.clickAddClientButton();
			newclientpage.setCompanyName(data.getCompanyName());
			newclientpage.setClientFirstName(data.getFirstName());
			newclientpage.setClientLastName(data.getLastName());
			newclientpage.setCompanyMail(data.getCompanyMail());
			newclientpage.setCompanyPhone(data.getCompanyPhone());
			newclientpage.setCompanyShipToAddress(data.getShipToAddress());
			newclientpage.setCompanyShipToAddress2(data.getShipToAddress2());
			newclientpage.setCompanyShipToCity(data.getShipToCity());
			newclientpage.selectCompanyShipToCountry(data.getCountry());
			newclientpage.selectCompanyShipToState(data.getState());
			newclientpage.setCompanyShipToZip(data.getShipToZip());
			newclientpage.clickOKButton();
		}
	}
}

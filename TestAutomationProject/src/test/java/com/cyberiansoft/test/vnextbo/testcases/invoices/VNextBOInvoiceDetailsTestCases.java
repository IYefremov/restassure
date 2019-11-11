package com.cyberiansoft.test.vnextbo.testcases.invoices;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInvoiceDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOAddNewUserDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.users.VNextBOUsersPageValidations;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInvoiceDetailsTestCases extends BaseTestCase {

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getInvoicesDetailsTD();
	}

	//TODO test disabled, TC 45496 should be updated or test should be completely re-implemented
	@Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSetupConfigurationToRunInvoiceListSuite(String rowID, String description, JSONObject testData) {

		VNextBOInvoiceDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInvoiceDetailsData.class);

        VNextBOLeftMenuInteractions.selectUsersMenu();
		final String usermail = data.getUserMailPrefix() + RandomUtils.nextInt(100000, 1000000) + data.getUserMailPostbox();
		System.out.println(usermail);
		VNextBOAddNewUserDialogSteps.createNewUser(data.getTechFirstName(), data.getTechLastName(),
				usermail, data.getTechUserPhone(), false);
		Assert.assertTrue(VNextBOUsersPageValidations.verifyUserIsPresentInTableByText(usermail));
        VNextBOHeaderPanelSteps.logout();

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

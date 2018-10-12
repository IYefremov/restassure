package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInvoiceDetailsData;
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

import java.util.UUID;

public class VNextBOInvoiceDetailsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOInvoiceDetailsData.json";

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
        WebDriverUtils.webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
                VNextBOHeaderPanel.class);
        if (headerpanel.logOutLinkExists())
            headerpanel.userLogout();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSetupConfigurationToRunInvoiceListSuite(String rowID, String description, JSONObject testData) {

        VNextBOInvoiceDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInvoiceDetailsData.class);
        
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		final String usermail = data.getUserMailPrefix() + UUID.randomUUID() + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(data.getTechFirstName(), data.getTechLastName(), usermail, data.getTechUserPhone(), false);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
		
		WebDriverUtils.webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getBOoldURL());
		BackOfficeLoginWebPage oldloginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		oldloginpage.UserLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(),
                VNextBOConfigInfo.getInstance().getVNextBOPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.makeSearchPanelVisible();
		final String fullcustomer = data.getFirstName() + " " + data.getLastName();
		clientspage.searchClientByName(fullcustomer);
		
		if (!clientspage.isClientPresentInTable(fullcustomer)) {
			NewClientDialogWebPage newclientpage = clientspage.clickAddClientButton();
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

package com.cyberiansoft.test.vnextbo.testcases;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNexBOServicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAddNewServiceDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextBOServicestestCases extends BaseTestCase {
	
	String userName = "";
	String userPassword = "";
	String usermail = "";
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String usernm, String userpsw) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		userName = usernm;
		userPassword = userpsw;
	}
	
	@AfterMethod
	public void BackOfficeLogout() {		
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		if (headerpanel.isLogOutLinkExists())
			headerpanel.userLogout();		
	}
	
	@Test(description = "Test Case 43806:vNext: add money service")
	public void testAddMoneyService() throws IOException {
		
		final String servicename = "Test Money Service";
		final String servicetype = "Dent Repair";
		final String servicedesc = "test money service";
		final String servicepricetype = "Money";
		final String serviceprice = "5";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewserviceButton();
		servicespage = addnewservicedialog.addNewService(servicename, servicetype, servicedesc, servicepricetype, serviceprice);
		servicespage.searchServiceByServiceName(servicename);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(servicename));
	}
	

	@Test(description = "Test Case 43807:vNext: add percentage service")
	public void testAddPercentageService() throws IOException {
		
		final String servicename = "Test Percentage service";
		final String servicetype = "Dent Repair";
		final String servicedesc = "test percentage service";
		final String servicepricetype = "Percentage";
		final String serviceprice = "5";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewserviceButton();
		servicespage = addnewservicedialog.addNewPercentageService(servicename, servicetype, servicedesc, servicepricetype, serviceprice);
		servicespage.searchServiceByServiceName(servicename);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(servicename));
	}

}

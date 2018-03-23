package com.cyberiansoft.test.vnextbo.testcases;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNexBOServicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAddNewServiceDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextConfirmationDialog;
import com.cyberiansoft.test.vnextbo.utils.VNextPriceCalculations;

public class VNextBOServicestestCases extends BaseTestCase {
	
	String userName = "";
	String userPassword = "";
	String usermail = "";
	
	final String priceservicename = "Test Money Service";
	final String servicetype = "Dent Repair";
	final String servicedesc = "test money service";
	final String servicepricetype = "Money";
	final String serviceprice = "5";
	
	final String servicetypeedited = "Other";
	final String servicepriceedited = "1";
	final String serviceedited = " edit";
	
	final String percentageservicename = "Test Percentage service";
	final String percentageservicedesc = "test percentage service";
	final String servicepercentagetype = "Percentage";
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String usernm, String userpsw) {
		WebDriverUtils.webdriverGotoWebPage(backofficeurl);
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
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.searchServiceByServiceName(priceservicename);
		if (servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename))
			servicespage.deleteServiceByServiceName(priceservicename);
		if (servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename + serviceedited))
			servicespage.deleteServiceByServiceName(priceservicename + serviceedited);
		
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewserviceButton();
		servicespage = addnewservicedialog.addNewService(priceservicename, servicetype, servicedesc, servicepricetype, serviceprice);
		servicespage.searchServiceByServiceName(priceservicename);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename));
	}
	

	@Test(description = "Test Case 43807:vNext: add percentage service")
	public void testAddPercentageService() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.searchServiceByServiceName(percentageservicename);
		if (servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename))
			servicespage.deleteServiceByServiceName(percentageservicename);
		if (servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename + serviceedited))
			servicespage.deleteServiceByServiceName(percentageservicename + serviceedited);
		
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewserviceButton();
		servicespage = addnewservicedialog.addNewPercentageService(percentageservicename, servicetype, percentageservicedesc, servicepercentagetype, serviceprice);
		servicespage.searchServiceByServiceName(percentageservicename);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename));
	}
	
	@Test(description = "Test Case 43812:vNext: Edit money service",
			dependsOnMethods = { "testAddMoneyService" })
	public void testEditMoneyService() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.searchServiceByServiceName(priceservicename);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename));
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickEditServiceByServiceName(priceservicename);
		Assert.assertEquals(addnewservicedialog.getServiceName(), priceservicename);
		Assert.assertEquals(addnewservicedialog.getServiceType(), servicetype);
		Assert.assertEquals(addnewservicedialog.getServiceDescription(), servicedesc);
		Assert.assertEquals(addnewservicedialog.getServicePricePercentageValueTxtField().getAttribute("value"), VNextPriceCalculations.getPriceRepresentation(serviceprice));
		Assert.assertTrue(addnewservicedialog.isServicePriceTypeVisible());
		
		addnewservicedialog.setServiceName(priceservicename + serviceedited);
		addnewservicedialog.selectServiceType(servicetypeedited);
		addnewservicedialog.setServiceDescription(servicedesc + serviceedited);
		addnewservicedialog.setServicePriceValue(servicepriceedited);
		servicespage = addnewservicedialog.saveNewService();
		servicespage.searchServiceByServiceName(priceservicename + serviceedited);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename + serviceedited));
		
		Assert.assertEquals(servicespage.getServiceTypeValue(priceservicename + serviceedited), servicetypeedited);
		Assert.assertEquals(servicespage.getServicePriceValue(priceservicename + serviceedited), VNextPriceCalculations.getPriceRepresentation(servicepriceedited));
		Assert.assertEquals(servicespage.getServiceDescriptionValue(priceservicename + serviceedited), servicedesc + serviceedited);
	}
	
	@Test(description = "Test Case 44149:vNext: Remove money service",
			dependsOnMethods = { "testEditMoneyService" })
	public void testRemoveMoneyService() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.searchServiceByServiceName(priceservicename + serviceedited);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename + serviceedited));
		servicespage.deleteServiceByServiceName(priceservicename + serviceedited);
		Assert.assertFalse(servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename + serviceedited));
	}
	
	@Test(description = "Test Case 43813:vNext: Edit percentage service",
			dependsOnMethods = { "testAddPercentageService" })
	public void testEditPercentageService() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.searchServiceByServiceName(percentageservicename);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename));
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickEditServiceByServiceName(percentageservicename);
		Assert.assertEquals(addnewservicedialog.getServiceName(), percentageservicename);
		Assert.assertEquals(addnewservicedialog.getServiceType(), servicetype);
		Assert.assertEquals(addnewservicedialog.getServiceDescription(), percentageservicedesc);
		Assert.assertEquals(addnewservicedialog.getServicePricePercentageValueTxtField().getAttribute("value"), VNextPriceCalculations.getPercentageRepresentation(serviceprice));
		Assert.assertTrue(addnewservicedialog.isServicePriceTypeVisible());
		
		addnewservicedialog.setServiceName(percentageservicename + serviceedited);
		addnewservicedialog.selectServiceType(servicetypeedited);
		addnewservicedialog.setServiceDescription(percentageservicedesc + serviceedited);
		addnewservicedialog.setServicePercentageValue(servicepriceedited);
		servicespage = addnewservicedialog.saveNewService();
		servicespage.searchServiceByServiceName(percentageservicename + serviceedited);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename + serviceedited));
		
		Assert.assertEquals(servicespage.getServiceTypeValue(percentageservicename + serviceedited), servicetypeedited);
		Assert.assertEquals(servicespage.getServicePriceValue(percentageservicename + serviceedited), VNextPriceCalculations.getPercentageRepresentation(servicepriceedited));
		Assert.assertEquals(servicespage.getServiceDescriptionValue(percentageservicename + serviceedited), percentageservicedesc + serviceedited);
	}
	
	@Test(description = "Test Case 44151:vNext: Remove percentage service",
			dependsOnMethods = { "testEditPercentageService" })
	public void testRemovePercentageService() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.searchServiceByServiceName(percentageservicename + serviceedited);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename + serviceedited));
		servicespage.deleteServiceByServiceName(percentageservicename + serviceedited);
		Assert.assertFalse(servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename + serviceedited));
	}
	
	@Test(description = "Test Case 44150:vNext: Resume removed money service",
			dependsOnMethods = { "testRemoveMoneyService" })
	public void testResumeRemovedMoneyService() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.advancedSearchService(priceservicename + serviceedited, true);
		VNextConfirmationDialog confirmdialog = servicespage.clickUnarchiveButtonForService(priceservicename + serviceedited);
		Assert.assertEquals(confirmdialog.clickNoAndGetConfirmationDialogMessage(), 
				"Are you sure you want to restore \"" + priceservicename + serviceedited + "\" service?");
		servicespage.unarchiveServiceByServiceName(priceservicename + serviceedited);
		servicespage.advancedSearchService(priceservicename + serviceedited, false);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(priceservicename + serviceedited));	
		servicespage.deleteServiceByServiceName(priceservicename + serviceedited);
	}
	
	@Test(description = "Test Case 44152:vNext: Resume removed percentage service",
			dependsOnMethods = { "testRemovePercentageService" })
	public void testResumeRemovedPercentageService() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.advancedSearchService(percentageservicename + serviceedited, true);
		VNextConfirmationDialog confirmdialog = servicespage.clickUnarchiveButtonForService(percentageservicename + serviceedited);
		Assert.assertEquals(confirmdialog.clickNoAndGetConfirmationDialogMessage(), 
				"Are you sure you want to restore \"" + percentageservicename + serviceedited + "\" service?");
		servicespage.unarchiveServiceByServiceName(percentageservicename + serviceedited);
		servicespage.advancedSearchService(percentageservicename + serviceedited, false);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(percentageservicename + serviceedited));	
		servicespage.deleteServiceByServiceName(percentageservicename + serviceedited);
	}
	
	@Test(description = "Test Case 44153:vNext: verify error messages on Create-Edit service dialog")
	public void testVerifyErrorMessagesOnCreateEditServiceDialog() throws IOException {
		
		final String emptyservicename = "    ";
		final String servicename = "TestErrors";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickAddNewserviceButton();
		addnewservicedialog.clickServiceAddButton();
		Assert.assertEquals(addnewservicedialog.getErrorMessage(), 
				"Service name is required!");
		addnewservicedialog.setServiceName(emptyservicename);
		addnewservicedialog.clickServiceAddButton();
		Assert.assertEquals(addnewservicedialog.getErrorMessage(), 
				"Service name is required!");
		addnewservicedialog.setServiceName(servicename);
		addnewservicedialog.saveNewService();
		servicespage.searchServiceByServiceName(servicename);
		addnewservicedialog = servicespage.clickEditServiceByServiceName(servicename);
		addnewservicedialog.setServiceName(emptyservicename);
		addnewservicedialog.clickServiceAddButton();
		Assert.assertEquals(addnewservicedialog.getErrorMessage(), 
				"Service name is required!");
		servicespage = addnewservicedialog.closeNewServiceDialog();
		servicespage.deleteServiceByServiceName(servicename);
	}
	
	@Test(description = "Test Case 44157:vNext: Edit matrix service")
	public void testEditMatrixService() throws IOException {
		
		final String matrixservicetype = "Hail Damage Repair";
		final String matrixservicename = "Hail Dent Repair";
		final String newmatrixservicename = "Test Matrix Service";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOServicesWebPage servicespage = leftmenu.selectServicesMenu();
		servicespage.advancedSearchServiceByServiceType(matrixservicetype);
		VNextBOAddNewServiceDialog addnewservicedialog = servicespage.clickEditServiceByServiceName(matrixservicename);
		addnewservicedialog.setServiceName(newmatrixservicename);
		servicespage = addnewservicedialog.saveNewService();
		servicespage.searchServiceByServiceName(newmatrixservicename);
		Assert.assertTrue(servicespage.isServicePresentOnCurrentPageByServiceName(newmatrixservicename));
		
	}

}

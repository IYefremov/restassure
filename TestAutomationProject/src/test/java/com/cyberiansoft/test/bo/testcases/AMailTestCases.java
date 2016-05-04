package com.cyberiansoft.test.bo.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.utils.MailChecker;

public class AMailTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		backofficeheader.clickLogout();
		//Thread.sleep(3000);
	}
	
	@Test(description = "Test Case 15322:Company- Clients: Search")
	public void testCompanyClientsSearch() throws Exception {

		final String clientname = "IntCompany";
		final String clienttype = "Wholesale";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		//boolean search = MailChecker.searchEmail("olexandr.kramar@cyberiansoft.com", "Y10906_olkr", "Back-Office automated test cases", "olexandr.kramar@cyberiansoft.com", "m.cyberiansoft.test.ios_client.testcases.BackOfficeCompanyCRUDTestCases");
		boolean search = MailChecker.searchEmailAndGetAttachment("olexandr.kramar@cyberiansoft.com", "Y10906_olkr", "Invoice #I-002-01160 from Recon Pro Development QA", "ReconPro@cyberianconcepts.com");
		
		System.out.println("+++++=" + search);
	}

}

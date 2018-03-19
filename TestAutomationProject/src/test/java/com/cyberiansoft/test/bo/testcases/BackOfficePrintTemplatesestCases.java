package com.cyberiansoft.test.bo.testcases;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionTypesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoiceTypesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewInspectionTypeDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewInvoiceTypeDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewPrintTemplatesDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.PrintTemplatesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.QuestionsFormsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.QuestionsSectionDialogWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.SendInspectionCustomEmailTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.SendInvoiceCustomEmailTabWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.WorkOrderTypesWebPage;
import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.LoginScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularServicesScreen;
import com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens.RegularVehicleScreen;

public class BackOfficePrintTemplatesestCases extends BaseTestCase {
	
	final String questionsectionname = "AT_print_section1";
	final String insptypename = "AT_print_inspection";
	final String invoicetypename = "AT_print_invoice";
	String inspectionnum;
	String invoicenum;
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "setupengineer.name", "setupengineer.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		backofficeLogin(backofficeurl, userName, userPassword);
	}
	
	public void backofficeLogin(String backofficeurl, String userName, String userPassword) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
	}
			
	
	@AfterMethod
	public void BackOfficeLogout() {
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		backofficeheader.clickLogout();
	}
	
	@Test(testName = "Test Case 35010:Login as Setup Engineer in BO application",  description = "Login as Setup Engineer in BO application")
	public void testLoginAsSetupEngineerInBOApplication() {

		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		companypage.clickPrintTemplatesConfigurationsLink();		
	}
	
	@Test(testName = "Test Case 35011:Create invoice print template based on ReconPro Default Package.Default template", description = "Create invoice print template based on ReconPro Default Package.Default template")
	public void testCreateInvoicePrintTemplateBasedOnReconProDefaultPackage_DefaultTemplate() {

		String ptcategory = "Invoice";
		String pttype = "ReconPro Default Package.Default";
		String ptname = "Default_package_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InvoiceTypesWebPage invoicetypespage = companypage.clickInvoiceTypesLink();
		if (invoicetypespage.isInvoiceTypeExists(invoicetypename)) {
			invoicetypespage.deleteInvoiceType(invoicetypename);
		}
		
		companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		if (printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
		//printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		//Assert.assertFalse(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
	}
	
	@Test(testName = "Test Case 35013:Create invoice print template based on DentWizard Package.DentWizard_CarMax template", description = "Create invoice print template based on DentWizard Package.DentWizard_CarMax template")
	public void testCreateInvoicePrintTemplateBasedOnDentWizardPackage_DentWizard_CarMaxTemplate() {

		String ptcategory = "Invoice";
		String pttype = "DentWizard Package.DentWizard_CarMax";
		String ptname = "DentWizard_CarMax_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		if (printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
		printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		Assert.assertFalse(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
	}
	
	@Test(testName = "Test Case 35014:Create invoice print template based on DentWizard Package.DentWizard_Default template", description = "Create invoice print template based on DentWizard Package.DentWizard_Default template")
	public void testCreateInvoicePrintTemplateBasedOnDentWizardPackage_DentWizard_DefaultTemplate() {

		String ptcategory = "Invoice";
		String pttype = "DentWizard Package.DentWizard_Default";
		String ptname = "DentWizard_Default_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		if (printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
		printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		Assert.assertFalse(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
	}
	
	@Test(testName = "Test Case 35015:Create invoice print template based on DentWizard Package.DentWizard_Hail template", description = "Create invoice print template based on DentWizard Package.DentWizard_Hail template")
	public void testCreateInvoicePrintTemplateBasedOnDentWizardPackage_DentWizard_HailTemplate() {

		String ptcategory = "Invoice";
		String pttype = "DentWizard Package.DentWizard_Hail";
		String ptname = "DentWizard_Hail_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		if (printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
		printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		Assert.assertFalse(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
	}
	
	@Test(testName = "Test Case 35016:Create invoice print template based on ReconPro Default Package.Custom1 template", description = "Create invoice print template based on ReconPro Default Package.Custom1 template")
	public void testCreateInvoicePrintTemplateBasedOnReconProDefaultPackage_Custom1Template() {

		String ptcategory = "Invoice";
		String pttype = "ReconPro Default Package.Custom1";
		String ptname = "Custom1_package_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		if (printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
		printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		Assert.assertFalse(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
	}
	
	@Test(testName = "Test Case 35017:Create inspection print template based on ReconPro Default Package.Default template", description = "Create inspection print template based on ReconPro Default Package.Default template")
	public void testCreateInspectionPrintTemplateBasedOnReconProDefaultPackage_DefaultTemplate() {

		String ptcategory = "Inspection";
		String pttype = "ReconPro Default Package.Default";
		String ptname = "Default_package_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InspectionTypesWebPage inspectiontypespage= companypage.clickInspectionTypesLink();
		if (inspectiontypespage.isInspectionTypeExists(insptypename)) {
			inspectiontypespage.deleteInspectionType(insptypename);
		}
		
		companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		if (printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInspectionPrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname));
		//printtemplateswebpage.deleteInspectionPrintTemplatePackage(ptname);
		//Assert.assertFalse(printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname));
	}
	
	@Test(testName = "Test Case 35018:Create inspection print template based on ReconPro Default Package.Custom1 template", description = "Create inspection print template based on ReconPro Default Package.Custom1 template")
	public void testCreateInspectionPrintTemplateBasedOnReconProDefaultPackage_Custom1Template() {

		String ptcategory = "Inspection";
		String pttype = "ReconPro Default Package.Custom1";
		String ptname = "Custom1_package_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		if (printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInspectionPrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname));
		printtemplateswebpage.deleteInspectionPrintTemplatePackage(ptname);
		Assert.assertFalse(printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname));
	}
	
	@Test(testName = "Test Case 35023:Create question section", description = "Create question section")
	public void testCreateQuestionSection() {

		
		final String questiontypenametraffic = "Traffic-light (GYR)_test1";
		final String questiontypetraffic = "Traffic-light (GYR)";	
		final String questiontypenamemulti = "Multi-select";
		final String questiontypenamesingle = "Single-select";
		final String questiontypenamelogic = "Logical";
		final String questiontypelogic = "Logical (Y/N)";
		
		final String questiontypenameimage = "Image";
		final String questiontypenamefreetext = "Free Text";
		final String questiontypenamedate = "Date";
		final String questiontypenamehandwr = "Handwriting";
		
		final String OKanswer = "Checked And OK'd";
		final String futureanswer = "Future Attention Needed";
		final String immediateattentionanswer = "Immediate Attention Required";
		final String answerservice = "Tires";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		QuestionsFormsWebPage questionsformspage = companypage.clickQuestionsFormsLink();
		questionsformspage.createQuestionSection(questionsectionname, true);
		String mainWindowHandle = webdriver.getWindowHandle();
		QuestionsSectionDialogWebPage questionsectiondialog = questionsformspage.clickQuestionsLinkForQuestionSection(questionsectionname);
		for (String activeHandle : webdriver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				webdriver.switchTo().window(activeHandle);
			}
		}
		questionsectiondialog.createNewQuestionForQuestionSection(questiontypenametraffic, questiontypetraffic);
		questionsectiondialog.clickAnswersLinkForQuestion(questiontypenametraffic);
		Assert.assertTrue(questionsectiondialog.isAnswerPresentInTable(OKanswer));
		Assert.assertTrue(questionsectiondialog.isAnswerPresentInTable(futureanswer));
		Assert.assertTrue(questionsectiondialog.isAnswerPresentInTable(immediateattentionanswer));
		questionsectiondialog.editAndAssingnServiceToAnswer(immediateattentionanswer, answerservice);
		questionsectiondialog.clickBackToQuestionsLink();
		
		questionsectiondialog.createNewQuestionForQuestionSection(questionsectionname, questiontypenamemulti, questiontypenamemulti);
		questionsectiondialog.clickAnswersLinkForQuestion(questiontypenamemulti);
		questionsectiondialog.addAnswer("A1", "Bumpers");
		questionsectiondialog.addAnswer("A2", "Odor");
		questionsectiondialog.clickBackToQuestionsLink();
		
		questionsectiondialog.createNewQuestionForQuestionSection(questionsectionname, questiontypenamesingle, questiontypenamesingle);
		questionsectiondialog.clickAnswersLinkForQuestion(questiontypenamesingle);
		questionsectiondialog.addAnswer("A1", "Detail");
		questionsectiondialog.clickBackToQuestionsLink();
		
		questionsectiondialog.createNewQuestionForQuestionSection(questionsectionname, questiontypenamelogic, questiontypelogic);
		questionsectiondialog.clickAnswersLinkForQuestion(questiontypenamelogic);
		questionsectiondialog.editAndAssingnServiceToAnswer("No", "Do you have discount?", "");
		questionsectiondialog.editAndAssingnServiceToAnswer("Yes", "Do you have discount?", "Discount");
		questionsectiondialog.clickBackToQuestionsLink();
		
		questionsectiondialog.createNewQuestionForQuestionSection(questionsectionname, questiontypenameimage, questiontypenameimage);
		questionsectiondialog.createNewQuestionForQuestionSection(questionsectionname, questiontypenamefreetext, questiontypenamefreetext);
		questionsectiondialog.createNewQuestionForQuestionSection(questionsectionname, questiontypenamedate, questiontypenamedate);
		questionsectiondialog.createNewQuestionForQuestionSection(questionsectionname, questiontypenamehandwr, questiontypenamehandwr, "I agree");
		
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenametraffic));
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenamemulti));
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenamesingle));
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenamelogic));
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenameimage));
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenamefreetext));
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenamedate));
		Assert.assertTrue(questionsectiondialog.isQuestionPresentInTable(questiontypenamehandwr));
				
		questionsectiondialog.closeNewTab(mainWindowHandle);
		//questionsformspage.deleteQuestionSections(questionsectionname);
	}

	@Test(testName = "Test Case 35459:Create question form", description = "Create question form",
			dependsOnMethods = { "testCreateQuestionSection" })
	public void testCreateQuestionForm() {
		
		final String questionformname = "AT_print_form";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		QuestionsFormsWebPage questionsformspage = companypage.clickQuestionsFormsLink();
		if (questionsformspage.isQuestionFormExists(questionformname)) {
			questionsformspage.deleteQuestionForm(questionformname);
		}
		
		questionsformspage.createQuestionFormAndAssignSection(questionformname, questionsectionname);
		Assert.assertTrue(questionsformspage.isQuestionFormExists(questionformname));
	}
	
	
	
	@Test(testName = "Test Case 35460:Create inspection type", description = "Create inspection type",
			dependsOnMethods = { "testCreateQuestionForm" } )
	public void testCreateInspectionType() {
		
		
		final String sharingtype = "Team Sharing";
		final String questionformtoassign = "AT_print_form";
		final String pricematrixtoassign = "Default";
		final String visualformtype = "Future Audi Car";
		final String packagetype = "Test Exterior";
		
		String ptcategory = "Inspection";
		String pttype = "ReconPro Default Package.Default";
		String ptname = "Default_package_test";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InspectionTypesWebPage inspectiontypespage= companypage.clickInspectionTypesLink();
		if (inspectiontypespage.isInspectionTypeExists(insptypename)) {
			inspectiontypespage.deleteInspectionType(insptypename);
		}
		
		companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		if (printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInspectionPrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInspectionsPrintTemplatesGroupContainsPackage(ptname));
				
		companypage = backofficeheader.clickCompanyLink();
		inspectiontypespage= companypage.clickInspectionTypesLink();
		NewInspectionTypeDialogWebPage newinspectiontypedialog = inspectiontypespage.clickAddInspectionTypeButton();
		newinspectiontypedialog.setNewInspectionTypeName(insptypename);
		newinspectiontypedialog.selectNewInspectionTypeSharingOption(sharingtype);
		newinspectiontypedialog.selectEmailPicturesCheckBox();
		newinspectiontypedialog.selectUseNewPrintingCheckBox();
		newinspectiontypedialog.selectNewInspectionTypeWholesalePrintTemplate(ptname);
		newinspectiontypedialog.selectNewInspectionTypeRetailPrintTemplate(ptname);
		newinspectiontypedialog.assignQuestionForm(questionformtoassign);
		newinspectiontypedialog.assignPriceMatrix(pricematrixtoassign);
		newinspectiontypedialog.assignPackage("All Services");
		newinspectiontypedialog.insertVisualForm(visualformtype, packagetype);
		
		newinspectiontypedialog.clickNewInspectionTypeOKButton();
		Assert.assertTrue(inspectiontypespage.isInspectionTypeExists(insptypename));
		
	}

	@Test(testName = "Test Case 35462:Create invoice type", description = "Create invoice type",
			dependsOnMethods = { "testCreateQuestionForm" } )
	public void testCreateInvoiceType() {
		
		
		final String sharingtype = "Team Sharing";
		
		String ptcategory = "Invoice";
		String pttype = "ReconPro Default Package.Default";
		String ptname = "Default_package_test";	
		String questionformname = "AT_print_form";	
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InvoiceTypesWebPage invoicetypespage = companypage.clickInvoiceTypesLink();
		if (invoicetypespage.isInvoiceTypeExists(invoicetypename))
			invoicetypespage.deleteInvoiceType(invoicetypename);
		
		companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		if (printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname)) {
			printtemplateswebpage.deleteInvoicePrintTemplatePackage(ptname);
		}
		
		NewPrintTemplatesDialogWebPage newprinttemplatesDialogpage = printtemplateswebpage.clickAddNewPrintTemplateButton();
		newprinttemplatesDialogpage.createNewPrintTemplate(ptcategory, pttype, ptname, false);
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		Assert.assertTrue(printtemplateswebpage.isInvoicesPrintTemplatesGroupContainsPackage(ptname));
				
		companypage = backofficeheader.clickCompanyLink();
		invoicetypespage = companypage.clickInvoiceTypesLink();
		NewInvoiceTypeDialogWebPage newinvoicetypedialog = invoicetypespage.clickAddInvoiceTypeButton();
		newinvoicetypedialog.setInvoiceTypeName(invoicetypename);
		newinvoicetypedialog.selectSharingOption(sharingtype);
		newinvoicetypedialog.selectUseNewPrintingCheckBox();
		newinvoicetypedialog.selectNewInvoiceTypeWholesalePrintTemplate(ptname);
		newinvoicetypedialog.selectNewInvoiceTypeRetailPrintTemplate(ptname);
		newinvoicetypedialog.selectQuestionForm(questionformname);
		newinvoicetypedialog.clickOKAddInvoiceTypeButton();
		Assert.assertTrue(invoicetypespage.isInvoiceTypeExists(invoicetypename));
	}
	

	@Test(testName = "Test Case 35463:Create Work Order type", description = "Create Work Order type",
			dependsOnMethods = { "testCreateInvoiceType" } )
	public void testCreateWorkOrderType() throws InterruptedException {
		
		final String wotype = "AT_print_WO";
		final String wotypeoption = "Invoice From Device";
		
		final String wotypeservicepkg = "All Services";
		final String questionformtoassign = "AT_print_form";
		final String invoicetypetoassign = "AT_print_invoice";		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		WorkOrderTypesWebPage workordertypespage = companypage.clickWorkOrderTypesLink();
		if (workordertypespage.isWorkOrderTypeExists(wotype))
			workordertypespage.deleteWorkOrderType(wotype);
		
		workordertypespage.clickAddWorkOrderTypeButton();
		workordertypespage.setNewWorkOrderTypeName(wotype);
		workordertypespage.chechWOTypeOption(wotypeoption);
		workordertypespage.selectNewWorkOrderyTypeServicePackage(wotypeservicepkg);
		workordertypespage.assignQuestionForm(questionformtoassign);
		workordertypespage.assignInvoiceType(invoicetypetoassign);
		workordertypespage.clickNewWorkOrderTypeOKButton();
		Assert.assertTrue(workordertypespage.isWorkOrderTypeExists(wotype));
		
	}
	
	@Test(testName = "Test Case 36046:Test inspection print template with all options selected", description = "Test inspection print template with all options selected",
			dependsOnMethods = { "testCreateInspectionOnMobileDevice", "testCreateinvoiceOnMobileDevice" })
	@Parameters({ "backoffice.url", "setupengineer.name", "setupengineer.psw" })
	public void testTestInspectionPrintTemplateWithAllOptionsSelected(String backofficeurl, String userName, String userPassword) throws InterruptedException {

		final String ptname = "Default_package_test";	
		
		final String footertext = "Footer Text";	
		final String taglabel = "Tag Label test";
		final String estimation = "Estimation Test";
		final String inspectedby = "tauser";
		final String headertext = "Header Text";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		printtemplateswebpage.selectInspectionsPrintTemplatesPackage(ptname);
		printtemplateswebpage.checkAllPrintOutOptionsCheckboxes();
		printtemplateswebpage.setFooterTextPrintTemplateOptions(footertext);
		printtemplateswebpage.setInspectionLabelsPrintTemplateOptions(taglabel, estimation, inspectedby, headertext);
		printtemplateswebpage.clickSavePrintTemplateOptionsButton();
		
		OperationsWebPage operationspage= backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.setInspectionNumberSearchCriteria(inspectionnum);
		inspectionspage.clickFindButton();
		
		inspectionspage.sendInspectionEmail(inspectionnum, "petruk@cyberiansoft.com");
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		SendInspectionCustomEmailTabWebPage sendcustomemailtab= inspectionspage.clickSendCustomEmail(inspectionnum);
		sendcustomemailtab.setEmailToValue("petruk@cyberiansoft.com");
		sendcustomemailtab.selectIncludeInvoicePDFCheckbox();
		sendcustomemailtab.clickSendEmailButton();
		sendcustomemailtab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 36047:Test inspection print template with no options selected", description = "Test inspection print template with no options selected",
			dependsOnMethods = { "testCreateInspectionOnMobileDevice", "testCreateinvoiceOnMobileDevice" })
	public void testTestInspectionPrintTemplateWithNoOptionsSelected() throws InterruptedException {

		final String ptname = "Default_package_test";	
		
		final String footertext = "";	
		final String taglabel = "";
		final String estimation = "";
		final String inspectedby = "";
		final String headertext = "";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		printtemplateswebpage.selectInspectionsPrintTemplatesPackage(ptname);
		printtemplateswebpage.uncheckAllPrintOutOptionsCheckboxes();
		printtemplateswebpage.setFooterTextPrintTemplateOptions(footertext);
		printtemplateswebpage.setInspectionLabelsPrintTemplateOptions(taglabel, estimation, inspectedby, headertext);
		printtemplateswebpage.clickSavePrintTemplateOptionsButton();
		
		OperationsWebPage operationspage= backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.setInspectionNumberSearchCriteria(inspectionnum);
		inspectionspage.clickFindButton();
		
		inspectionspage.sendInspectionEmail(inspectionnum, "petruk@cyberiansoft.com");
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		SendInspectionCustomEmailTabWebPage sendcustomemailtab= inspectionspage.clickSendCustomEmail(inspectionnum);
		sendcustomemailtab.setEmailToValue("petruk@cyberiansoft.com");
		sendcustomemailtab.selectIncludeInvoicePDFCheckbox();
		sendcustomemailtab.clickSendEmailButton();
		sendcustomemailtab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 36048: Test inspection print template several options selected", description = "Test inspection print template several options selected",
			dependsOnMethods = { "testCreateInspectionOnMobileDevice", "testCreateinvoiceOnMobileDevice" })
	public void testTestInspectionPrintTemplateSeveralOptionsSelected() throws InterruptedException {

		final String ptname = "Default_package_test";	
		
		final String footertext = "Footer Text";	
		final String taglabel = "Tag Label test";
		final String estimation = "Estimation Test";
		final String inspectedby = "tauser";
		final String headertext = "Header Text";
		
		final String[] optionstoselect = { "Show Company Logo", "Show Company Name", "Show Customer Email", "Show Interior Splat",
				"Show Exterior Splat", "Show Footer", "Show Stock", "Show Customer Signature", "Show Estimation Notes", "Show Line Items",
				"Show Retail Price", "Show Mileage" };
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInspectionsPrintTemplatesGroup();
		printtemplateswebpage.selectInspectionsPrintTemplatesPackage(ptname);
		printtemplateswebpage.uncheckAllPrintOutOptionsCheckboxes();
		printtemplateswebpage.checkPrintOutOptionsCheckboxes(optionstoselect);
		printtemplateswebpage.setFooterTextPrintTemplateOptions(footertext);
		printtemplateswebpage.setInspectionLabelsPrintTemplateOptions(taglabel, estimation, inspectedby, headertext);
		printtemplateswebpage.clickSavePrintTemplateOptionsButton();
		
		OperationsWebPage operationspage= backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.setInspectionNumberSearchCriteria(inspectionnum);
		inspectionspage.clickFindButton();
		
		inspectionspage.sendInspectionEmail(inspectionnum, "petruk@cyberiansoft.com");
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		SendInspectionCustomEmailTabWebPage sendcustomemailtab= inspectionspage.clickSendCustomEmail(inspectionnum);
		sendcustomemailtab.setEmailToValue("petruk@cyberiansoft.com");
		sendcustomemailtab.selectIncludeInvoicePDFCheckbox();
		sendcustomemailtab.clickSendEmailButton();
		sendcustomemailtab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 36052:Test invoice print template with all options selected", description = "Test invoice print template with all options selected",
			dependsOnMethods = { "testCreateInspectionOnMobileDevice", "testCreateinvoiceOnMobileDevice" })
	public void testTestInvoicePrintTemplateWithAllOptionsSelected() throws InterruptedException {

		final String ptname = "Default_package_test";	
		
		final String footertext = "Footer Text";	
		final String taglabel = "Tag Label test";
		final String customerinfo = "tausertest";
		final String ownerinfo = "tauser";
		final String _ro = "12345";
		final String stock = "stock01";
		final String claiminfo = "Claim Test";
		
		final String[] addtodisplayedcolumns = { "Owner", "OrderNo", "Make", "Technician", "Model", "Amount", "Total_Sale", "Vin", "RO" };
		final String[] addtoavailablecolumns = { "Year", "Tag", "Color", "Mileage", "Stock" };
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		printtemplateswebpage.selectInvoicesPrintTemplatesPackage(ptname);
		printtemplateswebpage.checkAllPrintOutOptionsCheckboxes();
		printtemplateswebpage.setFooterTextPrintTemplateOptions(footertext);
		printtemplateswebpage.setInvoiceLabelsPrintTemplateOptions(taglabel, customerinfo, ownerinfo, _ro, stock, claiminfo);
		printtemplateswebpage.selectInvoiceColumnsPrintTemplateOptions(addtodisplayedcolumns, addtoavailablecolumns);
		printtemplateswebpage.clickSavePrintTemplateOptionsButton();
		
		OperationsWebPage operationspage= backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.setSearchInvoiceNumber(invoicenum);
		invoicespage.clickFindButton();
		
		invoicespage.sendInvoiceEmail(invoicenum, "petruk@cyberiansoft.com");
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		SendInvoiceCustomEmailTabWebPage sendcustomemailtab= invoicespage.clickSendCustomEmail(invoicenum);
		sendcustomemailtab.setEmailToValue("petruk@cyberiansoft.com");
		sendcustomemailtab.selectIncludeInvoicePDFCheckbox();
		sendcustomemailtab.clickSendEmailButton();
		sendcustomemailtab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 36087:Test invoice print template with no options selected", description = "Test invoice print template with no options selected",
			dependsOnMethods = { "testCreateInspectionOnMobileDevice", "testCreateinvoiceOnMobileDevice" })
	public void testTestInvoicePrintTemplateWithNoOptionsSelected() throws InterruptedException {

		final String ptname = "Default_package_test";	
		
		final String footertext = "";	
		final String taglabel = "";
		final String customerinfo = "";
		final String ownerinfo = "";
		final String _ro = "";
		final String stock = "";
		final String claiminfo = "";
		
		final String[] addtodisplayedcolumns = { "Owner", "OrderNo", "Make", "Technician", "Model", "Amount", "Total_Sale", "Vin", "RO" };
		final String[] addtoavailablecolumns = { "Year", "Tag", "Color", "Mileage", "Stock" };
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		printtemplateswebpage.selectInvoicesPrintTemplatesPackage(ptname);
		printtemplateswebpage.uncheckAllPrintOutOptionsCheckboxes();
		printtemplateswebpage.setFooterTextPrintTemplateOptions(footertext);
		printtemplateswebpage.setInvoiceLabelsPrintTemplateOptions(taglabel, customerinfo, ownerinfo, _ro, stock, claiminfo);
		printtemplateswebpage.selectInvoiceColumnsPrintTemplateOptions(addtodisplayedcolumns, addtoavailablecolumns);
		printtemplateswebpage.clickSavePrintTemplateOptionsButton();
		
		OperationsWebPage operationspage= backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.setSearchInvoiceNumber(invoicenum);
		invoicespage.clickFindButton();
		
		invoicespage.sendInvoiceEmail(invoicenum, "petruk@cyberiansoft.com");
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		SendInvoiceCustomEmailTabWebPage sendcustomemailtab= invoicespage.clickSendCustomEmail(invoicenum);
		sendcustomemailtab.setEmailToValue("petruk@cyberiansoft.com");
		sendcustomemailtab.selectIncludeInvoicePDFCheckbox();
		sendcustomemailtab.clickSendEmailButton();
		sendcustomemailtab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 36530:Test invoice print template with several options selected", description = "Test invoice print template with several options selected",
			dependsOnMethods = { "testCreateInspectionOnMobileDevice", "testCreateinvoiceOnMobileDevice" })
	public void testTestInvoicePrintTemplateWithSeveralOptionsSelected() throws InterruptedException {

		final String ptname = "Default_package_test";	
		
		final String footertext = "Footer Text";	
		final String taglabel = "Tag Label test";
		final String customerinfo = "tausertest";
		final String ownerinfo = "tauser";
		final String _ro = "12345";
		final String stock = "stock01";
		final String claiminfo = "Claim Test";
		
		final String[] addtodisplayedcolumns = { "Owner", "OrderNo", "Make", "Technician", "Model", "Amount", "Total_Sale", "Vin", "RO" };
		final String[] addtoavailablecolumns = { "Year", "Tag", "Color", "Mileage", "Stock" };
		
		final String[] optionstoselect = { "Show Gratuity", "Show Company Address", "Show Footer", "Show Service Name", 
				"Show Service Technicians", "Show Advisor Name", "Show Customer Signature", "Show Advisor Name", 
				"Show PO", "Show Tax Items Below", "Claim - Policy#", "Show Claim Info", "Show Owner Info", 
				"Show Customer Info", "Show \"Price X Quantity\" for services", "Show Order Notes", "Show Page Numbers" };
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		printtemplateswebpage.selectInvoicesPrintTemplatesPackage(ptname);
		printtemplateswebpage.uncheckAllPrintOutOptionsCheckboxes();
		printtemplateswebpage.checkPrintOutOptionsCheckboxes(optionstoselect);
		printtemplateswebpage.setFooterTextPrintTemplateOptions(footertext);
		printtemplateswebpage.setInvoiceLabelsPrintTemplateOptions(taglabel, customerinfo, ownerinfo, _ro, stock, claiminfo);
		printtemplateswebpage.selectInvoiceColumnsPrintTemplateOptions(addtodisplayedcolumns, addtoavailablecolumns);
		printtemplateswebpage.clickSavePrintTemplateOptionsButton();
		
		OperationsWebPage operationspage= backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.setSearchInvoiceNumber(invoicenum);
		invoicespage.clickFindButton();
		
		invoicespage.sendInvoiceEmail(invoicenum, "petruk@cyberiansoft.com");
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		SendInvoiceCustomEmailTabWebPage sendcustomemailtab= invoicespage.clickSendCustomEmail(invoicenum);
		sendcustomemailtab.setEmailToValue("petruk@cyberiansoft.com");
		sendcustomemailtab.selectIncludeInvoicePDFCheckbox();
		sendcustomemailtab.clickSendEmailButton();
		sendcustomemailtab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 36532:Test invoice print template with selected allow edit options", description = "Test invoice print template with selected allow edit options",
			dependsOnMethods = { "testCreateInspectionOnMobileDevice", "testCreateinvoiceOnMobileDevice" })
	public void testTestInvoicePrintTemplateWithSelectedAllowEditOptions() throws InterruptedException {

		final String ptname = "Default_package_test";	
		
		final String footertext = "Footer Text";	
		final String taglabel = "Tag Label test";
		final String customerinfo = "tausertest";
		final String ownerinfo = "tauser";
		final String _ro = "12345";
		final String stock = "stock01";
		final String claiminfo = "Claim Test";
		
		final String[] addtodisplayedcolumns = { "Owner", "OrderNo", "Make", "Technician", "Model", "Amount", "Total_Sale", "Vin", "RO" };
		final String[] addtoavailablecolumns = { "Year", "Tag", "Color", "Mileage", "Stock" };
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);		
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		PrintTemplatesWebPage printtemplateswebpage = companypage.clickPrintTemplatesConfigurationsLink();
		
		printtemplateswebpage.expandInvoicesPrintTemplatesGroup();
		printtemplateswebpage.selectInvoicesPrintTemplatesPackage(ptname);
		printtemplateswebpage.checkAllPrintOutOptionsCheckboxes();
		printtemplateswebpage.setFooterTextPrintTemplateOptions(footertext);
		printtemplateswebpage.setInvoiceLabelsPrintTemplateOptions(taglabel, customerinfo, ownerinfo, _ro, stock, claiminfo);
		printtemplateswebpage.selectInvoiceColumnsPrintTemplateOptions(addtodisplayedcolumns, addtoavailablecolumns);
		printtemplateswebpage.clickSavePrintTemplateOptionsButton();
		
		OperationsWebPage operationspage= backofficeheader.clickOperationsLink();
		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.setSearchInvoiceNumber(invoicenum);
		invoicespage.clickFindButton();
		
		invoicespage.sendInvoiceEmail(invoicenum, "petruk@cyberiansoft.com");
		
		final String mainWindowHandle = webdriver.getWindowHandle();
		SendInvoiceCustomEmailTabWebPage sendcustomemailtab= invoicespage.clickSendCustomEmail(invoicenum);
		sendcustomemailtab.setEmailToValue("petruk@cyberiansoft.com");
		sendcustomemailtab.selectIncludeInvoicePDFCheckbox();
		sendcustomemailtab.clickSendEmailButton();
		sendcustomemailtab.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName = "Test Case 36044:Create inspection on mobile device", description = "Create inspection on mobile device",
			dependsOnMethods = { "testCreateInspectionType" })
	@Parameters({ "user.name", "user.psw", "license.name", "ios.bundleid" })
	public void testCreateInspectionOnMobileDevice(String userName, String userPassword, String licensename, String bundleid) throws Exception {
	
		final String[] pricematrixservices = { "Body Shop 1", "Bumper Scuff", "Serv Price Policy Panel" };
		
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);	
		backofficeheader.clickLogout();
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		ActiveDevicesWebPage activedevicespage = companypage.clickManageDevicesLink();
		activedevicespage.setSearchCriteriaByName(licensename);
		String regCode = activedevicespage.getFirstRegCodeInTable();
		
		appiumdriverInicialize();
		appiumdriver.removeApp(bundleid);
		appiumdriverInicialize();
		LoginScreen loginscreen = new LoginScreen(appiumdriver);
		loginscreen.registeriOSDevice(regCode);
		RegularMainScreen mainscr = new RegularMainScreen(appiumdriver);
		RegularHomeScreen homescreen = mainscr.userLogin("Petruk", "1111");
		RegularCustomersScreen customersscreen = homescreen.clickCustomersButton();
		customersscreen.swtchToWholesaleMode();
		customersscreen.selectCustomerWithoutEditing("Amazing Nissan");

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.clickAddInspectionButton();
		myinspectionsscreen.selectInspectionType ("AT_print_inspection");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		vehiclescreeen.setVIN("KM8NU4CC8BU154129");
		vehiclescreeen.setColor("Black");
		vehiclescreeen.setStock("1111");
		inspectionnum = vehiclescreeen.getInspectionNumber();
		
		vehiclescreeen.selectNextScreen("AT_print_section1");
		RegularQuestionsScreen questionsscreen = new RegularQuestionsScreen(appiumdriver);
		questionsscreen.selectAnswerForQuestion("Traffic-light (GYR)_test1", "Immediate Attention Required");
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Multi-select", "A1");
		questionsscreen.selectAnswerForQuestion("Multi-select", "A2");
		questionsscreen.swipeScreenUp();
		questionsscreen.selectAnswerForQuestion("Single-select", "A1");
		questionsscreen.swipeScreenUp();
		questionsscreen.swipeScreenUp();
		questionsscreen.makeCaptureForQuestionRegular("Image");
		questionsscreen.swipeScreenUp();
		questionsscreen.setFreeText("Free text");
		questionsscreen.selectNextScreen("Price Matrix");
		RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen(appiumdriver);
		pricematrix.selectPriceMatrix("Hood");
		pricematrix.setSizeAndSeverity(RegularPriceMatrixScreen.DIME_SIZE, "HEAVY");
		
		for (String discount : pricematrixservices) {
			pricematrix.selectDiscaunt(discount);
			pricematrix.clickDiscaunt(discount);
			RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
			selectedservicescreen.setServiceQuantityValue("4");
			selectedservicescreen.saveSelectedServiceDetails();
		}
		pricematrix.selectDiscaunt("Dye");
		pricematrix.selectDiscaunt("Service with sub services");
		pricematrix.clickDiscaunt("Service with sub services");
		RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
		selectedservicescreen.setServiceQuantityValue("30");
		selectedservicescreen.saveSelectedServiceDetails();
		pricematrix.selectDiscaunt("VPS1");
		pricematrix.clickSaveButton();
		pricematrix.selectNextScreen("All Services");
		RegularServicesScreen servicesscreen = new RegularServicesScreen(appiumdriver);
		servicesscreen.clickToolButton();
		servicesscreen.selectService("Body Shop");
		selectedservicescreen = servicesscreen.openCustomServiceDetails("Bumpers");
		selectedservicescreen.setServiceQuantityValue("11");
		selectedservicescreen.setServicePriceValue("9");
		selectedservicescreen.saveSelectedServiceDetails();
		servicesscreen.clickAddServicesButton();
		servicesscreen.clickSaveButton();
		myinspectionsscreen.clickHomeButton();
		
	}
	
	@Test(testName = "Test Case 36045:Create invoice on mobile device", description = "Create invoice on mobile device",
			dependsOnMethods = { "testCreateInvoiceType", "testCreateInspectionType" })
	@Parameters({ "user.name", "user.psw", "license.name", "ios.bundleid" })
	public void testCreateinvoiceOnMobileDevice(String userName, String userPassword, String licensename, String bundleid) throws Exception {
		
		RegularHomeScreen homescreen = new RegularHomeScreen(appiumdriver);

		RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
		myinspectionsscreen.selectInspectionForCreatingWO(inspectionnum);
		myinspectionsscreen.selectInspectionType ("AT_print_WO");
		RegularVehicleScreen vehiclescreeen = new RegularVehicleScreen(appiumdriver);
		String wonum = vehiclescreeen.getInspectionNumber();
		vehiclescreeen.clickSaveButton();
		homescreen = myinspectionsscreen.clickHomeButton();
		RegularMyWorkOrdersScreen myworkordersscreen = homescreen.clickMyWorkOrdersButton();
		myworkordersscreen.clickCreateInvoicesMenuItemForWO(wonum);
		myworkordersscreen.selectWorkOrderType("AT_print_invoice");
		myworkordersscreen.selectNextScreen("Info");
		RegularInvoiceInfoScreen regularinfoscreen = new RegularInvoiceInfoScreen(appiumdriver);
		invoicenum = regularinfoscreen.getInvoiceNumber();
		regularinfoscreen.clickSaveButton();
		myworkordersscreen.clickCancelSearchButton();
		homescreen = myworkordersscreen.clickHomeButton();
		RegularMyInvoicesScreen myinvoicesscreen = homescreen.clickMyInvoices();
		Assert.assertTrue(myinvoicesscreen.myInvoiceExists(invoicenum));		
		myinvoicesscreen.clickHomeButton();
		appiumdriver.quit();
	}
}

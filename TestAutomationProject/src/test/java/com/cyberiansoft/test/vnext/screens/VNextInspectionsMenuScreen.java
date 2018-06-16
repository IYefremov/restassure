package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextInspectionsMenuScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//a[@data-name='view']")
	private WebElement viewinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='edit']")
	private WebElement editinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_createOrder']")
	private WebElement createwoinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='notes']")
	private WebElement notesinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='refresh']")
	private WebElement refreshpicturesinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='email']")
	private WebElement emailinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='archive']")
	private WebElement archiveinspectionbtn;
	
	@FindBy(xpath="//a[@data-name='approve']")
	private WebElement approveinspectionbtn;
	
	@FindBy(xpath="//a[@handler='_deleteWorkOrder']")
	private WebElement deleteorderbtn;
	
	@FindBy(xpath="//a[@data-name='addSupplement']")
	private WebElement addsupplementbtn;

	@FindBy(xpath="//a[@data-name='changeCustomer']")
	private WebElement changecustomerbtn;

	@FindBy(xpath="//a[@data-name='invoice']")
	private WebElement createinvoicemenuitem;
	
	@FindBy(xpath="//body/div[@data-menu='popup']")
	private WebElement inspectionsmenuscreen;
	
	@FindBy(xpath="//div[@class='close-popup close-actions']")
	private WebElement closebtn;
	
	public VNextInspectionsMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(inspectionsmenuscreen));
	}
	
	public VNextVehicleInfoScreen clickEditInspectionMenuItem() {			
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(editinspectionbtn));
		BaseUtils.waitABit(1000);
		tap(editinspectionbtn);
		BaseUtils.waitABit(4000);		
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public VNextEmailScreen clickEmailInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(emailinspectionbtn));
		tap(emailinspectionbtn);
		return new VNextEmailScreen(appiumdriver);
	}
	
	public VNextNotesScreen clickNotesInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(notesinspectionbtn));
		tap(notesinspectionbtn);
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickCreateWorkOrderInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(createwoinspectionbtn));
		tap(createwoinspectionbtn);
		BaseUtils.waitABit(8000);
	}
	
	public boolean isCreateWorkOrderMenuPresent() {
		return createwoinspectionbtn.isDisplayed();
	}
	
	public VNextInspectionsScreen archiveInspection() {
		clickArchiveInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogArchiveButton();	
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public void clickArchiveInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(archiveinspectionbtn));
		tap(archiveinspectionbtn);
	}
	
	public void clickApproveInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approveinspectionbtn));
		tap(approveinspectionbtn);
	}
	
	public boolean isApproveMenuPresent() {
		return approveinspectionbtn.isDisplayed();
	}
	
	public VNextViewScreen clickViewInspectionMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(viewinspectionbtn));
		tap(viewinspectionbtn);
		BaseUtils.waitABit(3000);
		return new VNextViewScreen(appiumdriver);
	}
	
	public VNextVehicleInfoScreen clickAddSupplementInspectionMenuItem() {			
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(addsupplementbtn));
		tap(addsupplementbtn);	
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public boolean isAddSupplementInspectionMenuItemPresent() {			
		return addsupplementbtn.isDisplayed();
	}

	public void clickCloseInspectionMenuButton() {
		List<MobileElement> closebtns = appiumdriver.findElements(By.xpath("//div[@class='close-popup close-actions']"));
		for (WebElement closebtn : closebtns)
			if (closebtn.isDisplayed()) {
				tap(closebtn);
				break;
			}
	}
	
	public boolean isDeleteWorkOrderMenuButtonExists() {
		return appiumdriver.findElement(By.xpath("//a[@handler='_deleteWorkOrder']")).isDisplayed();
	}
	
	public void clickDeleteWorkOrderMenuButton() {
		tap(deleteorderbtn);
	}
	
	public VNextWorkOrdersScreen deleteWorkOrder() {
		clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogDeleteButton();	
		return new VNextWorkOrdersScreen(appiumdriver);
	}

	public VNextCustomersScreen clickChangeCustomerMenuItem() {
		tap(changecustomerbtn);
		return new VNextCustomersScreen(appiumdriver);
	}

	public boolean isChangeCustomerMenuPresent() {
		return changecustomerbtn.isDisplayed();
	}

	public VNextInspectionTypesList clickCreateInvoiceMenuItem(){
		tap(createinvoicemenuitem);
		return new VNextInspectionTypesList(appiumdriver);
	}
}

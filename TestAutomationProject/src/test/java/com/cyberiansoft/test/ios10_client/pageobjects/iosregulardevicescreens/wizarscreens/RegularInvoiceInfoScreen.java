package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.IRegularTypeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularBaseTypeScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularInvoiceInfoScreen extends RegularBaseWizardScreen implements IRegularTypeScreen {

	private final TypeScreenContext INVOICEINFOCONTEXT = TypeScreenContext.INVOICEINFO;
	private static TypeScreenContext INVOICEINFOExCONTEXT = null;
	
	@iOSFindBy(accessibility = "Draft")
    private IOSElement draftalertbtn;
	
	@iOSFindBy(accessibility = "Final")
    private IOSElement finalalertbtn;

	@iOSFindBy(accessibility  = "action pay")
    private IOSElement invoicepaybtn;
	
	@iOSFindBy(accessibility  = "Payment_Tab_Cash")
    private IOSElement cashnormalbtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = "//UIAKeyboard[1]/UIAButton[@name=\"Return\"]")
    private IOSElement hidekeyboardbtn;

	public RegularInvoiceInfoScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Info")));
	}
	
	public void clickSaveEmptyPO() {
		savebtn.click();
		Helpers.acceptAlert();
	}

	public <T extends RegularBaseTypeScreen> T  clickSaveAsDraft() {
		clickSave();
		draftalertbtn.click();
		savebtn.click();
		return getTypeScreenFromContext();
	}

	public <T extends RegularBaseTypeScreen> T clickSaveAsFinal() {
		clickSave();
		finalalertbtn.click();
		savebtn.click();
		return getTypeScreenFromContext();
	}

	public void setPO(String _po) throws InterruptedException {
		setPOWithoutHidingkeyboard(_po);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		//((IOSDriver) appiumdriver).hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Return");
		//hidekeyboardbtn.click();
	}
	
	public void setPOWithoutHidingkeyboard(String _po)  {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("PO#")));
		WebElement par = getTableParentCell("PO#");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(_po);
	}
	
	public String  getInvoicePOValue()  {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("PO#")));
		WebElement par = getTableParentCell("PO#");
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}

	public boolean isWOSelected(String wo) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
						+ wo + "']").size() > 0;
	}
	
	public void clickWO(String wonumber) {
		WebElement par = getTableParentCell(wonumber);
		par.findElement(By.xpath("//XCUIElementTypeStaticText[1]")).click();
		INVOICEINFOExCONTEXT = RegularBaseWizardScreen.typeContext;
		RegularBaseWizardScreen.typeContext = INVOICEINFOCONTEXT;
	}

	public <T extends RegularBaseTypeScreen> T cancelInvoice() {
		System.out.println("+++++" + INVOICEINFOExCONTEXT.toString());
		System.out.println("+++++" + RegularBaseWizardScreen.typeContext.toString());
		if (INVOICEINFOExCONTEXT != null)
			RegularBaseWizardScreen.typeContext = INVOICEINFOExCONTEXT;
		clickCancelButton();
		acceptAlert();
		return getTypeScreenFromContext();
	}

	public String getOrderSumm() {
		return appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
	}
	
	public void clickOnWO(String wonum) {
		appiumdriver.findElementByAccessibilityId(wonum).click();
	}
	
	public void addWorkOrder(String wonumber)  {
		appiumdriver.findElementByAccessibilityId("Insert").click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[3]").click();
		WebElement par = getTableParentCell(wonumber);
		//par.findElement(By.xpath(".//XCUIElementTypeTextField[1]"));
		par.findElement(By.xpath("//XCUIElementTypeButton[@name=\"unselected\"]")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String getInvoiceNumber() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByIosNsPredicate("name CONTAINS 'I-'").getAttribute("value");
		//return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[contains(@name, \"I-\")]").getAttribute("value");
	}
	
	public String getInvoiceCustomer() {
		return appiumdriver.findElementByAccessibilityId("viewPrompt").getAttribute("value");
	}

	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

	public void clickInvoicePayButton() {
		invoicepaybtn.click();
	}
	
	public void changePaynentMethodToCashNormal() {
		cashnormalbtn.click();
	}
	
	public void setCashCheckAmountValue(String amountvalue) {
		appiumdriver.findElementByAccessibilityId("Payment_Cash_Amount").clear();
		appiumdriver.findElementByAccessibilityId("Payment_Cash_Amount").sendKeys(amountvalue);
	}
	
	public void clickInvoicePayDialogButon() {
		appiumdriver.findElementByAccessibilityId("Pay").click();
	}
}

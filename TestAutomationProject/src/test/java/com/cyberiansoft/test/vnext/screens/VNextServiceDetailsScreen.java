package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextQuestionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

@Getter
public class VNextServiceDetailsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement servicedtailsapplybtn;
	
	@FindBy(xpath="//*[@action='notes']/span")
	private WebElement notesbutton;
	
	@FindBy(xpath="//div[@data-page='details']")
	private WebElement rootElement;

	@FindBy(xpath = "//input[@name='technicians']")
	private WebElement technicianField;

	@FindBy(xpath = "//span[@action='quantity-plus']")
	private WebElement increaseQuantityButton;

	@FindBy(xpath = "//span[@action='quantity-minus']")
	private WebElement decreaseQuantityButton;

	@FindBy(xpath = "//*[@action='part-info']")
	private WebElement partServiceDetailButton;

	@FindBy(xpath = "//*[@action='labor-services']")
    private WebElement laborServicesButton;
	
	public VNextServiceDetailsScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(rootElement));
	}

	public VNextServiceDetailsScreen() {
	}
	
	public VNextNotesScreen clickServiceNotesOption() {
		tap(notesbutton);
		return new VNextNotesScreen();
	}
	
	public void clickServiceDetailsBackButton() {
		clickScreenBackButton();
	}
	
	public void clickServiceDetailsDoneButton() {
		tap(servicedtailsapplybtn);
		if (elementExists("//div[@class='modal-text']")) {
			VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
			informationDialog.clickInformationDialogNoButton();
		}
	}
	
	public void clickDeleteServiceIcon() {
		tap(rootElement.findElement(By.xpath(".//i[@action='remove']")));;
	}
	
	public VNextAvailableServicesScreen deleteService() {
		clickDeleteServiceIcon();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.ARE_YOU_SURE_REMOVE_THIS_ITEM));
		return new VNextAvailableServicesScreen(appiumdriver) ;
	}
	
	public void setServiceAmountValue(String amount) {
		clickServiceAmountField();	
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(rootElement.findElement(By.id("serviceDetailsPrice")).getAttribute("value"), amount);
	}
	
	public String getServiceAmountValue() {
		return rootElement.findElement(By.id("serviceDetailsPrice")).getAttribute("value");
	}
	
	public String getServiceQuantityValue() {
		return rootElement.findElement(By.id("serviceDetailsQuantityFloat")).getAttribute("value");
	}

	public String getServiceNotesValue() {
		return rootElement.findElement(By.id("serviceDetailsNotes")).getAttribute("value");
	}
	
	public void clickServiceAmountField() {
		WaitUtils.elementShouldBeVisible(rootElement, true);
		WaitUtils.elementShouldBeVisible(rootElement.findElement(By.id("serviceDetailsPrice")), true);
		tap(rootElement.findElement(By.id("serviceDetailsPrice")));
	}
	
	public void clickServiceQuantityField() {
		tap(rootElement.findElement(By.id("serviceDetailsQuantityFloat")));
	}
	
	public void setServiceQuantityValue(String quantity) {
		clickServiceQuantityField();
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(getServiceQuantityValue(), quantity);
	}

	public VNextQuestionsScreen clickServiceQuestionSection(String questionSectionName) {
		appiumdriver.findElement(By.xpath("//*[@action='select-question-section']/input[@value='" +
				questionSectionName + "']")).click();
		return new VNextQuestionsScreen(appiumdriver);
	}

	public void clickSelectPanelsAndParts() {
		tap(rootElement.findElement(By.xpath(".//*[@action='select-panel']")));
	}

}

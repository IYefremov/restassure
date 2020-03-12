package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextQuestionsScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class VNextServiceDetailsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement servicedtailsapplybtn;
	
	@FindBy(xpath="//*[@action='notes']/span")
	private WebElement notesbutton;

	@FindBy(xpath = "//div[contains(@data-page,'details')]")
	private WebElement rootElement;

	@FindBy(xpath = "//input[@name='technicians']")
	private WebElement technicianField;

	@FindBy(xpath = "//span[@action='quantity-plus']")
	private WebElement increaseQuantityButton;

	@FindBy(xpath = "//span[@action='quantity-minus']")
	private WebElement decreaseQuantityButton;

	@FindBy(xpath = "//*[@action='part-info']")
	private WebElement partServiceDetailButton;

	@FindBy(xpath = "//*[@action='add-labor-service']")
    private WebElement laborServicesButton;

	@FindBy(xpath = "//*[@action='add-part-service']")
    private WebElement partServicesButton;

    @FindBy(xpath = "//*[@data-name='vehiclePart']")
    private WebElement vehiclePartEditBox;

    @FindBy(xpath = "//*[@data-name='Amount']")
    private WebElement serviceDetailsPrice;

	@FindBy(xpath = "//*[@data-name='QuantityFloat']")
	private WebElement serviceDetailsQuantity;

	@FindBy(id = "serviceDetailsNotes")
	private WebElement serviceDetailsNotes;

    @FindBy(xpath = "//div[@class='services-part-info-title']")
    private WebElement partServiceInfoTitle;

	@FindBy(xpath = "//input[@name='question-section']")
	private List<WebElement> questionSections;

	@FindBy(xpath = "//*[@data-action-name='startService']")
	private WebElement startServiceBtn;

	@FindBy(xpath = "//*[@data-action-name='completeService']")
	private WebElement completeServiceBtn;

    public VNextServiceDetailsScreen(WebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(rootElement));
	}

	public VNextServiceDetailsScreen() {
	}
	
	public void clickServiceNotesOption() {
		tap(notesbutton);
	}
	
	public void clickServiceDetailsDoneButton() {
		tap(servicedtailsapplybtn);
        if (WaitUtils.isElementPresent(By.xpath("//div[@class='modal-text']"))) {
			VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
			informationDialog.clickInformationDialogNoButton();
		}
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

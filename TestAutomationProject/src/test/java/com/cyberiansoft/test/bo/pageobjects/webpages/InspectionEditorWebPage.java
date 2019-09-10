package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class InspectionEditorWebPage extends BaseWebPage {

	@FindBy(xpath = "//span[@class='editable-field date-field']")
	private WebElement dateInput;

	@FindBy(xpath = "//div[@id='ui-datepicker-div']")
	private WebElement dateWidget;

	@FindBy(xpath = "//a[@data-handler='prev']")
	private WebElement previousMonthButton;

	@FindBy(xpath = "//a[@data-handler='next']")
	private WebElement nextMonthButton;

	@FindBy(xpath = "//td[@data-handler='selectDay']/a")
	private List<WebElement> days;

	@FindBy(xpath = "//div[@title='Save']")
	private WebElement saveInspectionButton;

	@FindBy(id = "ajaxSpinner")
	private WebElement updateSpinner;

	@FindBy(xpath = "//p[text()='Inspection was sent to saving queue!']")
	private WebElement successfulSaveNotification;

	public InspectionEditorWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public boolean isDateInputDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(dateInput)).isDisplayed();
		} catch (Exception ignored) {
			return false;
		}
	}

	public void clickDateInput() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(dateInput)).click();
		} catch (Exception e) {
			Assert.fail("The date input field has not been displayed");
		}
		Assert.assertTrue(isDateWidgetOpened(), "The date widget has not been opened");
	}

	private boolean isDateWidgetOpened() {
		try {
			return wait.until(ExpectedConditions.attributeContains(dateWidget, "display", "block"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean isDateWidgetClosed() {
		try {
			return wait.until(ExpectedConditions.attributeContains(dateWidget, "display", "none"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isInspectionEditorOpened(String inspection) {
		try {
			return isTitleDisplayed(inspection);
		} catch (Exception ignored) {
			return false;
		}
	}

	private Boolean isTitleDisplayed(String inspection) {
		return wait.until(ExpectedConditions.titleContains(inspection));
	}

	public void clickPreviousMonthButton() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(previousMonthButton)).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isNextMonthButtonEnabled() {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(nextMonthButton)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isNextMonthButtonDisabled() {
		try {
			return !waitShort.until(ExpectedConditions.elementToBeClickable(nextMonthButton)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void selectDay(int day) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(days.get(day - 1))).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(isDateWidgetClosed(), "The date widget has not been closed");
	}

	public void clickSaveInspectionButton() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(saveInspectionButton)).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		waitForSpinnerToDisappear();
		waitForInspectionToBeSaved();
	}

	private void waitForSpinnerToDisappear() {
		try {
			waitForDisappearance(updateSpinner);
		} catch (Exception e) {
			e.printStackTrace();
			waitABit(2000);
		}
	}

	private void waitForInspectionToBeSaved() {
		try {
			waitForDisappearance(successfulSaveNotification);
		} catch (Exception ignored) {
		}
	}

	private void waitForDisappearance(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
}

package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextNotesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[contains(@class, 'page orders-note')]")
	private WebElement servicenotessscreen;
	
	@FindBy(xpath="//textarea[@name='notes']")
	private WebElement notestextfld;
	
	@FindBy(name="quick_notes")
	private WebElement quicknotescontent;
	
	@FindBy(xpath="//a[@action='save']/i")
	private WebElement notesbackbtn;
	
	public VNextNotesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(quicknotescontent));
	}
	
	public List<WebElement> getListOfQuickNotes() {
		return quicknotescontent.findElements(By.xpath(".//ul/li[@action='quick-note']"));
	}
	
	public void addQuickNote(String quicknote) {
		tap(quicknotescontent.findElement(By.xpath(".//ul/li[@action='quick-note']/div/div[text()='" + quicknote + "']")));
		testReporter.log(LogStatus.INFO, "Add '" + quicknote + "' quick note");
	}
	
	public String getSelectedNotes() {
		return notestextfld.getAttribute("value");
	}
	
	public void setNoteText(String notetext) {
		notestextfld.clear();
		notestextfld.sendKeys(notetext);
		testReporter.log(LogStatus.INFO, "Type note text '" + notetext + "'");
	}
	
	public void clickNotesBackButton() {
		tap(notesbackbtn);
		testReporter.log(LogStatus.INFO, "Clack Notes Back button");
	}

}

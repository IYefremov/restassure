package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class EventsWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable eventstable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addeventbtn;
	
	
	/////   NEW EVENT   ///////////////////////
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_ddlEvent")
	private WebElement eventcmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbAlertName")
	private TextField alertnamefld;
	
	@FindBy(xpath = "//div[@class='visibleCondition']")
	private WebElement visibleconditions;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement neweventOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement neweventCancelbtn;
	
	@FindBy(className = "updateProcess")
	private WebElement updateProcess;
	
	@FindBy(className = "rgSelectedRow")
	private WebElement selectedRow;
	

	public EventsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		wait.until(ExpectedConditions.visibilityOf(eventstable.getWrappedElement()));
	}
	
	public void verifyEventsTableColumnsAreVisible() {		
		Assert.assertTrue(eventstable.isTableColumnExists("Alert"));
		Assert.assertTrue(eventstable.isTableColumnExists("Email"));
		Assert.assertTrue(eventstable.isTableColumnExists("SMS"));
		Assert.assertTrue(eventstable.isTableColumnExists("Push"));
		Assert.assertTrue(eventstable.isTableColumnExists("Fax"));
		Assert.assertTrue(eventstable.isTableColumnExists("Voice"));		
	}
	
	public List<WebElement> getEventsTableRows() {
		return eventstable.getTableRows();
	}
	
	public void clickAddEventButton() {
		clickAndWait(addeventbtn);
		wait.until(ExpectedConditions.elementToBeClickable(alertnamefld.getWrappedElement()));
	}
	
	public void createNewEventWithConditions(String eventname, String alertname, String firstconditionname, String firstconditiontype, String firstconditioncriteria) throws InterruptedException {
		if (isEventWithConditionsExists(alertname)) {
			deleteEvent(alertname);
		}
		clickAddEventButton();
		selectEvent(eventname);
		setAlertNewName(alertname);
		driver.findElement(By.xpath("//span[@class='bs-btn bs-btn-mini addConditionBtn']")).click();
		selectFirstConditionValues(firstconditionname, firstconditiontype, firstconditioncriteria);
		saveNewEvent();
	}
	
	public boolean isEventWithConditionsExists(String alertname) {
		return driver.findElements(By.xpath("//tr/td[text()='" + alertname + "']")).size() > 0;
	}
	
	public void setAlertNewName(String alertname) throws InterruptedException {
		clearAndType(alertnamefld, alertname);
	}
	
	public void selectEvent(String eventname) {
		Select eventsnames = new Select(eventcmb);
		eventsnames.selectByVisibleText(eventname);
	}
	
	public void selectFirstConditionValues(String firstconditionname, String firstconditiontype, String firstconditioncriteria) throws InterruptedException {
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(visibleconditions));

		//Thread.sleep(1000);
		selectFirstConditionNameCriteria(firstconditionname); 	
		selectFirstConditionTypeCriteria(firstconditiontype);
		updateWait.until(ExpectedConditions.visibilityOf(updateProcess));
		updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
		//Thread.sleep(1000);
		setFirstConditionCriteria(firstconditioncriteria); 	
	}
	
	public void selectFirstConditionNameCriteria(String firstconditionname)  {	
		Select conditionname = getFirstConditionNameCriteriaCombobox();
		conditionname.selectByVisibleText(firstconditionname);
	}
	
	public Select getFirstConditionNameCriteriaCombobox()  {	
		return new Select(visibleconditions.findElement(By.xpath(".//select[1]")));
	}
	
	public void selectFirstConditionTypeCriteria(String firstconditiontype)  {	
		Select conditiontype = selectFirstConditionTypeCriteriaCombobox();
		conditiontype.selectByValue(firstconditiontype);
		//conditiontype.selectByVisibleText(firstconditiontype);
	}
	
	public Select selectFirstConditionTypeCriteriaCombobox()  {	
		Select conditiontype = null;
		if (visibleconditions.findElement(By.xpath(".//select[2]")).isDisplayed()) {
			conditiontype = new Select(visibleconditions.findElement(By.xpath(".//select[2]")));
		} else {
			conditiontype = new Select(visibleconditions.findElement(By.xpath(".//select[3]")));
		}
		
		return conditiontype;
	}
	
	public void setFirstConditionCriteria(String firstconditioncriteria)  {
		if (firstconditioncriteria.length() > 0) {
			getFirstConditionnameCriteriaField().clear();
			getFirstConditionnameCriteriaField().sendKeys(firstconditioncriteria);
		}
	}
	
	public WebElement getFirstConditionnameCriteriaField()  {
		return visibleconditions.findElement(By.xpath(".//input"));
	}
	
	public void verifyFirstConditionValues(String firstconditionname, String firstconditiontype, String firstconditioncriteria) {
		wait.until(ExpectedConditions.elementToBeClickable(visibleconditions));
		Assert.assertEquals(getFirstConditionNameCriteriaCombobox().getFirstSelectedOption().getText(), firstconditionname);
		Assert.assertEquals(selectFirstConditionTypeCriteriaCombobox().getFirstSelectedOption().getAttribute("value"), firstconditiontype);	
		Assert.assertEquals(getFirstConditionnameCriteriaField().getAttribute("value"), firstconditioncriteria);	
	}
	
	public boolean saveNewEvent() {	
		try{
		wait.until(ExpectedConditions.elementToBeClickable(neweventOKbtn)).click();
		waitABit(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		try{
		if(neweventOKbtn.isDisplayed())
			neweventCancelbtn.click();
		}catch(NoSuchElementException e){}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public void cancelNewEvent() {	
		click(neweventCancelbtn);
	}
	
	public void clickEditButtonForEvent(String alertname) throws InterruptedException {
		List<WebElement> eventsrows = getEventsTableRows();
		for (WebElement eventsrow : eventsrows) {
			if (eventsrow.getText().contains(alertname)) {
				eventsrow.findElement(By.xpath(".//td/input[@title='Edit']")).click();
				break;
			}
		}
		Thread.sleep(300);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.elementToBeClickable(alertnamefld.getWrappedElement()));
	}
	
	public void deleteEvent(String alertname) throws InterruptedException {
		List<WebElement> eventsrows = getEventsTableRows();
		for (WebElement eventsrow : eventsrows) {
			if (eventsrow.getText().contains(alertname)) {
				eventsrow.findElement(By.xpath(".//td/input[@title='Delete']")).click();
				//Thread.sleep(1000);
				Alert alert = driver.switchTo().alert();
				alert.accept();
				break;
			}
		}
		Thread.sleep(300);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}
	
	public boolean isEventExists(String expensetype) {
		boolean exists = false;
		List<WebElement> eventsrows = getEventsTableRows();
		for (WebElement eventsrow : eventsrows) {
			if (eventsrow.findElements(By.xpath(".//td[text()='" + expensetype + "']")).size() > 0) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	public void setEmailNotificationCheckBoxForSelected() throws InterruptedException {
		WebElement emailNotificationCheckBox = selectedRow.findElement(By.className("activeCell-outer")).findElement(By.xpath(".//input[@type='checkbox']"));
		if(emailNotificationCheckBox.getAttribute("checked") == null){
		emailNotificationCheckBox.click();
		//driver.findElement(By.linkText("Save")).click();
		new Actions(driver).moveToElement(emailNotificationCheckBox).moveToElement(selectedRow.findElement(By.linkText("Save"))).click()
		.build().perform();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		}
	}

	public void setEmailNototificationDropDownForSelected(String string) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,100)", "");
		selectedRow.findElement(By.xpath(".//div[@class ='RadComboBox RadComboBox_Office2007 ddlTemplates']")).click();
		driver.findElement(By.xpath(".//li[text()='"+string+"']")).click();
	}

	public void selectEventRowByName(String string) {
		try{
		driver.findElement(By.xpath("//td[contains(text(), '"+string+"')]")).click();
		}catch(Exception e){
			Assert.assertTrue(false);
		}
	}

	public void deleteSelectedEvent() throws InterruptedException {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title ='Delete']"))).click();
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		try{
		driver.switchTo().alert().accept();
		}catch(NoAlertPresentException e){}
		try{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		}catch(TimeoutException e){
			driver.navigate().refresh();
		}
		
	}

}

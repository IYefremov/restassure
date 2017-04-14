package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class QuestionsSectionDialogWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addquestiontosectionbtn;
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable questionstable;
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable answersstable;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_questionText")
	private TextField newquestionnamefld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_disclaimer")
	private TextField newquestiondisclaimerfld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_section_Input")
	private ComboBox questionsectioncmb;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_section_DropDown")
	private DropDown questionsectiondd;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_answerType_Input")
	private ComboBox questiontypecmb;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_answerType_DropDown")
	private DropDown questiontypedd;
	
	@FindBy(id = "ctl00_Content_labelBack")
	private WebElement backtoquestionslink;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
	private WebElement newquestiondialogOKbtn;
	
	/////////////////////////////
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_text")
	private TextField answertextfld;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_dlServices_lAvailable")
	private WebElement answeravailableservices;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_dlServices_lAssigned")
	private WebElement answerassignedservices;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl01_Card_dlServices_btnAddToAssigned")
	private WebElement answerasddtoassignedbtn;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnOk")
	private WebElement answeraddservicesOKbtn;
	
	@FindBy(id = "ctl00_Content_ctl01_ctl02_BtnCancel")
	private WebElement answeraddservicescancelbtn;
	
	///////////////////////////////////
	@FindBy(id = "ctl00_Content_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addanswerbtn;
	
	
	
	public QuestionsSectionDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void createNewQuestionForQuestionSection(String questionname) {
		clickAddNewQuestionForQuestionSection();
		setNewQuestionForQuestionSectionName(questionname);
		clickNewQuestionForQuestionSectionOKButton();
	}
	
	public void createNewQuestionForQuestionSection(String questionname, String questiontype) {
		clickAddNewQuestionForQuestionSection();
		setNewQuestionForQuestionSectionName(questionname);
		selectNewQuestionTypeForQuestionSection(questiontype);
		clickNewQuestionForQuestionSectionOKButton();
	}
	
	public void createNewQuestionForQuestionSection(String questionsection, String questionname, String questiontype) {
		clickAddNewQuestionForQuestionSection();
		selectNewQuestionSectionForQuestionSection(questionsection);
		setNewQuestionForQuestionSectionName(questionname);
		selectNewQuestionTypeForQuestionSection(questiontype);		
		clickNewQuestionForQuestionSectionOKButton();
	}
	
	public void createNewQuestionForQuestionSection(String questionsection, String questionname, String questiontype, String questiondisclaimer) {
		clickAddNewQuestionForQuestionSection();
		selectNewQuestionSectionForQuestionSection(questionsection);
		selectNewQuestionTypeForQuestionSection(questiontype);
		setNewQuestionForQuestionSectionName(questionname);
		setNewQuestionForQuestionSectionDisclaimer(questiondisclaimer);
		clickNewQuestionForQuestionSectionOKButton();
	}
	
	public void clickAddNewQuestionForQuestionSection() {
		clickAndWait(addquestiontosectionbtn);
	}
	
	public void clickNewQuestionForQuestionSectionOKButton() {
		clickAndWait(newquestiondialogOKbtn);
	}
	
	public void setNewQuestionForQuestionSectionName(String questionname) {
		newquestionnamefld.clearAndType(questionname);
	}
	
	public void setNewQuestionForQuestionSectionDisclaimer(String questiondisclaimer) {
		newquestiondisclaimerfld.clearAndType(questiondisclaimer);
	}
	
	public void selectNewQuestionSectionForQuestionSection(String questionsection) {
		selectComboboxValue(questionsectioncmb, questionsectiondd, questionsection);
	}
	
	public void selectNewQuestionTypeForQuestionSection(String questiontype) {
		selectComboboxValue(questiontypecmb, questiontypedd, questiontype);
	}
	
	public List<WebElement> getQuestionsForQuestionSectionTableRows() {
		return questionstable.getTableRows();
	}

	public WebElement getTableRowWithQuestion(String questionname) {
		List<WebElement> rows = getQuestionsForQuestionSectionTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[5]")).getText().equals(questionname)) {
				return row;
			}
		} 
		return null;
	}
	
	public void clickAnswersLinkForQuestion(String questionname) {
		WebElement row = getTableRowWithQuestion(questionname);
		if (row != null) {
			row.findElement(By.xpath(".//td[4]/a")).click();
			wait.until(ExpectedConditions.visibilityOf(backtoquestionslink));
		} else {
			Assert.assertTrue(false, "Can't find " + questionname + " question for question section");	
		}
	}
	
	public boolean isQuestionPresentInTable(String questionname) {
		if (getTableRowWithQuestion(questionname) == null)
			return false;
		else
			return true;	
	}
	
	public void clickBackToQuestionsLink() {
		backtoquestionslink.click();
	}
	
	public List<WebElement> getAnswersTableRows() {
		return answersstable.getTableRows();
	}
	
	public WebElement getTableRowWithAnswer(String answertype) {
		List<WebElement> rows = getQuestionsForQuestionSectionTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[3]")).getText().equals(answertype)) {
				return row;
			}
		} 
		return null;
	}
	
	
	public void editAndAssingnServiceToAnswer(String answertype, String servicename) {
		clickEditAnswerButton(answertype);
		assingnServiceToAnswer(servicename);
	}
	
	public void editAndAssingnServiceToAnswer(String answertype, String answertext, String servicename) {
		clickEditAnswerButton(answertype);
		answertextfld.clearAndType(answertext);
		assingnServiceToAnswer(servicename);
	}
	
	public void assingnServiceToAnswer(String servicename) {
		if (servicename.length() > 1) {
			selectAvailableServiceToAnswer(servicename);
			clickAssignAvailableServiceToAnswerButton();
		}		
		clicAnswersAssignServicesDialogOKButton();
	}
	
	public void clickEditAnswerButton(String answertype) {
		WebElement row = getTableRowWithAnswer(answertype);
		if (row != null) {
			clickEditTableRow(row);
			wait.until(ExpectedConditions.visibilityOf(backtoquestionslink));
		} else {
			Assert.assertTrue(false, "Can't find " + answertype + " answer");	
		}
	}
	
	public boolean isAnswerPresentInTable(String answertype) {
		if (getTableRowWithAnswer(answertype) == null)
			return false;
		else
			return true;	
	}
		
	public void selectAvailableServiceToAnswer(String servicename) {
		Select availablesrvs = new Select(answeravailableservices);
		availablesrvs.selectByVisibleText(servicename);
	}
	
	public void clickAssignAvailableServiceToAnswerButton() {
		answerasddtoassignedbtn.click();
	}
	
	public void clicAnswersAssignServicesDialogOKButton() {
		clickAndWait(answeraddservicesOKbtn);
	}
	
	public void addAnswer(String answertext, String ansverservice) {
		addanswerbtn.click();
		answertextfld.clearAndType(answertext);
		assingnServiceToAnswer(ansverservice);
	}
}

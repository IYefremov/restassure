package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

public class EmailTemplatesWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
	private WebTable emailtemplatestable;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
	private WebElement addemailtemplatebtn;
	
	//new Email template
	@FindBy(xpath = "//input[contains(@id, 'Card_tbName')]")
	private TextField emailtemplatenamefld;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_tbEmailSubject')]")
	private TextField emailtemplatesubjectfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_tbEditorCenter")
	private WebElement emailtemplatebodyfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement newemailtemplateOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement newemailtemplatecancelbtn;
	
	
	public EmailTemplatesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickAddMailTemplateButton() {
		clickAndWait(addemailtemplatebtn);
	}
	
	public void setNewEmailTemplateName(String templatename) {
		clearAndType(emailtemplatenamefld, templatename);		
	}
	
	public void setNewEmailTemplateSubject(String templatesubject) {
		clearAndType(emailtemplatesubjectfld, templatesubject);		
	}
	
	public void setNewEmailTemplateBody(String templatebody) {
        Utils.clearAndType(emailtemplatebodyfld, templatebody);
	}
	
	public void clickNewEmailTemplateOKButton() {
		clickAndWait(newemailtemplateOKbtn);
	}
	
	public void clickNewEmailTemplateCancelButton() {
		click(newemailtemplatecancelbtn);
	}
	
	public void createNewEmailTemplate(String templatename, String subject) {
		setNewEmailTemplateName(templatename);
		setNewEmailTemplateSubject(subject);
		clickNewEmailTemplateOKButton();
	}
	
	public void createNewEmailTemplate(String templatename, String subject, String templatebody) {
		setNewEmailTemplateName(templatename);
		setNewEmailTemplateSubject(subject);
		setNewEmailTemplateBody(templatebody);
		clickNewEmailTemplateOKButton();
	}
	
	public List<WebElement>  getEmailTemplatesTableRows() {
		return emailtemplatestable.getTableRows();
	}
	
	public WebElement getTableRowWithEmailTemplate(String templatename) {
	    waitABit(1500);
		List<WebElement> rows = getEmailTemplatesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[4]")).getText().equals(templatename)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getTableEmailTemplateSubject(String templatename) {
		String subject = "";
		WebElement row = getTableRowWithEmailTemplate(templatename);
		if (row != null) {
			subject = row.findElement(By.xpath(".//td[5]")).getText();
		} else
			Assert.fail("Can't find " + templatename + " email template");
		return subject;
	}
	
	public boolean isEmailTemplateExists(String templatename) {
        try {
            wait.until((ExpectedCondition<Boolean>) driver ->
                    emailtemplatestable.getWrappedElement().findElements(
                            By.xpath(".//tr/td[text()='" + templatename + "']")).size() > 0);
            return true;
        } catch (Exception e) {
            return false;
        }
	}
	
	public void clickEditEmailTemplate(String templatename) {
		WebElement row = getTableRowWithEmailTemplate(templatename);
		if (row != null) {
			clickEditTableRow(row);
		} else
			Assert.fail("Can't find " + templatename + " email template");
	}
	
	public void deleteEmailTemplate(String templatename) {
		WebElement row = getTableRowWithEmailTemplate(templatename);
		if (row != null) {
			deleteTableRow(row);
		} else {
			Assert.fail("Can't find " + templatename + " email template");
		}
	}
	
	public void deleteEmailTemplateAndCancelDeleting(String templatename) {
		WebElement row = getTableRowWithEmailTemplate(templatename);
		if (row != null) {
			cancelDeletingTableRow(row);
		} else {
			Assert.fail("Can't find " + templatename + " email template");
		}
	}

    public void verifyEmailTemplatesDoNoExist(String templatename, String templatenameedited) {
        while (isEmailTemplateExists(templatename)) {
            deleteEmailTemplate(templatename);
        }
        while (isEmailTemplateExists(templatenameedited)) {
            deleteEmailTemplate(templatenameedited);
        }
    }
}

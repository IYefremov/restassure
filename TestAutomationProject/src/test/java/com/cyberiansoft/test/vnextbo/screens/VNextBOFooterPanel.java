package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class VNextBOFooterPanel extends VNextBOBaseWebPage {

	@FindBy(id = "footer")
	private WebElement footerPanel;

    @FindBy(xpath = "//a[@data-bind='click: showTermsAndConditions']")
    private WebElement termsAndConditions;

    @FindBy(xpath = "//a[@data-bind='click: showPrivacyPolicy']")
    private WebElement privacyPolicy;

    @FindBy(xpath = "//iframe[@name='intercom-launcher-frame']")
    private WebElement intercomFrame;

    @FindBy(xpath = "//div[contains(@class, 'intercom-launcher')]")
    private WebElement intercom;

	public VNextBOFooterPanel(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public boolean isFooterPanelDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(footerPanel));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isTermsAndConditionsLinkDisplayed() {
        return footerContains(termsAndConditions, "Terms and Conditions");
    }

    public boolean isPrivacyPolicyLinkDisplayed() {
        return footerContains(privacyPolicy, "Privacy Policy");
    }

    public boolean isIntercomDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(intercomFrame));
        driver.switchTo().frame(intercomFrame);
        return isElementDisplayed(intercom);
    }

    private boolean footerContains(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText().contains(text);
    }
}
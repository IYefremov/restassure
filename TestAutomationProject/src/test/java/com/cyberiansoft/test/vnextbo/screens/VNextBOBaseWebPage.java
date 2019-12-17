package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@class, 'k-loading-mask')]")
    public static WebElement spinner;

    @FindBy(id = "app-progress-spinner")
    public static WebElement loadingProcess;

    @FindBy(xpath = "//a[@class='logo customLogo']")
    public WebElement logoBox;

    @FindBy(xpath = "//div[@class='time user-info__block']")
    public WebElement timeBox;

    @FindBy(xpath = "//a[@class='user user-info__block']")
    public WebElement userInfoBlock;

    @FindBy(xpath = "//a[@class='login user-info__block']")
    public WebElement logoutButton;

    @FindBy(xpath = "//div[@id='helpMenu']")
    public WebElement helpButton;

    @FindBy(xpath = "//div[@class='footer__left']")
    public WebElement copyRightLabel;

    @FindBy(xpath = "//a[@data-bind='click: showTermsAndConditions']")
    public WebElement termsAndConditionsLink;

    @FindBy(xpath = "//a[@data-bind='click: showPrivacyPolicy']")
    public WebElement privacyPolicyLink;

    @FindBy(xpath = "//iframe[@name='intercom-messenger-frame']")
    public WebElement intercomMessengerFrame;

    @FindBy(xpath = "//div[contains(@class,'intercom-messenger-new-conversation') or contains(@class,'intercom-messenger-home-screen')]")
    public WebElement intercomNewConversionSpace;

    @FindBy(xpath = "//iframe[@name='intercom-launcher-frame']")
    public WebElement intercomLauncherFrame;

    @FindBy(xpath = "//div[contains(@class, 'intercom-launcher')]")
    public WebElement openCloseIntercomButton;

    public WebDriver driver;
    public static WebDriverWait wait;
    public static WebDriverWait waitLong;
    public static WebDriverWait waitShort;

    public VNextBOBaseWebPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new ExtendedFieldDecorator(this.driver), this);
        wait = new WebDriverWait(driver, 15, 1);
        waitShort = new WebDriverWait(driver, 5, 1);
        waitLong = new WebDriverWait(driver, 30, 1);
    }
}
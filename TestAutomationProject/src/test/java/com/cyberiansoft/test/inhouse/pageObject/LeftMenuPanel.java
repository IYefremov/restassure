package com.cyberiansoft.test.inhouse.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LeftMenuPanel extends BasePage {

    /** Main Menu */
    @FindBy(xpath = "//span[text()='Financial Mapping']")
    private WebElement financialMapping;

    @FindBy(xpath = "//span[text()='Clients']")
    private WebElement clients;

    @FindBy(xpath = "//span[text()='Sales Quotes']")
    private WebElement salesQuotes;


    /** Submenu */
    @FindBy(xpath = "//ul[@class='treeview-menu menu-open']")
    private WebElement submenuOpened;

    @FindBy(xpath = "//span[text()='Client Quotes']")
    private WebElement clientQuotes;

    @FindBy(xpath = "//span[text()='Agreements in Progress']")
    private WebElement agreementsInProgress;

    @FindBy(xpath = "//span[text()='Active Opportunities']")
    private WebElement activeOpportunities;

    @FindBy(xpath = "//span[text()='Categories']")
    private WebElement categories;

    @FindBy(xpath = "//span[text()='Client Segments']")
    private WebElement clientSegments;

    @FindBy(xpath = "//span[text()='Signed Agreements']")
    private WebElement signedAgreements;

    @FindBy(xpath = "//span[text()='Organizations Rules']")
    private WebElement organizationsRules;

    @FindBy(xpath = "//span[text()='Accounts Rules']")
    private WebElement accountsRules;


    @FindBy(xpath = "//h1[contains(text(), 'Signed Agreements')]")
    private WebElement signedAgreementsHeader;

    @FindBy(xpath = "//h1[contains(text(), 'Agreements in Progress')]")
    private WebElement agreementsInProgressHeader;

    @FindBy(xpath = "//h1[contains(text(), 'Client Segments')]")
    private WebElement clientSegmentsHeader;

    public LeftMenuPanel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    @Step
    private void clickMenu(WebElement menu) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menu)).click();
        } catch (Exception e) {
            Assert.fail("The menu has not been displayed!", e);
        }
    }

    @Step
    private void clickSubMenu(WebElement submenu) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submenu)).click();
        } catch (Exception e) {
            Assert.fail("The submenu has not been clicked!", e);
        }
    }

    /**
     * MenuLinks
     * @return
     */
    @Step
    public LeftMenuPanel clickSalesQuotes() {
        clickMenu(salesQuotes);
        return this;
    }

    @Step
    public LeftMenuPanel clickClients() {
        clickMenu(clients);
        return this;
    }

    @Step
    public LeftMenuPanel clickFinancialMapping() {
        clickMenu(financialMapping);
        return this;
    }

    /**
     * SubmenuLinks
     * @return
     */
    @Step
    public SignedAgreements clickSignedAgreements() {
        clickSubMenu(signedAgreements);
        waitForLoading();
        try {
            wait.until(ExpectedConditions.visibilityOf(signedAgreementsHeader));
        } catch (Exception ignored) {
            clickSalesQuotes();
            clickSubMenu(signedAgreements);
            waitForLoading();
            wait.until(ExpectedConditions.visibilityOf(signedAgreementsHeader));
        }
        return PageFactory.initElements(driver, SignedAgreements.class);
    }

    @Step
    public SignedAgreements clickClientQuotesSubmenu() {
        clickSubMenu(clientQuotes);
        waitForLoading();
        try {
            wait.until(ExpectedConditions.visibilityOf(signedAgreementsHeader));
        } catch (Exception ignored) {
//            clickClientManagement().clickClientQuotesSubmenu();
//            wait.until(ExpectedConditions.visibilityOf(signedAgreementsHeader));
        }
        return PageFactory.initElements(driver, SignedAgreements.class);
    }

    @Step
    public SignedAgreements clickAgreementsInProgress() {
        clickSubMenu(agreementsInProgress);
        waitForLoading();
        try {
            wait.until(ExpectedConditions.visibilityOf(agreementsInProgressHeader));
        } catch (Exception ignored) {
            clickSalesQuotes().clickAgreementsInProgress();
            wait.until(ExpectedConditions.visibilityOf(agreementsInProgressHeader));
        }
        return PageFactory.initElements(driver, SignedAgreements.class);
    }

    @Step
    public ClientSegmentsPage clickClientSegmentsSubmenu() {
        clickSubMenu(clientSegments);
        waitForLoading();
        try {
            wait.until(ExpectedConditions.visibilityOf(clientSegmentsHeader));
        } catch (Exception ignored) {
//            clickClientManagement().clickClientSegmentsSubmenu();
//            wait.until(ExpectedConditions.visibilityOf(clientSegmentsHeader));
        }
        return PageFactory.initElements(driver, ClientSegmentsPage.class);
    }

    @Step
    public CategoriesPage clickCategoriesSubmenu() {
        clickSubMenu(categories);
        waitForLoading();
        return PageFactory.initElements(driver, CategoriesPage.class);
    }

    @Step
    public OrganizationsRulesPage clickOrganizationsRulesSubmenu() {
        clickSubMenu(organizationsRules);
        waitForLoading();
        return PageFactory.initElements(driver, OrganizationsRulesPage.class);
    }

    @Step
    public AccountsRulesPage clickAccountsRulesSubmenu() {
        clickSubMenu(accountsRules);
        waitForLoading();
        return PageFactory.initElements(driver, AccountsRulesPage.class);
    }
}

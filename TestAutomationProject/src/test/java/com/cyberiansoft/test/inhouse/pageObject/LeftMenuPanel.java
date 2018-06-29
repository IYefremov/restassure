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

    @FindBy(xpath = "//span[text()='Client Management']")
    private WebElement clientManagement;


    /** Submenu */
    @FindBy(xpath = "//ul[@class='treeview-menu menu-open']")
    private WebElement submenuOpened;

    @FindBy(xpath = "//span[text()='Client Quotes']")
    private WebElement clientQuotes;

    @FindBy(xpath = "//span[text()='Active Opportunities']")
    private WebElement activeOpportunities;

    @FindBy(xpath = "//span[text()='Categories']")
    private WebElement categories;

    @FindBy(xpath = "//span[text()='Client Segments']")
    private WebElement clientSegments;

    @FindBy(xpath = "//span[text()='Organizations Rules']")
    private WebElement organizationsRules;

    @FindBy(xpath = "//span[text()='Accounts Rules']")
    private WebElement accountsRules;


    @FindBy(xpath = "//h1[contains(text(), 'Client Quotes')]")
    private WebElement clientQuotesHeader;

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
    public LeftMenuPanel clickClientManagement() {
        clickMenu(clientManagement);
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
    public ClientQuotesPage clickClientQuotesSubmenu() {
        clickSubMenu(clientQuotes);
        waitForLoading();
        try {
            wait.until(ExpectedConditions.visibilityOf(clientQuotesHeader));
        } catch (Exception ignored) {
            clickClientManagement().clickClientQuotesSubmenu();
            wait.until(ExpectedConditions.visibilityOf(clientQuotesHeader));
        }
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    @Step
    public ClientSegmentsPage clickClientSegmentsSubmenu() {
        clickSubMenu(clientSegments);
        waitForLoading();
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

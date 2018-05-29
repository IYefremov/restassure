package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class ClientQuotesPage extends BasePage {

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-add-potential-client']")
    private WebElement addClientBTN;

    @FindBy(xpath = "//input[@id='ClientName']")
    private List<WebElement> newClientName;

    @FindBy(id = "ClientNickname")
    private WebElement newClientNickName;

    @FindBy(id = "ClientAddress")
    private WebElement newClientAddress;

    @FindBy(id = "ClientAddress2")
    private WebElement newClientAddress2;

    @FindBy(id = "ClientZip")
    private WebElement newClientZip;

    @FindBy(id = "ClientCountry")
    private WebElement newClientCountry;

    @FindBy(id = "ClientState")
    private WebElement newClientState;

    @FindBy(id = "ClientCity")
    private WebElement newClientCity;

    @FindBy(id = "ClientPhone")
    private WebElement newClientBusinessPhone;

    @FindBy(id = "ContactPhone")
    private WebElement newClientCellPhone;

    @FindBy(id = "ContactFirstName")
    private WebElement newClientFirstName;

    @FindBy(id = "ContactLastName")
    private WebElement newClientLastName;

    @FindBy(id = "ContactTitle")
    private WebElement newClientTitle;

    @FindBy(id = "ContactEmail")
    private WebElement newClientEmail;

    @FindBy(xpath = "//div[@id='add-potential-client-dialog']/div[@class='modal modal-primary']//button[@class='btn btn-outline btn-submit']")
    private WebElement confirmNewClient;

    @FindBy(id = "searchString")
    private WebElement searchField;

    @FindBy(id = "btnSearch")
    private WebElement searchBTN;

    @FindBy(xpath = "//button[@class='btn btn-outline btn-submit']")
    private List<WebElement> updateClientBTN;

    @FindBy(id = "ProposalName")
    private List<WebElement> agreementName;

    @FindBy(xpath = "//div[contains(@class, 'active')]//select[@id='EditionID']")
    private WebElement editionMenu;

    @FindBy(xpath = "//button[@class='btn btn-outline btn-submit']")
    private List<WebElement> addClientProposalBTN;

    @FindBy(xpath = "//div[@class='callout callout-info']/button")
    private WebElement closeNotificationBTN;

    @FindBy(xpath = "//div[@id='add-potential-client-dialog']/div[@class='modal modal-primary']")
    private WebElement addClientDialog;

    @FindBy(xpath = "//div[@id='add-client-proposal-dialog']/div[@class='modal modal-primary']")
    private WebElement addAgreementDialog;

    @FindBy(xpath = "//div[@id='update-client-proposal-dialog']/div[@class='modal modal-primary']")
    private WebElement updateAgreementDialog;

    @FindBy(className="shirma-dialog")
    private WebElement shirmaDialog;

    public ClientQuotesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ClientQuotesPage clickAddClientBTN() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='btn btn-sm blue btn-add-potential-client']")));
        addClientBTN.click();
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public void setNewClientName(String name) {
        newClientName.get(0).sendKeys(name);
    }

    public ClientQuotesPage clearAndSetNewClientName(String name) {
        wait.until(ExpectedConditions.visibilityOf(newClientName.get(1))).clear();
        newClientName.get(1).sendKeys(name);
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public void setNewClientNickName(String name) {
        newClientNickName.sendKeys(name);
    }

    public void setNewClientAddress(String address) {
        newClientAddress.sendKeys(address);
    }

    public void setNewClientAddress2(String address2) {
        newClientAddress2.sendKeys(address2);
    }

    public void setNewClientZip(String zip) {
        newClientZip.sendKeys(zip);
    }

    public void setNewClientCountry(String country) {
        new Select(newClientCountry).selectByVisibleText(country);
    }

    public void setNewClientState(String state) {
        newClientState.sendKeys(state);
    }

    public void setNewClientCity(String city) {
        newClientCity.sendKeys(city);
    }

    public void setNewClientBusinessPhone(String businessPhone) {
        newClientBusinessPhone.sendKeys(businessPhone);
    }

    public void setNewClientCellPhone(String cellPhone) {
        newClientCellPhone.sendKeys(cellPhone);
    }

    public void setNewClientFirstName(String firstName) {
        newClientFirstName.sendKeys(firstName);
    }

    public void setNewClientLastName(String lastName) {
        newClientLastName.sendKeys(lastName);
    }

    public void setNewClientTitle(String title) {
        newClientTitle.sendKeys(title);
    }

    public void setNewClientEmail(String email) {
        newClientEmail.sendKeys(email);
    }

    public ClientQuotesPage fillNewClientProfile(String name, String nickname, String address, String address2, String zip,
                                                 String country, String state, String city, String businessPhone, String cellPhone, String firstName,
                                                 String lastName, String title, String email) {
        wait.until(ExpectedConditions.attributeToBe(addClientDialog, "display", "block"));
        setNewClientName(name);
        setNewClientNickName(nickname);
        setNewClientAddress(address);
        setNewClientAddress2(address2);
        setNewClientZip(zip);
        setNewClientCountry(country);
        // setNewClientState(state);
        setNewClientCity(city);
        setNewClientBusinessPhone(businessPhone);
        setNewClientCellPhone(cellPhone);
        setNewClientFirstName(firstName);
        setNewClientLastName(lastName);
        setNewClientTitle(title);
        setNewClientEmail(email);
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }


    public ClientQuotesPage clickConfirmNewClientBTN() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmNewClient)).click();
        wait.until(ExpectedConditions.attributeToBe(addClientDialog, "display", "none"));
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }


    public boolean verifyUserWasCreated(String verifyParameter) {
        searchUser(verifyParameter);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + verifyParameter + "']")));
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ClientQuotesPage searchUser(String searchValue) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchField)).clear();
            searchField.sendKeys(searchValue);
            clickWithJS(searchBTN);
            waitForProcessing();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            wait.until(ExpectedConditions.visibilityOf(searchField));
////            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchString")));
////            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("searchString")));
//        } catch (TimeoutException e) {
//            waitABit(3000);
//        }
//        searchField.clear();
//        searchField.sendKeys(searchValue);
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(searchBTN)).click();
//        } catch (Exception e) {
//            clickWithJS(searchBTN);
//        }
//        try {
//            wait.until(ExpectedConditions.invisibilityOf(processingBar));
//        } catch (Exception e) {
//            waitABit(1000);
//        }
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public ClientQuotesPage deleteUser(String deleteParameter) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + deleteParameter + "']"))).
                    findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-delete btn-delete-potential-client']"));
            while(element.isDisplayed()) {
                element.click();
                driver.switchTo().alert().accept();
                wait.until(ExpectedConditions.visibilityOf(closeNotificationBTN)).click();
            }
        } catch (Exception ignored) {}
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }


    public ClientQuotesPage editClient(String editParemeter) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + editParemeter + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-update btn-update-potential-client']")).click();
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public void clickUpdateClientBTN() {
        wait.until(ExpectedConditions.elementToBeClickable(updateClientBTN.get(1))).click();
    }

    public ClientQuotesPage clickAddAgreementBTN(String agreementIdentifier) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + agreementIdentifier + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-add btn-add-client-proposal']")).click();
        wait.until(ExpectedConditions.attributeToBe(addAgreementDialog, "display", "block"));
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public void setAgreementName(String name) {
        agreementName.get(0).sendKeys(name);
    }

    public void selectEdition(String edition) {
        new Select(editionMenu).selectByVisibleText(edition);
    }

    public ClientQuotesPage setAgreement(String agreement, String team) {
        setAgreementName(agreement);
        selectEdition(team);
        saveAgreement();
        wait.until(ExpectedConditions.attributeToBe(addAgreementDialog, "display", "none"));
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    private void saveAgreement() {
            wait.until(ExpectedConditions.elementToBeClickable(updateClientBTN.get(2))).click();
//            clickWithJS(updateClientBTN.get(2));
    }

    public ClientQuotesPage expandAgreementList(String identifier) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + identifier + "']"))).
                    findElement(By.xpath("..")).findElement(By.xpath("//td[@class=' details-control']")).click();
        } catch (Exception e) {
            Assert.fail("The agreement list has not been displayed");
        }
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public ClientQuotesPage clickEditAgreement(String s) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + s + "']"))).
                    findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-update-client-proposal']")).click();
            //td[text()='First agreement'] todo check this locator!!!
        } catch (Exception e) {
            Assert.fail("The \"Edit Agreement\" button has not been clicked!");
        }
        wait.until(ExpectedConditions.attributeToBe(updateAgreementDialog, "display", "block"));
        return PageFactory.initElements(driver, ClientQuotesPage.class);
    }

    public boolean verifyAgreementEditionCannotBeChanged(String newName) {
        try {
            selectEdition(newName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAgreementNameChangeable(String s) {
        try {
            wait.until(ExpectedConditions.visibilityOf(agreementName.get(1))).clear();
            agreementName.get(1).sendKeys(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkAgreementByName(String s) {
        try {
            waitABit(1000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + s + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }

    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void updateAgreement() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(updateClientBTN.get(3))).click();
        } catch (Exception e) {
            wait.until(ExpectedConditions.invisibilityOf(shirmaDialog));
            wait.until(ExpectedConditions.elementToBeClickable(updateClientBTN.get(3))).click();
        }
    }

    public ClientQuotesDetailPage clickSetupAgreementButton(String agreementIdentifier) {
        waitForProcessing();
        waitABit(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + agreementIdentifier + "']")))
                .findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-setup-client-proposal']"))
                .click();
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='agreement-statuses']")));
            return PageFactory.initElements(driver, ClientQuotesDetailPage.class);
        } catch (TimeoutException e) {
            return null;
        }
    }
}

package com.cyberiansoft.test.inhouse.pageObject.webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class SignedAgreements extends BasePage {

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-add-potential-client']")
    private WebElement addClientButton;

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
    private WebElement searchButton;

    @FindBy(xpath = "//button[@class='btn btn-outline btn-submit']")
    private List<WebElement> updateClientButton;

    @FindBy(id = "ProposalName")
    private List<WebElement> agreementName;

    @FindBy(xpath = "//div[contains(@class, 'active')]//select[@id='EditionID']")
    private WebElement editionMenu;

    @FindBy(xpath = "//button[@class='btn btn-outline btn-submit']")
    private List<WebElement> addClientProposalButton;

    @FindBy(xpath = "//div[@class='callout callout-info']/button")
    private WebElement closeNotificationButton;

    @FindBy(xpath = "//div[@id='add-potential-client-dialog']/div[@class='modal modal-primary']")
    private WebElement addClientDialog;

    @FindBy(xpath = "//div[@id='add-client-proposal-dialog']/div[@class='modal modal-primary']")
    private WebElement addAgreementDialog;

    @FindBy(xpath = "//div[@id='update-client-proposal-dialog']/div[@class='modal modal-primary']")
    private WebElement updateAgreementDialog;

    @FindBy(className="shirma-dialog")
    private WebElement shirmaDialog;

    @FindBy(className="dataTables_empty")
    private WebElement emptyDataTable;

    public SignedAgreements(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public SignedAgreements clickAddClientButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='btn btn-sm blue btn-add-potential-client']")));
        addClientButton.click();
        return this;
    }

    @Step
    public void setNewClientName(String name) {
        newClientName.get(0).sendKeys(name);
    }

    @Step
    public SignedAgreements clearAndSetNewClientName(String name) {
        wait.until(ExpectedConditions.visibilityOf(newClientName.get(1))).clear();
        newClientName.get(1).sendKeys(name);
        return this;
    }

    @Step
    public void setNewClientNickName(String name) {
        newClientNickName.sendKeys(name);
    }

    @Step
    public void setNewClientAddress(String address) {
        newClientAddress.sendKeys(address);
    }

    @Step
    public void setNewClientAddress2(String address2) {
        newClientAddress2.sendKeys(address2);
    }

    @Step
    public void setNewClientZip(String zip) {
        newClientZip.sendKeys(zip);
    }

    @Step
    public void setNewClientCountry(String country) {
        new Select(newClientCountry).selectByVisibleText(country);
    }

    @Step
    public void setNewClientState(String state) {
        newClientState.sendKeys(state);
    }

    @Step
    public void setNewClientCity(String city) {
        newClientCity.sendKeys(city);
    }

    @Step
    public void setNewClientBusinessPhone(String businessPhone) {
        newClientBusinessPhone.sendKeys(businessPhone);
    }

    @Step
    public void setNewClientCellPhone(String cellPhone) {
        newClientCellPhone.sendKeys(cellPhone);
    }

    @Step
    public void setNewClientFirstName(String firstName) {
        newClientFirstName.sendKeys(firstName);
    }

    @Step
    public void setNewClientLastName(String lastName) {
        newClientLastName.sendKeys(lastName);
    }

    @Step
    public void setNewClientTitle(String title) {
        newClientTitle.sendKeys(title);
    }

    @Step
    public void setNewClientEmail(String email) {
        newClientEmail.sendKeys(email);
    }

    @Step
    public SignedAgreements fillNewClientProfile(String name, String nickname, String address, String address2, String zip,
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
        return this;
    }


    @Step
    public SignedAgreements clickConfirmNewClientButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmNewClient)).click();
        wait.until(ExpectedConditions.attributeToBe(addClientDialog, "display", "none"));
        return this;
    }


    @Step
    public boolean isUserCreated(String verifyParameter) {
        searchUser(verifyParameter);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + verifyParameter + "']")));
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    public SignedAgreements searchUser(String searchValue) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchField)).clear();
            searchField.sendKeys(searchValue);
            clickWithJS(searchButton);
            waitForProcessing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Step
    public SignedAgreements deleteUsers(String deleteParameter) {
        try {
            new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(emptyDataTable));
        } catch (Exception ignored) {
            deleteParameters(deleteParameter);
        }
        return this;
    }

    @Step
    private SignedAgreements deleteParameters(String deleteParameter) {
        try {
            while(wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//td[text()='" + deleteParameter + "']"))).isDisplayed()) {
                wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//a[@class='btn-delete btn-delete-potential-client']")))
                        .click();
                driver.switchTo().alert().accept();
                try {
                    wait.until(ExpectedConditions.visibilityOf(closeNotificationButton)).click();
                } catch (Exception ignored) {}
                waitABit(1500);
                try {
                    if (emptyDataTable.isDisplayed())
                        break;
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
        return this;
    }

    @Step
    public SignedAgreements editClient(String editParemeter) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + editParemeter + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-update btn-update-potential-client']")).click();
        return this;
    }

    @Step
    public void clickUpdateClientButton() {
        wait.until(ExpectedConditions.elementToBeClickable(updateClientButton.get(1))).click();
    }

    @Step
    public SignedAgreements clickAddAgreementButton(String agreementIdentifier) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + agreementIdentifier + "']"))).
                findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-add btn-add-client-proposal']")).click();
        wait.until(ExpectedConditions.attributeToBe(addAgreementDialog, "display", "block"));
        return this;
    }

    @Step
    public void setAgreementName(String name) {
        agreementName.get(0).sendKeys(name);
    }

    @Step
    public void selectEdition(String edition) {
        new Select(editionMenu).selectByVisibleText(edition);
    }

    @Step
    public SignedAgreements setAgreement(String agreement, String team) {
        setAgreementName(agreement);
        selectEdition(team);
        saveAgreement();
        wait.until(ExpectedConditions.attributeToBe(addAgreementDialog, "display", "none"));
        return this;
    }

    @Step
    private void saveAgreement() {
            wait.until(ExpectedConditions.elementToBeClickable(updateClientButton.get(2))).click();
    }

    @Step
    public SignedAgreements expandAgreementList(String identifier) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + identifier + "']"))).
                    findElement(By.xpath("..")).findElement(By.xpath("//td[@class=' details-control']")).click();
        } catch (Exception e) {
            Assert.fail("The agreement list has not been displayed");
        }
        return this;
    }

    @Step
    public SignedAgreements clickEditAgreement(String s) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + s + "']"))).
                    findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-update-client-proposal']")).click();
            //td[text()='First agreement'] todo check this locator!!!
        } catch (Exception e) {
            Assert.fail("The \"Edit Agreement\" button has not been clicked!");
        }
        wait.until(ExpectedConditions.attributeToBe(updateAgreementDialog, "display", "block"));
        return this;
    }

    @Step
    public boolean verifyAgreementEditionCannotBeChanged(String newName) {
        try {
            selectEdition(newName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public boolean isAgreementNameChangeable(String s) {
        try {
            wait.until(ExpectedConditions.visibilityOf(agreementName.get(1))).clear();
            agreementName.get(1).sendKeys(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public boolean checkAgreementByName(String s) {
        try {
            waitABit(1000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + s + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Step
    public void updateAgreement() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(updateClientButton.get(3))).click();
        } catch (Exception e) {
            wait.until(ExpectedConditions.invisibilityOf(shirmaDialog));
            wait.until(ExpectedConditions.elementToBeClickable(updateClientButton.get(3))).click();
        }
    }

    @Step
    public AgreementDetailPage clickSetupAgreementButton(String agreementIdentifier) {
        waitForProcessing();
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions
                    .stalenessOf(driver.findElement(By.xpath("//td[text()='" + agreementIdentifier + "']"))
                            .findElement(By.xpath(".."))
                            .findElement(By.xpath("//a[@class='btn-row btn-setup-client-proposal']")))));
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//td[text()='" + agreementIdentifier + "']")))
                    .findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-setup-client-proposal']"))
                    .click();
        } catch (Exception e) {
            waitABit(3500);
            driver.findElement(By.xpath("//td[text()='" + agreementIdentifier + "']"))
                    .findElement(By.xpath("..")).findElement(By.xpath("//a[@class='btn-row btn-setup-client-proposal']"))
                    .click(); //td[text()='Second Agreement']/..//a[@class='btn-row btn-setup-client-proposal']
        }
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='agreement-statuses']")));
            return PageFactory.initElements(driver, AgreementDetailPage.class);
        } catch (TimeoutException e) {
            return null;
        }
    }
}

package com.cyberiansoft.test.inhouse.pageObject;

import com.cyberiansoft.test.email.EmailUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import com.cyberiansoft.test.inhouse.utils.MailChecker;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientQuotesDetailPage extends BasePage {

    private String userName;
    private String userPassword;

    @FindBy(className = "agreement-statuses")
    private WebElement agreementStatusesBlock;

    @FindBy(className = "btn-select-edition-discount")
    private WebElement discountButton;

    @FindBy(className = "btn-license-fee-discount")
    private WebElement licenseFreeDiscountButton;

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-finalize-agreement']")
    private WebElement finalizeAgreementButton;

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-send-notification']")
    private WebElement sendNotificationButton;

    @FindBy(className = "form-control")
    private WebElement formControlList;

    @FindBy(xpath = "//button[@class='submit btn-save-select-edition-discount']")
    private WebElement submitDiscountButton;

    @FindBy(xpath = "//button[@class='submit btn-save-select-edition-feature-setup-fee']")
    private WebElement submitSetupFeeButton;

    @FindBy(xpath = "//tbody[@data-tbody-feature-group-id]")
    private List<WebElement> clientSupportTables;

    @FindBy(xpath = "//a[@class='btn-add-ischecked-addon-edition-feature addon-checked-value']")
    private WebElement yesAddItemToAgreement;

    @FindBy(className = "price")
    private WebElement repair360Free;

    @FindBy(xpath = "//table[@class='text-center table-price']//td[@data-price-per-month]")
    private WebElement pricePerMonth;

    @FindBy(xpath = "//table[@class='text-center table-price']//td[@data-setup-fee]")
    private WebElement totalSetUpFee;

    @FindBy(xpath = "//div[@id='finalize-validation-error-dialog']/div[@class='modal modal-primary']")
    private WebElement modalDialog;

    @FindBy(xpath = "//span[@class='notification-status']")
    private WebElement notificationStatus;

    @FindBy(xpath = "//span[text()='Select setup fee...']")
    private List<WebElement> emptySetupFeeSelectionList;

    @FindBy(xpath = "//div[@class='dropup open']")
    private WebElement dropUpOpen;

    @FindBy(xpath = "//div[@class='dropup']//span[@class='btn-license-fee-discount']")
    private WebElement dropUpLicenseFeeDiscountClosed;

    @FindBy(xpath = "//div[@class='dropup open']//select[@class='form-control']")
    private WebElement dropUpOptions;

    @FindBy(xpath = "//span[contains (text(), 'Agreement status:')]/following-sibling::b")
    private WebElement agreementStatus;

    @FindBy(xpath = "//span[contains (text(), 'Paid status:')]/following-sibling::span")
    private WebElement paidStatus;

    @FindBy(xpath = "//span[contains (text(), 'Viewed letter:')]/following-sibling::span")
    private WebElement viewedLetterStatus;

    @FindBy(xpath = "//span[contains (text(), 'Viewed agreement:')]/following-sibling::span")
    private WebElement viewedAgreementStatus;

    @FindBy(xpath = "//div[@data-feature-setup-fee]")
    private List<WebElement> setupFeePrices;

    @FindBy(xpath = "//span[@data-billing-starts-str]")
    private WebElement billingStartsLink;

    @FindBy(name = "BillingStartsStr")
    private WebElement billingStartsField;

    @FindBy(xpath = "//div[contains(@class, 'bootstrap-datetimepicker-widget')]")
    private WebElement billingStartsDateWidget;

    @FindBy(xpath = "//button[@class='submit btn-save-billing-starts']")
    private WebElement billingStartsSubmitButton;

    @FindBy(id = "LicenseFeeDiscount")
    private WebElement licenseFeeDiscountField;

    @FindBy(id = "LicenseFeeDiscountType")
    private WebElement licenseFeeDiscountType;

    @FindBy(id = "LicenseFeeDiscountDescription")
    private WebElement licenseFeeDiscountDescription;

    @FindBy(xpath = "//span[@class='btn-license-fee-discount']//following::button[@class='submit btn-save-edition-discount']")
    private WebElement licenseFeeDiscountSubmitButton;

    public ClientQuotesDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        userName = InHouseConfigInfo.getInstance().getUserName();
        userPassword = InHouseConfigInfo.getInstance().getUserPassword();
    }

    @Step
    public boolean checkAgreementStatus(String agreement, String payment, String letterView, String agreementView) {
        try {
            wait.until(ExpectedConditions.visibilityOf(agreementStatusesBlock));
            wait.until(e -> agreementStatus.getText().equals(agreement));
            wait.until(e -> paidStatus.getText().equals(payment));
            wait.until(e -> viewedLetterStatus.getText().equals(letterView));
            wait.until(e -> viewedAgreementStatus.getText().equals(agreementView));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    public boolean checkAgreementStatus(String agreement) {
        try {
            wait.until(ExpectedConditions.visibilityOf(agreementStatusesBlock));
            wait.until(e -> agreementStatus.getText().equals(agreement));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    public ClientQuotesDetailPage clickDiscountButton() {
        discountButton.click();
        wait.until(ExpectedConditions.visibilityOf(dropUpOpen));
        return this;
    }

    @Step
    public ClientQuotesDetailPage clickLicenseFreeDiscountButton() {
        licenseFreeDiscountButton.click();
        wait.until(ExpectedConditions.visibilityOf(dropUpOpen));
        return this;
    }

    @Step
    public ClientQuotesDetailPage clickFinalizeAgreementButton() {
        wait.until(ExpectedConditions.elementToBeClickable(finalizeAgreementButton)).click();
        try {
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {}
        return this;
    }

    @Step
    private void handleAlertForFinalizeAgreementButton() {
        try {
            wait.until(ExpectedConditions.attributeToBe(modalDialog, "display", "block"));
            wait.until(ExpectedConditions.elementToBeClickable(modalDialog.findElement(By.className("close")))).click();
            wait.until(ExpectedConditions.attributeToBe(modalDialog, "display", "none"));
        } catch (Exception ignored) {}
    }

    @Step
    public void sendNotification() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(sendNotificationButton)).click();
        } catch (Exception e) {
            clickWithJS(sendNotificationButton);
        }
        try {
            driver.switchTo().alert().accept();
            waitForLoading();
        } catch (Exception e) {
            handleAlertForFinalizeAgreementButton();
            Assert.fail("The modal dialog has been displayed after clicking the \"Send Notification\" button." + e);
        }
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(notificationStatus, "Sending..."));
            waitABit(10000);
            wait.until(ExpectedConditions.textToBePresentInElement(notificationStatus, "Notification successfully sent."));
        } catch (TimeoutException e) {
            waitABit(20000);
            try {
                wait.until(ExpectedConditions.textToBePresentInElement(notificationStatus, "Notification successfully sent."));
            } catch (TimeoutException ex) {
                Assert.fail("The \"Notification successfully sent\" has not been displayed!");
            }
        }
    }

    public ArrayList<String> getLinks() {
        String mailContent = MailChecker.getUserMailContentFromSpam();
        Pattern linkPattern = Pattern.compile("(<a[^>]+>.+?</a>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher pageMatcher = linkPattern.matcher(mailContent);
        ArrayList<String> links = new ArrayList<>();
        while (pageMatcher.find()) {
            links.add(pageMatcher.group());
        }
        return links;
    }

    public String getAgreementApproveLink() throws Exception {
//        String mailContent = null;
//        try {
//            mailContent = MailChecker.getUserMailContentFromSpam();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Document doc = Jsoup.parse(mailContent);
//        String text = doc.body().text();
//        System.out.println(text);
//        String[] allTexts = text.split(" ");
//        String result = "";
//        for (String str : allTexts) {
//            if (str.contains("https://goo.gl")) {
//                result = str;
//            }
//        }
//        return result;

        EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, userName,
                userPassword, EmailFolder.INBOX);
        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
                .withSubject("Agreement")
                .unreadOnlyMessages(true).maxMessagesToSearch(5);
        return emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);
    }

    public String getMailContentFromSpam() {
        return MailChecker.getUserMailContentFromSpam();
    }

    @Step
    public void selectDiscount(String discount) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(formControlList));
        Select selection = new Select(formControlList);
        selection.selectByVisibleText(discount);
        wait.until(s -> !selection.getAllSelectedOptions().isEmpty());
        waitABit(500);
        wait.until(ExpectedConditions.elementToBeClickable(submitDiscountButton)).click();
        waitForLoading();
    }

    @Step
    public void insertLicenseFeeDiscount(int discount) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.elementToBeClickable(licenseFeeDiscountField)).clear();
        licenseFeeDiscountField.sendKeys(String.valueOf(discount));
    }

    @Step
    public void selectRandomDiscountType() {
        wait.until(ExpectedConditions.elementToBeClickable(licenseFeeDiscountType));
        Select selection = new Select(licenseFeeDiscountType);
        List<WebElement> allSelectedOptions = selection.getAllSelectedOptions();
        selection.selectByIndex(RandomUtils.nextInt(1, allSelectedOptions.size()));
    }

    @Step
    public void insertLicenseFeeDiscountDescription(String description) {
        wait.until(ExpectedConditions.elementToBeClickable(licenseFeeDiscountDescription)).clear();
        licenseFeeDiscountDescription.sendKeys(description);
    }

    @Step
    public void clickSubmitLicenseFeeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(licenseFeeDiscountSubmitButton)).click();
        waitForLoading();
        try {
            wait.until(ExpectedConditions.visibilityOf(dropUpLicenseFeeDiscountClosed));
        } catch (Exception e) {
            Assert.fail("License fee dropup has not been closed");
        }
    }

    @Step
    public void selectLicenseDiscount(int discount, String description) {
        clickLicenseFreeDiscountButton();
        insertLicenseFeeDiscount(discount);
        selectRandomDiscountType();
        insertLicenseFeeDiscountDescription(description);
        clickSubmitLicenseFeeButton();
    }

    @Step
    public boolean checkNewPrice(String price) {
        waitABit(2000);
        System.out.println("PRICE:");
        System.out.println(pricePerMonth.getText());
        System.out.println(price);
        return pricePerMonth.getText().equals(price);
    }

    @Step
    public boolean checkSetupFee(String fee) {
        return wait.until(s -> totalSetUpFee.getText().contains(fee));
    }

    @Step
    public boolean checkPricePerMonth(String price) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(pricePerMonth, price));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step
    public void clickAddClientSupportItem(String clientSupportItem) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[text()='" +
                            clientSupportItem + "']/following::td[1]//i[@class='icon cb-icon-check-empty']")))).click();
        } catch (Exception e) {
            e.printStackTrace();
            clickWithJS(driver.findElement(By.xpath("//span[text()='" +
                            clientSupportItem + "']/following::td[1]//i[@class='icon cb-icon-check-empty']")));
        }
        clickYesToAddItemToAgreement();
    }

    private void selectSetupFee(String clientSupportItem, String option) {
        waitABit(3000);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver
                    .findElement(By.xpath("//span[text()='" + clientSupportItem + "']/following::td[2]//span")))).click();
            wait.until(ExpectedConditions.elementToBeClickable(formControlList));
            new Select(formControlList).selectByVisibleText(option);
        } catch (Exception e) {
            try {
                new Select(formControlList).selectByVisibleText(option);
            } catch (Exception e1) {
                Assert.fail("The Setup fee for \"" + clientSupportItem + "\" has not been selected!" + e);
            }
        }
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitSetupFeeButton)).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitForLoading();
    }

//    public void selectSetupFeeForAllClients() {
//        selectSetupFee("testFeature2_1 test mike", "setupfee1_1");
//        selectSetupFee("testFeature3 test mike", "setupfee3_1");
//        selectSetupFee("tf22222", "1_1");
//        selectSetupFee("Test private", "test name");
//        selectSetupFee("Create Invoices (single or multiple vehicles) (edit)", "1");
//        selectSetupFee("Copy to Agreement settings page only features with \"Publice view\" State (when creating Client Agreement).", "test_r_1");
//    }

    @Step
    private int getSetupFeeListSize() {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(emptySetupFeeSelectionList)).size();
        } catch (TimeoutException ignored) {
            return 0;
        }
    }

    @Step
    private int getSetupFeePricesSize() {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(setupFeePrices)).size();
        } catch (TimeoutException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Step
    public void selectSetupFeeForAllClients() {
        try {
            int setupFeeListSize = getSetupFeeListSize();
            while (setupFeeListSize > 0) {
                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(emptySetupFeeSelectionList.get(0))).click();
                wait.until(ExpectedConditions.visibilityOf(dropUpOpen));
                Select selection = new Select(dropUpOptions);
                selection.selectByIndex(1);
                wait.until(s -> !selection.getAllSelectedOptions().isEmpty());
                waitABit(300);
                wait.until(ExpectedConditions.elementToBeClickable(submitSetupFeeButton)).click();
                waitForLoading();
                setupFeeListSize--;
                waitABit(2500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    private void clickYesToAddItemToAgreement() {
        yesAddItemToAgreement.click();
        waitForLoading();
    }

    @Step
    public double calculatePricePerMonth() {
        int setupFeePricesSize = getSetupFeePricesSize();
        List<Double> prices = new ArrayList<>();
        while (setupFeePricesSize > 0) {
            prices.add(Double.valueOf(wait.until(ExpectedConditions
                    .visibilityOf(setupFeePrices.get(--setupFeePricesSize)))
                    .getText()
                    .substring(1)));
        }
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }

    @Step
    public String getPricePerMonth() {
        return "$" + calculatePricePerMonth();
    }

    @Step
    private void clickBillingStartsLink() {
        wait.until(ExpectedConditions.elementToBeClickable(billingStartsLink)).click();
        wait.until(ExpectedConditions.visibilityOf(dropUpOpen));
    }

    @Step
    private void chooseBillingStartsFromToday() {
        wait.until(ExpectedConditions.elementToBeClickable(billingStartsField)).click();
        wait.until(ExpectedConditions.visibilityOf(billingStartsDateWidget));
        String format = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        wait.until(ExpectedConditions.elementToBeClickable(billingStartsDateWidget
                .findElement(By.xpath(".//td[@data-day='" + format + "']")))).click();
    }

    @Step
    private void verifyDateIsSaved() {
        wait.until(ExpectedConditions.elementToBeClickable(billingStartsSubmitButton)).click();
        waitForLoading();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        wait.until(ExpectedConditions.textToBePresentInElement(
                billingStartsLink, today));
        Assert.assertEquals(today, billingStartsLink.getText(), "The Billing Starts date has not been set!");
        waitABit(500);
    }

    @Step
    public ClientQuotesDetailPage setBillingStartsFromToday() {
        clickBillingStartsLink();
        chooseBillingStartsFromToday();
        verifyDateIsSaved();
        return this;
    }
}
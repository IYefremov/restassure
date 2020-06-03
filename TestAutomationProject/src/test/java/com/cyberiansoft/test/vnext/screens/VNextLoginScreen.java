package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.ControlUtils;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VNextLoginScreen extends VNextBaseScreen {

    @FindBy(xpath = "//div[@data-page='employees']")
    private WebElement loginScreen;

    @FindBy(xpath = "//*[@data-autotests-id='employees-list']")
    private WebElement employeesList;

    @FindBy(xpath = "//*[@action='main-db']/i")
    private WebElement updateMainDbBtn;

    @FindBy(xpath = "//*[@action='vin-db']/i")
    private WebElement updateVinDbBtn;

    @FindBy(xpath = "//div[@class='modal-password-username']/b")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//span[@class='modal-button ' and text()='Login']")
    private WebElement loginBtn;

    @FindBy(xpath = "//span[@class='modal-button ' and text()='Cancel']")
    private WebElement cancelBtn;

    @FindBy(xpath = "//*[@data-autotests-id='search-icon']")
    private WebElement searchIcon;

    @FindBy(xpath = "//*[@data-autotests-id='search-input']")
    private WebElement searchField;

    @FindBy(xpath = "//*[@data-autotests-id='search-cancel']")
    private WebElement cancelSearchBtn;

    @FindBy(xpath = "//div[@class='simple-item-main-content']")
    private List<WebElement> employeesNamesList;

    public VNextLoginScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), this);
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='employees']")));
        WaitUtils.elementShouldBeVisible(ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@data-page='employees']")), true);
    }

    public void userLogin(String username, String userpsw) {
        selectEmployee(username);
        if (!StringUtils.isEmpty(userpsw)) {
            setUserLoginPassword(userpsw);
            tapLoginButton();
        }
    }

    public VNextLoginScreen incorrectUserLogin(String username, String userpsw) {
        selectEmployee(username);
        setUserLoginPassword(userpsw);
        tapLoginButton();
        BaseUtils.waitABit(300);
        VNextInformationDialog infrmdialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = infrmdialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.ENTERED_PASSWORD_IS_INCORRECT);
        waitUserListVisibility();
        return new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
    }

    public void setUserLoginPassword(String userpsw) {
        ControlUtils.setValue(passwordField, userpsw);
    }

    public boolean isUserLoginPasswordDialogVisible() {
        return WaitUtils.isElementPresent(By.xpath("//input[@type='password']"));
    }

    public void selectEmployee(String username) {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 25);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), '" + username + "')]")));
        tapListElement(employeesList, username);
    }

    public void tapLoginButton() {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 15);
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        //tap(loginbtn);
        loginBtn.click();
    }

    public void tapLoginDialogCancelButton() {
        tap(cancelBtn);
        waitUserListVisibility();
    }

    public void waitUserListVisibility() {
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(employeesList));
    }

    public void updateMainDB() {
        tap(updateMainDbBtn);
        BaseUtils.waitABit(10000);
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationdlg.clickInformationDialogOKButton();
    }

    public void searchEmployee(String searchText) {
        if (!searchField.isDisplayed())
            searchIcon.click();
        BaseUtils.waitABit(1000);
        WebDriverWait wait = new WebDriverWait(ChromeDriverProvider.INSTANCE.getMobileChromeDriver(), 15);
        wait.until(ExpectedConditions.elementToBeClickable(searchField)).clear();
        searchField.sendKeys(searchText + "\n");
        BaseUtils.waitABit(1000);
    }

    public int getNumberOfEmployeesInTheList() {
        return employeesList.findElements(By.xpath(".//*[@class='employee-list-item']")).size();
    }

    public boolean isEmployeepresentInTheList(String employeeName) {
        return employeesList.findElements(By.xpath(".//*[@class='item-title' and text()='" +
                employeeName + "']")).size() > 0;
    }

    public boolean isNothingFoundTextDisplayed() {
        return loginScreen.findElement(By.xpath(".//*[text()='Nothing found']")).isDisplayed();
    }

    public ArrayList<String> getEmployeeList() {
        ArrayList<String> employeesList = new ArrayList<>();
        List<WebElement> employees = this.employeesList.findElements(By.xpath(".//*[@class='employee-list-item']"));
        for (WebElement employeeItem : employees)
            employeesList.add(employeeItem.getText().trim());
        return employeesList;
    }

    public boolean isEmployeeListSorted() {
        ArrayList<String> employeesListSorted = getEmployeeList();
        employeesListSorted = employeesListSorted.stream()
                .sorted(Comparator.comparing((String e) -> e.split(" ")[1]))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> employeesList = getEmployeeList();

        boolean sorted = true;
        for (int i = 1; i < employeesListSorted.size(); i++) {
            if (!employeesListSorted.get(i).equals(employeesList.get(i))) {
                sorted = false;
                break;
            }
        }

        return sorted;
    }
}

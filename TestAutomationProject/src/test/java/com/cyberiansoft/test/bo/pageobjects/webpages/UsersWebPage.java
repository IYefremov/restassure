package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;


public class UsersWebPage extends WebPageWithPagination {

    final String allowcreatingsuppticketschkbox = "Allow creating support tickets";

    @FindBy(xpath = "//span[@id='ctl00_ctl00_Content_Main_cpFilterer']/div")
    private WebElement searchtab;

    @FindBy(xpath = "//a[text()='Search']")
    private WebElement searchbtn;

    @FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00")
    private WebTable userstable;

    @FindBy(id = "ctl00_ctl00_Content_Main_gvDeleted_ctl00")
    private WebTable archiveduserstable;

    @FindBy(xpath = "//span[@class='rtsTxt' and text()='Active']")
    private WebElement activetab;

    @FindBy(xpath = "//span[@class='rtsTxt' and text()='Archived']")
    private WebElement archivedtab;

    @FindBy(id = "ctl00_ctl00_Content_Main_gv_ctl00_ctl02_ctl00_lbInsert")
    private WebElement useraddbtn;

    //New User
    @FindBy(xpath = "//input[contains(@id, 'Card_tbEmail')]")
    private TextField newusermailfld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbPassword')]")
    private TextField newuserpswfld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbConfirmPassword')]")
    private TextField newuserconfirmpswfld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbFirstName')]")
    private TextField newuserfirstnamefld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbLastName')]")
    private TextField newuserlastnamefld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbCompany')]")
    private TextField newusercompanyfld;

    @FindBy(xpath = "//textarea[contains(@id, 'Card_tbAddress')]")
    private TextField newuseraddressfld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbCity')]")
    private TextField newusercityfld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbZip')]")
    private TextField newuserzipfld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbPhone')]")
    private TextField newuserphonefld;

    @FindBy(xpath = "//input[contains(@id, 'Card_tbAccountingID')]")
    private TextField newuseraccidfld;

    @FindBy(xpath = "//textarea[contains(@id, 'Card_tbComments')]")
    private TextField newusercommentsfld;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
    private WebElement newuserOKbtn;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
    private WebElement newusercancelbtn;
    //Search Panel

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbSearch")
    private TextField searchuserfld;

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
    private WebElement findbtn;


    public UsersWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public boolean searchPanelIsExpanded() {
        return searchtab.getAttribute("class").contains("open");
    }

    public void makeSearchPanelVisible() {
        if (!searchPanelIsExpanded()) {
            click(searchbtn);
        }
    }

    public void verifyUsersTableColumnsAreVisible() {
        Assert.assertTrue(userstable.tableColumnExists("Full Name"));
        Assert.assertTrue(userstable.tableColumnExists("Email"));
        Assert.assertTrue(userstable.tableColumnExists("Address"));
        Assert.assertTrue(userstable.tableColumnExists("Phone"));
        Assert.assertTrue(userstable.tableColumnExists("Roles"));
        Assert.assertTrue(userstable.tableColumnExists("Accounting ID"));
    }

    public void verifyTabsAreVisible() {
        wait.until(ExpectedConditions.visibilityOf(activetab)).click();
        Assert.assertTrue(activetab.isDisplayed());
        Assert.assertTrue(archivedtab.isDisplayed());
    }

    public void clickArchivedTab() {
        clickAndWait(archivedtab);
    }

    public void clickActiveTab() {
        wait.until(ExpectedConditions.elementToBeClickable(activetab));
        clickAndWait(activetab);
    }

    public void setSearchUserParameter(String username) {
        clearAndType(searchuserfld, username);
    }

    public void clickFindButton() {
        clickAndWait(findbtn);
    }

    public int getUsersTableRowCount() {
        return getUsersTableRows().size();
    }

    public List<WebElement> getUsersTableRows() {
        return userstable.getTableRows();
    }

    public List<WebElement> getArchivedUsersTableRows() {
        return archiveduserstable.getTableRows();
    }

    public WebElement getTableRowWithActiveUser(String firstName, String lastName) {
        List<WebElement> rows = getUsersTableRows();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(rows);
        return rows
                .stream()
                .peek(row -> WaitUtilsWebDriver.waitForElementNotToBeStale(row).findElement(By.xpath(".//td[3]")))
                .collect(Collectors.toList())
                .stream()
                .filter(e -> Utils.getText(e).contains(firstName + " " + lastName))
                .findAny()
                .get();
    }

    public WebElement getTableRowWithArchivedUser(String firstname, String lastname) {
        List<WebElement> rows = getArchivedUsersTableRows();
        for (WebElement row : rows) {
            if (row.findElement(By.xpath(".//td[2]")).getText().contains(firstname + " " + lastname)) {
                return row;
            }
        }
        return null;
    }

    public List<String> getActiveUserNames() {
        List<String> usernames = new ArrayList<>();
        List<WebElement> usernamesact = userstable.getWrappedElement().findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gv_ctl00')]/td[4]"));
        for (WebElement useractcell : usernamesact) {
            usernames.add(useractcell.getText());
        }
        return usernames;
    }

    public String verifyUserNamesDuplicatesArchived(List<String> usernamesact) {
        List<String> usernamesarch = new ArrayList<>();
        List<WebElement> usercolumnsarch = userstable.getWrappedElement().findElements(By.xpath(".//tbody/tr[contains(@id, 'ctl00_ctl00_Content_Main_gvDeleted_ctl00')]/td[2]"));
        for (WebElement useractcell : usercolumnsarch) {
            usernamesarch.add(useractcell.getText());
        }
        return findDuplicateNames(usernamesact, usernamesarch);

    }

    public void clickEditActiveUser(String firstname, String lastname) {
        WebElement row = getTableRowWithActiveUser(firstname, lastname);
        if (row != null) {
            clickEditTableRow(row);
        } else
            Assert.fail("Can't find " + firstname + " " + lastname + " user");
    }

    public void archiveUser(String firstname, String lastname) {
        WebElement row = getTableRowWithActiveUser(firstname, lastname);
        if (row != null) {
            archiveTableRow(row);
        } else
            Assert.fail("Can't find " + firstname + " " + lastname + " user");
    }

    public boolean isUserActive(String firstname, String lastname) {
        return userstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + firstname + " " + lastname + "']")).size() > 0;
    }

    public void unarchiveUser(String firstname, String lastname) {
        WebElement row = getTableRowWithArchivedUser(firstname, lastname);
        if (row != null) {
            restoreTableRow(row);
        } else
            Assert.fail("Can't find archived " + firstname + " " + lastname + " user");
    }

    public boolean isUserArchived(String firstname, String lastname) {
        return archiveduserstable.getWrappedElement().findElements(By.xpath(".//tr/td[text()='" + firstname + " " + lastname + "']")).size() > 0;
    }

    public String findDuplicateNames(List<String> usernamesact, List<String> usernamesarch) {
        String duplicates = "";
        for (String username : usernamesact) {
            if (usernamesarch.contains(username))
                duplicates = username;
        }
        return duplicates;
    }

    public void clickUserAddButton() {
        useraddbtn.click();
    }

    public void createNewUser(String usermail, String firstname, String lastname, String userrole) {
        setNewUserMail(usermail);
        setNewUserFirstName(firstname);
        setNewUserLastName(lastname);
        selectNewUserRole(userrole);
        clickNewUserOKButton();
    }


    public void setNewUserMail(String usermail) {
        clearAndType(newusermailfld, usermail);
    }

    public String getNewUserMail() {
        return newusermailfld.getValue();
    }

    public void setNewUserFirstName(String firstname) {
        clearAndType(newuserfirstnamefld, firstname);
    }

    public String getNewUserFirstName() {
        return newuserfirstnamefld.getValue();
    }

    public void setNewUserLastName(String lastname) {
        clearAndType(newuserlastnamefld, lastname);
    }

    public String getNewUserLastName() {
        return newuserlastnamefld.getValue();
    }

    public void setNewUserCompany(String usercompany) {
        clearAndType(newusercompanyfld, usercompany);
    }

    public String getNewUserCompany() {
        return newusercompanyfld.getValue();
    }

    public void setNewUserAddress(String useraddress) {
        clearAndType(newuseraddressfld, useraddress);
    }

    public String getNewUserAddress() {
        return newuseraddressfld.getValue();
    }

    public void setNewUserCity(String usercity) {
        clearAndType(newusercityfld, usercity);
    }

    public String getNewUserCity() {
        return newusercityfld.getValue();
    }

    public void setNewUserZip(String userzip) {
        clearAndType(newuserzipfld, userzip);
    }

    public String getNewUserZip() {
        return newuserzipfld.getValue();
    }

    public void setNewUserPhone(String userphone) {
        clearAndType(newuserphonefld, userphone);
    }

    public String getNewUserPhone() {
        return newuserphonefld.getValue();
    }

    public void setNewUserAccountingID(String useraccid) {
        clearAndType(newuseraccidfld, useraccid);
    }

    public String getNewUserAccountingID() {
        return newuseraccidfld.getValue();
    }

    public void setNewUserComments(String usercomments) {
        clearAndType(newusercommentsfld, usercomments);
    }

    public String getNewUserComments() {
        return newusercommentsfld.getValue();
    }

    public void selectNewUserRole(String userrole) {
        click(driver.findElement(By.xpath("//label[text()='" + userrole + "']")));
    }

    public void selectAllowCreatingSupportTickets() {
        if (!isCheckboxChecked(driver.findElement(By.xpath("//label[text()='" + allowcreatingsuppticketschkbox + "']"))))
            click(driver.findElement(By.xpath("//label[text()='" + allowcreatingsuppticketschkbox + "']")));
    }

    public boolean isNewUserRoleSelected(String userrole) {
        return isCheckboxChecked(driver.findElement(By.xpath("//label[text()='" + userrole + "']")));
    }

    public boolean isAllowCreatingSupportTicketsSelected() {
        return isCheckboxChecked(driver.findElement(By.xpath("//label[text()='" + allowcreatingsuppticketschkbox + "']")));
    }

    public void clickNewUserOKButton() {
        clickAndWait(newuserOKbtn);
    }

    public void clickNewUserCancelButton() {
        click(newusercancelbtn);
    }
}
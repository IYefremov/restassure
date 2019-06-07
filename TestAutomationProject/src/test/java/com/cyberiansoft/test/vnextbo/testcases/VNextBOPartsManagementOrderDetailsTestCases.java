package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementOrderDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOPartsManagementOrderDetailsTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOPartsManagementOrderDetailsData.json";
    private VNexBOLeftMenuPanel leftMenu;
    private VNextBOBreadCrumbPanel breadCrumbPanel;
    private VNextBOPartsManagementSearchPanel partsManagementSearch;
    private VNextBOPartsOrdersListPanel partsOrdersListPanel;
    private VNextBOPartsDetailsPanel partsDetailsPanel;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);

        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
        breadCrumbPanel = PageFactory.initElements(webdriver, VNextBOBreadCrumbPanel.class);
        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        partsOrdersListPanel = PageFactory.initElements(webdriver, VNextBOPartsOrdersListPanel.class);
        partsDetailsPanel = PageFactory.initElements(webdriver, VNextBOPartsDetailsPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerPanel = PageFactory.initElements(webdriver, VNextBOHeaderPanel.class);
        if (headerPanel.logOutLinkExists()) {
            headerPanel.userLogout();
        }

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectAndDeselectThePartByActivatingAndDeactivatingTheCheckbox(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementOrderDetailsData data = JSonDataParser
                .getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.selectPartCheckbox(0);

        Assert.assertTrue(partsDetailsPanel.isDeleteButtonDisplayed(),
                "The Delete button hasn't appeared in the Parts Details panel");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsGeneralOptionChecked(), "The general checkbox " +
                "hasn't been checked in the Parts Details panel after selecting the part checkbox");

        partsDetailsPanel.selectPartCheckbox(0);

        Assert.assertFalse(partsDetailsPanel.isDeleteButtonDisplayed(),
                "The Delete button hasn't disappeared in the Parts Details panel");
        Assert.assertFalse(partsDetailsPanel.isPartsDetailsGeneralOptionChecked(), "The general checkbox " +
                "hasn't been unchecked in the Parts Details panel after deselecting the part checkbox");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAndDeleteLabor(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementOrderDetailsData data = JSonDataParser
                .getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertTrue(partsDetailsPanel.isAddLaborButtonDisplayed(0),
                "The Add Labor button hasn't been displayed");

        final int numberOfLaborBlocksBefore = partsDetailsPanel.getNumberOfLaborBlocks();
        System.out.println("before: " + numberOfLaborBlocksBefore);

        final VNextBOAddLaborPartsDialog laborPartsDialog = partsDetailsPanel.clickAddLaborButton(0);
        Assert.assertTrue(laborPartsDialog.isAddLaborDialogDisplayed(),
                "The Labor dialog hasn't been displayed");

        Assert.assertFalse(laborPartsDialog.isLaborClearIconDisplayed(),
                "The Labor Clear icon has been displayed before selecting the labor");
        laborPartsDialog.setLaborService(data.getLabor());
        Assert.assertTrue(laborPartsDialog.isLaborClearIconDisplayed(), "The Labor Clear icon hasn't been displayed");

        laborPartsDialog.clickAddLaborButtonForDialog();
        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");

        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertTrue(partsDetailsPanel.isAddLaborButtonDisplayed(0),
                "The Add Labor button hasn't been displayed");
        final int numberOfLaborBlocksAfter = partsDetailsPanel.getNumberOfLaborBlocks();
        System.out.println("after: " + numberOfLaborBlocksAfter);
        Assert.assertEquals(numberOfLaborBlocksAfter, numberOfLaborBlocksBefore + 1,
                "The labor hasn't been added");

        partsDetailsPanel
                .clickDeleteLaborButton(numberOfLaborBlocksBefore)
                .clickXIconForDeletingLabor();

        Assert.assertEquals(partsDetailsPanel.getNumberOfLaborBlocks(), numberOfLaborBlocksAfter,
                "The labor has been deleted despite cancelling");

        partsDetailsPanel
                .clickDeleteLaborButton(numberOfLaborBlocksBefore)
                .clickCancelDeletingButton();

        Assert.assertEquals(partsDetailsPanel.getNumberOfLaborBlocks(), numberOfLaborBlocksAfter,
                "The labor has been deleted despite cancelling");

        partsDetailsPanel
                .clickDeleteLaborButton(numberOfLaborBlocksBefore)
                .clickConfirmDeletingButton();

        Assert.assertEquals(partsDetailsPanel.getNumberOfLaborBlocks(), numberOfLaborBlocksBefore,
                "The labor hasn't been deleted");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanTypeLaborServiceNameAddingLabor(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementOrderDetailsData data = JSonDataParser
                .getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertTrue(partsDetailsPanel.isAddLaborButtonDisplayed(0),
                "The Add Labor button hasn't been displayed");

        final VNextBOAddLaborPartsDialog laborPartsDialog = partsDetailsPanel.clickAddLaborButton(0);
        Assert.assertTrue(laborPartsDialog.isAddLaborDialogDisplayed(),
                "The Labor dialog hasn't been displayed");

        laborPartsDialog.typeLaborName(data.getLaborStart());
        Assert.assertTrue(laborPartsDialog.isLaborDisplayed(data.getLabor()));
        final int laborOptionsQuantityBefore = laborPartsDialog.getLaborOptionsQuantity();
        laborPartsDialog.selectLaborNameFromBoxList(data.getLaborStart());

        Assert.assertTrue(laborPartsDialog.isLaborClearIconDisplayed(), "The Labor Clear icon hasn't been displayed");
        laborPartsDialog.clickClearLaborIcon();
        final int laborOptionsQuantityAfter = laborPartsDialog.getLaborOptionsQuantity();

        System.out.println(laborOptionsQuantityBefore);
        System.out.println(laborOptionsQuantityAfter);

        Assert.assertNotEquals(laborOptionsQuantityBefore, laborOptionsQuantityAfter,
                "The labor options number hasn't been changed after clearing the search filter input field");
        laborPartsDialog.clickCancelAddingLaborButtonForDialog();

        partsDetailsPanel.deleteLastLabor();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelAddingLabor(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementOrderDetailsData data = JSonDataParser
                .getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertTrue(partsDetailsPanel.isAddLaborButtonDisplayed(0),
                "The Add Labor button hasn't been displayed");

        final int numberOfLaborBlocksBefore = partsDetailsPanel.getNumberOfLaborBlocks();
        System.out.println("before: " + numberOfLaborBlocksBefore);

        final VNextBOAddLaborPartsDialog laborPartsDialog = partsDetailsPanel.clickAddLaborButton(0);
        Assert.assertTrue(laborPartsDialog.isAddLaborDialogDisplayed(),
                "The Labor dialog hasn't been displayed");

        laborPartsDialog
                .setLaborService(data.getLabor())
                .clickXIconToCancelAddingLaborButtonForDialog();

        Assert.assertFalse(laborPartsDialog.isAddLaborDialogDisplayed(), "The Labor dialog hasn't been closed");

        partsDetailsPanel
                .clickAddLaborButton(0)
                .setLaborService(data.getLabor())
                .clickCancelAddingLaborButtonForDialog();

        Assert.assertFalse(laborPartsDialog.isAddLaborDialogDisplayed(), "The Labor dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMaximizeMinimizeThePartTab(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementOrderDetailsData data = JSonDataParser
                .getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertTrue(partsDetailsPanel.isFirstPartLaborBlockExpanded(),
                "The part labor block hasn't been expanded");
        partsDetailsPanel.clickPartsArrow(0);
        Assert.assertFalse(partsDetailsPanel.isFirstPartLaborBlockExpanded(),
                "The part labor block hasn't been collapsed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeOEM(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementOrderDetailsData data = JSonDataParser
                .getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);

        leftMenu.selectPartsManagementMenu();
        breadCrumbPanel.setLocation(data.getLocation());

        partsManagementSearch
                .setPartsSearchText(data.getWoNum())
                .clickSearchLoupeIcon();

        Assert.assertEquals(partsOrdersListPanel.getWoNumsListOptions().get(0), data.getWoNum(),
                "The WO order hasn't been displayed after search");
        Assert.assertTrue(partsDetailsPanel.isPartsDetailsTableDisplayed(),
                "Tha Parts details panel hasn't been displayed");

        partsDetailsPanel.setOEM(data.getOemNum());
        Assert.assertEquals(partsDetailsPanel.getPartOEMValue(), data.getOemNum(),
                "The OEM value hasn't been set");
    }
}
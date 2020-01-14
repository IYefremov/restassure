package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOEditNotesDialog;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VNextBOROPageInteractions {

    public static void clickTechniciansFieldForWO(String woNumber) {
        Utils.clickElement(new VNextBOROWebPage().getTechnicianFieldForWO(woNumber));
    }

    public static void clickWoLink(String woNumber) {
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        Utils.clickElement(By.xpath("//a[@class='order-no']/strong[text()='" + woNumber + "']/.."));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static WebElement clickWoLink() {
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        final WebElement woElement = new VNextBOROWebPage().getRandomOrderNumber();
        if (woElement != null) {
            Utils.clickElement(woElement);
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        } else {
            Assert.fail("The work orders list is empty");
        }
        return woElement;
    }

    public static void revealNoteForWorkOrder(String woNumber) {
        if (VNextBOROPageValidations.isNoteForWorkOrderDisplayed(woNumber, false)) {
            clickNoteForWo(woNumber);
        }
    }

    public static void openNotesDialog() {
        Utils.clickElement(new VNextBOROWebPage().getRoNotesLink());
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new VNextBOEditNotesDialog().getRoEditNotesModalDialog(), 5);
    }

    private static void clickNoteForWo(String woNumber) {
        Utils.clickElement(By.xpath("//strong[text()='" + woNumber
                + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']"));
    }

    public static void hideNoteForWorkOrder(String woNumber) {
        if (VNextBOROPageValidations.isNoteForWorkOrderDisplayed(woNumber, true)) {
            WaitUtilsWebDriver.waitABit(1000);
            clickNoteForWo(woNumber);
        }
    }

    public static void clickAdvancedSearchCaret() {
        Utils.clickElement(new VNextBOROWebPage().getAdvancedSearchCaret());
    }

    public static void setWoDepartment(String woNumber, String department) {
        hideNoteForWorkOrder(woNumber);
        Utils.clickElement(new VNextBOROWebPage().getTableBody().findElement(By.xpath("//a[@class='order-no']" +
                "/strong[text()='" + woNumber + "']/../following-sibling::div/i[@class='menu-trigger']")));
        final WebElement departmentDropDown = new VNextBOROWebPage().getTableBody()
                .findElement(By.xpath("//a[@class='order-no']/strong[text()='"
                + woNumber + "']/../..//div[@class='drop department-drop']"));
        WaitUtilsWebDriver.waitForVisibility(departmentDropDown);
        final List<WebElement> dropDownOptions = departmentDropDown.findElements(By.xpath(".//label"));
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(dropDownOptions);
        dropDownOptions
                .stream()
                .filter(e -> e.getText().equals(department))
                .findFirst()
                .ifPresent(WebElement::click);
        WaitUtilsWebDriver.waitForInvisibility(departmentDropDown);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void clickXIconToCloseNoteForWorkOrder(String woNumber) {
        if (VNextBOROPageValidations.isNoteForWorkOrderDisplayed(woNumber, true)) {
            Utils.clickElement(By.xpath("//strong[text()='" + woNumber
                    + "']/../../../div[@data-bind='visible: orderDescriptionDisplay']//a[@class='x']"));
        }
    }

    public static void clickSavedSearchArrow() {
        Utils.clickElement(new VNextBOROWebPage().getSavedSearchArrow());
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new VNextBOROWebPage().getSavedSearchDropDown());
    }

    public static void selectSavedSearchDropDownOption(String option) {
        final List<WebElement> savedSearchDropDownOptions = new VNextBOROWebPage().getSavedSearchDropDownOptions();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(savedSearchDropDownOptions);
        savedSearchDropDownOptions
                .stream()
                .filter(o -> o.getText().equals(option))
                .findFirst()
                .ifPresent(Utils::clickWithActions);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getOrderNoteText() {
        return Utils.getText(new VNextBOROWebPage().getRoNotesLink());
    }

    public static void movePointerToSearchResultsField() {
        final WebElement searchResults = new VNextBOROWebPage().getSearchResults();
        WaitUtilsWebDriver.waitForVisibility(searchResults);
        Utils.moveToElement(searchResults);
    }

    public static String getSearchFilterText() {
        return Utils.getText(new VNextBOROWebPage().getSearchFilterText());
    }

    public static List<String> getSearchResultsList() {
        return WaitUtilsWebDriver
                .waitForVisibilityOfAllOptions(new VNextBOROWebPage().getSearchOptions())
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static void clickLocationInDropDown(String location) {
        new VNextBOROWebPage().getLocationLabels()
                .stream()
                .filter(loc -> loc.getText().contains(location))
                .findFirst()
                .ifPresent(WebElement::click);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickEditIconForSavedSearch() {
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickElement(new VNextBOROWebPage().getSavedSearchEditIcon());
    }

    public static void clickNextButton() {
        Utils.clickElement(new VNextBOROWebPage().getNextButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickPrevButton() {
        Utils.clickElement(new VNextBOROWebPage().getPrevButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    private static void makeTabActive(WebElement wideTab, WebElement wideTabActive) {
        Utils.clickElement(wideTab);
        WaitUtilsWebDriver.waitForVisibility(wideTabActive, 5);
    }

    public static void setDepartmentsTabActive() {
        final VNextBOROWebPage roWebPage = new VNextBOROWebPage();
        makeTabActive(roWebPage.getDepartmentsWideTab(), roWebPage.getDepartmentsWideTabActive());
    }

    public static String getAllDepartmentsSum() {
        return getCalculatedSum(new VNextBOROWebPage().getAllDepartmentsWideScreenValues());
    }

    public static String getAllPhasesSum() {
        return getCalculatedSum(new VNextBOROWebPage().getAllPhasesWideScreenValues());
    }

    private static String getCalculatedSum(List<WebElement> values) {
        if (VNextBOROPageValidations.areValuesDisplayed(values)) {
            final WebElement element = values.get(0);
            WaitUtilsWebDriver.waitForElementNotToBeStale(element, 10);
            return Utils.getText(element);
        }
        return "";
    }

    private static String calculateSum(List<WebElement> values) {
        if (VNextBOROPageValidations.areValuesDisplayed(values)) {
            final List<WebElement> collection = values.subList(1, values.size());
            return collection
                    .stream()
                    .map((value) -> WaitUtilsWebDriver.waitForElementNotToBeStale(value, 3)
                            .getText()
                            .equals("") ? 0 : Integer.valueOf(value.getText()))
                    .reduce((val1, val2) -> val1 + val2)
                    .get()
                    .toString();
        }
        return "";
    }

    public static String getDepartmentsValuesSum() {
        return calculateSum(new VNextBOROWebPage().getAllDepartmentsWideScreenValues());
    }

    public static String getPhasesValuesSum() {
        return calculateSum(new VNextBOROWebPage().getAllPhasesWideScreenValues());
    }

    public static String getValue(List<WebElement> values, int order) {
        if (VNextBOROPageValidations.areValuesDisplayed(values)) {
            return values.get(order).getText();
        } else {
            return null;
        }
    }

    public static int getDepartmentsValue(int order) {
        return handleWideTabValues(order, new VNextBOROWebPage().getAllDepartmentsWideScreenValues());
    }

    public static int getPhasesValue(int order) {
        final VNextBOROWebPage roWebPage = new VNextBOROWebPage();
        return handleWideTabValues(order, roWebPage.getAllPhasesWideScreenValues());
    }

    public static List<Integer> getDepartmentsValues() {
        return Utils.getText(new VNextBOROWebPage().getAllDepartmentsWideScreenValues())
                .stream()
                .map(e -> e.equals("") ? 0 : Integer.valueOf(e))
                .collect(Collectors.toList());
    }

    public static List<Integer> getPhasesValues() {
        return Utils.getText(new VNextBOROWebPage().getAllPhasesWideScreenValues())
                .stream()
                .map(e -> e.equals("") ? 0 : Integer.valueOf(e))
                .collect(Collectors.toList());
    }

    private static int handleWideTabValues(int order, List<WebElement> allPhasesWideScreenValues) {
        final String value = VNextBOROPageInteractions.getValue(allPhasesWideScreenValues, order);
        assert value != null;
        return value.equals("") || value.equals("0") ? 0 : Integer.valueOf(value);
    }

    public static void clickDepartmentForWideScreen(String department) {
        clickTabForWideScreen(department, new VNextBOROWebPage().getDepartmentsOptionsForWideScreen());
    }

    public static void clickDepartmentForNarrowScreen(String department) {
        clickOptionForNarrowScreen(department, new VNextBOROWebPage().getDepartmentsTabPane(), new VNextBOROWebPage().getDepartmentsNarrowScreenDropDownOptions());
    }

    public static void clickPhaseForWideScreen(String phase) {
        clickTabForWideScreen(phase, new VNextBOROWebPage().getPhasesOptionsForWideScreen());
    }

    public static void clickPhaseForNarrowScreen(String phase) {
        clickOptionForNarrowScreen(phase, new VNextBOROWebPage().getPhasesTabPane(), new VNextBOROWebPage().getPhasesNarrowScreenDropDownOptions());
    }

    private static void clickTabForWideScreen(String phase, List<WebElement> phasesOptionsForWideScreen) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(phasesOptionsForWideScreen);
        phasesOptionsForWideScreen
                .stream()
                .filter(ph -> ph.getText().equals(phase))
                .findFirst().ifPresent(Utils::clickElement);
        WaitUtilsWebDriver.waitForLoading();
    }

    private static void clickOptionForNarrowScreen(String phase, WebElement phasesTabPane, List<WebElement> phasesNarrowScreenDropDownOptions) {
        if (VNextBOROPageValidations.isDepartmentNarrowScreenClickable()) {
//        departmentsNarrowScreen.click();
            Utils.clickElement(phasesTabPane);
            WaitUtilsWebDriver.waitABit(1000);
        System.out.println(phase);
        phasesNarrowScreenDropDownOptions
                .stream()
                .filter(dep -> dep.getText().equals(phase))
                .findFirst().ifPresent(Utils::clickElement);
        WaitUtilsWebDriver.waitForLoading();
        }
    }

    public static int getNumOfOrdersOnPage() {
        try {
            return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(new VNextBOROWebPage().getOrdersDisplayedOnPage()).size();
        } catch (Exception ignored) {
            return 0;
        }
    }

    public static List<String> getOrdersTargetDatesList() {
        final List<WebElement> ordersDateList = new VNextBOROWebPage().getTargetDateOrdersList();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(ordersDateList);
        return ordersDateList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static List<String> getOrdersCurrentPhaseList() {
        return Utils.getText(new VNextBOROWebPage().getOrdersCurrentPhaseList());
    }

    public static void setPhasesTabActive() {
        final VNextBOROWebPage ROWebPage = new VNextBOROWebPage();
        makeTabActive(ROWebPage.getPhasesWideTab(), ROWebPage.getPhasesWideTabActive());
    }

    public static void openIntercom() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getIntercomFrame());
        Utils.switchToFrame(new VNextBOROWebPage().getIntercomFrame());
        Utils.clickElement(new VNextBOROWebPage().getIntercom());
        WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getIntercomOpen());
    }

    public static void closeIntercom() {
        Utils.clickElement(new VNextBOROWebPage().getCloseIntercomButton());
        WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getIntercom());
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.switchTo().window(driver.getWindowHandle());
    }

    public static void clickPhasesWide() {
        Utils.clickElement(new VNextBOROWebPage().getPhasesWideTab());
    }

    public static String getTableTitleDisplayed(int titleHeaderNumber) {
        final List<WebElement> tableHeader = new VNextBOROWebPage().getTableHeader();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(tableHeader);
        return tableHeader.get(titleHeaderNumber).getText();
    }

    public static String getWoDepartment(String woNumber) {
        return WaitUtilsWebDriver.waitForVisibility(new VNextBOROWebPage().getTableBody()
                .findElement(By.xpath("//a[@class='order-no']/strong[text()='" + woNumber
                        + "']/../following-sibling::div/i[@class='menu-trigger']")))
                .getText();
    }

    public static void clickCancelSearchIcon() {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(new VNextBOROWebPage().getCancelSearchIcon());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getWorkOrderActivePhaseValue(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//td[@class='phase']")).getText().trim();
    }

    public static String getWorkOrderDaysInProgressValue(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//td[@class='days']/div/p")).getText().trim();
    }

    public static void clickWorkOrderOtherMenuButton(String orderNumber) {
        WebElement wotablerow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        wotablerow.findElement(By.xpath(".//i[@data-bind='click: dropMenuClicked']")).click();
    }

    public static void clickViewProblemsLink() {
        Utils.clickElement(new VNextBOROWebPage().getViewProblemsLink());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickStartRoForWorkOrder(String orderNumber) {
        clickWorkOrderOtherMenuButton(orderNumber);
        Utils.clickElement(By.xpath("//i[@class='icon-start-ro']"));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickWorkOrderCurrentPhaseMenu(String orderNumber) {
        Utils.clickElement(new VNextBOROWebPage().getPhaseMenu(orderNumber));
    }

    public static String getCompletedWorkOrderValue(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(
                By.xpath("./td[8]/span")).getText();
    }

    public static void clickInvoiceNumberInTable(String ro, String invoice) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(ro);
        System.out.println(woTableRow);
        Utils.clickElement(By.xpath("//a[@class='stockRo__invoiceNo' and text()='" + invoice + "']"));
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForNewTab();
    }

    public static void openOtherDropDownMenu(String wo) {
        final VNextBOROWebPage repairOrdersPage = new VNextBOROWebPage();
        WebElement roTableRow = repairOrdersPage.getTableRowWithWorkOrder(wo);
        final WebElement icon = roTableRow
                .findElement(By.xpath("//td[@class='grid__actions']//i[contains(@class, 'icon-arrow')]"));
        Utils.clickElement(icon);
        WaitUtilsWebDriver.waitForVisibility(repairOrdersPage.getOtherDropDown());
    }

    public static void openWorkOrderDetailsPage(String wo) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(wo);
        Utils.clickElement(woTableRow.findElement(By.xpath(".//a/strong[text()='" + wo + "']")));
    }

    public static void clickSearchTextToCloseLocationDropDown() {
        Utils.clickElement(new VNextBOROWebPage().getSearchText());
        WaitUtilsWebDriver.waitForInvisibility(new VNextBOROWebPage().getLocationExpanded());
    }

    public static String getTechnicianValueForWO(String woNumber) {
        return Utils.getText(new VNextBOROWebPage().getTechnicianFieldForWO(woNumber));
    }

    public static List<String> getMultipleTechniciansValuesForWO(String orderNumber) {
        return Arrays.asList(VNextBOROPageInteractions.getTechnicianValueForWO(orderNumber).split(", "));
    }

    public static void waitForTechnicianToBeUpdated(String orderNumber, String prevTechnician) {
        WaitUtilsWebDriver.getWebDriverWait(10).until((ExpectedCondition<Boolean>) driver -> {
            final String technicianValue = VNextBOROPageInteractions.getTechnicianValueForWO(orderNumber);
            return !(technicianValue.equals(prevTechnician) && technicianValue.isEmpty());
        });
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static List<String> getArbitrationDatesList() {
        return Utils.getText(new VNextBOROWebPage().getArbitrationDatesList());
    }

    public static List<String> getRoNumbersListValues() {
        return Utils.getTextByValue(new VNextBOROWebPage().getRoNumbersList());
    }

    public static List<String> getDescSortedRoNumbersListValues() {
        return Utils.getTextByValue(new VNextBOROWebPage().getRoNumbersList())
                .stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    public static List<String> getAscSortedRoNumbersListValues() {
        return Utils.getTextByValue(new VNextBOROWebPage().getRoNumbersList())
                .stream()
                .sorted((first, second) -> first.isEmpty() ? first.compareTo(second) : second.compareTo(first))
                .collect(Collectors.toList());
    }

    public static List<String> getOrdersPriorityValues() {
        final List<WebElement> priorityIconsList = new VNextBOROWebPage().getPriorityIconsList();
        List<String> priorityValuesList = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(priorityIconsList);
        priorityIconsList.forEach(icon -> {
            if (icon.getAttribute("style").contains("red")) {
                priorityValuesList.add(OrderPriority.HIGH.getValue());
            } else if (icon.getAttribute("style").contains("green")) {
                priorityValuesList.add(OrderPriority.LOW.getValue());
            } else {
                priorityValuesList.add(OrderPriority.NORMAL.getValue());
            }
        });
        return priorityValuesList;
    }

    public static List<String> sortByPriority(List<String> ordersPriorityValues) {
        List<String> high = new ArrayList<>();
        List<String> normal = new ArrayList<>();
        List<String> low = new ArrayList<>();

        for (String ordersPriorityValue : ordersPriorityValues) {
            if (ordersPriorityValue.equals(OrderPriority.HIGH.getValue())) {
                high.add(ordersPriorityValue);
            } else if (ordersPriorityValue.equals(OrderPriority.NORMAL.getValue())) {
                normal.add(ordersPriorityValue);
            } else if (ordersPriorityValue.equals(OrderPriority.LOW.getValue())) {
                low.add(ordersPriorityValue);
            }
        }
        return Stream.concat(
                Stream.concat(high.stream(), normal.stream()),
                low.stream())
                .collect(Collectors.toList());
    }

    public static List<String> getHighPriorityDates() {
        return getDatesByPriority(new VNextBOROWebPage().getHighPriorityOrdersTargetDatesList());
    }

    public static List<String> getNormalPriorityDates() {
        return getDatesByPriority(new VNextBOROWebPage().getNormalPriorityOrdersTargetDatesList());
    }

    public static List<String> getLowPriorityDates() {
        return getDatesByPriority(new VNextBOROWebPage().getLowPriorityOrdersTargetDatesList());
    }

    private static List<String> getDatesByPriority(List<WebElement> priorityOrdersDatesList) {
        try {
            return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(priorityOrdersDatesList)
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static int getOrdersNumberOnPage() {
        final List<WebElement> ordersNumber = new VNextBOROWebPage().getWoNumbersList();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(ordersNumber, 5);
        return ordersNumber.size();
    }

    public static void setStockNum(String orderNumber, String value) {
        Utils.clearAndType(new VNextBOROWebPage().getStockNumInputField(orderNumber), value);
        Utils.clickElement(new VNextBOROWebPage().getStockVinROPOInvoicesTableHeader());
    }

    public static void setRONum(String orderNumber, String value) {
        Utils.clearAndType(new VNextBOROWebPage().getRONumInputField(orderNumber), value);
        Utils.clickElement(new VNextBOROWebPage().getStockVinROPOInvoicesTableHeader());
    }

    public static void setPONum(String orderNumber, String value) {
        Utils.clearAndType(new VNextBOROWebPage().getPONumInputField(orderNumber), value);
        Utils.clickElement(new VNextBOROWebPage().getStockVinROPOInvoicesTableHeader());
    }

    public static String getStockNum(String orderNumber) {
        return Utils.getInputFieldValue(new VNextBOROWebPage().getStockNumInputField(orderNumber));
    }

    public static String getRONum(String orderNumber) {
        return Utils.getInputFieldValue(new VNextBOROWebPage().getRONumInputField(orderNumber));
    }

    public static String getPONum(String orderNumber) {
        return Utils.getInputFieldValue(new VNextBOROWebPage().getPONumInputField(orderNumber));
    }
}
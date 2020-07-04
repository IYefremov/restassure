package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.validations.ServiceRequestsListVerifications;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.cyberiansoft.test.baseutils.WaitUtilsWebDriver.waitForLoading;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValueWithTyping;

public class ServiceRequestsListInteractions {

    private ServiceRequestsListWebPage srListPage;

	public ServiceRequestsListInteractions() {
		srListPage = new ServiceRequestsListWebPage();
	}

	public void makeSearchPanelVisible() {
        if (!ServiceRequestsListVerifications.isSearchPanelExpanded()) {
            Utils.clickElement(srListPage.getSearchButton());
        }
	}

	public void selectSearchStatus(String _status) {
		selectComboboxValue(srListPage.getStatuscmb(), srListPage.getStatusdd(), _status);
	}

	public void selectSearchTeam(String teamname) {
		selectComboboxValueWithTyping(srListPage.getTeamcmb(), srListPage.getTeamdd(), teamname);
	}

	public void selectSearchTechnician(String technician) {
		selectComboboxValueWithTyping(srListPage.getTechniciancmb(), srListPage.getTechniciandd(), technician);
	}

	public void setServiceRequestType(String serviceRequest) {
		selectComboboxValue(srListPage.getServiceRequestcmb(), srListPage.getServiceRequestdd(), serviceRequest);
	}

	public void setSearchFreeText(String anytext) {
	    Utils.clearAndType(srListPage.getFreetextfld(), anytext);
	}

	public void clickFindButton() {
	    Utils.acceptAlertIfPresent();
		Utils.clickElement(srListPage.getFindbtn());
	}

	public void clickAddServiceRequestButtonAndSave() {
        clickAddSRButton();
		DriverBuilder.getInstance().getDriver().switchTo().frame(srListPage.getEditServiceRequestPanelFrame());
		WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getSaveServiceRequestButton(), true, 2);
		Utils.clickElement(srListPage.getSaveServiceRequestButton());
		waitForSRLoading();
		WaitUtilsWebDriver.waitABit(2000);
        selectFirstServiceRequestFromList();
	}

	public void clickAddServiceRequestButtonWithoutSaving() {
        clickAddSRButton();
		DriverBuilder.getInstance().getDriver().switchTo().frame(srListPage.getEditServiceRequestPanelFrame());
	}

    public void clickAddSRButton() {
        srListPage.getAddServiceRequestButton().click();
        waitForLoading();
        WaitUtilsWebDriver.waitForInvisibility(srListPage.getEditServiceRequestPanelImage());
    }

    public void selectFirstServiceRequestFromList() {
        Utils.clickWithActions(srListPage.getServiceRequestsPopoverList().get(0));
        waitForLoading();
        WaitUtilsWebDriver.waitABit(1500);
        ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
    }

    //todo needs clarification!!!
	public void closeFirstServiceRequestFromTheList() {
		selectFirstServiceRequestFromList();
//		try {
//		    Utils.clickElement(closeServiceRequestButton);
//		} catch (TimeoutException e) {
			DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
			selectFirstServiceRequestFromList();
//            Utils.clickElement(closeServiceRequestButton);
//		}
		clickCloseServiceRequestButton();
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		waitForLoading();
	}

    public void clickCloseServiceRequestButton() {
        Utils.clickElement(srListPage.getCloseServiceRequestButton());
    }

	public void acceptFirstServiceRequestFromList() {
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickWithActions(srListPage.getServiceRequestsPopoverList().get(0));
        Utils.clickElement(srListPage.getServiceRequestsAcceptButton());
        WaitUtilsWebDriver.waitABit(1000);
	}

	public void rejectFirstServiceRequestFromList() {
        WaitUtilsWebDriver.waitABit(2000);
        Utils.clickWithActions(srListPage.getServiceRequestsPopoverList().get(0));
        Utils.clickElement(srListPage.getServiceRequestsRejectButton());
        Utils.acceptAlertIfPresent();
		WaitUtilsWebDriver.waitForInvisibility(srListPage.getEditServiceRequestPanelImage());
	}

	public static String getStatusOfFirstServiceRequestFromList() {
		return new ServiceRequestsListWebPage().getFirstSRFromList()
				.findElement(By.xpath(".//span[@class='serviceRequestStatus']"))
				.getText()
				.replaceAll("\\u00A0", "")
				.trim();
	}

	public String getFirstInTheListServiceRequestNumber() {
		String srnumber = srListPage.getFirstSRFromList().findElement(By.xpath(".//span[@class='itemSrNo']/b"))
				.getText();
		return srnumber.substring(0, srnumber.length() - 1);
	}

	public static void clickAddAppointmentToFirstServiceRequestFromList() {
	    Utils.clickElement(new ServiceRequestsListWebPage().getFirstSRFromList().findElement(By.xpath(".//i[contains(@class, 'icon-calendar')]")));
	    WaitUtilsWebDriver.waitABit(2000);
	}

	public String getWOForFirstServiceRequestFromList() {
	    return Utils.getText(srListPage.getFirstSRFromList().findElement(By.xpath(".//span[@class='itemWO']")));
	}

    private WebElement getServiceRequestCellBySRNumber(String srNumber) {
        WebElement srCell = null;
        List<WebElement> serviceRequestsCells = WaitUtilsWebDriver.waitForVisibilityOfAllOptions(srListPage.getServiceRequestsList()
                .findElements(By.xpath("./div[contains(@class,'item')]")));
        for (WebElement cell : serviceRequestsCells)
            if (Utils.getText(cell.findElement(By.xpath(".//span[@class='itemSrNo']/b"))).trim().contains(srNumber)) {
                srCell = cell;
                break;
            }

        return srCell;
    }

    public String getWOForServiceRequestFromList(String srNumber) {
        return Utils.getText(getServiceRequestCellBySRNumber(srNumber).findElement(By.xpath(".//span[@class='itemWO']")));
    }

    public String getFirstServiceRequestStatus() {
        return srListPage.getFirstSRFromList().findElement(By.xpath(".//span[@class='serviceRequestStatus']"))
                .getText()
                .replaceAll("\\u00A0", "")
                .trim();
    }

    public void clickGeneralInfoEditButton() {
        Utils.clickElement(getGeneralInfoEditButton());
    }

    public void clickCustomerEditButton() {
        final WebElement customerEditButton = getCustomerEditButton();
        WaitUtilsWebDriver.elementShouldBeVisible(customerEditButton, true);
        Utils.clickElement(customerEditButton);
    }

    public void clickVehicleInfoEditButton() {
        Utils.clickElement(srListPage.getVehicleInfoButton());
        try {
            WaitUtilsWebDriver.waitForAttributeToContain(srListPage.getEditVehicleInfoBlock(), "display", "block");
        } catch (TimeoutException e) {
            Assert.fail("The edit Vehicle Info block has not been opened.", e);
        }
    }

    public void clickClaimInfoEditButton() {
        ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
        Utils.clickElement(By.id("Card_divCliamInfoAll"));
    }

    public void clickServiceEditButton() {
	    Utils.clickElement(srListPage.getServicesEditButton());
    }

    public WebElement getGeneralInfoEditButton() {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        Utils.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']")));
        Utils.moveToElement(driver.findElement(By.xpath("//div[@class='infoBlock-content']/b[text()='General Info:']")));
        return driver.findElement(By.xpath("//div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"));
    }

    public WebElement getCustomerEditButton() {
        try {
            Utils.moveToElement(srListPage.getCustomerBlock());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srListPage.getCustomerEditIcon();
    }

    public boolean isVehicleInfoEditButtonNotDisplayed() {
	    return WaitUtilsWebDriver.elementShouldBeVisible(srListPage.getVehicleInfoButton(), false);
    }

    public void setServiceRequestGeneralInfoAssignedTo(String value) {
        selectComboboxValueWithTyping(srListPage.getAddsrvassignedtocmb(), srListPage.getAddsrvassignedtodd(), value);
    }

    public void setServiceRequestGeneralInfoTeam(String value) {
        selectComboboxValueWithTyping(srListPage.getAddsrvteamcmb(), srListPage.getAddsrvteamdd(), value);
    }

    public void selectAddServiceRequestsComboboxValue(String value) {
        selectComboboxValue(srListPage.getAddservicerequestcmb(), srListPage.getAddservicerequestdd(), value);
    }

    public void setServiceRequestGeneralInfo(String _team, String assignedto, String po, String ro) {
        setServiceRequestGeneralInfo(_team, assignedto);
        Utils.clearAndType(srListPage.getAddsrvponum().getWrappedElement(), po);
        Utils.clearAndType(srListPage.getAddsrvronum().getWrappedElement(), ro);
    }

    public void setServiceRequestGeneralInfo(String _team, String assignedTo) {
        setServiceRequestGeneralInfoTeam(_team);
        WaitUtilsWebDriver.waitABit(2000);
        setServiceRequestGeneralInfoAssignedTo(assignedTo);
    }

    public void selectServiceRequestCustomer(String customer) {
        selectComboboxValueWithTyping(srListPage.getAddsrvcustomercmb(), srListPage.getAddsrvcustomerdd(), customer);
    }

    public void selectServiceRequestOwner(String owner) {
	    Utils.clickElement(By.xpath("//li[@id='tabsCustOwner_o']/a"));
        selectComboboxValueWithTyping(srListPage.getAddsrvownercmb(), srListPage.getAddsrvownerdd(), owner);
    }

    public void setServiceRequestVIN(String vin) {
        Utils.clearAndType(srListPage.getAddsrvvin().getWrappedElement(), vin);
    }

    public void setServiceRequestLabel(String _label) {
        Utils.clearAndType(srListPage.getAddsrvlabel().getWrappedElement(), _label);
    }

    public WebElement getServiceRequestLabelField() {
        return srListPage.getAddsrvlabel().getWrappedElement();
    }

    public void setServiceRequestDescription(String description) {
	    Utils.clickElement(srListPage.getInfoBlockEditButton());
        Utils.clearAndType(srListPage.getAddSRDescription().getWrappedElement(), description);
        Utils.clickElement(srListPage.getInfoBlockDoneButton());
    }

    public void decodeAndVerifyServiceRequestVIN(String _make, String _model) {
	    Utils.clickElement(srListPage.getAddsrvcardecodevinbtn());
	    WaitUtilsWebDriver.waitABit(2000);
        Assert.assertEquals(srListPage.getAddsrvcarmake().getValue(), _make);
        Assert.assertEquals(srListPage.getAddsrvcarmodel().getValue(), _model);
    }

    public void selectServiceRequestInsurance(String insurance) {
        selectComboboxValueWithTyping(srListPage.getAddsrvinsurancecmb(), srListPage.getAddsrvinsurancedd(), insurance);
    }

    public void clickDoneButton() {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(srListPage.getDonebtns());
        for (WebElement donebtn : srListPage.getDonebtns()) {
            if (donebtn.isDisplayed()) {
                Utils.clickElement(donebtn);
                WaitUtilsWebDriver.waitABit(3000);
                break;
            }
        }
    }

	public void saveNewServiceRequest() {
	    Utils.clickElement(srListPage.getSaveServiceRequestButton());
	    waitForLoading();
	    waitForSRLoading();
	}

	public void cancelNewServiceRequest() {
		Utils.clickElement(srListPage.getCancelservicerequestbutton());
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
	}

	public void clickCheckInButtonForSelectedSR() {
		ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
		Utils.clickElement(srListPage.getServiceRequestCheckInButton());
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		WaitUtilsWebDriver.waitABit(5000);
	}

    private String getTextValue(WebElement element) {
        ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
        String value = Utils.getText(element);
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        return value;
    }

    public String getCheckInButtonValueForSelectedSR() {
        return getTextValue(srListPage.getServiceRequestCheckInButton());
	}

	public String getVINValueForSelectedServiceRequest() {
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-for='Card_vehicleVin']")));
	    return getTextValue(DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@data-for='Card_vehicleVin']")));
	}

	public String getCustomerValueForSelectedServiceRequest() {
		DriverBuilder.getInstance().getDriver().switchTo().frame(srListPage.getEditServiceRequestPanelFrame());
	    return getTextValue(DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@data-for='Card_hdnFullClientName']")));
	}

	public String getEmployeeValueForSelectedServiceRequest() {
		DriverBuilder.getInstance().getDriver().switchTo().frame(srListPage.getEditServiceRequestPanelFrame());
	    return getTextValue(DriverBuilder.getInstance().getDriver().findElement(By.xpath("//span[@data-for='Card_hdnEmployeeFullName']")));
	}

	public WebElement clickAddServicesIcon() {
		Utils.clickWithActions(By.xpath("//div[contains(@class, 'infoBlock-list')]" +
                "/div[@class='infoBlock-content']/span[@class='infoBlock-editBtn']"));
		return WaitUtilsWebDriver.waitForVisibility(srListPage.getServicesPopup());
	}

	public void addServicesToServiceRequest(String... services) {
		WebElement servicesPopup = clickAddServicesIcon();
		for (String service : services) {
            handleServices(service);
        }
        Utils.clickElement(servicesPopup.findElement(By.xpath(".//div[@class='infoBlock-list-doneBtn rp-btn-blue']")));
    }

	public void addServicesToServiceRequest(List<ServiceData> services) {
		WebElement servicesPopup = clickAddServicesIcon();
		services.forEach(serviceData -> handleServices(serviceData.getServiceName()));
		Utils.clickElement(servicesPopup.findElement(By.xpath(".//div[@class='infoBlock-list-doneBtn rp-btn-blue']")));
	}

    private void handleServices(String serviceName) {
        Utils.clearAndType(By.id("Card_comboService_Input"), serviceName);
        Utils.clickElement(DriverBuilder.getInstance().getDriver().findElement(By.id("Card_comboService_DropDown")).findElement(By.name("serviceCheckbox")));
        Utils.clickElement(By.id("Card_btnAddServiceToList"));
        WaitUtilsWebDriver.waitABit(500);
    }

    public void setServiePriceAndQuantity(String serviceName, String price, String quantity) {
		WebElement servicesPopup = clickAddServicesIcon();
		WebElement serviceRow = getSelectedServiceRow(serviceName);

		Utils.clickElement(serviceRow.findElement(By.xpath(".//input[@class='k-formatted-value price-service k-input']")));
		Utils.clearAndType(serviceRow.findElement(By.xpath(".//input[@class='price-service k-input']")), price);

        Utils.clickElement(serviceRow.findElement(By.xpath(".//input[@class='k-formatted-value quantity-service k-input']")));
		Utils.clearAndType(serviceRow.findElement(By.xpath(".//input[@class='quantity-service k-input']")), quantity);

		Utils.clickElement(servicesPopup.findElement(By.xpath(".//div[@class='infoBlock-list-doneBtn rp-btn-blue']")));
	}

	public WebElement getSelectedServiceRow(String serviceName) {
		WebElement serviceRow = null;
		List<WebElement> selectedServices = srListPage.getServicesPopup()
				.findElements(By.xpath(".//*[@class='container-service container-service-selected']"));
		for (WebElement srvrow : selectedServices)
			if (srvrow.findElements(By.xpath(".//span[text()='" + serviceName + "']")).size() > 0) {
				serviceRow = srvrow;
				break;
			}
		return serviceRow;
	}

	public void clickDocumentButton() {
		ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
		String oldWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
		Utils.clickElement(srListPage.getDescriptionDocuments().findElement(By.tagName("i")));
		Set allWindows = DriverBuilder.getInstance().getDriver().getWindowHandles();
		allWindows.remove(oldWindow);
		try {
			DriverBuilder.getInstance().getDriver().switchTo().window((String) allWindows.iterator().next());
		} catch (NoSuchElementException ignored) {}
	}

	public void clickAddImageButton() {
	    Utils.clickElement(By.className("add"));
	}

	public void selectAddServiceRequestDropDown(String string) {
	    Utils.clickElement(srListPage.getAddServiceRequestDropDown());
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem")).stream().filter(e -> e
				.getText()
				.equals(string))
				.findFirst()
				.ifPresent(WebElement::click);
	}

	public void clickAddAppointmentButtonFromSRList() {
	    Utils.clickElement(srListPage.getAddAppointmentButton());
	}

    public void setAppointmentValues(String fromDate, String toDate) {
        Utils.clickElement(srListPage.getAppointmentCalendarIcon());
        getAppointmentHashMap(fromDate, toDate).forEach((value, key) -> {
            Utils.clearAndType(value, key);
            value.sendKeys(Keys.ENTER);
            WaitUtilsWebDriver.waitABit(1500);
        });
    }

    private HashMap<WebElement, String> getAppointmentHashMap(String fromDate, String toDate) {
        HashMap<WebElement,String> appointmentValues = new HashMap<>();
        appointmentValues.put(srListPage.getAppointmentFromDate(), fromDate);
        appointmentValues.put(srListPage.getAppointmentToDate(), toDate);
        appointmentValues.put(srListPage.getAppointmentFromTime(), "6:00 AM");
        appointmentValues.put(srListPage.getAppointmentToTime(), "7:00 AM");
        return appointmentValues;
    }

    private HashMap<WebElement, String> getAppointmentSREditHashMap(String fromDate, String toDate) {
        HashMap<WebElement,String> appointmentValuesSREdit = new HashMap<>();
        appointmentValuesSREdit.put(srListPage.getAppointmentFromDateSRedit(), fromDate);
        appointmentValuesSREdit.put(srListPage.getAppointmentToDateSRedit(), toDate);
        appointmentValuesSREdit.put(srListPage.getAppointmentFromTimeSRedit(), "6:00 AM");
        appointmentValuesSREdit.put(srListPage.getAppointmentToTimeSRedit(), "7:00 AM");
        return appointmentValuesSREdit;
    }

    public void setAppointmentSREditValues(String fromDate, String toDate) {
        getAppointmentSREditHashMap(fromDate, toDate).forEach((value, key) -> {
            Utils.clearAndType(value, key);
            Utils.clickElement(By.xpath("//td[text()='From:']"));
            WaitUtilsWebDriver.waitABit(1000);
        });
    }

    public void addAppointmentFromSRList(String fromDate, String toDate, String technician) {
        setAppointmentValues(fromDate, toDate);
        addTechnician(technician);
        clickAddAppointmentButtonFromSRList();
	}

    public void addAppointmentFromSRList(String fromDate, String toDate) {
        setAppointmentValues(fromDate, toDate);
        clickAddAppointmentButtonFromSRList();
	}

	public void clickAddAppointmentButton() {
	    Utils.clickElement(srListPage.getAddAppButton());
	    WaitUtilsWebDriver.waitForInvisibilityIgnoringException(srListPage.getAppointmentTable());
	    WaitUtilsWebDriver.waitABit(1000);
	}

	public void openAddAppointmentForFirstSR() {
        ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
        try {
			clickAddAppointmentButtonFromSREdit();
		} catch (TimeoutException e) {
			DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
			selectFirstServiceRequestFromList();
			clickAddAppointmentButtonFromSREdit();
		}
		WaitUtilsWebDriver.waitForVisibility(srListPage.getAppointmentTable());
	}

	public void clickAddAppointmentButtonFromSREdit() {
	    Utils.clickElement(srListPage.getAddAppointmentBTNfromSRedit());
	    WaitUtilsWebDriver.waitABit(1500);
        WaitUtilsWebDriver.waitForVisibility(srListPage.getAppointmentTable());
	}

	public void setSubjectForSRAppointment(String subject) {
	    Utils.clearAndType(srListPage.getSubjectField(), subject);
	    WaitUtilsWebDriver.waitABit(1000);
    }

	public void setSuggestedStartDate(String startDate) {
		ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
        srListPage.getServiceRequestInfoBlocks().get(0).click();
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(srListPage.getSuggestedStart())).sendKeys(startDate);
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(srListPage.getAcceptGeneralInfoBTN())).click();
	}

	public void findClick(By by, By byInner, String startDate) {
        waitForLoading();
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(driver.findElements(By.className("rsWrap")));

                WaitUtilsWebDriver.getWait()
						.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)))
						.findElements(byInner)
						.stream().map(w -> w.findElement(By.tagName("a")))
						.filter(t -> Utils.getText(t).split(" ")[1].equals(startDate.split("/")[1]))
                        .findFirst()
                        .ifPresent(Utils::clickElement);
	}

	public int checkSchedulerByDateWeek(String startDate, boolean isDateShifted) {
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		try {
			WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(srListPage.getScheduler())).click();
		} catch (Exception e) {
			Assert.fail("The scheduler has not been clickable", e);
		}
		waitForLoading();

        if (isDateShifted) {
            retryingFindClick(By.className("rsNextDay"));
            waitForLoading();
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(By.className("rsFullTime"))).click();
        }
        retryingFindClick(By.className("rsFullTime"));
        waitForLoading();
        findClick(By.className("rsHorizontalHeaderTable"), By.tagName("th"), startDate);

        waitForLoading();
		return WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.className("rsWrap")))
				.findElements(By.xpath("//div[contains(@class, 'rsApt appointmentClassDefault')]")).size();
	}

	public void goToSRMenu() {
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		Utils.clickElement(By.id("btnListTop"));
		WaitUtilsWebDriver.waitABit(2000);
	}

	public void reloadPage() {
		DriverBuilder.getInstance().getDriver().navigate().refresh();
		WaitUtilsWebDriver.waitABit(5000);
	}

	public int checkSchedulerByDateMonth(String date) {
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		Utils.clickElement(By.id("lbViewChangeScheduler"));
		Utils.clickElement(By.className("rsHeaderMonth"));
        Utils.clickElement(By.xpath("//a[contains(@title, '" + date + "')]"));
        Utils.clickElement(By.className("rsFullTime"));
		return WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.className("rsNonWorkHour")))
				.findElements(By.xpath("//div[contains(@class, 'rsApt appointmentClassDefault')]")).size();
	}

	public void goToMonthInScheduler() {
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		waitForLoading();
		Utils.clickElement(By.id("lbViewChangeScheduler"));
		waitForLoading();
		retryingFindClick(By.className("rsHeaderMonth"));
		// WaitUtilsWebDriver.getWait().ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.className("rsHeaderMonth"))).click();
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("rsDateBox")));
	}

	private void retryingFindClick(By by) {
		int attempts = 0;
		while (attempts < 10) {
			try {
				DriverBuilder.getInstance().getDriver().findElement(by).click();
				break;
			} catch (StaleElementReferenceException e) {
				WaitUtilsWebDriver.waitABit(500);
			}
			attempts++;
		}
	}

	public int addMaximumTechnicians() {
        int size = 0;
        for (int i = 0; i < 8; i++) {
            addTechnician();
            if (WaitUtilsWebDriver.waitForVisibilityOfAllOptions(srListPage.getTechniciansList(), 2).size() == 5) {
                size = 5;
                break;
            }
		}
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		return size;
	}

    private void addTechnician() {
        Utils.clickElement(srListPage.getTechniciansSchedulerArrow());
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(srListPage.getTechniciansDropDown(), 1);
        Utils.selectOptionInDropDown(srListPage.getTechniciansDropDown(), srListPage.getTechniciansListBox());
        Utils.clickElement(srListPage.getTechniciansSchedulerAddButton());
    }

    private void addTechnician(String technician) {
        Utils.clickElement(srListPage.getTechniciansArrow());
        selectComboboxValueWithTyping(srListPage.getAddSRAppTechCombobox(), srListPage.getAddSRAppTechnicianDropDown(), technician);
        Utils.clickElement(srListPage.getTechniciansSchedulerAddButton());
    }

    public void clickArrowTechniciansLink() {
        Utils.clickElement(srListPage.getArrowInTechniciansList());
    }

    public void clickTechniciansSchedulerDropDown() {
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        waitForLoading();
        retryingFindClick(By.className("scheduler-dropdown"));
    }

	public void retryingFindClick(WebElement element) {
		int attempts = 0;
		while (attempts < 10) {
			try {
				WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(element)).click();
				break;
			} catch (StaleElementReferenceException e) {
				WaitUtilsWebDriver.waitABit(500);
			}
			attempts++;
		}
	}

	public void applyTechniciansFromScheduler() {
        clickArrowTechniciansLink();
	    WaitUtilsWebDriver.waitForVisibilityIgnoringException(srListPage.getTechniciansDialogApplyButton(), 5);
	    Utils.clickElement(srListPage.getTechniciansDialogApplyButton());
		waitForLoading();
	}

	public int countSR() {
		// WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("appointmentClassDefault")));
		int defaultSRs = DriverBuilder.getInstance().getDriver().findElements(By.className("appointmentClassDefault")).size();
		int failedSRs = DriverBuilder.getInstance().getDriver().findElements(By.className("appointmentClassFailed")).size();
		int completedSRs = DriverBuilder.getInstance().getDriver().findElements(By.className("appointmentClassCompleted")).size();
		return defaultSRs + failedSRs + completedSRs;
	}

	public void resetTechniciansFromScheduler() {
        retryingFindClick(By.className("scheduler-dropdown"));
        clickArrowTechniciansLink();

        Utils.clickElement(By.className("btn-reset"));
        waitForLoading();
        WaitUtilsWebDriver.waitABit(3000);
    }

    public void goToLifeCycle() {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        String parentFrame = driver.getWindowHandle();
        clickSRLifeCycleButton();
        Set windows = driver.getWindowHandles();
		driver.close();
		windows.remove(parentFrame);
		driver.switchTo().window((String) windows.iterator().next());
	}

    public void clickSRLifeCycleButton() {
        ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickElement(srListPage.getSrLifeCycle());
        WaitUtilsWebDriver.waitABit(1000);
    }

    public void goToDocumentLinkFromLC() {
		WaitUtilsWebDriver.waitABit(1000);
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		Utils.clickElement(By.xpath("//a[contains(text(), 'Link to R-')]"));
		waitForLoading();
	}

	public void clickVehicleEditButton() {
	    Utils.clickElement(By.id("Card_divVehInfoAll"));
	}

	public void setVehicleInfo(String stock, String vin) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.findElement(By.id("Card_tbStock")).sendKeys(stock);
		driver.findElement(By.id("Card_vehicleVin")).sendKeys(vin);
	}

    public void goToWOFromLifeCycle() {
        waitForLoading();
        final By linkTo = By.xpath("//a[contains(text(), 'Link to')]");
        WaitUtilsWebDriver.waitForVisibility(linkTo);
        Utils.clickElement(linkTo);
        waitForSRLoading();
        waitForLoading();
    }

	private void waitForSRLoading() {
	    WaitUtilsWebDriver.waitForAttributeToContainIgnoringException(srListPage.getLoading(), "style", "display: block;");
	    WaitUtilsWebDriver.waitABit(2000);
        WaitUtilsWebDriver.waitForAttributeToContainIgnoringException(srListPage.getLoading(), "style", "display: none;");
	}

	public void setServiceRequestGeneralInfo(String _assignedto) {
		setServiceRequestGeneralInfoAssignedTo(_assignedto);
//		DriverBuilder.getInstance().getDriver().findElement(By.id("Card_ddlClientsAssignedTo_Arrow")).click();
//		waitABit(7000);
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(By.id("divGeneralButtonsDone"))).click();
	}

	public void addAppointmentWithTechnician(String startDate, String endDate, String string) {
		WaitUtilsWebDriver.waitABit(3000);
		ServiceRequestsListVerifications.verifyServiceRequestInfoFrameIsOn();

		clickAddAppointmentButtonFromSREdit();
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(srListPage.getAppointmentFromDateSRedit()));
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(srListPage.getAppointmentToDateSRedit()));
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(srListPage.getAppointmentFromTimeSRedit()));
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(srListPage.getAppointmentToTimeSRedit()));

        srListPage.getAppointmentFromDateSRedit().clear();
        srListPage.getAppointmentToDateSRedit().clear();
        srListPage.getAppointmentFromTimeSRedit().clear();
        srListPage.getAppointmentToTimeSRedit().clear();

        srListPage.getAppointmentFromDateSRedit().sendKeys(startDate);
        srListPage.getAppointmentToDateSRedit().sendKeys(endDate);
        srListPage.getAppointmentFromTimeSRedit().sendKeys("11:00 AM");
        srListPage.getAppointmentToTimeSRedit().sendKeys("11:30 AM");
        srListPage.getTimePopupLink().click();
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.attributeToBe(srListPage.getEndTimeDialog(), "visibility", "hidden"));
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(srListPage.getTechniciansField())).click();

		WaitUtilsWebDriver.getWait().until(ExpectedConditions.presenceOfElementLocated(By.className("rcbList")))
				.findElements(By.className("rcbItem"))
				.stream()
				.filter(e -> e.getText().equals(string))
				.findFirst()
				.ifPresent(WebElement::click);

		WaitUtilsWebDriver.waitABit(1000);
		Utils.clickElement(srListPage.getAddAppButton());
	}

	public void scrollWindow(String pixels) {
		DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
		JavascriptExecutor jse = (JavascriptExecutor) DriverBuilder.getInstance().getDriver();
		jse.executeScript("window.scrollBy(0," + pixels + ")", "");
	}

	public void setRO(String ro) {
	    WaitUtilsWebDriver.waitABit(1500);
		Utils.clearAndType(srListPage.getAddsrvronum().getWrappedElement(), ro);
	}

	public void clickRejectUndoButton() {
		Utils.moveToElement(srListPage.getFirstSRFromList());
		Utils.clickElement(srListPage.getFirstSRFromList().findElement(By.xpath(".//a[@title='Undo Reject']")));
		Utils.acceptAlertIfPresent();
	}

	public String getFirstServiceAdviserName() {
	    Utils.clickElement(getCustomerEditButton());
		try {
		    Utils.clickElement(srListPage.getAddsrvcustomercmb().getWrappedElement());
		} catch (TimeoutException e) {
			Assert.fail("The combobox with customers list is not clickable.", e);
		}
		waitForLoading();
		return WaitUtilsWebDriver.waitForVisibilityOfAllOptions(srListPage.getClients())
                .stream()
                .map(Utils::getText)
                .findFirst()
                .get();
	}

	public void selectSearchTimeFrame(WebConstants.TimeFrameValues timeFrame) {
		selectComboboxValue(srListPage.getSearchtimeframecmb(), srListPage.getSearchtimeframedd(), timeFrame.getName());
	}

	public void setSearchFromDate(String date) {
        Utils.clearAndType(srListPage.getFromDateInputField(), date);
	}

	public void setSearchToDate(String date) {
	    Utils.clearAndType(srListPage.getToDateInputField(), date);
	}

	public String getServiceRequestInspectionNumber() {
		return srListPage.getServiceRequestInspection().getText();
	}

	public String getServiceRequestInvoiceNumber() {
		return srListPage.getServiceRequestInvoice().getText();
	}

	public String getServiceRequestWorkOrderNumber() {
		return srListPage.getServiceRequestWorkOrder().getText();
	}

	public String getServiceRequestAppointmentStatus() {
		return srListPage.getServiceRequestAppointmentFrame().findElement(By.xpath(".//span[@class='appointmentReason']")).getText().trim();
	}
}
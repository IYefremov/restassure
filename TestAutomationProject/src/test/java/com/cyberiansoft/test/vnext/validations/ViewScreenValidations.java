package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextViewScreen;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ViewScreenValidations {

    public static void verifyEstimationDataFieldFormat() {
        DateTimeFormatter dateFormatLong =
                DateTimeFormatter.ofPattern("dd MMMM yyyy hh:mm a");
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String date = viewScreen.getEstimationDateValue();
        Assert.assertTrue(isValidFormat(dateFormatLong, date));
    }

    public static void verifyEstimationCustomerValues(RetailCustomer retailCustomer) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(viewScreen.getCustomerNameValue(), retailCustomer.getFullName());
        Assert.assertEquals(viewScreen.getCustomerEmailValue(), retailCustomer.getMailAddress());
        Assert.assertEquals(viewScreen.getCustomerPhoneValue(), retailCustomer.getCustomerPhone());
        Assert.assertEquals(viewScreen.getCustomerAddressValue(), getEstimationCustomerAddressValue(retailCustomer));
    }

    public static void verifyServiceIsPresent(String serviceName, boolean isPresent) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        if (isPresent)
            Assert.assertTrue(viewScreen.isServicePresent(serviceName));
        else
            Assert.assertFalse(viewScreen.isServicePresent(serviceName));
    }

    public static void verifyVehiclePartIsPresent(String serviceName, VehiclePartData vehiclePartData, boolean isPresent) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        if (isPresent) {
            Assert.assertTrue(viewScreen.isVehiclePartLinePresent(vehiclePartData.getVehiclePartName()));
            Assert.assertTrue(viewScreen.isVehiclePartInfoLinePresent(serviceName, vehiclePartData));
        } else {
            Assert.assertFalse(viewScreen.isVehiclePartLinePresent(vehiclePartData.getVehiclePartName()));
            Assert.assertFalse(viewScreen.isVehiclePartInfoLinePresent(serviceName, vehiclePartData));
        }
    }

    public static void verifyMatrixServiceHasNotes(String serviceName, String notesText) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(viewScreen.getServiceNotesValue(serviceName), notesText);
    }

    public static void verifyServiceOriginalAmaunt(String serviceName, String expectedAmaunt) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(viewScreen.getServiceOriginalAmount(serviceName), expectedAmaunt);
    }

    public static void verifyServiceSupplementAmaunt(String serviceName, String expectedAmaunt) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(viewScreen.getServiceSupplementAmount(serviceName), expectedAmaunt);
    }

    public static void verifySupplementAmaunt(String expectedAmaunt) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(viewScreen.getSupplementAmaunt(), expectedAmaunt);
    }

    public static void verifyServiceTotalAmaunt(String serviceName, String expectedAmaunt) {
        VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(viewScreen.getServiceTotalAmount(serviceName), expectedAmaunt);
    }

    public static String getEstimationCustomerAddressValue(RetailCustomer retailCustomer) {
        String addressString = "";
        if (retailCustomer.getCustomerAddress1() != null)
            addressString = addressString + retailCustomer.getCustomerAddress1() + ", ";
        if (retailCustomer.getCustomerAddress2() != null)
            addressString = addressString + retailCustomer.getCustomerAddress2() + ", ";
        if (retailCustomer.getCustomerCity() != null)
            addressString = addressString + retailCustomer.getCustomerCity() + ", ";
        if (retailCustomer.getCustomerState() != null)
            addressString = addressString + retailCustomer.getCustomerState() + " ";
        if (retailCustomer.getCustomerZip() != null)
            addressString = addressString + retailCustomer.getCustomerZip() + ", ";
        if (retailCustomer.getCustomerCountry() != null)
            addressString = addressString + retailCustomer.getCustomerCountry();
        return addressString.trim();
    }

    public static boolean isValidFormat(DateTimeFormatter dateFormat, String value) {
        try {
            LocalDateTime ldt = LocalDateTime.parse(value, dateFormat);
            String result = ldt.format(dateFormat);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, dateFormat);
                String result = ld.format(dateFormat);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, dateFormat);
                    String result = lt.format(dateFormat);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {

                }
            }
        }

        return false;
    }
}

package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.InvoiceCashCheckPaymentData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.paymentsscreens.RegularPaymentScreen;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class RegularPaymentScreenSteps {


    public static void sitchToCashCheckPayOption() {
        RegularPaymentScreen paymentScreen = new RegularPaymentScreen();
        paymentScreen.waitPaymentScreenLoaded();
        paymentScreen.selectCashCheckPayOption();
    }

    public static void setCashCheckNumberValue(String cashCheckNumberValue) {
        RegularPaymentScreen paymentScreen = new RegularPaymentScreen();
        paymentScreen.setCashCheckNumberValue(cashCheckNumberValue);
    }

    public static void setCashCheckAmountValue(String cashCheckAmountValue) {
        RegularPaymentScreen paymentScreen = new RegularPaymentScreen();
        paymentScreen.setCashCheckAmountValue(cashCheckAmountValue);
    }

    public static void payForInvoice() {
        RegularPaymentScreen paymentScreen = new RegularPaymentScreen();
        paymentScreen.payForInvoice();
    }

    public static void setInvoiceCashCheckPaymentValues(InvoiceCashCheckPaymentData cashCheckPaymentData) {
        if (cashCheckPaymentData.getCashCheckNumber() != null) {
            setCashCheckNumberValue(cashCheckPaymentData.getCashCheckNumber());
        }
        if (cashCheckPaymentData.getCashCheckAmount() != null) {
            setCashCheckAmountValue(cashCheckPaymentData.getCashCheckAmount());
        }
    }

    public static String getCashCheckAmountValue() {
        RegularPaymentScreen paymentScreen = new RegularPaymentScreen();
        return paymentScreen.getCashCheckAmountValue();
    }


}

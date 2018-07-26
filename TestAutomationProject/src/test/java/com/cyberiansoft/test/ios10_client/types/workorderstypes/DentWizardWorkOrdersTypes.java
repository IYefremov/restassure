package com.cyberiansoft.test.ios10_client.types.workorderstypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum DentWizardWorkOrdersTypes implements  IWorkOrdersTypes {

    wizprotrackerrouteworkordertype("WizardPro Tracker Route"),
    routeusworkordertype("Route - U.S."),
    servicedriveworkordertype("Service Drive"),
    routecanadaworkordertype("Route - Canada"),
    wholesailhailworkordertype("Wholesale Hail"),
    retailhailworkordertype("Retail Hail"),
    carmaxworkordertype("CarMax"),
    enterpriseworkordertype("Enterprise"),
    avisworkordertype("AVIS"),
    auctionworkordertype("Auction"),
    wizardprotrackeravisworkordertype("WizardPro Tracker Avis"),
    wizardprotrackerservicedriveworkordertype("WizardPro Tracker Service Drive");

    private final String woType;

    DentWizardWorkOrdersTypes(final String srType) {
        this.woType = srType;
    }

    public String getWorkOrderTypeName() {
        return woType;
    }

    public DentWizardWorkOrdersTypes getWorkOrderType(){
        for(DentWizardWorkOrdersTypes type : values()){
            if(type.getWorkOrderTypeName().equals(woType)){
                return type;
            }
        }

        throw new IllegalArgumentException(woType + " is not a valid WorkOrdersTypes");
    }

    public <T extends IBaseWizardScreen>T getFirstVizardScreen() {
        DentWizardWorkOrdersTypes type = getWorkOrderType();
        switch (type) {
            case wizprotrackerrouteworkordertype:
            case routeusworkordertype:
            case servicedriveworkordertype:
            case routecanadaworkordertype:
            case wholesailhailworkordertype:
            case carmaxworkordertype:
            case enterpriseworkordertype:
            case avisworkordertype:
            case auctionworkordertype:
            case wizardprotrackeravisworkordertype:
            case wizardprotrackerservicedriveworkordertype:
                if (BaseTestCase.mobilePlatform.equals(MobilePlatform.IOS_HD))
                    return (T) new VehicleScreen();
                else
                    return (T) new RegularVehicleScreen();
            case retailhailworkordertype:
                if (BaseTestCase.mobilePlatform.equals(MobilePlatform.IOS_HD))
                    return (T) new QuestionsScreen();
                else
                    return (T) new RegularQuestionsScreen();


        }
        return null;
    }
}

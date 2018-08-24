package com.cyberiansoft.test.ios10_client.types.inspectionstypes;

import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;

public enum DentWizardInspectionsTypes implements IInspectionsTypes {

    carmaxinspectiontype("CarMax"),
    routecanadainspectiontype("Route - Canada"),
    routeinspectiontype("Route"),
    wizprotrackerrouteunspectiontype("WizardPro Tracker Route"),
    servicedriveinspectiondertype("Service Drive"),
    economicalinspectiondertype("Economical"),
    wizprotrackerrouteinspectiontype("WizardPro Tracker Route"),
    wizardprotrackerrouteinspectiondertype("WizardPro Tracker Service");

    private final String inspType;

    DentWizardInspectionsTypes(final String srType) {
        this.inspType = srType;
    }

    public String getInspectionTypeName() {
        return inspType;
    }

    public DentWizardInspectionsTypes getInspectionType(){
        for(DentWizardInspectionsTypes type : values()){
            if(type.getInspectionTypeName().equals(inspType)){
                return type;
            }
        }

        throw new IllegalArgumentException(inspType + " is not a valid InspectionsTypes");
    }

    public <T extends IBaseWizardScreen>T getFirstVizardScreen() {
        DentWizardInspectionsTypes type = getInspectionType();
        switch (type) {
            case carmaxinspectiontype:
            case routeinspectiontype:
            case wizprotrackerrouteunspectiontype:
            case servicedriveinspectiondertype:
            case economicalinspectiondertype:
            case wizardprotrackerrouteinspectiondertype:
            case routecanadainspectiontype:
            case wizprotrackerrouteinspectiontype:
                if (BaseTestCase.mobilePlatform.equals(MobilePlatform.IOS_HD))
                    return (T) new VehicleScreen();
                else
                    return (T) new RegularVehicleScreen();


        }
        return null;
    }
}

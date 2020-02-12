package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOAddNewPartDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddNewPartDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAddNewPartDialogValidations;

import java.util.List;

public class VNextBOAddNewPartDialogSteps {

    public static void setServiceField(String serviceName) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clearAndType(addNewPartDialog.getServiceField(), serviceName);
        Utils.selectOptionInDropDownWithJs(addNewPartDialog.getServiceFieldDropDown(),
                addNewPartDialog.dropDownFieldOption(serviceName));
    }

    public static void setDescription(String description) {

        Utils.clearAndType(new VNextBOAddNewPartDialog().getDescriptionTextarea(), description);
    }

    public static void setCategory(String categoryName) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clickElement(addNewPartDialog.getCategoryField());
        Utils.selectOptionInDropDownWithJs(addNewPartDialog.getCategoryFieldDropDown(),
                addNewPartDialog.dropDownFieldOption(categoryName));
    }

    public static void setSubCategory(String subCategoryName) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        Utils.clickElement(addNewPartDialog.getSubCategoryField());
        Utils.selectOptionInDropDownWithJs(addNewPartDialog.getSubCategoryFieldDropDown(),
                addNewPartDialog.dropDownFieldOption(subCategoryName));
    }

    public static void selectPartsFromPartsList(List<String> partsList) {

        VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
        for (String part : partsList) {
            Utils.clearAndType(addNewPartDialog.getPartsListFilterField(), part);
            Utils.clickElement(addNewPartDialog.partsListRecordByText(part));
        }
    }

    public static void submit() {
        VNextBOAddNewPartDialogInteractions.clickSubmitButton();
        VNextBOAddNewPartDialogValidations.verifyDialogIsDisplayed(false);
    }
}

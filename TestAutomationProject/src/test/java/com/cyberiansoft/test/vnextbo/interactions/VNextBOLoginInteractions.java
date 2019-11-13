package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextBOLoginInteractions {

    public static  void clickForgotPasswordLink() {
        Utils.clickElement(new VNextBOLoginScreenWebPage().getForgotPasswordLink());
    }

    public static  String getValueFromEmailField() {
        return Utils.getInputFieldValue(new VNextBOLoginScreenWebPage().getEmailField());
    }

    public static  String getValueFromPasswordField() {
        return Utils.getInputFieldValue(new VNextBOLoginScreenWebPage().getPasswordField());
    }

    public static  String getEmailErrorMessage() {
        return new VNextBOLoginScreenWebPage().getEmailErrorText().getText();
    }

    public static  String getPasswordErrorMessage() {
        return new VNextBOLoginScreenWebPage().getPasswordErrorText().getText();
    }

    public static  void setEmailField(String username) {
        Utils.clearAndType(new VNextBOLoginScreenWebPage().getEmailField(), username);
    }

    public static  void setPasswordField(String userPsw) {
        Utils.clearAndType(new VNextBOLoginScreenWebPage().getPasswordField(), userPsw);
    }
}

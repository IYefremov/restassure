package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextBOLoginInteractions {

    public static  void clickForgotPasswordLink() {
        Utils.clickElement(new VNextBOLoginScreenWebPage().getForgotPasswordLink());
    }

    public static  String getValueFromEmailField() {
        return new VNextBOLoginScreenWebPage().getEmailField().getValue();
    }

    public static  String getValueFromPasswordField() {
        return new VNextBOLoginScreenWebPage().getPasswordField().getValue();
    }

    public static  String getEmailErrorMessage() {
        return new VNextBOLoginScreenWebPage().getEmailErrorText().getText();
    }

    public static  String getPasswordErrorMessage() {
        return new VNextBOLoginScreenWebPage().getPasswordErrorText().getText();
    }

    public static  void setEmailField(String username) {
        Utils.clearAndType(new VNextBOLoginScreenWebPage().getEmailField().getWrappedElement(), username);
    }

    public static  void setPasswordField(String userPsw) {
        Utils.clearAndType(new VNextBOLoginScreenWebPage().getPasswordField().getWrappedElement(), userPsw);
    }
}

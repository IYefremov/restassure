package com.cyberiansoft.test.bo.webelements;

public interface TextField extends IWebElement {
    void typeValue(String value);

    void clear();

    void clearAndType(String value);
    
    String getValue();
    
    void click();

    void sendKeys(CharSequence... var1);
}

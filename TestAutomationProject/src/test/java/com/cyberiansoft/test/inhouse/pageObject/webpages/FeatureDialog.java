package com.cyberiansoft.test.inhouse.pageObject.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class FeatureDialog extends BasePage {

    FeatureDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public abstract FeatureDialog typeFeatureName(String featureName);

    public abstract FeatureDialog selectFeatureState(String featureStateOption);

    public abstract FeatureDialog typeMarketingInfo(String marketingInfoValue);

    public abstract FeatureDialog typeDescription(String descriptionValue);
}
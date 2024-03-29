package com.cyberiansoft.test.dataclasses.inHouseTeamPortal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class TeamPortalPricingData {

    @JsonProperty("editionName")
    private String editionName;

    @JsonProperty("price")
    private String price;

    @JsonProperty("minCommitments")
    private List<String> minCommitments;

    @JsonProperty("prices")
    private List<String> prices;

    @JsonProperty("featureGroupName")
    private String featureGroupName;

    @JsonProperty("featureGroupState")
    private String featureGroupState;

    @JsonProperty("featureGroupMarketingInfo")
    private String featureGroupMarketingInfo;

    @JsonProperty("featureGroupNameEdited")
    private String featureGroupNameEdited;

    @JsonProperty("featureGroupStateEdited")
    private String featureGroupStateEdited;

    @JsonProperty("featureGroupMarketingInfoEdited")
    private String featureGroupMarketingInfoEdited;

    @JsonProperty("featureState")
    private String featureState;

    @JsonProperty("setupFeeName")
    private String setupFeeName;

    @JsonProperty("setupFeeQuantity")
    private String setupFeeQuantity;

    @JsonProperty("setupFeePrice")
    private String setupFeePrice;

    @JsonProperty("setupFeeEstimatedHours")
    private String setupFeeEstimatedHours;

    @JsonProperty("setupFeeName2")
    private String setupFeeName2;

    @JsonProperty("setupFeeQuantity2")
    private String setupFeeQuantity2;

    @JsonProperty("setupFeePrice2")
    private String setupFeePrice2;

    @JsonProperty("setupFeeEstimatedHours2")
    private String setupFeeEstimatedHours2;

    @JsonProperty("featureName")
    private String featureName;

    @JsonProperty("marketingInfo")
    private String marketingInfo;

    @JsonProperty("featureDescription")
    private String featureDescription;

    @JsonProperty("pricePerMonth")
    private String pricePerMonth;

    @JsonProperty("pricePerYear")
    private String pricePerYear;

    @JsonProperty("featureNames")
    private List<String> featureNames;

    @JsonProperty("featureDescriptions")
    private List<String> featureDescriptions;

    @JsonProperty("featureMarketingInfoList")
    private List<String> featureMarketingInfoList;

    @JsonProperty("featureStates")
    private List<String> featureStates;

    @JsonProperty("marketingInfoOptions")
    private List<String> marketingInfoOptions;

    public List<String> getMinCommitments() {
        return minCommitments;
    }

    public List<String> getPrices() {
        return prices;
    }

    public String getPrice() {
        return price;
    }

    public String getEditionName() {
        return editionName;
    }

    public String getFeatureGroupName() {
        return featureGroupName;
    }

    public String getFeatureGroupState() {
        return featureGroupState;
    }

    public String getFeatureGroupMarketingInfo() {
        return featureGroupMarketingInfo;
    }

    public String getFeatureGroupNameEdited() {
        return featureGroupNameEdited;
    }

    public String getFeatureGroupStateEdited() {
        return featureGroupStateEdited;
    }

    public String getFeatureGroupMarketingInfoEdited() {
        return featureGroupMarketingInfoEdited;
    }

    public String getFeatureState() {
        return featureState;
    }

    public String getSetupFeeName() {
        return setupFeeName;
    }

    public String getSetupFeeQuantity() {
        return setupFeeQuantity;
    }

    public String getSetupFeePrice() {
        return setupFeePrice;
    }

    public String getSetupFeeEstimatedHours() {
        return setupFeeEstimatedHours;
    }

    public String getSetupFeeName2() {
        return setupFeeName2;
    }

    public String getSetupFeeQuantity2() {
        return setupFeeQuantity2;
    }

    public String getSetupFeePrice2() {
        return setupFeePrice2;
    }

    public String getSetupFeeEstimatedHours2() {
        return setupFeeEstimatedHours2;
    }

    public String getMarketingInfo() {
        return marketingInfo;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public String getFeatureName() {
        return featureName;
    }

    public String getPricePerMonth() {
        return pricePerMonth;
    }

    public String getPricePerYear() {
        return pricePerYear;
    }

    public List<String> getSetupFeeData() {
        return Arrays.asList(setupFeeName, setupFeeQuantity, setupFeePrice, setupFeeEstimatedHours);
    }

    public List<String> getSetupFeeData2() {
        return Arrays.asList(setupFeeName2, setupFeeQuantity2, setupFeePrice2, setupFeeEstimatedHours2);
    }

    public List<String> getFeatureNames() {
        return featureNames;
    }

    public List<String> getFeatureDescriptions() {
        return featureDescriptions;
    }

    public List<String> getFeatureMarketingInfoList() {
        return featureMarketingInfoList;
    }

    public List<String> getFeatureStates() {
        return featureStates;
    }

    public List<String> getMarketingInfoOptions() {
        return marketingInfoOptions;
    }
}

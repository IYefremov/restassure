package com.cyberiansoft.test.dataclasses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VisualScreenData {

    @JsonProperty("screenName")
    String screenName;

    @JsonProperty("damageData")
    DamageData damageData;

    @JsonProperty("damagesData")
    List<DamageData> damagesData;

    @JsonProperty("screenPrice")
    String screenPrice;

    @JsonProperty("screenTotalPrice")
    String screenTotalPrice;

    @JsonProperty("screenPrice2")
    String screenPrice2;

    @JsonProperty("screenTotalPrice2")
    String screenTotalPrice2;

}

package com.cyberiansoft.test.ios10_client.types.servicerequeststypes;

public enum UATServiceRequestTypes implements IServiceRequestTypes {

    SR_TYPE_ALL_PHASES("SR_type_all_phases_AQA");

    private final String srType;

    UATServiceRequestTypes(final String srType) {
        this.srType = srType;
    }

    public String getServiceRequestTypeName() {
        return srType;
    }

    public UATServiceRequestTypes getServiceRequestType(){
        for(UATServiceRequestTypes type : values()){
            if(type.getServiceRequestTypeName().equals(srType)){
                return type;
            }
        }

        throw new IllegalArgumentException(srType + " is not a valid ServiceRequestTypes");
    }
}

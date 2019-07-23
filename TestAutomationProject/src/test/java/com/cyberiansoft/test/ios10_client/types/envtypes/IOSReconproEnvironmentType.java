package com.cyberiansoft.test.ios10_client.types.envtypes;

public enum IOSReconproEnvironmentType {

    INTEGRATION("QC Environment"),
    DEVELOPMENT("Dev Environment"),
    UAT("UAT Environment");


    private final String environmentType;

    IOSReconproEnvironmentType(final String environmentType) {
        this.environmentType = environmentType;
    }

    public String getEnvironmentTypeName() {
        return environmentType;
    }

    public static IOSReconproEnvironmentType getEnvironmentType(String environmentType){
        for(IOSReconproEnvironmentType type : values()){
            if(type.getEnvironmentTypeName().toLowerCase().equals(environmentType.toLowerCase())){
                return type;
            }
        }

        throw new IllegalArgumentException(environmentType + " is not a valid Environment Type");
    }
}

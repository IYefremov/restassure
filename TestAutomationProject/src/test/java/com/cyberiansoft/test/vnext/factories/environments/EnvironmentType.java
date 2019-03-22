package com.cyberiansoft.test.vnext.factories.environments;

public enum EnvironmentType {
    INTEGRATION("Integration"),
    DEVELOPMENT("Development"),
    QC1("QC1");

    private final String environmentType;

    EnvironmentType(final String environmentType) {
        this.environmentType = environmentType;
    }

    public String getEnvironmentTypeName() {
        return environmentType;
    }

    public static EnvironmentType getEnvironmentType(String environmentType){
        for(EnvironmentType type : values()){
            if(type.getEnvironmentTypeName().toLowerCase().equals(environmentType.toLowerCase())){
                return type;
            }
        }

        throw new IllegalArgumentException(environmentType + " is not a valid Environment Type");
    }

}

package com.cyberiansoft.test.vnext.factories.environments;

import java.util.stream.Stream;

public enum EnvironmentType {
    INTEGRATION("Integration"),
    AUTOTESTS("AutoTests"),
    DEVELOPMENT("Development"),
    AZURE_MANHEIM_UAT("Azure Manheim UAT"),
    QC1("QC1"),
    QC4("QC4");

    private final String environmentType;

    EnvironmentType(final String environmentType) {
        this.environmentType = environmentType;
    }

    public String getEnvironmentTypeName() {
        return environmentType;
    }

    public static EnvironmentType getEnvironmentType(String environmentType) {
        return Stream.of(values())
                .filter(value -> value.getEnvironmentTypeName().equals(environmentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(environmentType + " is not a valid Environment Type"));
    }
}

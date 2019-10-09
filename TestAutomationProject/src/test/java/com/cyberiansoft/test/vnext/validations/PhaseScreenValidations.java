package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import org.testng.Assert;

public class PhaseScreenValidations {
    public static void validateServiceStatus(ServiceData serviceData) {
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).getStatus(), serviceData.getStatus());
    }

    public static void validateServiceStatus(ServiceData serviceData, ServiceStatus serviceStatus) {
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).getStatus(), serviceStatus);
    }

    public static void validatePhaseStatus(OrderPhaseDto phaseDto) {
        Assert.assertEquals(PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).getStatus(), phaseDto.getStatus());
    }

    public static void validatePhaseStatus(OrderPhaseDto phaseDto, ServiceStatus serviceStatus) {
        Assert.assertEquals(PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).getStatus(), serviceStatus.getStatus());
    }

    public static void verifyTimetrachingShoudBeStartedOnPhase(OrderPhaseDto phaseDto, Boolean shouldBeStarted) {
        Assert.assertEquals(PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).isClockIconPresent(), shouldBeStarted);
    }

    public static void verifyTimetrachingShoudBeStartedOnSerivce(ServiceData serviceData, Boolean shouldBeStarted) {
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).isClockIconPresent(), shouldBeStarted);
    }

    public static void serviceShouldHaveStartDate(ServiceData serviceData, Boolean shouldHaveStartDate) {
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).isStartDatePresent(), shouldHaveStartDate);

    }

    public static void phaseShouldHaveStartDate(OrderPhaseDto phaseDto, Boolean shouldHaveStartDate) {
        Assert.assertEquals(PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).isStartDatePresent(), shouldHaveStartDate);
    }
}

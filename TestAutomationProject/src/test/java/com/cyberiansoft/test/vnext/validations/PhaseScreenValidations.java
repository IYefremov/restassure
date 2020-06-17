package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.enums.monitor.OrderPhaseStatuses;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import org.testng.Assert;

public class PhaseScreenValidations {
    public static void validateServiceStatus(ServiceData serviceData) {
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).getStatus(), serviceData.getStatus());
    }

    public static void validateServiceStatus(ServiceData serviceData, ServiceStatus serviceStatus) {
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).getStatus().toLowerCase(), serviceStatus.getStatus().toLowerCase());
    }

    public static void validateServicePresent(ServiceData serviceData, boolean isPresent) {
        if (isPresent)
            Assert.assertTrue(PhaseScreenInteractions.isServiceExists(serviceData.getServiceName()));
        else
            Assert.assertFalse(PhaseScreenInteractions.isServiceExists(serviceData.getServiceName()));
    }

    public static void validatePhasePresent(OrderPhaseDto orderPhaseDto, boolean isPresent) {
        if (isPresent)
            Assert.assertTrue(PhaseScreenInteractions.isPhaseExists(orderPhaseDto.getPhaseName()));
        else
            Assert.assertFalse(PhaseScreenInteractions.isPhaseExists(orderPhaseDto.getPhaseName()));
    }

    public static void validateServiceNotes(ServiceData serviceData, String expectedServiceNotes) {
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).getNotes(), expectedServiceNotes);
    }

    public static void validatePhaseStatus(OrderPhaseDto phaseDto) {
        Assert.assertEquals(PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).getStatus(), phaseDto.getStatus());
    }

    public static void validatePhaseStatus(OrderPhaseDto phaseDto, ServiceStatus serviceStatus) {
        Assert.assertEquals(PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).getStatus(), serviceStatus.getStatus());
    }

    public static void validatePhaseStatus(OrderPhaseDto phaseDto, OrderPhaseStatuses orderPhaseStatuses) {
        Assert.assertEquals(PhaseScreenInteractions.getPhaseElement(phaseDto.getPhaseName()).getStatus(), orderPhaseStatuses.getValue().toUpperCase());
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

    public static void validateServiceTechnician(ServiceData serviceData) {
        BaseUtils.waitABit(2000);
        Assert.assertEquals(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()).getTechnician(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
    }

    public static void validatePhaseWorkOrderID(String expectedWorkOrderId) {
        Assert.assertEquals(PhaseScreenInteractions.getPhasesWorkOrderId(), expectedWorkOrderId);
    }

    public static void validatePhaseVINNumber(String expectedVIN) {
        Assert.assertEquals(PhaseScreenInteractions.getPhasesVINNumber(), expectedVIN);
    }

    public static void validatePhaseStockNumber(String expectedStockNumber) {
        Assert.assertEquals(PhaseScreenInteractions.getPhasesStockNumber(), expectedStockNumber);
    }

    public static void validateStartIconPresentForService(ServiceData serviceData, Boolean shouldPresent) {
        if (shouldPresent)
            Assert.assertTrue(PhaseScreenInteractions.isServiceStartIconPresent(serviceData.getServiceName()));
        else
            Assert.assertFalse(PhaseScreenInteractions.isServiceStartIconPresent(serviceData.getServiceName()));
    }
}

package com.cyberiansoft.test.targetprocessintegration.model;

import com.cyberiansoft.test.targetprocessintegration.config.TargetProcessConfig;
import com.cyberiansoft.test.targetprocessintegration.dto.TestCaseRunDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunsDTO;
import com.cyberiansoft.test.targetprocessintegration.enums.TestCaseRunStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TPIntegrationService {

    private static final String POST_REQUEST_CONTENT_TYPE = "application/json";
    private static final String ACCESS_TOKEN_ROUTE_PARAM = "access-token";
    private static final String TESTCASE_RUN_ID_ROUTE_PARAM = "testcaserun-id";
    private static final String TESTCASE_ID_ROUTE_PARAM = "testcase-id";
    private static final String TESTPLAN_ID_ROUTE_PARAM = "testplan-id";
    private static final String TARGET_PROCESS_API_URL = "https://cyb.tpondemand.com/api/v1/";
    private static final String TARGET_PROCESS_CREATE_TESTPLAN_RUN_PARAM = TARGET_PROCESS_API_URL + "TestPlanRuns?resultFormat=json&resultInclude=[Id,TestCaseRuns[Id,TestCase],TestPlanRuns[Id]]&access_token={access-token}";
    private static final String TARGET_PROCESS_GET_TESTPLAN_RUN__INFO_PARAM = TARGET_PROCESS_API_URL + "TestPlanRuns/{testcaserun-id}?resultFormat=json&resultInclude=[Id,TestCaseRuns[Id,TestCase],TestPlanRuns[Id]]&access_token={access-token}";
    private static final String TARGET_PROCESS_GET_TESTPLAN_RUN__STATUS_PARAM = TARGET_PROCESS_API_URL + "TestPlanRuns/?include=[TestCaseRuns[Status,Comment,TestCase]]&where=(TestPlan.Id%20eq%20{testplan-id})&format=json&access_token={access-token}";
    private static final String TARGET_PROCESS_SET_TESTCASE_STATUS_PARAM = TARGET_PROCESS_API_URL + "TestCaseRuns/{testcaserun-id}?access_token={access-token}";
    private static final String TARGET_PROCESS_SET_TESTCASE_AUTOMATED_FIELD = TARGET_PROCESS_API_URL + "TestCases/{testcase-id}?access_token={access-token}";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public TPIntegrationService() {

    }

    public TestCaseRunDTO setTestCaseRunStatus(String testcaseRun_Id, TestCaseRunStatus runStatus, String runComment) throws UnirestException, IOException {
        String msgs = Unirest.post(TARGET_PROCESS_SET_TESTCASE_STATUS_PARAM)
                .routeParam(TESTCASE_RUN_ID_ROUTE_PARAM, testcaseRun_Id)
                .routeParam(ACCESS_TOKEN_ROUTE_PARAM, TargetProcessConfig.getInstance().getTargetProcessToken())
                .header("accept", POST_REQUEST_CONTENT_TYPE)
                .header("Content-Type", POST_REQUEST_CONTENT_TYPE)
                .body(getSetTestRunStatusBodyRequest(runStatus, runComment))
                .asJson()
                .getBody()
                .getObject()
                .toString();
        return MAPPER.readValue(msgs, TestCaseRunDTO.class);
    }

    public TestPlanRunDTO createTestPlanRun(String testplan_Id) throws UnirestException, IOException {
        Unirest.setTimeouts(10000000, 10000000);
        String msgs = Unirest.post(TARGET_PROCESS_CREATE_TESTPLAN_RUN_PARAM)
                .routeParam(ACCESS_TOKEN_ROUTE_PARAM, "Mzc6bzRIZXc0VW1acktsNlVNeDYwUVNDUnVod2hsY250b1ljVXBZTTZOUUdsTT0=")
                .header("accept", POST_REQUEST_CONTENT_TYPE)
                .header("Content-Type", POST_REQUEST_CONTENT_TYPE)
                .body(getCreateTestPlanRunBodyRequest(testplan_Id))
                .asJson()
                .getBody()
                .getObject()
                .toString();
        return MAPPER.readValue(msgs, TestPlanRunDTO.class);
    }

    public TestPlanRunDTO getTestPlanRunInfo(String testPlanRunId) throws IOException, UnirestException {
        String msgs = Unirest.get(TARGET_PROCESS_GET_TESTPLAN_RUN__INFO_PARAM)
                .routeParam(TESTCASE_RUN_ID_ROUTE_PARAM, testPlanRunId)
                .routeParam(ACCESS_TOKEN_ROUTE_PARAM, "Mzc6bzRIZXc0VW1acktsNlVNeDYwUVNDUnVod2hsY250b1ljVXBZTTZOUUdsTT0=")
                .header("accept", POST_REQUEST_CONTENT_TYPE)
                .header("Content-Type", POST_REQUEST_CONTENT_TYPE)
                .asJson()
                .getBody()
                .getObject()
                .toString();
        return MAPPER.readValue(msgs, TestPlanRunDTO.class);
    }

    public void setTestCaseAutomatedField(String testCaseId) throws UnirestException {
        Unirest.post(TARGET_PROCESS_SET_TESTCASE_AUTOMATED_FIELD)
                .routeParam(TESTCASE_ID_ROUTE_PARAM, testCaseId)
                .routeParam(ACCESS_TOKEN_ROUTE_PARAM, "Mzc6bzRIZXc0VW1acktsNlVNeDYwUVNDUnVod2hsY250b1ljVXBZTTZOUUdsTT0=")
                .header("accept", POST_REQUEST_CONTENT_TYPE)
                .header("Content-Type", POST_REQUEST_CONTENT_TYPE)
                .body(getSetAutomatedFieldBodyRequest())
                .asJson();
    }

    public Map<String, String> testCaseToTestRunMap(TestPlanRunDTO testPlanRunDTO) {
        Map<String, String> testCaseToTestRunMap = new HashMap<>();
        testPlanRunDTO.getTestCaseRuns().getItems().forEach(testCaseRunDTO -> testCaseToTestRunMap.put(String.valueOf(testCaseRunDTO.getTestCase().getId()), String.valueOf(testCaseRunDTO.getId())));
        return testCaseToTestRunMap;
    }

    public Map<String, String> testCaseToTestRunMapRecursevley(TestPlanRunDTO testPlanRunDTO) {
        Map<String, String> testCaseToTestRunMap = new HashMap<>();
        testPlanRunDTO.getTestCaseRuns().getItems().forEach(testCaseRunDTO -> testCaseToTestRunMap.put(String.valueOf(testCaseRunDTO.getTestCase().getId()), String.valueOf(testCaseRunDTO.getId())));
        testPlanRunDTO.getTestPlanRuns().getItems().forEach(testPlanRun-> {
            try {
                testCaseToTestRunMap.putAll(this.testCaseToTestRunMapRecursevley(this.getTestPlanRunInfo(String.valueOf(testPlanRun.getId()))));
            } catch (IOException | UnirestException e) {
                e.printStackTrace();
            }
        });
        return testCaseToTestRunMap;
    }

    private String getCreateTestPlanRunBodyRequest(String testplan_Id) {
        JtwigTemplate template = JtwigTemplate.fileTemplate(new File("src/test/java/com/cyberiansoft/test/targetprocessintegration/templates/create-test-plan-run.json"));
        JtwigModel model = JtwigModel.newModel().with("testPlanId", testplan_Id);
        return template.render(model);
    }

    private String getSetTestRunStatusBodyRequest(TestCaseRunStatus runStatus, String runComment) {
        JtwigTemplate template = JtwigTemplate.fileTemplate(new File("src/test/java/com/cyberiansoft/test/targetprocessintegration/templates/set-test-run-status.json"));
        JtwigModel model = JtwigModel.newModel()
                .with("runStatus", runStatus.getRunStatus())
                .with("comment", runComment);
        return template.render(model);
    }

    private String getSetAutomatedFieldBodyRequest() {
        JtwigTemplate template = JtwigTemplate.fileTemplate(new File("src/test/java/com/cyberiansoft/test/targetprocessintegration/templates/set-test-case-automated-field.json"));
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }

    public TestPlanRunsDTO getTestPlanRunStatuses(String testplan_Id) throws UnirestException, IOException {
        Unirest.setTimeouts(10000000, 10000000);
        String msgs = Unirest.get(TARGET_PROCESS_GET_TESTPLAN_RUN__STATUS_PARAM)
                .routeParam(TESTPLAN_ID_ROUTE_PARAM, testplan_Id)
                .routeParam(ACCESS_TOKEN_ROUTE_PARAM, "Mzc6bzRIZXc0VW1acktsNlVNeDYwUVNDUnVod2hsY250b1ljVXBZTTZOUUdsTT0=")
                .header("accept", POST_REQUEST_CONTENT_TYPE)
                .header("Content-Type", POST_REQUEST_CONTENT_TYPE)
                .asJson()
                .getBody()
                .getObject()
                .toString();
        return MAPPER.readValue(msgs, TestPlanRunsDTO.class);
    }
}

package com.cyberiansoft.test.targetprocessintegration.model;

import com.cyberiansoft.test.targetprocessintegration.config.TargetProcessConfig;
import com.cyberiansoft.test.targetprocessintegration.dto.TestCaseRunDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunDTO;
import com.cyberiansoft.test.targetprocessintegration.enums.TestCaseRunStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.io.IOException;

public class TPIntegrationService {

    private static final String POST_REQUEST_CONTENT_TYPE = "application/json";
    private static final String ACCESS_TOKEN_ROUTE_PARAM = "aceess-token";
    private static final String TESTCASE_RUN_ID_ROUTE_PARAM = "testcaserun-id";
    private static final String TARGET_PROCESS_API_URL = "https://cyb.tpondemand.com/api/v1/";
    private static final String TARGET_PROCESS_CREATE_TESTPLAN_RUN_PARAM = TARGET_PROCESS_API_URL + "TestPlanRuns?resultFormat=json&resultInclude=[Id,TestCaseRuns[Id,TestCase]]&access_token={aceess-token}";
    private static final String TARGET_PROCESS_SET_TESTCASE_STATUS_PARAM = TARGET_PROCESS_API_URL + "TestCaseRuns/{testcaserun-id}?access_token={aceess-token}";

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
        System.out.println("+====" + getCreateTestPlanRunBodyRequest(testplan_Id));
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

    private String getCreateTestPlanRunBodyRequest(String testplan_Id) {
        JtwigTemplate template = JtwigTemplate.fileTemplate(new File("src/test/java/com/cyberiansoft/test/targetprocessintegration/templates/create-test-plan-run.json"));
        JtwigModel model = JtwigModel.newModel().with("testPlanId", testplan_Id);
        return template.render(model);
    }

    private String getSetTestRunStatusBodyRequest(TestCaseRunStatus runStatus, String runComment) {
        System.out.println("+====" + runStatus.getRunStatus());
        JtwigTemplate template = JtwigTemplate.fileTemplate(new File("src/test/java/com/cyberiansoft/test/targetprocessintegration/templates/set-test-run-status.json"));
        JtwigModel model = JtwigModel.newModel()
                .with("runStatus", runStatus.getRunStatus())
                .with("comment", runComment);
        return template.render(model);
    }

}




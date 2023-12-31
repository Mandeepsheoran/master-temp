package org.test.testrail;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.AccessException;
import java.util.HashMap;
import java.util.Map;

import org.igt.configfactory.TestRailFactory;
import org.igt.utils.TestRailSetUp;
import org.json.simple.JSONObject;
import org.test.annotationinterface.TestRailAnnotation;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

public class TestRailIntegrationWebTest {

	public final int TEST_CASE_PASSED_STATUS = 1;
	public final int TEST_CASE_FAILED_STATUS = 5;
	 protected String testCaseId;
	APIClient client = null;

	/**
	@BeforeSuite
	public void initTestRailSetup(ITestContext ctx) throws IOException, AccessException, APIException {
		String PROJECT_ID = "1";
		client = new APIClient(TestRailFactory.getConfig().testrailurl());
		client.setUser(TestRailFactory.getConfig().testrailusername());
		client.setPassword(TestRailFactory.getConfig().testrailpassword());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("include_all", true);
		data.put("name", "Test Run " + System.currentTimeMillis());
		// data.put("name", "23"); //Hardcoded Test Run ID
		JSONObject c = null;
		c = (JSONObject) client.sendPost("add_run/" + PROJECT_ID, data);
		System.out.print("Data is sent for test run creation and output is :" + c);
		Long suite_id = (Long) c.get("id");
		System.out.print("Suite id is : " + suite_id);
		ctx.setAttribute("suiteId", suite_id);
	}
	**/

	/**
	@BeforeMethod
	public void preTestRunSetup(ITestContext ctx, Method method, ITestResult result) throws NoSuchMethodException {
		System.out.print("before method in test class started");
		Method m = result.getMethod().getConstructorOrMethod().getMethod();
		if (m.isAnnotationPresent(TestRailAnnotation.class)) {
			TestRailAnnotation ta = m.getAnnotation(TestRailAnnotation.class);
			System.out.println("Test case is: " + ta.id());
			ctx.setAttribute("caseId", ta.id());
		}
	}
	**/

	@Test(description = "Push test to TestRail")
	@TestRailAnnotation(id = "1")
	public void  getRoyalAirPageTitle() {
		 testCaseId = "1";
		Assert.assertTrue(true);
	}

	@Test(description = "Push test to TestRail")
	@TestRailAnnotation(id = "2")
	public void executeroyalAirLoginFlow() {
		 testCaseId = "2";
		Assert.assertTrue(true);
	}
	
	@Test(description = "Push test to TestRail")
	@TestRailAnnotation(id = "3")
	public void  verifySupportEmailAddress() {
		 testCaseId = "3";
		Assert.assertTrue(true);
	}
	
	@Test(description = "Push test to TestRail")
	@TestRailAnnotation(id = "4")
	public void verifySupportPhoneNo() {
		 testCaseId = "4";
		Assert.assertTrue(true);
	}

	@AfterMethod
	public void postTestSteup(ITestResult result, ITestContext ctx) throws IOException, APIException {
	/**	Map<String, Object> data = new HashMap<String, Object>();
		if (result.isSuccess()) {
			data.put("status_id", TEST_CASE_PASSED_STATUS);
		} else {
			data.put("status_id", TEST_CASE_FAILED_STATUS);
			data.put("comment", result.getThrowable().toString());
		}
		String caseId = (String) ctx.getAttribute("caseId");
		Long suiteId = (Long) ctx.getAttribute("suiteId");
		System.out.println("Case Id is: " + caseId + " and suiteID is: " + suiteId);
		client.sendPost("add_result_for_case/" + suiteId + "/" + caseId, data);
		**/
		
		 if (result.getStatus() == ITestResult.SUCCESS) {
			 TestRailSetUp.addResultForTestCase(testCaseId, TestRailSetUp.TEST_CASE_PASS_STATUS, "");
	        } else if (result.getStatus() == ITestResult.FAILURE) {
	        	TestRailSetUp.addResultForTestCase(testCaseId, TestRailSetUp.TEST_CASE_FAIL_STATUS, String.valueOf(result.getThrowable()));

	        }
	}
}

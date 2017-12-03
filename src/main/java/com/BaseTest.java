package com;

import com.pages.PageController;
import com.relevantcodes.extentreports.LogStatus;
import com.utils.TestData;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.common.Helper;
import com.parallel.ParallelTest;
import com.report.ExtentManager;
import com.report.ExtentTestManager;
import com.utils.AutomationConfig;

import java.io.*;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class BaseTest extends ParallelTest{

    public Helper helper;
    public PageController page;


    @BeforeClass
    public void testClassSetUp() {
        System.out.println("Test Class Setup " + getClass().getName() + " on threadID: " + Thread.currentThread().getId());
        System.out.println(getClass().getName());
        startExtentReport(getClass().getName(), getClass().getMethods().toString(), AutomationConfig.BROWSER);
    }

    @AfterClass
    public void testClassTearDown(){
        System.out.println("Test Class Teardown " + getClass().getName() + " on threadID: " + Thread.currentThread().getId());
        endExtentReport(getClass().getName(), AutomationConfig.BROWSER);
    }

    @BeforeMethod(alwaysRun = true)
    public void testCaseSetUp(Method name) throws Exception{
        System.out.println("Test Case: " + name.getName() + " setup.");
        driver = startWebDriverInParallel(AutomationConfig.BROWSER);
        initHelper();
        initPages();
        driver.get(AutomationConfig.URL);
        startLogResults(name.getName());
    }

    @AfterMethod
    public void testCaseTearDown(ITestResult result) throws Exception{
        System.out.println("Test Case: " + result.getName() + " teardown");
        endLogTestResults(result, result.getMethod().getMethodName());
        killDriverInstance();
    }

    protected void initHelper() {
        helper = new Helper(driver, testData);
    }

    protected void initPages() {
        page = new PageController(driver, helper);
    }

    public void startLogResults(String methodName) throws IOException {
        child = ExtentTestManager.extent.startTest(methodName).assignCategory(category);
        child.log(LogStatus.INFO, "Info Child 1");
        logEntries = driver.manage().logs().get(LogType.CLIENT).filter(Level.ALL).toString();
        logFile = new File(System.getProperty("user.dir") + "/report/logfiles/" + "__" + methodName +".txt");
        log_file_writer = new PrintWriter(logFile);
    }

    public void endLogTestResults(ITestResult result, String methodName) throws Exception{
        if (result.isSuccess()) {
            child.log(LogStatus.PASS,"<pre>"+"<CENTER>"+ "Test case "+ methodName +" is PASSED" + "</CENTER>"+"</pre>");

            log_file_writer.println(logEntries);
            log_file_writer.flush();
            System.out.println("Saving test log - Done.");
        }

        if (result.getStatus() == ITestResult.FAILURE) {
            helper.takeScreenshot(child, methodName, LogStatus.FAIL,"Screenshot", result.getThrowable());
            child.log(LogStatus.FAIL, "<pre>"+"<CENTER>"+"Test case "+ methodName +" is FAILED"+"</CENTER>"+ "</pre>");

            log_file_writer.println(getStackTrace(result.getThrowable()));
            log_file_writer.flush();
            System.out.println("Saving test log - Done.");
        }

        if (result.getStatus() == ITestResult.SKIP) {
            child.log(LogStatus.SKIP, "<pre>"+"<CENTER>"+"Test case "+ methodName +" is skipped!!!"+"</CENTER>"+ "</pre>");

            log_file_writer.println(logEntries);
            log_file_writer.flush();
            System.out.println("Saving test log - Done.");
        }

        ExtentManager.getExtentReports().endTest(ExtentTestManager.getTest());
        parentContext.get(Thread.currentThread().getId()).appendChild(child);
        ExtentManager.getExtentReports().flush();
    }

    public static String getStackTrace(Throwable t) {
        if(t == null) {
            return null;
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return sw.toString();
        }
    }

}

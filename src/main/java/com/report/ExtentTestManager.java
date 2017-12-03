package com.report;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;


public class ExtentTestManager {
    public static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

    public static ExtentReports extent = ExtentManager.getExtentReports();

    public synchronized static ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public synchronized static void endTest() {
        extent.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public synchronized static ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

        Map sysInfo = new HashMap();
        sysInfo.put("Selenium Java Version", "2.53.2");
        sysInfo.put("Environment", "Prod");
        sysInfo.put("TestNG Version", "6.9.10");
        extent.addSystemInfo(sysInfo);
        return test;
    }

    public synchronized static void logOutPut(String s) {
        extent.setTestRunnerOutput("<h5>" + "ClassName::" + Thread.currentThread().getStackTrace()[2].getClassName() + "*******" + "MethodName:::"
                + Thread.currentThread().getStackTrace()[2].getMethodName() + "</h5>");
        extent.setTestRunnerOutput(s);
    }
}

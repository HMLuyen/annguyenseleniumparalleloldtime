package com.parallel;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.TestListenerAdapter;
import com.configuration.TestCapacitiesConfig;
import com.report.ExtentManager;
import com.report.ExtentTestManager;
import com.utils.TestData;
import com.utils.AutomationConfig;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelTest extends TestListenerAdapter {

    public WebDriver driver;

    protected List<TestData> testDataList;

    protected Map<Long, ExtentTest> parentContext = new HashMap<Long, ExtentTest>();

    public TestData testData;

    public String testData_id;

    public ExtentTest parent;

    public String logEntries;

    public File logFile;

    public PrintWriter log_file_writer;

    public ExtentTest child;

    protected static TestCapacitiesConfig testCapacities = new TestCapacitiesConfig();

    protected ArrayList<String> testRunID_list = testCapacities.getTestIDList();

    protected final static String category = "Qui mặt bướm report";


    public ParallelTest() {
        super();
        try {

            // Create directory
            File reportFolder = new File(System.getProperty("user.dir") + "/report/");

            if (!reportFolder.exists()) {
                System.out.println("Creating directory: " + "report");
                boolean result = false;
                try {
                    reportFolder.mkdir();
                    result = true;
                } catch (SecurityException se) {
                }

                if (result) {
                    System.out.println("Report folder created");
                }
            }

            File screenshotFolder = new File(System.getProperty("user.dir") + "/report/screenshot/");

            if (screenshotFolder.exists()) {
                FileUtils.cleanDirectory(screenshotFolder);
            }

            File logFileFolder = new File(System.getProperty("user.dir") + "/report/logfiles/");

            if (logFileFolder.exists()) {
                FileUtils.cleanDirectory(logFileFolder);
            }

            for (String testRunID : testRunID_list) {
                if (!TestCapacitiesConfig.dataMapping.containsKey(testRunID)) {
                    TestCapacitiesConfig.dataMapping.put(testRunID, true);
                }
            }

            String testDataConfig = AutomationConfig.TEST_DATA;

            if (!StringUtils.isBlank(testDataConfig)) {
                testDataList = new ArrayList<TestData>();
                JSONArray dataArr = new JSONArray(testDataConfig);
                for (int i = 0; i < dataArr.length(); i++) {
                    TestData data = new TestData((JSONObject) dataArr.get(i));
                    testDataList.add(data);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize framework");
        }
    }

    private TestData getTestData() {
        if (testDataList == null)
            return null;

        for (TestData test : testDataList) {
            if (test.testRunID.equals(testData_id)) {
                return test;
            }
        }
        return null;
    }

    public static synchronized String getNextAvailableData() {
        ConcurrentHashMap<String, Boolean> dataMapping;
        ConcurrentHashMap<String, String> dataMappingThread;

        dataMapping = TestCapacitiesConfig.dataMapping;
        dataMappingThread = TestCapacitiesConfig.dataMappingThread;

        ConcurrentHashMap.KeySetView<String, Boolean> dataIDList = dataMapping.keySet();

        String threadId = String.valueOf(Thread.currentThread().getId());
        for (String dataID : dataIDList) {
            if (dataMapping.get(dataID) == true
                    && (dataMappingThread.get(dataID) == null
                    || dataMappingThread.get(dataID).equals(threadId))) {
                dataMapping.put(dataID, false);
                dataMappingThread.put(dataID, threadId);

                return dataID;
            }
        }

        return null;
    }

    public static void freeDataRow(String testRunID) {
        ConcurrentHashMap<String, Boolean> dataMapping;
        dataMapping = TestCapacitiesConfig.dataMapping;

        dataMapping.put(testRunID , true);
    }

    public synchronized WebDriver startWebDriverInParallel(String browserType){
        testData_id = getNextAvailableData();
        testData = getTestData();

        WebDriver driver = null;
        if (browserType.toLowerCase().contains("firefox")) {
            driver = new FirefoxDriver();
            return driver;
        }

        if (browserType.toLowerCase().contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", AutomationConfig.CHROME_PATH);
            driver = new ChromeDriver();
            return driver;
        }

        if (browserType.toLowerCase().contains("internet")) {
            driver = new InternetExplorerDriver();
            return driver;
        }
        return driver;
    }

    public synchronized void killDriverInstance() {
        freeDataRow(testData_id);
        System.out.println("Driver quit...");
        driver.quit();
    }

    public synchronized void startExtentReport(String className, String methodName, String browserType) {
        parent = ExtentTestManager.startTest(className).assignCategory(category);
        parentContext.put(Thread.currentThread().getId(), parent);
        parent.log(LogStatus.INFO, "<STRONG>"+"Start Test Class: " + className + " on"+" Browser:"+ " " + browserType + "</STRONG>");
    }

    public synchronized void endExtentReport(String className, String browserType){
        System.out.println();
        System.out.println("**************ClosingTestSession****************");
        parent.log(LogStatus.INFO, "<STRONG>"+"End Test Class: " + className + " on"+" Browser:"+ " " + browserType+ "</STRONG>");
        ExtentManager.getExtentReports().flush();
    }

}

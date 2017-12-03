package com.common;

import com.BaseTest;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.pages.HomePage;
import com.report.ExtentTestManager;
import com.utils.TestData;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;


public class Helper{
    public WebDriver driver;
    public TestData testData;


    public Helper(WebDriver driver, TestData testData){
        this.driver = driver;
        this.testData = testData;
    }

    public String getUsername() {
        return testData.user;
    }

    public void takeScreenshot(ExtentTest eTest, String methodName, LogStatus logStatus, String message, Throwable throwable){
        File screenshotFolder = new File(System.getProperty("user.dir") + "/report/screenshot/");

        if (screenshotFolder.exists()) {
            screenshotFolder.delete();
        }

        if (!screenshotFolder.exists()) {
            System.out.println("Creating screenshot folder: " + "screenshot");
            boolean result = false;

            try {
                screenshotFolder.mkdir();
                result = true;
            } catch (SecurityException se) {
            }

            if (result) {
                System.out.println("screenshot folder created");
            }
        }
        Calendar calobj = Calendar.getInstance();
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String screenShotPath = "screenshot/" + methodName +"__" + "time_"+ calobj.getTime()+ "_"+ message.replace(":", "") + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/report/" + screenShotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        eTest.log(LogStatus.FAIL,"<pre>" + BaseTest.getStackTrace(throwable) + "</pre>"+message+ ExtentTestManager.getTest().addScreenCapture(screenShotPath));
    }

}

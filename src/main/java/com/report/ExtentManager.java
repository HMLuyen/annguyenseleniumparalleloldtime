package com.report;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;


import java.io.File;

public class ExtentManager {
    public static ExtentReports extent;

    public synchronized static ExtentReports getExtentReports() {
        if (extent == null) {

            File reportFolder = new File(System.getProperty("user.dir") + "/report/");

            if (!reportFolder.exists()) {
                System.out.println("creating directory: " + "report");
                boolean result = false;
                try {
                    reportFolder.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                }

                if (result) {
                    System.out.println("report folder created");
                }
            }

            extent = new ExtentReports(System.getProperty("user.dir") + "/report/ExtentReport.html", NetworkMode.OFFLINE);
        }

        extent.loadConfig(new File(System.getProperty("user.dir") + "/" + "resources" + "/" + "extent-config.xml"));

        return extent;
    }
}

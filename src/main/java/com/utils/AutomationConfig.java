package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AutomationConfig {
    public static Properties prop = new Properties();

    static {
        try {
            prop.load(new FileInputStream("config.properties"));

            AutomationConfig.URL = prop.getProperty("URL");
            AutomationConfig.BROWSER = prop.getProperty("BROWSER");
            AutomationConfig.TEST_DATA = prop.getProperty("TEST_DATA");
            AutomationConfig.TEST_CAPACITIES = prop.getProperty("TEST_CAPACITIES");
            AutomationConfig.LANGUAGE_STRING = prop.getProperty("LANGUAGE");
            switch (AutomationConfig.LANGUAGE_STRING) {
                case "en":
                    AutomationConfig.LANGUAGE = Language.EN;
                    break;
                case "vi":
                    AutomationConfig.LANGUAGE = Language.VI;
                    break;
                default:
            }

            String runner = prop.getProperty("RUNNER");
            switch (runner) {
                case "distribute":
                    AutomationConfig.RUNNER = Runner.DISTRIBUTE;
                    break;
                case "com/parallel" :
                    AutomationConfig.RUNNER = Runner.PARALLEL;
                    break;
                default:
            }
            
            AutomationConfig.CHROME_PATH = prop.getProperty("CHROME_PATH");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String URL;
    public static String CHROME_PATH;
    public static String LANGUAGE_STRING;
    public static Language LANGUAGE;
    public static Runner RUNNER;
    public static String BROWSER;
    public static String TEST_DATA;
    public static String TEST_CAPACITIES;

}

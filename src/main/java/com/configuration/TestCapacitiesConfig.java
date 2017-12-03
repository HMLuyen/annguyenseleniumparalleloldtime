package com.configuration;

import com.utils.AutomationConfig;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TestCapacitiesConfig {
    public static ConcurrentHashMap<String, Boolean> dataMapping = new ConcurrentHashMap<String, Boolean>();
    public static ConcurrentHashMap<String, String> dataMappingThread = new ConcurrentHashMap<String, String>();

    ArrayList<String> testIDList = new ArrayList<>();

    public ArrayList<String> getTestIDList() {

        for (int i = 1; i <= Integer.valueOf(AutomationConfig.TEST_CAPACITIES); i++) {
            testIDList.add(String.valueOf(i));
        }

        return testIDList;
    }
}

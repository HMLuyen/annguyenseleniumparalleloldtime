package com.utils;

import org.json.JSONObject;

public class TestData {
    public String testRunID;
    public String user;
    public String pass;

    public TestData(JSONObject object) {
        testRunID = object.getString("testrun");
        user = object.getString("username");
        pass = object.getString("pass");
    }
}
